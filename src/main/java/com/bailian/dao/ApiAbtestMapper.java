package com.bailian.dao;

import java.util.List;

import com.bailian.model.ApiAbtest;

public interface ApiAbtestMapper {
	
	List<ApiAbtest> selectApiAbtestByApi(String api);

}
