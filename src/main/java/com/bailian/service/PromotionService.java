package com.bailian.service;

import java.util.List;

import com.bailian.entity.ApiParameter;
import com.bailian.entity.Goods;

public interface PromotionService {
	
	public List<Goods> getPromotinalGoods(ApiParameter apiParameter,List<Goods> goodsList);

}
