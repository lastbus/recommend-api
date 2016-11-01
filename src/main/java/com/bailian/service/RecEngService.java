package com.bailian.service;

import com.bailian.entity.ApiParameter;
import com.bailian.entity.RecommendationResult;

/**
 *  推荐引擎
 * @author haojutao
 *
 */
public interface RecEngService {
	
	public RecommendationResult getResult( ApiParameter apiParameter);
	
	

}
