package com.bailian.entity;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class SearchHotWords {

	private List<Keyword> wordList;
	private String pageType;
	private int chan;
	private int rcmdAmt;

	private int statusCode;

	public SearchHotWords(SearchParameters sp,List<Keyword> wdList) {
		if(wdList.size()>sp.getNum())
		{
			//1031 oom
			this.wordList = new ArrayList<Keyword>(wdList.subList(0, sp.num));
			wdList = null;
			
		}
		else
		{
			this.wordList = wdList;
		}
		
		this.chan = sp.getChan();
		if (wordList.size() == 0) {
			statusCode = 400;
		} else {
			statusCode = 200;
		}
		this.pageType = sp.getPageType();
		this.rcmdAmt = wordList.size();
	}



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
		this.pageType = sp.getPageType();
		this.rcmdAmt = wordList.size();

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

	
	public int getRcmdAmt() {
		return rcmdAmt;
	}


	public void setRcmdAmt(int rcmdAmt) {
		this.rcmdAmt = rcmdAmt;
	}

	public String getPageType() {
		return pageType;
	}

	public void setPageType(String pageType) {
		this.pageType = pageType;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

}
