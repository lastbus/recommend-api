package com.bailian.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bailian.configuration.RecommendConfiguration;
import com.bailian.entity.ApiParameter;
import com.bailian.entity.Goods;
import com.bailian.model.MethodSource;
import com.bailian.model.SourceData;
import com.bailian.service.DataService;
import com.bailian.service.GoodsService;
import com.bailian.service.SourceDataService;
import com.bailian.utils.StringUtil;

@Service
public class SourceDataServiceImpl implements SourceDataService {

	@Autowired
	DataService dataService;
	@Autowired
	GoodsService goodsServcie;
	
	@Override
	public List<String> getResBySourceCode(MethodSource methodSource,ApiParameter apiParameter) {
	String sourceCode =	methodSource.getSourceCode();
	//數據源前綴
	SourceData sourceData = RecommendConfiguration.soureKeyMap.get(sourceCode);
	String keyPrefix = sourceData.getKeyPrefix();
	String keyPara = sourceData.getInputParameter();
	String redisKey = null ;
	if(StringUtil.isEmpty(keyPara))
	{
		redisKey = keyPrefix;
	}
	else
	{
		String inputParameter = apiParameter.get(keyPara);
		if(!StringUtil.isEmpty(inputParameter))
		{
			redisKey = keyPrefix+inputParameter;
		}
		else
		{
			return null;
		}
	}
	
	String goodsIds = dataService.get(redisKey);
	List<String> gIdList = null;
	if(goodsIds != null)
	{
		//带权重
		if(goodsIds.contains(":"))
		{
			gIdList = StringUtil.splitItemsWithWight(goodsIds);
		}
		else
		{
			gIdList = StringUtil.split(goodsIds);
		}
	}
	//去掉推荐结果中自己
	String curGoodsId = apiParameter.getGoodsId();
	if(!StringUtil.isEmpty(curGoodsId) && gIdList != null &&gIdList.contains(curGoodsId.trim()))
	{
			gIdList.remove(curGoodsId.trim());
	}

		return gIdList;
	}


}
