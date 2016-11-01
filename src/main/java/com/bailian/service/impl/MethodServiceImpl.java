package com.bailian.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.bailian.configuration.RecommendConfiguration;
import com.bailian.entity.ApiConfiguration;
import com.bailian.entity.ApiParameter;
import com.bailian.entity.Goods;
import com.bailian.model.ApiAbtest;
import com.bailian.model.ApiPromotionalGoods;
import com.bailian.model.MethodSource;
import com.bailian.model.RecommendApi;
import com.bailian.service.DataService;
import com.bailian.service.GoodsService;
import com.bailian.service.MethodService;
import com.bailian.service.PromotionService;
import com.bailian.service.SourceDataService;
import com.bailian.service.SourceFunction;
import com.bailian.utils.StringUtil;

@Service
public class MethodServiceImpl implements MethodService {

	@Autowired
	private SourceDataService sourceServcie;
	@Autowired
	private GoodsService goodsService;
	@Autowired
	private SourceFunction sourceFunction;
	@Autowired
	private DataService dataService;
	@Autowired
	private PromotionService promService;

	@Override
	public List<Goods> getResultByMethod(RecommendApi recApi,
			ApiConfiguration apiConf, ApiAbtest method,
			ApiParameter apiParameter) {

		if (method == null)
			return null;
		List<Goods> resList = new ArrayList<Goods>();
		String methodCode = method.getMethodCode();
		// 判断缓存 后期接口配置是否缓存
		if (recApi.getApi().equals("pcgl") || recApi.getApi().equals("afpay")
				|| recApi.getApi().equals("gyl")
				|| recApi.getApi().equals("cart")) {
			String memberId = apiParameter.getMemberId();
			if (StringUtil.isEmpty(memberId)) {
				memberId = "unlogin";
			}
			String cacheKey = methodCode + "_" + memberId;
			// 购物车用户id可能为空 处理逻辑不同，缓存为了翻页商品重复
			if (recApi.getApi().equals("cart")) {
				String items = apiParameter.getItems();
				int hashCode = items.hashCode();
				cacheKey += "_" + hashCode;
				String cacheResult = dataService.get(cacheKey);
				if (cacheResult != null) {
					resList = JSON.parseArray(cacheResult, Goods.class);
					return resList;
				}
			}
			//试用缓存
			if (recApi.getApi().equals("trial")) {
				String goodsId = apiParameter.getGoodsId();
				cacheKey = methodCode + "_trial_" + goodsId;
				String cacheResult = dataService.get(cacheKey);
				if (cacheResult != null) {
					resList = JSON.parseArray(cacheResult, Goods.class);
					return resList;
				}
			}

			String cacheResult = dataService.get(cacheKey);
			if (cacheResult != null) {
				resList = JSON.parseArray(cacheResult, Goods.class);
				// 未登录情况下读取10分钟热门
				if (memberId.equals("unlogin")) {
					List<Goods> rtList = sourceFunction.getRealTimeHotGoods(20,
							apiParameter);
					if (rtList == null || rtList.size() == 0) {
						return resList;
					}
					for (Goods gd : rtList) {
						if (!resList.contains(gd)) {
							resList.add(4, gd);
							resList.remove(resList.size() - 1);
						}
					}
					rtList = null;
				}
				return resList;
			}
		}
		int chan = Integer.parseInt(apiParameter.getChan().trim());
		int recNum = recApi.getNum();
		List<MethodSource> sourceList = RecommendConfiguration.methodSourceMap
				.get(methodCode);

		for (MethodSource source : sourceList) {
			List<Goods> goodsList = null;
			String sourceCode = source.getSourceCode();
			// data類型source
			if (source.getSourceType() == 0) {
				List<String> gIdList = sourceServcie.getResBySourceCode(source,
						apiParameter);
				goodsList = goodsService.forSaleFilter(gIdList, chan,
						sourceCode , apiParameter);
			}// 函數式source
			else if (source.getSourceType() == 1) {

				List<String> gIdList = sourceFunction.get(sourceCode, recApi,
						apiParameter);
				goodsList = goodsService.forSaleFilter(gIdList, chan,
						sourceCode ,apiParameter);
			}
			if (goodsList == null || goodsList.size() == 0) {
				continue;
			}
			int maxNum = (int) Math.ceil(source.getProportion() * recNum);
			// 防止重复
			int selNum = 0;
			HashMap<String, Integer> cateGoodsCountMap = new HashMap<String, Integer>();
			for (Goods g : goodsList) {
				// 来源推荐个数控制
				if (!resList.contains(g) && selNum <= maxNum) {

					// 针对猜你喜欢和支付成功控制每个品类商品个数
					if (recApi.getApi().equals("pcgl")
							|| recApi.getApi().equals("afpay")
							|| recApi.getApi().equals("gyl")) {
						Integer count = cateGoodsCountMap.get(g
								.getCategory_id());
						if (count == null) {
							cateGoodsCountMap.put(g.getCategory_id(), 1);
						} else {
							cateGoodsCountMap
									.put(g.getCategory_id(), count + 1);
						}
						// 控制每个品类不超俩个
						if (count == null || count < 2) {
							selNum++;
							resList.add(g);
						}
					} else {
						selNum++;
						resList.add(g);
					}
				}
			}
			//1025
			cateGoodsCountMap = null;
			goodsList.clear();
			goodsList = null;
			//满足推荐商品个数终止
			if(resList.size() > recNum)
			{
				break;
			}
		}
		//1025 oom
		sourceList = null;
		
		// 筛选后为空
		if (resList.size() == 0) {
			List<String> gIdList = sourceFunction.getNRandGoods(recNum);
			resList = goodsService.forSaleFilter(gIdList, chan, "random",apiParameter);
		}
		List<Goods> rcmdList = null;
		if (resList.size() > recNum) {
			rcmdList = new ArrayList<Goods>(resList.subList(0, recNum));
			//1025 oom
			resList.clear();
			resList = null;
		}else
		{
			rcmdList = resList;
			resList = null;
		}

		// 推广商品处理
		rcmdList = promService.getPromotinalGoods(apiParameter, rcmdList);

		// 缓存
		if (recApi.getApi().equals("pcgl") || recApi.getApi().equals("afpay")
				|| recApi.getApi().equals("gyl")) {
			String memberId = apiParameter.getMemberId();
			if (StringUtil.isEmpty(memberId)) {
				memberId = "unlogin";
			}
			String cacheKey = methodCode + "_" + memberId;
			dataService.set(cacheKey, rcmdList, 2 * 60);
		}
		// 购物车缓存
		if (recApi.getApi().equals("cart")) {
			String memberId = apiParameter.getMemberId();
			if (StringUtil.isEmpty(memberId)) {
				memberId = "unlogin";
			}
			String items = apiParameter.getItems();
			int hashCode = items.hashCode();
			String cacheKey = methodCode + "_" + memberId + "_" + hashCode;
			dataService.set(cacheKey, rcmdList, 2 * 60);
		}

		// 试用频道缓存
		if (recApi.getApi().equals("trial")) {
			
			String goodsId = apiParameter.getGoodsId();
			String cacheKey = methodCode + "_trial_" + goodsId;
			dataService.set(cacheKey, rcmdList, 2 * 60);
		}
		return rcmdList;
	}

}
