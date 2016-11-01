package com.bailian.service;

import java.util.List;

import com.bailian.entity.ApiParameter;
import com.bailian.entity.Goods;

public interface GoodsService {
	/**
	 *取得商品信息 
	 * @param goodsId
	 * @return
	 */
	public Goods get(String goodsId,int chan);
	
	/**
	 * 
	 * @param goodsId
	 * @param chan
	 * @return
	 */
	public boolean isNew(String goodsId,int chan);
	
	
	public List<Goods> forSaleFilter(List<String> goodsList,int chan,String sourceCode,ApiParameter apiParameter);
	
	
	

}
