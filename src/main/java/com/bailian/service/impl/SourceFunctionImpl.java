package com.bailian.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.bailian.entity.ApiParameter;
import com.bailian.entity.Goods;
import com.bailian.model.CategoryManagement;
import com.bailian.model.RecommendApi;
import com.bailian.service.DataService;
import com.bailian.service.GoodsService;
import com.bailian.service.SourceFunction;
import com.bailian.utils.StringUtil;

@Service
public class SourceFunctionImpl implements SourceFunction {

	@Autowired
	private GoodsService goodsService;
	@Autowired
	private DataService dataService;

	@Override
	public List<String> getCategoryRandGoodsByGoodsId(RecommendApi recApi,
			ApiParameter apiParameter) {
		String goodsId = apiParameter.getGoodsId();
		String schan = apiParameter.getChan();
		// pc端口無渠道
		int chan = 3;
		if (schan != null) {
			chan = Integer.parseInt(schan);
		}
		// 不存在當前商品不存在的情況，前面已處理
		Goods goods = goodsService.get(goodsId, chan);
		if (goods == null) {
			return null;
		}

		String categoryId = goods.getCategory_id();
		String goodsIds = null;
		// ***************************
		// * mp商品处理
		// **************************
		if (goods.getCom_sid() == 4000) {
			String store_sid = goods.getStore_sid();
			goodsIds = dataService.get("rcmd_cate_goods_" + store_sid + "_"
					+ categoryId);
		} else {
			goodsIds = dataService.get("rcmd_cate_goods_" + categoryId);
		}

		if (goodsIds == null) {
			return null;
		}
		List<String> gIdList = StringUtil.split(goodsIds);
		// 去除自己推荐自己

		if (gIdList != null && gIdList.contains(goodsId)) {
			gIdList.remove(goodsId);
		}
		Collections.shuffle(gIdList);

		int recNum = recApi.getNum();
		if (gIdList.size() > recNum) {
			List<String> subList = new ArrayList<String>(gIdList.subList(0, recNum));
			gIdList = null;
			return subList;

		}
		return gIdList;
	}

	@Override
	public List<String> get(String funName, RecommendApi recApi,
			ApiParameter apiParameter) {

		String schan = apiParameter.getChan();
		int chan = 3;
		if (!StringUtil.isEmpty(schan)) {
			chan = Integer.parseInt(schan);
		}

		String api = apiParameter.getApi();

		// 商品同品类下随机商品(区分自营商家)
		if (funName.equals("SOURCE_GOODS_CATEGORY_RANDM_SELECTED")) {
			return getCategoryRandGoodsByGoodsId(recApi, apiParameter);
		}
		if (funName.trim().equals("SOURCE_GOODS_BROTHER_CATEGORY_RANDOM")) {
			return getBroCategoryRandGoodsByGoodsId(recApi, apiParameter);
		}
		// 商品同品类下热销商品
		if (funName.trim().equals("SOURCE_FUN_CATEGORY_HOTSALE")) {
			String goodsId = apiParameter.getGoodsId();
			Goods goods = goodsService.get(goodsId, chan);
			if (goods == null)
				return null;
			String categoryId = goods.getCategory_id();
			// 接入中台实时数据商品出现category为空
			if (categoryId == null) {
				return null;
			}
			List<String> goodsIdList = null;
			// mp处理
			if (goods.getCom_sid() == 4000) {
				String store_sid = goods.getStore_sid();
				goodsIdList = getMPCategoryHotSale(categoryId, store_sid);
			} else// 自营
			{
				goodsIdList = getCategoryHotSale(categoryId);

			}
			// 去除自己推荐自己
			if (goodsIdList != null && goodsIdList.contains(goodsId)) {
				int index = goodsIdList.indexOf(goodsId);
				goodsIdList.remove(index);
			}
			return goodsIdList;
		}
		// App综合排序
		if (funName.trim().equals("SOURCE_FUN_CATEGORY_COMP_RANK")) {
			String goodsId = apiParameter.getGoodsId();
			Goods goods = goodsService.get(goodsId, chan);
			if (goods == null)
				return null;
			String categoryId = goods.getCategory_id();
			// 综合排序商品
			List<String> goodsIdList = getCategoryCompRank(categoryId);
			// 去除自己推荐自己
			if (goodsIdList.contains(goodsId.trim())) {
				goodsIdList.remove(goodsId.trim());
			}
			return goodsIdList;
		}
		// 兄弟类目下综合排序商品
		if (funName.trim().equals("SOURCE_FUN_BROTHER_CATEGORY_COMP_RANK")) {
			List<String> broCategoryCompRankGoods = getBroCategoryCompRankGoods(
					recApi, apiParameter);
			return broCategoryCompRankGoods;

		}

		// 未登陆配置品类
		if (funName.trim().equals("RCMD_FUN_SENSE_CATEGORY")) {
			List<String> promGoodsList = new ArrayList<String>();

			// 配置品类
			String cateRedisKey = "rcmd_prom_category_" + chan + "_" + api;
			String promCategories = dataService.get(cateRedisKey);
			long time = System.currentTimeMillis();
			if (promCategories != null) {
				List<CategoryManagement> cmList = JSON.parseArray(
						promCategories, CategoryManagement.class);
				for (CategoryManagement cm : cmList) {
					long st = cm.getStartDate().getTime();
					long et = cm.getEndDate().getTime();
					if (st < time && time < et) {
						String cateId = cm.getCategorySid();
						int num = cm.getGoodsNumDisplay();
						List<String> goodsList = getNRandGoods(cateId, num);
						if (goodsList != null) {
							promGoodsList.addAll(goodsList);
						}
						goodsList = null;
					}
				}
				//1025 oom
				cmList = null;
			}
			Collections.shuffle(promGoodsList);
			String categoryIds = "101744#101766#101914#101920#101926#101928#101950#101963#101964#102001#102010#"
					+ "102682#102713#102822#102838#102839#102852102868#102869#103015#103181#103209#103235#103250#103919#105144#105543#105544#105545#105546#105548#105549#105550";
			List<String> categoryList = StringUtil.split(categoryIds);
			Collections.shuffle(categoryList);
			List<String> resList = new ArrayList<String>();
			for (String cId : categoryList) {
				List<String> goodsList = getNRandGoods(cId, 2);
				if (goodsList == null)
					continue;
				resList.addAll(goodsList);
				if(resList.size() > 100)
				{
					break;
				}
				goodsList = null;
			}
			//1025 oom
			categoryList = null;
			Collections.shuffle(resList);
			// 配置优先
			promGoodsList.addAll(resList);
			//1025 oom
			resList.clear();
			resList = null;
			return promGoodsList;
		}
		// 用户偏好品类浏览记录推荐
		if (funName.trim().equals("SOURCE_FUN_USER_PREF")) {
			return getUserPrefCategoryGoods(recApi, apiParameter);
		}// 用户浏览记录
		if (funName.trim().equals("RCMD_FUN_HISTGL")) {
			return getHistGL(recApi, apiParameter);
		}
		// 购物车猜你喜欢
		if (funName.trim().equals("RCMD_FUN_GOODS_BOUGHT_TOGETHER")) {
			return getCartRecGoods(recApi, apiParameter);
		}
		// 试用频道业态新品推荐
		if (funName.trim().equals("RCMD_FUN_COM_CATEGORY_NEW_GOODS")) {
			return getComCateNewGoods(recApi, apiParameter);
		}
		// App用户浏览记录100
		if (funName.trim().equals("SOURCE_FUN_BROWSING_HISTORY")) {
			String mId = apiParameter.getMemberId();

			Map<String, String> map = dataService
					.hgetAll("rcmd_rt_view_" + mId);
			if (map == null)
				return null;
			TreeMap<Long, String> treeMap = new TreeMap<Long, String>(
					new Comparator<Long>() {

						@Override
						public int compare(Long o1, Long o2) {
							return (int) (o2 - o1);
						}
					});
			for (Map.Entry<String, String> e : map.entrySet()) {
				try {
					Long datetime = Long.parseLong(e.getValue());
					treeMap.put(datetime, e.getKey());
				} catch (Exception ex) {
					continue;
				}
			}
			map = null;
			Collection<String> values = treeMap.values();
			List<String> goodsIdList = new ArrayList<String>(values);
			values = null;
			return goodsIdList;

		}

		return null;
	}

	@Override
	public List<String> getBroCategoryRandGoodsByGoodsId(RecommendApi recApi,
			ApiParameter apiParameter) {
		String goodsId = apiParameter.getGoodsId();
		int chan = 3;
		String schan = apiParameter.getChan();
		if (!StringUtil.isEmpty(schan)) {
			chan = Integer.parseInt(schan);
		}
		Goods goods = goodsService.get(goodsId, chan);
		//1025
		if(goods == null)
			return null;
		String categoryId = goods.getCategory_id();
		String rootCategoryId = dataService.get("rcmd_parent_category_"
				+ categoryId);
		String broCategoryIds = dataService.get("rcmd_subcategory_"
				+ rootCategoryId);
		List<String> broIdList = null;
		if (broCategoryIds == null) {
			return null;
		}
		broIdList = StringUtil.split(broCategoryIds);

		List<String> gIdList = new ArrayList<String>();
		for (String cId : broIdList) {
			List<String> cgList;
			if (goods.getCom_sid() == 4000) {
				String store_sid = goods.getStore_sid();
				cgList = getNRandGoodsWithMP(cId, store_sid, 10);
			} else {
				cgList = getNRandGoods(cId, 10);
			}
			if (cgList != null) {
				gIdList.addAll(cgList);
				cgList = null;
			}
		}
		broIdList = null;
		
		// mp 所有商品补位
		int rnum = recApi.getNum();
		if (gIdList != null && gIdList.size() < rnum) {
			if (goods.getCom_sid() == 4000) {
				String store_sid = goods.getStore_sid();
				String redisKey = "rcmd_store_goods_" + store_sid;
				String gIds = dataService.get(redisKey);
				if (gIds != null) {
					List<String> list = StringUtil.split(gIds);
					gIdList.addAll(list);
				}
			}
		}
		int recNum = recApi.getNum();
		if (gIdList.size() > recNum) {
			List<String> subList = new ArrayList<String>(gIdList.subList(0, recNum));
			gIdList = null;
			return subList;
		}
		return gIdList;
	}

	/**
	 * 
	 */
	public List<String> getBroCategoryCompRankGoods(RecommendApi recApi,
			ApiParameter apiParameter) {
		String goodsId = apiParameter.getGoodsId();
		int chan = 3;
		String schan = apiParameter.getChan();
		if (!StringUtil.isEmpty(schan)) {
			chan = Integer.parseInt(schan);
		}
		Goods goods = goodsService.get(goodsId, chan);
		String categoryId = goods.getCategory_id();
		String rootCategoryId = dataService.get("rcmd_parent_category_"
				+ categoryId);
		String broCategoryIds = dataService.get("rcmd_subcategory_"
				+ rootCategoryId);
		List<String> broIdList = null;
		if (broCategoryIds == null) {
			return null;
		}
		broIdList = StringUtil.split(broCategoryIds);
		List<String> gIdList = new ArrayList<String>();
		for (String cId : broIdList) {
			List<String> cgList = getCategoryCompRank(cId);
			if (cgList != null) {
				gIdList.addAll(cgList);
			}
		}
		broIdList = null;
		return gIdList;
	}

	int DateCompare(String source, String traget, String type) throws Exception {
		int ret = 2;
		SimpleDateFormat format = new SimpleDateFormat(type);
		Date sourcedate = format.parse(source);
		Date tragetdate = format.parse(traget);
		ret = sourcedate.compareTo(tragetdate);
		return ret;
	}

	@Override
	public List<Goods> getRandomGoodsInHotCategory(int num, int chan,
			String from,ApiParameter apiParameter) {
		String nowTime = new SimpleDateFormat("hh:mm").format(new Date());
		String dateKey = "rcmd_topcategory_forenoon";

		try {
			int i = DateCompare(nowTime, "12:00", "hh:mm");
			int j = DateCompare(nowTime, "18:00", "hh:mm");
			if (i == -1) {
				dateKey = "rcmd_topcategory_forenoon";
			} else if (i == 1 && j == -1) {
				dateKey = "rcmd_topcategory_forenoon";
			} else if (i == 1 && j == 1) {
				dateKey = "rcmd_topcategory_evening";
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String categoryIds = dataService.get(dateKey);
		List<String> categoryIdList = StringUtil.split(categoryIds);
		List<String> gIdList = new ArrayList<String>();
		for (String cId : categoryIdList) {
			List<String> cgList = getNRandGoods(cId, 2);
			if (cgList != null) {
				gIdList.addAll(cgList);
			}
			if (gIdList.size() > num) {
				break;
			}
		}
		from = "RAND_GOODS_IN_HOT_CATEGORY";
		List<Goods> goodsList = goodsService.forSaleFilter(gIdList, chan, from,apiParameter);
		if(goodsList == null)
			return null;
		if (goodsList.size() > num) {
			List<Goods> tmpList = new ArrayList<Goods>(goodsList.subList(0, num));
			goodsList = null;
			return tmpList;
		} else {
			return goodsList;
		}
	}

	/**
	 * 商家品类下随机商品
	 * 
	 * @param categoryId
	 * @param store_sid
	 * @param num
	 * @return
	 */
	public List<String> getNRandGoodsWithMP(String categoryId,
			String store_sid, int num) {
		String goodsIds = dataService.get("rcmd_cate_goods_" + store_sid + "_"
				+ categoryId);
		if (StringUtil.isEmpty(goodsIds))
			return null;
		List<String> gIdList = StringUtil.split(goodsIds);
		Collections.shuffle(gIdList);
		if (gIdList.size() > num) {
			List <String> retList = new ArrayList<String>(gIdList.subList(0, num));
			gIdList = null;
			return retList;
		} else {
			return gIdList;
		}
	}

	@Override
	public List<String> getNRandGoods(String categoryId, int num) {
		String goodsIds = dataService.get("rcmd_cate_goods_" + categoryId);
		if (StringUtil.isEmpty(goodsIds))
			return null;
		List<String> gIdList = StringUtil.split(goodsIds);
		Collections.shuffle(gIdList);
		if (gIdList.size() > num) {
			List<String> tmpList = new ArrayList<String>(gIdList.subList(0, num));
			gIdList = null;
			return tmpList;
		} else {
			return gIdList;
		}
	}

	/**
	 * 随机取得N个商品
	 * 
	 * @param num
	 * @return
	 */
	@Override
	public List<String> getNRandGoods(int num) {
		String nowTime = new SimpleDateFormat("hh:mm").format(new Date());
		String dateKey = "rcmd_topcategory_forenoon";

		try {
			int i = DateCompare(nowTime, "12:00", "hh:mm");
			int j = DateCompare(nowTime, "18:00", "hh:mm");
			if (i == -1) {
				dateKey = "rcmd_topcategory_forenoon";
			} else if (i == 1 && j == -1) {
				dateKey = "rcmd_topcategory_forenoon";
			} else if (i == 1 && j == 1) {
				dateKey = "rcmd_topcategory_evening";
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		String categoryIds = dataService.get(dateKey);
		List<String> categoryIdList = StringUtil.split(categoryIds);
		List<String> gIdList = new ArrayList<String>();
		for (String cId : categoryIdList) {
			List<String> cgList = getNRandGoods(cId, 2);
			if (cgList != null) {
				gIdList.addAll(cgList);
				//1025 oom
				cgList.clear();
				cgList = null;
			}
			if (gIdList.size() > num) {
				break;
			}
		}
		//1025 oom
		categoryIdList.clear();
		categoryIdList = null;
		return gIdList;

	}

	public List<String> getMPCategoryHotSale(String categoryId, String store_sid) {
		String goodsIds = dataService.get("rcmd_cate_hotsale_" + store_sid
				+ "_" + categoryId);
		if (goodsIds == null) {
			return null;
		}
		List<String> gIdList = StringUtil.split(goodsIds);
		return gIdList;
	}

	@Override
	public List<String> getCategoryHotSale(String categoryId) {
		String goodsIds = dataService.get("rcmd_cate_hotsale_" + categoryId);
		if (goodsIds == null) {
			return null;
		}
		List<String> gIdList = StringUtil.split(goodsIds);
		return gIdList;
	}

	/**
	 * 获取品类综合排序
	 */
	List<String> getCategoryCompRank(String categoryId) {
		String goodsIds = dataService.get("rcmd_goods_comp_ranking_"
				+ categoryId);
		if (goodsIds == null) {
			return null;
		}
		List<String> gIdList = StringUtil.split(goodsIds);
		return gIdList;
	}

	/**
	 * 
	 * @param recApi
	 * @param apiParameter
	 * @return
	 */
	public List<String> getUserPrefCategoryGoods(RecommendApi recApi,
			ApiParameter apiParameter) {
		String memberId = apiParameter.getMemberId();
		if(StringUtil.isEmpty(memberId))
		{
			return null;
		}
		String prefCategory = dataService.get("cate_pref_" + memberId);
		if (prefCategory == null)
			return null;
		List<String> categoryIdList = StringUtil
				.splitItemsWithWight(prefCategory);
		List<String> subList = categoryIdList;
		if (categoryIdList != null && categoryIdList.size() > 30) {
			subList = new ArrayList<String>(categoryIdList.subList(0, 30));
			//1025 oom
			categoryIdList.clear();
			categoryIdList = null;
		}
		
		//1025 oom
		List<String> viewList = new ArrayList<String>();

		String pref_goods = dataService.get("rcmd_goods_pref_" + memberId);
		List<String> cateGoodsList = null;
		if (pref_goods != null) {
			cateGoodsList = StringUtil.split(pref_goods);
		}
		// 每个品类取两个商品
		if (cateGoodsList == null) {
			return null;
		}
		for (String cate : cateGoodsList) {
			String viewCategory = cate.split(":")[0];
			if (subList.contains(viewCategory)) {
				String[] gsArray = cate.split(":")[1].split(",");
				viewList.add(gsArray[0]);
				if (gsArray.length >= 2) {
					viewList.add(gsArray[1]);
				}
				//1025 oom
				gsArray = null; 
			}
		}
		//1025 oom
		cateGoodsList.clear();
		cateGoodsList = null;
		subList.clear();
		subList = null;
		List<String> resList = new ArrayList<String>();

		// 根据浏览记录
		for (String gId : viewList) {
			List<String> tmpList = getGoodsViewedTogether(gId, 2);
			if (tmpList == null) {
				tmpList = getSimiliarGoods(gId, 2);
			}
			if (tmpList != null) {
				resList.addAll(tmpList);
			}
		}
		
		//1025 oom
		viewList.clear();
		viewList = null;
		Collections.shuffle(resList);
		return resList;
	}

	/**
	 * 相似商品
	 * 
	 * @param goodsId
	 * @param num
	 * @return
	 */
	public List<String> getSimiliarGoods(String goodsId, int num) {
		String gIds = dataService.get("rcmd_sim_" + goodsId);
		if (gIds == null) {
			return null;
		} else {
			List<String> gIdList = StringUtil.split(gIds);
			if (gIdList.size() >= num) {
				//1025 oom
				List<String> retList = new ArrayList<String>(gIdList.subList(0, num));
				gIdList.clear();
				gIdList = null;
				return retList;
			}

			return gIdList;
		}
	}

	/**
	 * 
	 * @param goodsId
	 * @param recApi
	 * @param apiParameter
	 * @return
	 */
	public List<String> getComCateNewGoods(RecommendApi recApi,
			ApiParameter apiParameter) {
		int chan = 3;
		String schan = apiParameter.getChan();
		if (!StringUtil.isEmpty(schan)) {
			chan = Integer.parseInt(schan);
		}
		String goodsId = apiParameter.getGoodsId();
		Goods goods = goodsService.get(goodsId, chan);
		if (goods == null)
			return null;

		String categoryId = goods.getCategory_id();
		// 实时数据可能categoryid为空
		if (categoryId == null) {
			return null;
		}
		String comNewGoods = null;
		// 逻辑同业态一级类目下30天新品
		int comSid = goods.getCom_sid();
		// 商家商品
		if (goods.getCom_sid() == 4000) {
			String store_sid = goods.getStore_sid();
			comNewGoods = dataService.get("rcmd_cate_goods_" + store_sid + "_"
					+ categoryId);
		} else {
			String redCateKey = "rcmd_category_tree_" + categoryId;
			Map<String, String> ct = dataService.hgetAll(redCateKey);
			String lev1Id = ct.get("1");
			if (lev1Id == null)
				return null;

			String redComKey = "rcmd_com_trail_" + comSid + "_" + lev1Id;
			comNewGoods = dataService.get(redComKey);
		}
		if (comNewGoods == null) {
			return null;
		}
		List<String> comGoodsList = StringUtil.split(comNewGoods);
		Collections.shuffle(comGoodsList);
		return comGoodsList;

	}

	/**
	 * 同时购买品类
	 * 
	 * @param goodsId
	 * @param num
	 * @return
	 */
	public List<String> getCategoryBoughtTogether(String goodsId,
			RecommendApi recApi, ApiParameter apiParameter) {
		int chan = 3;
		String schan = apiParameter.getChan();
		if (!StringUtil.isEmpty(schan)) {
			chan = Integer.parseInt(schan);
		}
		Goods goods = goodsService.get(goodsId, chan);
		if (goods == null)
			return null;

		String categoryId = goods.getCategory_id();
		// 实时数据可能categoryid为空
		if (categoryId == null) {
			return null;
		}
		String cateIds = dataService.get("rcmd_bab_category_" + categoryId);
		if (cateIds == null) {
			return null;
		} else {
			List<String> cateIdList = StringUtil.split(cateIds);
			return cateIdList;
		}
	}

	/**
	 * 同时浏览商品
	 * 
	 * @param goodsId
	 * @param num
	 * @return
	 */
	public List<String> getGoodsViewedTogether(String goodsId, int num) {
		String gIds = dataService.get("rcmd_view_" + goodsId);
		if (gIds == null) {
			return null;
		} else {
			List<String> gIdList = StringUtil.split(gIds);
			if (gIdList.size() >= num) {
				//1025 oom
				List<String> retGoodsIdList = new ArrayList<String>(gIdList.subList(0, num));
				gIdList.clear();
				gIdList = null;
				return retGoodsIdList;
			}

			return gIdList;
		}

	}

	/**
	 * 商品买了还买
	 * 
	 * @param goodsId
	 * @param num
	 * @return
	 */

	public List<String> getGoodsBoughtTogether(List<String> cartGoodsList,
			String goodsId, int num) {
		String gIds = dataService.get("rcmd_bab_goods_" + goodsId);
		if (gIds == null) {
			return null;
		} else {
			List<String> gIdList = StringUtil.split(gIds);
			// 去除购物车
			for (String gId : cartGoodsList) {
				gIdList.remove(gId);
			}
			if (gIdList.size() >= num) {
				//1025 oom
				List<String> retGoodsIdList = new ArrayList<String>(gIdList.subList(0, num));
				gIdList.clear();
				gIdList = null;
				return retGoodsIdList;
			}
			return gIdList;
		}
	}

	/**
	 * 购物车推荐
	 * 
	 * @param recApi
	 * @param apiParameter
	 * @return
	 */
	public List<String> getCartRecGoods(RecommendApi recApi,
			ApiParameter apiParameter) {
		String items = apiParameter.getItems();
		// 如果无ITEM参数，随机返回
		int num = recApi.getNum();
		if (num == 0) {
			num = 16;
		}
		if (StringUtil.isEmpty(items)) {
			return getNRandGoods(num);
		}
		String[] itemArray = items.split(",");
		List<String> gIdList = new ArrayList<String>();
		if (itemArray != null && itemArray.length != 0) {
			for (String si : itemArray) {
				//去重一下，避免购买两个商品传入两次
				if(!gIdList.contains(si))
				{
					gIdList.add(si);
				}
			}
		} else {
			return getNRandGoods(10);
		}
		int num_per_countpart = num;
		if (gIdList.size() > 1) {
			num_per_countpart = (int) Math.ceil(num * 1.0 / gIdList.size());
		}

		List<String> resList = new ArrayList<String>();
		List<String> cateList = new ArrayList<String>();
		for (String gId : gIdList) {
			List<String> tmpList;
			// 同时购买商品
			tmpList = getGoodsBoughtTogether(gIdList, gId, num_per_countpart);
			if (tmpList == null|| tmpList.size() < num_per_countpart) {
				// 同时购买品类
				List<String> tmpCateList = getCategoryBoughtTogether(gId,
						recApi, apiParameter);
				if (tmpCateList == null || tmpCateList.size() == 0) {
					continue;
				}
				int cateCnt = 0;
				for (String cateId : tmpCateList) {
					if (!cateList.contains(cateId)) {
						cateList.add(cateId);
						cateCnt++;
						if (cateCnt > 2) {
							break;
						}
					}
				}
			} else {
				for (String id : tmpList) {
					if (!resList.contains(id)) {
						resList.add(id);
					}
				}
			}

		}

		// 对于无同时购买
		int goodsNum = resList.size();
		int catesNum = cateList.size();

		if (goodsNum < num && catesNum != 0) {
			int cateGoodsNum = (int) Math.ceil((num - goodsNum) * 1.0
					/ catesNum);
			for (String cateId : cateList) {
				int cnt = 0;
				List<String> cateGoodsList = getCategoryHotSale(cateId);
				if (cateGoodsList != null && cateGoodsList.size() != 0) {
					for (String gId : cateGoodsList) {
						if (!gIdList.contains(gId) && !resList.contains(gId)) {

							resList.add(gId);
							cnt++;
							if (cnt + 1 > cateGoodsNum) {
								break;
							}
						}
					}
				}
			}
		}
		Collections.shuffle(resList);
		
		//1025 oom
		gIdList.clear();
		gIdList = null;
		cateList.clear();
		cateList = null;
		
		return resList;
	}

	/**
	 * pc端根据浏览记录猜你喜欢
	 * 
	 * @param recApi
	 * @param apiParameter
	 * @return
	 */
	public List<String> getHistGL(RecommendApi recApi, ApiParameter apiParameter) {
		String items = apiParameter.getItems();
		// 如果无ITEM参数，随机返回
		if (StringUtil.isEmpty(items)) {
			return getNRandGoods(10);
		}
		String[] itemArray = items.split(",");
		List<String> gIdList = new ArrayList<String>();
		if (itemArray != null && itemArray.length != 0) {
			for (String si : itemArray) {
				gIdList.add(si);
			}
		} else {
			return getNRandGoods(10);
		}
		int num_per_countpart = 5;
		if (gIdList.size() > 5) {
			num_per_countpart = (int) Math.ceil(recApi.getNum() * 1.0
					/ gIdList.size());
		}
		// 浏览记录反序
		Collections.reverse(gIdList);
		List<String> resList = new ArrayList<String>();
		for (String gId : gIdList) {
			List<String> tmpList;
			tmpList = getGoodsViewedTogether(gId, num_per_countpart);
			if (tmpList == null || tmpList.size() < num_per_countpart) {
				tmpList = getSimiliarGoods(gId, num_per_countpart);
			}
			if (tmpList == null) {
				tmpList = getNRandGoods(num_per_countpart);
			}
			
			resList.addAll(tmpList);
			//1025 oom
			tmpList.clear();
			tmpList = null;
		}

		//1025 oom
		gIdList.clear();
		gIdList = null;
		return resList;
	}

	@Override
	public List<Goods> getRealTimeHotGoods(int num, ApiParameter apiParameter) {
		String top20 = dataService.get("rcmd_top20_10min");
		List<Goods> goodsList = new ArrayList<Goods>();
		if (top20 == null)
			return null;
		List<String> ids = StringUtil.split(top20);
		if (ids == null) {
			return null;
		}
		int chan = 3;
		String schan = apiParameter.getChan();
		if (schan != null) {
			chan = Integer.parseInt(schan);
		}
		for (String id : ids) {
			if (StringUtil.isEmpty(id)) {
				continue;
			}
			Goods gd = goodsService.get(id, chan);
			if (gd != null && !StringUtil.isEmpty(gd.getSale_price())
					&& gd.getSale_status() == 4
					&& !StringUtil.isEmpty(gd.getGoods_sales_name())
					&& !StringUtil.isEmpty(gd.getUrl())) {
				gd.setFrom("real_time_hot");
				goodsList.add(gd);
			}

		}

		return goodsList;
	}

}
