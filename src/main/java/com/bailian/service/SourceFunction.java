package com.bailian.service;

import java.util.List;

import com.bailian.entity.ApiParameter;
import com.bailian.entity.Goods;
import com.bailian.model.RecommendApi;

public interface SourceFunction {
	
	
	List<String> get(String funName,RecommendApi recApi,ApiParameter apiParameter);
	List<String> getCategoryRandGoodsByGoodsId(RecommendApi recApi,ApiParameter apiParameter);
	List<String> getBroCategoryRandGoodsByGoodsId(RecommendApi recApi,ApiParameter apiParameter);

	
	/**
	 * 分时段热销品类下的的随机商品
	 * @param num
	 * @param chan
	 * @param from
	 * @return
	 */
	List<Goods> getRandomGoodsInHotCategory(int num,int chan,String from,ApiParameter apiParameter);
	/**
	 * 取得某品类下N个随机商品
	 * @param category
	 * @param num
	 * @return
	 */
	List<String> getNRandGoods(String categoryId,int num);
	
	/**
	 * 取得品类下热销商品
	 * TOP20
	 * @param categoryId
	 * @return
	 */
	List<String> getCategoryHotSale(String categoryId);
	
	/**
	 * Random N goods
	 * @param num
	 * @return
	 */
	List<String> getNRandGoods(int num);
	
	/**
	 * 当前10分钟热门浏览商品
	 * @param num
	 * @return
	 */
	List<Goods> getRealTimeHotGoods(int num,ApiParameter apiParameter);
	
	

}
