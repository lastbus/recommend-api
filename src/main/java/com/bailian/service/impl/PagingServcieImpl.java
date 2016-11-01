package com.bailian.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.bailian.entity.ApiParameter;
import com.bailian.entity.Goods;
import com.bailian.model.RecommendApi;
import com.bailian.service.PagingServcie;
@Service
public class PagingServcieImpl implements PagingServcie {

	@Override
	public List<Goods> paging(RecommendApi recApi, ApiParameter apiParameter,
			List<Goods> goodsList) {
		
		int pageSize = apiParameter.getpSize();
		int pageNum = apiParameter.getpNum();
		int start = (pageNum-1)* pageSize;
		int end = pageSize*pageNum;
		
		if(start >= goodsList.size())
		{
			return null;
		}

		//后期合并优化
		if(end >= goodsList.size())
		{
			//1025 oom
			List<Goods> subList = new ArrayList<Goods>(goodsList.subList(start, goodsList.size()));
			return subList;
		}else
		{
			//1025 oom
			List<Goods> subList = new ArrayList<Goods>(goodsList.subList(start,end));
			return subList;
		}
		
	}

}
