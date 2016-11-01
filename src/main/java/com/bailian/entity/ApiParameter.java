package com.bailian.entity;

public class ApiParameter {

	private String api;
	private String memberId;
	private String cookieId;
	private String goodsId;
	private String items;
	private int pSize;
	private int pNum;
	private String chan;
	

	
	@Override
	public String toString() {
		return "ApiParameter [api=" + api + ", memberId=" + memberId
				+ ", cookieId=" + cookieId + ", goodsId=" + goodsId
				+ ", items=" + items + ", pSize=" + pSize + ", pNum=" + pNum
				+ ", chan=" + chan + "]";
	}

	public String getChan() {
		return chan;
	}

	public String get(String parameterType)
	{
		if(parameterType.equalsIgnoreCase("memberId"))
		{
			return memberId;
		}else if(parameterType.equalsIgnoreCase("cookieId"))
		{
			return cookieId;
		}
		else if(parameterType.equalsIgnoreCase("goodsId"))
		{
			return goodsId;
		}
		else if(parameterType.equalsIgnoreCase("items"))
		{
			return items;
		}
		else if(parameterType.equalsIgnoreCase("pSize"))
		{
			return String.valueOf(pSize);
		}
		else if(parameterType.equalsIgnoreCase("pNum"))
		{
			return String.valueOf(pNum);
		}
		else if(parameterType.equalsIgnoreCase("chan"))
		{
			return chan;
		}
		
		return null;
		
	}

	public ApiParameter(String api, int chan, String memberId, String cookieId,
			String goodsId, String items,int pNum,int pSize) {
		this.api = api;
		this.chan = String.valueOf(chan);
		this.memberId = memberId;
		this.cookieId = cookieId;
		this.goodsId = goodsId;
		this.items = items;
		this.pNum = pNum;
		this.pSize = pSize;
	}

	public int getpSize() {
		return pSize;
	}

	public void setpSize(int pSize) {
		this.pSize = pSize;
	}

	public int getpNum() {
		return pNum;
	}

	public void setpNum(int pNum) {
		this.pNum = pNum;
	}

	public void setChan(String chan) {
		this.chan = chan;
	}

	public String getApi() {
		return api;
	}

	public void setApi(String api) {
		this.api = api;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getCookieId() {
		return cookieId;
	}

	public void setCookieId(String cookieId) {
		this.cookieId = cookieId;
	}

	public String getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}

	public String getItems() {
		return items;
	}

	public void setItems(String items) {
		this.items = items;
	}

	

}
