package com.bailian.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.bailian.entity.ApiConfiguration;
import com.bailian.entity.ApiParameter;
import com.bailian.entity.Goods;
import com.bailian.model.ApiPromotionalGoods;
import com.bailian.service.DataService;
import com.bailian.service.GoodsService;
import com.bailian.service.PromotionService;
import com.bailian.utils.StringUtil;

@Service
public class PromotionServiceImpl implements PromotionService {

	@Autowired
	private GoodsService goodsService;
	@Autowired
	private DataService dataService;

	@Override
	public List<Goods> getPromotinalGoods(ApiParameter apiParameter,
			List<Goods> goodsList) {
		String api = apiParameter.getApi();
		int chan = 3;
		String schan = apiParameter.getChan();
		if (schan != null) {
			chan = Integer.parseInt(apiParameter.getChan());
		}
		String inputGoodsId = apiParameter.getGoodsId();
		String key = "rcmd_prom_" + api + "_" + chan;
		if (!StringUtil.isEmpty(inputGoodsId)) {
			Goods goods = goodsService.get(inputGoodsId, chan);
			if (goods != null) {
				key = "rcmd_prom_" + api + "_" + chan + "_"
						+ goods.getCategory_id();
			}
		}
		String strPromGIds = dataService.get(key);
		if (strPromGIds == null)
			return goodsList;
		List<ApiPromotionalGoods> promGoodsList = JSON.parseArray(strPromGIds,
				ApiPromotionalGoods.class);
		for (ApiPromotionalGoods pg : promGoodsList) {
			String goodsId = pg.getGoodsSid();
			/**
			 * 推广渠道和当前访问渠道一致才加入
			 */
			Goods goods = goodsService.get(goodsId, chan);
			//推广商品也有可能在redis里面查不到，这里出了个大bug
			if(goods == null || goods.getSale_status()== 0 || goods.getGoods_sales_name()== null)
			{
				continue;
			}
			int index = pg.getPosition();
			if (goods != null && goods.getGoods_sales_name() != null
					&& goods.getGoods_sales_name().length() != 0
					&& index < goodsList.size()) {
				goods.setFrom("promotion");
				// 去掉之前相同商品
				if (goodsList.contains(goods)) {
					goodsList.remove(goods);
					goodsList.add(pg.getPosition(), goods);

				} else {
					if (goods != null && goods.getGoods_sales_name() != null
							&& goods.getGoods_sales_name().length() != 0) {
						goodsList.add(pg.getPosition(), goods);
						// 去掉最后一个
						goodsList.remove(goodsList.size() - 1);
					}
				}
			}

		}
		//1025 oom
		promGoodsList.clear();
		promGoodsList = null;
		return goodsList;
	}

}
