package com.bailian.dao;

import java.util.List;

import com.bailian.model.MethodSource;


public interface MethodSourceMapper {
	public List<MethodSource>selectSoureByMethorCode(String methodCode);
	public List<MethodSource>selectAllSource();


}
