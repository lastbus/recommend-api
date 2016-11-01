package com.bailian.service;

import java.util.HashMap;
import java.util.List;

import com.bailian.model.ApiAbtest;
import com.bailian.model.ApiPromotionalGoods;
import com.bailian.model.CategoryManagement;
import com.bailian.model.CombinedHotwords;
import com.bailian.model.KeywordBlacklist;
import com.bailian.model.MethodSource;
import com.bailian.model.RecommendApi;
import com.bailian.model.SourceData;



public interface ApiConfigService {
	
	public List<RecommendApi> selectAllApi();
	public List<ApiAbtest> selectApiAbtestByApi(String api);
	public List<MethodSource>selectSoureByMethorCode(String methodCode);
	public HashMap<String, List<MethodSource>> getAllMethodSource();
	public HashMap<String,SourceData> getAllSourceData();
	//促销商品接口
	public List<ApiPromotionalGoods> getAllPromotionsByApi(String api);
	public List<ApiPromotionalGoods> getAllPromotions(); 
	//品类管理接口
	public List<CategoryManagement> getAllCategoryByApi(String api);
	public List<CategoryManagement> getAllCategories(); 
	
	//热搜词配置
	//黑名单
	public List<KeywordBlacklist> getAllBlackList();
	//配置热搜词
	public List<CombinedHotwords> getAllHotwords();


}
