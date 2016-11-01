package com.bailian.service;

import java.util.List;

import com.bailian.entity.ApiParameter;
import com.bailian.model.MethodSource;

public interface SourceDataService {
	
	public List<String> getResBySourceCode(MethodSource methodSource,ApiParameter apiParameter);

}
