package com.bailian.entity;

import org.codehaus.jackson.annotate.JsonIgnore;

public class Keyword {
	
	@JsonIgnore
	private int wordType;
	private String url;
	private String keyword;
	
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((keyword == null) ? 0 : keyword.hashCode());
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
		Keyword other = (Keyword) obj;
		if (keyword == null) {
			if (other.keyword != null)
				return false;
		} else if (!keyword.equals(other.keyword))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Keyword [wordType=" + wordType + ", url=" + url + ", keyword="
				+ keyword + "]";
	}
	public int getWordType() {
		return wordType;
	}
	public void setWordType(int wordType) {
		this.wordType = wordType;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

}
