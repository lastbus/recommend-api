package com.bailian.dao;

import java.util.List;

import com.bailian.model.ApiPromotionalGoods;

public interface ApiPromotionalGoodsMapper {
	
	public List<ApiPromotionalGoods> getAllPromotionsByApi(String api);
	public List<ApiPromotionalGoods> getAllPromotions(); 

}
