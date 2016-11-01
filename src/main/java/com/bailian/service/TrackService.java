package com.bailian.service;

import com.bailian.entity.ApiParameter;
import com.bailian.entity.RecommendationResult;
import com.bailian.model.RecommendApi;

/**
 * 
 * @author haojutao
 *
 */
public interface TrackService {
	public void track(RecommendApi recApi,ApiParameter apiParameter,RecommendationResult recResult);

}
