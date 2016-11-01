package com.bailian.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.bailian.entity.ApiParameter;
import com.bailian.entity.ApiTimer;
import com.bailian.entity.Goods;
import com.bailian.entity.RecommendationResult;
import com.bailian.model.RecommendApi;
import com.bailian.service.GoodsService;
import com.bailian.service.SourceFunction;
import com.bailian.service.UnnormalHandlerService;
import com.bailian.utils.StringUtil;

@Service
public class UnnormalHandlerServiceImpl implements UnnormalHandlerService {

	@Autowired
	private GoodsService goodsService;
	@Autowired
	SourceFunction sourceFunction;

	@Override
	public RecommendationResult handle(RecommendApi recApi,
			ApiParameter apiParameter) {
		ApiTimer apiTimer = new ApiTimer();
		apiTimer.start();
		if (recApi == null) {
			RecommendationResult recResult = new RecommendationResult(
					"api is undefined");
			return recResult;
		}
		// *******************************************************
		// 根據接口判斷必須參數
		// *******************************************************
		String keyParameter = recApi.getKeyParameter();
		if (keyParameter == null) {
			RecommendationResult recResult = new RecommendationResult(
					"Error in api parameter configuration");
			return recResult;
		} else {

			// 核心参数为goodsId
			if (apiParameter.getGoodsId() != null) {
				String schan = apiParameter.getChan();
				// pc端默认3
				int chan = 3;
				if (StringUtils.isEmpty(schan)) {
					chan = Integer.parseInt(schan.trim());
				}
				Goods goods = goodsService.get(apiParameter.getGoodsId(), chan);
				// 未登录商品
				if (goods == null) {
					int num = recApi.getNum();
					String api = apiParameter.getApi();
					// 最后替补免检同业态
					if (api.equals("trial")) {
						apiParameter.setApi("trial_free_com");
					}
					List<Goods> goodsList = sourceFunction
							.getRandomGoodsInHotCategory(num, chan, "",
									apiParameter);
					apiTimer.end();
					if(recApi.getPaging() == 1)
					{
						int pageSize = apiParameter.getpSize();
						List<Goods> pgList = null;
						if(pageSize < goodsList.size())
						{
							//1025 oom
							pgList = new ArrayList<Goods>(goodsList.subList(0, pageSize));
							goodsList.clear();
							goodsList = null;
						}else
						{
							pgList = goodsList;
						}
						RecommendationResult recResult = new RecommendationResult(
								pgList, apiTimer, "RANDOM", apiParameter);
						return recResult;
					}
					else
					{
						RecommendationResult recResult = new RecommendationResult(
								goodsList, apiTimer, "RANDOM", apiParameter);
						return recResult;
					}
					

				} else {
					return null;
				}
			}
		}
		return null;
	}

}
