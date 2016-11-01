package com.bailian.model;

import java.util.Date;

/**
 * API參數
 * 
 * @author haojutao
 *
 */
public class RecommendApi {
	private Integer id;

	private String api;

	private String apiName;

	private Integer apiTrack;

	private Integer channel;

	private String memberId;

	private String cookieId;

	private String goodsId;

	private String items;

	private Integer paging;

	private Integer pageSize;

	private Integer num;

	private Date updatetime;

	@Override
	public String toString() {
		return "RecommendApi [id=" + id + ", api=" + api + ", apiName="
				+ apiName + ", apiTrack=" + apiTrack + ", channel=" + channel
				+ ", memberId=" + memberId + ", cookieId=" + cookieId
				+ ", goodsId=" + goodsId + ", items=" + items + ", paging="
				+ paging + ", pageSize=" + pageSize + ", num=" + num
				+ ", updatetime=" + updatetime + "]";
	}

	/**
	 * 0 无该参数 1 可选参数 2 必选参数
	 * 
	 * @return
	 */
	public String getKeyParameter() {

		if (memberId.trim().equals("1")) {
			return "memberId";
		} else if (items.trim().equals("2")) {
			return "items";
		} else if (cookieId.trim().equals("2")) {
			return "cookieId";
		} else if (goodsId.trim().equals("2")) {
			return "goodsId";
		}
		return null;

	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getApi() {
		return api;
	}

	public void setApi(String api) {
		this.api = api;
	}

	public String getApiName() {
		return apiName;
	}

	public void setApiName(String apiName) {
		this.apiName = apiName;
	}

	public Integer getapiTrack() {
		return apiTrack;
	}

	public void setapiTrack(Integer apiTrack) {
		this.apiTrack = apiTrack;
	}

	public Integer getChannel() {
		return channel;
	}

	public void setChannel(Integer channel) {
		this.channel = channel;
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

	public Integer getPaging() {
		return paging;
	}

	public void setPaging(Integer paging) {
		this.paging = paging;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public Date getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}
}