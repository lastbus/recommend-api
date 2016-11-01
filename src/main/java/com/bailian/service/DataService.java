package com.bailian.service;

import java.util.List;
import java.util.Map;

import com.bailian.entity.Goods;
import com.bailian.model.ApiPromotionalGoods;
import com.bailian.model.CategoryManagement;
import com.bailian.model.CombinedHotwords;

/**
 * 读取指定数据源的redis数据 
 * @author haojutao
 *
 */
public interface DataService {
	
	
	public String get(String key);
	
	public Map<String,String> hgetAll(String key);
	
	public void set(String key,List<Goods>valList,long expire);
	
	public void setPromGoods(String key,List<ApiPromotionalGoods>valList,long expire);
	
	public void set(String key,String value);
	public void set(String key,String value,long expire);
	
	public void setCombinedHotwords(String key,List<CombinedHotwords>value,long expire);
	public void setategoryManagement(String key,List<CategoryManagement>value,long expire);
	
	public void hmset(String key,Map<String,String> value);

	
	//public void set(String key,List<Object>valList,long expire);



}
