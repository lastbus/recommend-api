package com.bailian.utils.entity;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class SearchHotWords {

	private List<Keyword> wordList;
	private String location;
	private int chan;

	private int statusCode;

	public SearchHotWords(List<String> wdList, SearchParameters sp) {
		wordList = new ArrayList<Keyword>();
		for (String s : wdList) {
			Keyword kw = new Keyword();
			try {
				kw.setUrl(URLEncoder.encode("http://search.bl.com/k-" + s
						+ ".html", "UTF-8"));
				kw.setKeyword(s);
				if (wordList.size() < sp.num) {
					wordList.add(kw);
				}
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		this.chan = sp.getChan();
		if (wordList.size() == 0) {
			statusCode = 400;
		} else {
			statusCode = 200;
		}
		this.location = sp.getLocaton();
	}

	public int getChan() {
		return chan;
	}

	public void setChan(int chan) {
		this.chan = chan;
	}

	public List<Keyword> getWordList() {
		return wordList;
	}

	public void setWordList(List<Keyword> wordList) {
		this.wordList = wordList;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

}
