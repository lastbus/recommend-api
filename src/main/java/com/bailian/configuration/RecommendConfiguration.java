package com.bailian.configuration;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bailian.entity.ApiConfiguration;
import com.bailian.model.ApiAbtest;
import com.bailian.model.ApiPromotionalGoods;
import com.bailian.model.MethodSource;
import com.bailian.model.RecommendApi;
import com.bailian.model.SourceData;
import com.bailian.service.ApiConfigService;

/**
 *  推荐系统配置
 *  包括：接口及接口参数
 * @author haojutao
 *
 */
@Component
public class RecommendConfiguration {
	@Autowired
	private  ApiConfigService apiConfigService;
	
	public static boolean  inited = false;
	
	//推荐系统所有接口
	public static HashMap<String,RecommendApi> apiMap;
	
	//接口配置参数
	public static HashMap<String,ApiConfiguration> apiConfMap;
	
	//方法数据源
	public static HashMap<String,List<MethodSource>>methodSourceMap;
	
	//源key
	public static HashMap<String,SourceData> soureKeyMap;
	
	public void init()
	{
		List<RecommendApi> apiList = apiConfigService.selectAllApi();
		HashMap<String,RecommendApi> apiMap = new HashMap<String,RecommendApi>();
		HashMap<String,ApiConfiguration> apiConfMap = new HashMap<String,ApiConfiguration>();
		for(RecommendApi ra:apiList)
		{
			String api = ra.getApi();
			apiMap.put(api, ra);
			
			
			//接口配置
			ApiConfiguration apiConf = new ApiConfiguration();
			List<ApiAbtest> methodList = apiConfigService.selectApiAbtestByApi(api);
			apiConf.setMethodList(methodList);
//			//推广商品
//			List<ApiPromotionalGoods> promList = apiConfigService.getAllPromotionsByApi(api);
//			apiConf.setPromtionList(promList);
			apiConfMap.put(api, apiConf);
		}
		if(apiMap.size() != 0 )
		{
			RecommendConfiguration.apiMap = apiMap;
		}
		if(apiConfMap.size() != 0)
		{
			RecommendConfiguration.apiConfMap = apiConfMap;
		}
		HashMap<String,List<MethodSource>> msMap = apiConfigService.getAllMethodSource();
		if(msMap.size() !=0 )
		{
			RecommendConfiguration.methodSourceMap = msMap;
		}

		HashMap<String,SourceData> soureKeyMap = apiConfigService.getAllSourceData();	
		if(soureKeyMap.size() !=0 )
		{
			RecommendConfiguration.soureKeyMap = soureKeyMap;
		}
		System.out.println("--init-apiMap--" + apiMap);
		System.out.println("--init-apiConfMap--" + apiConfMap);
		System.out.println("--init-msMap--" + msMap);
		System.out.println("--init-sourKeyMap--" + soureKeyMap);
		inited = true;
	}
	

		
		

}
