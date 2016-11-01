package com.bailian.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bailian.entity.SearchHotWords;
import com.bailian.model.ApiPromotionalGoods;
import com.bailian.model.CategoryManagement;
import com.bailian.model.CombinedHotwords;
import com.bailian.model.KeywordBlacklist;
import com.bailian.service.ApiConfigService;
import com.bailian.service.DataBusService;
import com.bailian.service.DataService;
import com.bailian.service.GoodsService;

/**
 * 将db中配置的推广商品导入redis
 * 
 * @author haojutao
 *
 */
@Service
public class DataBusServiceImpl implements DataBusService {

	@Autowired
	private GoodsService goodsService;
	@Autowired
	private ApiConfigService apiConfServcie;
	@Autowired
	private DataService dataService;

	private int day = 24 * 60 * 60;

	@Override
	public void promGoodsToReids() {
		List<ApiPromotionalGoods> promGoodsList = apiConfServcie
				.getAllPromotions();
		if (promGoodsList == null)
			return;
		HashMap<String, List<ApiPromotionalGoods>> apiGoodsMap = new HashMap<String, List<ApiPromotionalGoods>>();
		for (ApiPromotionalGoods pg : promGoodsList) {
			String api = pg.getApi();
			String chan = pg.getChannel();
			String category = pg.getCategorySid();
			List<ApiPromotionalGoods> list = null;
			String key = null;
			if (category == null || category.equals("0")
					|| category.trim().length() == 0) {
				key = api + "_" + chan;
				list = apiGoodsMap.get(key);
			} else {
				key = api + "_" + chan + "_" + category;
				list = apiGoodsMap.get(key);
			}
			if (list == null) {
				list = new ArrayList<ApiPromotionalGoods>();
				list.add(pg);
				apiGoodsMap.put(key, list);
			} else {
				list.add(pg);
			}
		}
		for (Map.Entry<String, List<ApiPromotionalGoods>> e : apiGoodsMap
				.entrySet()) {
			dataService.setPromGoods("rcmd_prom_" + e.getKey(), e.getValue(),
					day);
		}

	}

	/**
	 * 黑名单导入redis
	 */
	@Override
	public void blackListToRedis() {
		List<KeywordBlacklist> list = apiConfServcie.getAllBlackList();
		HashMap<String, String> wdMap = new HashMap<String, String>();
		for (KeywordBlacklist b : list) {
			String chan = b.getChannel();
			String pageType = b.getPageType();
			String word = b.getKeyword();
			String key = "search_black_" + chan + "_" + pageType;
			String ws = wdMap.get(key);
			if (ws == null) {
				wdMap.put(key, word.trim().toLowerCase());
			} else {
				ws += "#" + word;
				wdMap.put(key, ws);
			}
		}

		for (Map.Entry<String, String> e : wdMap.entrySet()) {
			dataService.set(e.getKey(), e.getValue(),day);
		}
	}

	@Override
	public void hotwordsToRedis() {
		List<CombinedHotwords> list = apiConfServcie.getAllHotwords();
		HashMap<String, List<CombinedHotwords>> wdMap = new HashMap<String, List<CombinedHotwords>>();
		for (CombinedHotwords c : list) {
			String chan = c.getChannel();
			String pageType = c.getPagetype();
			String key = "search_hotword_" + chan + "_" + pageType;
			Integer categoryId = c.getCategoryid();
			if (categoryId != null && categoryId != 0) {
				key = "search_hotword_" + chan + "_" + pageType + "_"
						+ categoryId;
			}
			
			
			List<CombinedHotwords> cwList = wdMap.get(key);
			if (cwList == null) {
				cwList = new ArrayList<CombinedHotwords>();
				cwList.add(c);
				wdMap.put(key, cwList);
			} else {
				cwList.add(c);
			}
		}

		for (Map.Entry<String, List<CombinedHotwords>> e : wdMap.entrySet()) {
			dataService.setCombinedHotwords(e.getKey(), e.getValue(), day);
		}

	}

	@Override
	public void promCategoriesToRedis() {
		List<CategoryManagement> allCategories = apiConfServcie
				.getAllCategories();
		HashMap<String, List<CategoryManagement>> cateMap = new HashMap<String, List<CategoryManagement>>();
		for (CategoryManagement cm : allCategories) {
			int chan = cm.getChannel();
			String api = cm.getApi();
			// chan 0 全部 ， 1 app, 2 h5,3 pc
			String key = "rcmd_prom_category_" + chan + "_" + api;
			List<CategoryManagement> cateList = cateMap.get(key);
			if (cateList == null) {
				cateList = new ArrayList<CategoryManagement>();
				cateList.add(cm);
				cateMap.put(key, cateList);
			} else {
				cateList.add(cm);
			}

		}
		
		for (Map.Entry<String, List<CategoryManagement>> e : cateMap.entrySet()) {
			dataService.setategoryManagement(e.getKey(), e.getValue(), day);
		}
	}

}
