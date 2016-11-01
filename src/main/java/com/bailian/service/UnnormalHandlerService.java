package com.bailian.service;

import com.bailian.entity.ApiParameter;
import com.bailian.entity.RecommendationResult;
import com.bailian.model.RecommendApi;

public interface UnnormalHandlerService {
	public RecommendationResult handle(RecommendApi recApi,ApiParameter apiParameter);

}
