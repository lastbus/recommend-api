package com.bailian.service.impl;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.bailian.configuration.RecommendConfiguration;
import com.bailian.entity.ApiConfiguration;
import com.bailian.entity.ApiParameter;
import com.bailian.entity.ApiTimer;
import com.bailian.entity.Goods;
import com.bailian.entity.RecommendationResult;
import com.bailian.model.ApiAbtest;
import com.bailian.model.RecommendApi;
import com.bailian.service.DataService;
import com.bailian.service.MethodService;
import com.bailian.service.PagingServcie;
import com.bailian.service.RecEngService;
import com.bailian.service.TrackService;
import com.bailian.service.UnnormalHandlerService;
import com.bailian.utils.StringUtil;

@Service
public class RecEngServiceImpl implements RecEngService {

	static Random rand = new Random(System.currentTimeMillis());
	@Autowired
	private RecommendConfiguration conf;

	@Autowired
	private MethodService methodService;
	@Autowired
	private UnnormalHandlerService unnormalService;
	@Autowired
	private TrackService trackService;

	@Autowired
	private DataService dataService;

	@Autowired
	private PagingServcie pagingService;

	private int day = 24 * 60 * 60;
	
	@Override
	public RecommendationResult getResult(ApiParameter apiParameter) {
		// 计时
		ApiTimer apiTimer = new ApiTimer();
		apiTimer.start();
		// 初始化
		if (!conf.inited) {
			conf.init();
		}

		RecommendApi recApi = RecommendConfiguration.apiMap.get(apiParameter
				.getApi());

		// 异常参数检测处理
		RecommendationResult unnormResutl = unnormalService.handle(recApi,
				apiParameter);
		if (unnormResutl != null) {
			return unnormResutl;
		}

		// 取得接口配置
		ApiConfiguration apiConf = RecommendConfiguration.apiConfMap.get(recApi
				.getApi());
		List<ApiAbtest> methodList = apiConf.getMethodList();
		// ABtest 分支選擇
		ApiAbtest method = null;
		if (methodList.size() == 1) {
			method = methodList.get(0);
		} else {
			String mId = apiParameter.getMemberId();
			if(recApi.getApi().equals("gyl") && !StringUtil.isEmpty(mId))
			{
				String methordKey = "abtest_"+ recApi.getApi() + "_" + mId;
				String strMethod = dataService.get(methordKey);
				if(strMethod != null)
				{
					method = JSON.parseObject(strMethod, ApiAbtest.class);
				}
				else
				{
					int randNum = rand.nextInt(100);
					method = methodChoice(randNum, methodList);
					dataService.set(methordKey, JSON.toJSONString(method), day);
				}
			}
			else
			{
				//取默认ALS
				method = methodList.get(0);
			}
			
		}
		//1025 oom
		methodList = null;

		// 选择方法对应结果
		List<Goods> resList = methodService.getResultByMethod(recApi, apiConf,method,
				apiParameter);
		
		RecommendationResult recResult = null;
		// 分页处理
		if (recApi.getPaging() == 1) {
			List<Goods> pagingResultList = pagingService.paging(recApi,
					apiParameter, resList);
			apiTimer.end();
			recResult = new RecommendationResult(resList,pagingResultList, apiTimer,
					method.getMethodCode(), apiParameter);
			//1025 oom
			resList.clear();
			resList = null;

		} else {
			// 计时结束
			apiTimer.end();
			recResult = new RecommendationResult(resList, apiTimer,
					method.getMethodCode(), apiParameter);
		}

		// 写入kafka
		trackService.track(recApi, apiParameter, recResult);
		return recResult;
	}

	public ApiAbtest methodChoice(int randNum, List<ApiAbtest> methodList) {

		double sum = 0;
		ApiAbtest method = null;
		if (methodList.size() == 2) {
			if (methodList.get(0).getFlowRate() * 100 > randNum) {
				method = methodList.get(0);
			} else {
				method = methodList.get(1);
			}
		} else {
			for (int i = 0; i <= methodList.size() - 1; i++) {
				sum += methodList.get(i).getFlowRate();
				if( randNum < Math.ceil(sum * 100))
				{
					method = methodList.get(i);
					break;
				}

			}
		}
		return method;
	}

}
