package com.bailian.model;

import java.util.Date;

public class MethodSource {
    private Integer id;

	private String methodCode;

	private String sourceCode;
	
	private Integer sourceType; 

	private Integer rank;

	private Float proportion;

	private Integer isValid;

	private Date createDatetime;



	@Override
	public String toString() {
		return "MethodSource [id=" + id + ", methodCode=" + methodCode
				+ ", sourceCode=" + sourceCode + ", rank=" + rank
				+ ", proportion=" + proportion + ", isValid=" + isValid
				+ ", createDatetime=" + createDatetime + "]";
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

	public String getSourceCode() {
		return sourceCode;
	}

	public void setSourceCode(String sourceCode) {
		this.sourceCode = sourceCode;
	}

	public Integer getRank() {
		return rank;
	}

	public void setRank(Integer rank) {
		this.rank = rank;
	}

	public Float getProportion() {
		return proportion;
	}

	public void setProportion(Float proportion) {
		this.proportion = proportion;
	}

	public Integer getIsValid() {
		return isValid;
	}

	public void setIsValid(Integer isValid) {
		this.isValid = isValid;
	}

	public Date getCreateDatetime() {
		return createDatetime;
	}

	public void setCreateDatetime(Date createDatetime) {
		this.createDatetime = createDatetime;
	}

	public Integer getSourceType() {
		return sourceType;
	}

	public void setSourceType(Integer sourceType) {
		this.sourceType = sourceType;
	}

	


}