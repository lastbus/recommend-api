package com.bailian.model;

import java.util.Date;

public class CombinedHotwords {
    private Integer seqnum;

    private String pagetype;

    private Integer categoryid;

    private Date startdate;

    private Date enddate;

    private String url;

    private String keyword;

    private String channel;

    private String from;

    private Integer iseffective;
    
 
    @Override
	public String toString() {
		return "CombinedHotwords [seqnum=" + seqnum + ", pagetype=" + pagetype
				+ ", categoryid=" + categoryid + ", startdate=" + startdate
				+ ", enddate=" + enddate + ", url=" + url + ", keyword="
				+ keyword + ", channel=" + channel + ", from=" + from
				+ ", iseffective=" + iseffective + "]";
	}

	public Integer getSeqnum() {
        return seqnum;
    }

    public void setSeqnum(Integer seqnum) {
        this.seqnum = seqnum;
    }

    public String getPagetype() {
        return pagetype;
    }

    public void setPagetype(String pagetype) {
        this.pagetype = pagetype;
    }

    public Integer getCategoryid() {
        return categoryid;
    }

    public void setCategoryid(Integer categoryid) {
        this.categoryid = categoryid;
    }

    public Date getStartdate() {
        return startdate;
    }

    public void setStartdate(Date startdate) {
        this.startdate = startdate;
    }

    public Date getEnddate() {
        return enddate;
    }

    public void setEnddate(Date enddate) {
        this.enddate = enddate;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getKeyword() {
        return keyword.toLowerCase();
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword.toLowerCase();
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public Integer getIseffective() {
        return iseffective;
    }

    public void setIseffective(Integer iseffective) {
        this.iseffective = iseffective;
    }
}