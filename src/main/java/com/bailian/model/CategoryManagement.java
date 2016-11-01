package com.bailian.model;

import java.util.Date;

public class CategoryManagement {
    private Integer id;

    private String categorySid;

    private String categoryName;

    private Date startDate;

    private Date endDate;

    private Integer goodsNumDisplay;

    private Integer channel;

    private String api;

    
    
    @Override
	public String toString() {
		return "CategoryManagement [id=" + id + ", categorySid=" + categorySid
				+ ", categoryName=" + categoryName + ", startDate=" + startDate
				+ ", endDate=" + endDate + ", goodsNumDisplay="
				+ goodsNumDisplay + ", channel=" + channel + ", api=" + api
				+ "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((api == null) ? 0 : api.hashCode());
		result = prime * result
				+ ((categoryName == null) ? 0 : categoryName.hashCode());
		result = prime * result
				+ ((categorySid == null) ? 0 : categorySid.hashCode());
		result = prime * result + ((channel == null) ? 0 : channel.hashCode());
		result = prime * result + ((endDate == null) ? 0 : endDate.hashCode());
		result = prime * result
				+ ((goodsNumDisplay == null) ? 0 : goodsNumDisplay.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((startDate == null) ? 0 : startDate.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CategoryManagement other = (CategoryManagement) obj;
		if (api == null) {
			if (other.api != null)
				return false;
		} else if (!api.equals(other.api))
			return false;
		if (categoryName == null) {
			if (other.categoryName != null)
				return false;
		} else if (!categoryName.equals(other.categoryName))
			return false;
		if (categorySid == null) {
			if (other.categorySid != null)
				return false;
		} else if (!categorySid.equals(other.categorySid))
			return false;
		if (channel == null) {
			if (other.channel != null)
				return false;
		} else if (!channel.equals(other.channel))
			return false;
		if (endDate == null) {
			if (other.endDate != null)
				return false;
		} else if (!endDate.equals(other.endDate))
			return false;
		if (goodsNumDisplay == null) {
			if (other.goodsNumDisplay != null)
				return false;
		} else if (!goodsNumDisplay.equals(other.goodsNumDisplay))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (startDate == null) {
			if (other.startDate != null)
				return false;
		} else if (!startDate.equals(other.startDate))
			return false;
		return true;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCategorySid() {
        return categorySid;
    }

    public void setCategorySid(String categorySid) {
        this.categorySid = categorySid;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
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

    public Integer getGoodsNumDisplay() {
        return goodsNumDisplay;
    }

    public void setGoodsNumDisplay(Integer goodsNumDisplay) {
        this.goodsNumDisplay = goodsNumDisplay;
    }

    public Integer getChannel() {
        return channel;
    }

    public void setChannel(Integer channel) {
        this.channel = channel;
    }

    public String getApi() {
        return api;
    }

    public void setApi(String api) {
        this.api = api;
    }
}