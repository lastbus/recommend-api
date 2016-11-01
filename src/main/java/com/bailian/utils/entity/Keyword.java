package com.bailian.utils.entity;

public class Keyword {
	
	private int wordType;
	private String url;
	private String keyword;
	
	
	
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
