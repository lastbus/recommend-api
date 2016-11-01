package com.bailian.model;

import java.util.Date;

public class ApiPromotionalGoods {
    private Integer id;

    private String api;

    private String channel;

    private Integer position;

    private String goodsSid;

    private String categorySid;

    private Date startDate;

    private Date endDate;

    private Integer isValid;

    private Date updateDatetime;
    

    @Override
	public String toString() {
		return "ApiPromotionalGoods [id=" + id + ", api=" + api
				+ ", channel=" + channel + ", position=" + position
				+ ", goodsSid=" + goodsSid + ", categorySid=" + categorySid
				+ ", startDate=" + startDate + ", endDate=" + endDate
				+ ", isValid=" + isValid + ", updateDatetime=" + updateDatetime
				+ "]";
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


	public String getChannel() {
		return channel;
	}


	public void setChannel(String channel) {
		this.channel = channel;
	}


	public Integer getPosition() {
		return position;
	}


	public void setPosition(Integer position) {
		this.position = position;
	}


	public String getGoodsSid() {
		return goodsSid;
	}


	public void setGoodsSid(String goodsSid) {
		this.goodsSid = goodsSid;
	}


	public String getCategorySid() {
		return categorySid;
	}


	public void setCategorySid(String categorySid) {
		this.categorySid = categorySid;
	}


	public Date getStartDate() {
		return startDate;
	}


	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}


	public Date getEndDate() {
		return endDate;
	}


	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}


	public Integer getIsValid() {
		return isValid;
	}


	public void setIsValid(Integer isValid) {
		this.isValid = isValid;
	}


	public Date getUpdateDatetime() {
		return updateDatetime;
	}


	public void setUpdateDatetime(Date updateDatetime) {
		this.updateDatetime = updateDatetime;
	}


}