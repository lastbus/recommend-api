package com.bailian.model;

public class ApiAbtest {
    private Integer id;

    private String methodCode;

    private String api;

    private Integer apiCode;

    private String apiName;

    private Integer rank;

    private Integer isValid;

    private Double flowRate;
    
    

    @Override
	public String toString() {
		return "ApiAbtest [id=" + id + ", methodCode=" + methodCode + ", api="
				+ api + ", apiCode=" + apiCode + ", apiName=" + apiName
				+ ", rank=" + rank + ", isValid=" + isValid + ", flowRate="
				+ flowRate + "]";
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMethodCode() {
        return methodCode;
    }

    public void setMethodCode(String methodCode) {
        this.methodCode = methodCode;
    }

    public String getApi() {
        return api;
    }

    public void setApi(String api) {
        this.api = api;
    }

    public Integer getApiCode() {
        return apiCode;
    }

    public void setApiCode(Integer apiCode) {
        this.apiCode = apiCode;
    }

    public String getApiName() {
        return apiName;
    }

    public void setApiName(String apiName) {
        this.apiName = apiName;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public Integer getIsValid() {
        return isValid;
    }

    public void setIsValid(Integer isValid) {
        this.isValid = isValid;
    }

    public Double getFlowRate() {
        return flowRate;
    }

    public void setFlowRate(Double flowRate) {
        this.flowRate = flowRate;
    }
}