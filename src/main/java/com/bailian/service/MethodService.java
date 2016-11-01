package com.bailian.service;

import java.util.List;

import com.bailian.entity.ApiConfiguration;
import com.bailian.entity.ApiParameter;
import com.bailian.entity.Goods;
import com.bailian.model.ApiAbtest;
import com.bailian.model.RecommendApi;

public interface MethodService {
	
	public List<Goods> getResultByMethod(RecommendApi recApi,ApiConfiguration apiConf,ApiAbtest method, ApiParameter apiParameter);

}
