package com.bailian.utils.entity;

import java.util.List;

import com.bailian.utils.StringUtil;

/**
 * 推荐返回结果
 * @author haojutao
 *
 */
public class RecommendationResult {

	private int statusCode;
	private long elapsedTime;
	private List<Goods> goodsList;
	private int rcmdAmt;
	private int totalPages;
	private String method;
	private String message;
	private String api;


	public RecommendationResult(String message)
	{
		this.message = message;
	}
	
	public RecommendationResult(List<Goods> goodsList,ApiTimer apiTimer,String method,ApiParameter apiParameter) {
		if(goodsList==null||goodsList.size()==0)
		{
			this.statusCode = 400;
			this.message = "返回结果异常";
			this.elapsedTime = apiTimer.getTime();
			this.api = apiParameter.getApi();
		}
		else
		{
			this.statusCode = 200;
			this.message = "正常";
			if(apiParameter.getpSize()==0)
			{
				this.totalPages = 1;
			}else
			{
				int pageSize = Integer.parseInt(apiParameter.get("pSize").trim());
				this.totalPages = (int) Math.ceil(goodsList.size()/pageSize);
			}
			this.rcmdAmt = goodsList.size();
			this.method = method;
			this.elapsedTime = apiTimer.getTime();
			this.goodsList = goodsList;
			this.api = apiParameter.getApi();
						
		}
	}

	public RecommendationResult(List<Goods> goodsList,List<Goods> pagingList,ApiTimer apiTimer,String method,ApiParameter apiParameter) {
		if(pagingList==null||pagingList.size()==0)
		{
			this.statusCode = 400;
			this.message = "返回结果异常";
			this.elapsedTime = apiTimer.getTime();
			this.api = apiParameter.getApi();

		}
		else
		{
			this.statusCode = 200;
			this.message = "正常";
			if(StringUtil.isEmpty(apiParameter.get("pSize")))
			{
				this.totalPages = 1;
			}else
			{
				int pageSize = Integer.parseInt(apiParameter.get("pSize").trim());
				this.totalPages = (int) Math.ceil(goodsList.size()*1.0/pageSize);
			}
			this.rcmdAmt = pagingList.size();
			this.method = method;
			this.elapsedTime = apiTimer.getTime();
			this.goodsList = pagingList;
			//为埋点用
			this.api = apiParameter.getApi();
						
		}
	}

	public int getStatusCode() {
		return statusCode;
	}


	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}


	public long getElapsedTime() {
		return elapsedTime;
	}


	public void setElapsedTime(long elapsedTime) {
		this.elapsedTime = elapsedTime;
	}


	public List<Goods> getGoodsList() {
		return goodsList;
	}


	public void setGoodsList(List<Goods> goodsList) {
		this.goodsList = goodsList;
	}


	public int getRcmdAmt() {
		return rcmdAmt;
	}


	public void setRcmdAmt(int rcmdAmt) {
		this.rcmdAmt = rcmdAmt;
	}


	public int getTotalPages() {
		return totalPages;
	}


	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}


	public String getMethod() {
		return method;
	}


	public void setMethod(String method) {
		this.method = method;
	}


	public String getMessage() {
		return message;
	}


	public void setMessage(String message) {
		this.message = message;
	}

	public String getApi() {
		return api;
	}

	public void setApi(String api) {
		this.api = api;
	}

}
