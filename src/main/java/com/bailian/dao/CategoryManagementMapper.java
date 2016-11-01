package com.bailian.dao;

import java.util.List;

import com.bailian.model.CategoryManagement;

public interface CategoryManagementMapper {
	
	public List<CategoryManagement> getAllCategoryByApi(String api);
	public List<CategoryManagement> getAllCategories(); 

}
