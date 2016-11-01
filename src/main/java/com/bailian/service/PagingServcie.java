package com.bailian.service;

import java.util.List;

import com.bailian.entity.ApiParameter;
import com.bailian.entity.Goods;
import com.bailian.model.RecommendApi;

public interface PagingServcie {

	public List<Goods> paging(RecommendApi recApi,ApiParameter apiParameter,List<Goods> goodsList);
}
