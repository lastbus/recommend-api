package com.bailian.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bailian.dao.ApiAbtestMapper;
import com.bailian.dao.ApiPromotionalGoodsMapper;
import com.bailian.dao.CategoryManagementMapper;
import com.bailian.dao.CombinedHotwordsMapper;
import com.bailian.dao.KeywordBlacklistMapper;
import com.bailian.dao.MethodSourceMapper;
import com.bailian.dao.RecommendApiMapper;
import com.bailian.dao.SourceDataMapper;
import com.bailian.model.ApiAbtest;
import com.bailian.model.ApiPromotionalGoods;
import com.bailian.model.CategoryManagement;
import com.bailian.model.CombinedHotwords;
import com.bailian.model.KeywordBlacklist;
import com.bailian.model.MethodSource;
import com.bailian.model.RecommendApi;
import com.bailian.model.SourceData;
import com.bailian.service.ApiConfigService;

@Service
public class ApiConfigServiceImpl implements ApiConfigService
{
	
	
	
	@Autowired
	private RecommendApiMapper recApiMapper;
	
	@Autowired
	private ApiAbtestMapper apiAbtestMapper;
	
	@Autowired
	private MethodSourceMapper methodSourceMapper;
	@Autowired
	private SourceDataMapper sdMapper;
	
	@Autowired
	private ApiPromotionalGoodsMapper promMapper;
	
	@Autowired
	private KeywordBlacklistMapper blackListMapper;
	
	@Autowired
	private CombinedHotwordsMapper combHotwordsMapper;
	
	@Autowired
	private CategoryManagementMapper categoryManagementMapper;

	@Override
	public List<RecommendApi> selectAllApi() {
		// TODO Auto-generated method stub
		return recApiMapper.selectAllApi();
	}

	@Override
	public List<ApiAbtest> selectApiAbtestByApi(String api) {
		return apiAbtestMapper.selectApiAbtestByApi(api);
	}


	@Override
	public List<MethodSource> selectSoureByMethorCode(String methodCode) {
		// TODO Auto-generated method stub
		return methodSourceMapper.selectSoureByMethorCode(methodCode);
	}

	@Override
	public HashMap<String, List<MethodSource>> getAllMethodSource() {
		 HashMap<String, List<MethodSource>> msMap = new  HashMap<String, List<MethodSource>>();
		List<MethodSource> msList = methodSourceMapper.selectAllSource();
		for(MethodSource ms:msList)
		{
			String mc= ms.getMethodCode();
			List<MethodSource> _msList = msMap.get(mc);
			if(_msList == null)
			{
				_msList = new ArrayList<MethodSource>();
				_msList.add(ms);
				msMap.put(mc, _msList);
				
			}
			else
			{
				_msList.add(ms);
			}
		}
		return msMap;
	}

	@Override
	public HashMap<String, SourceData> getAllSourceData() {
		List<SourceData> sdList = sdMapper.selectAllSourceData();
		HashMap<String,SourceData> kvMap = new HashMap<String,SourceData>();
		for(SourceData sd:sdList)
		{
			kvMap.put(sd.getSouceCode(), sd);
		}
		return kvMap;
	}

	@Override
	public List<ApiPromotionalGoods> getAllPromotionsByApi(String api) {
		// TODO Auto-generated method stub
		return promMapper.getAllPromotionsByApi(api);
	}

	@Override
	public List<ApiPromotionalGoods> getAllPromotions() {
		// TODO Auto-generated method stub
		return promMapper.getAllPromotions();
	}

	@Override
	public List<KeywordBlacklist> getAllBlackList() {
		return blackListMapper.getAllBlackList();
	}

	@Override
	public List<CombinedHotwords> getAllHotwords() {
		// TODO Auto-generated method stub
		return combHotwordsMapper.getAllHotwords();
	}

	@Override
	public List<CategoryManagement> getAllCategoryByApi(String api) {
		// TODO Auto-generated method stub
		return categoryManagementMapper.getAllCategoryByApi(api);
	}

	@Override
	public List<CategoryManagement> getAllCategories() {
		// TODO Auto-generated method stub
		return categoryManagementMapper.getAllCategories();
	}



	

}
