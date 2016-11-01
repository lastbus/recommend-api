package com.bailian.entity;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import com.bailian.utils.StringUtil;

/**
 * 
 * @author haojutao
 *
 */
public class SearchParameters {

	String pageType;
	int chan;
	String cateId;
	String serwd;
	int num;

	public SearchParameters(String pageType, int chan, String cateId,
			String serwd, String num) {
		if (serwd != null && !serwd.isEmpty()) {
			try {
				serwd = new String(serwd.getBytes("ISO-8859-1"), "UTF-8");
			} catch (UnsupportedEncodingException e1) {
				e1.printStackTrace();
			}
			serwd = serwd.replaceAll("%(?![0-9a-fA-F]{2})", "%25");
			try {
				serwd = URLDecoder.decode(serwd, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			serwd = serwd.trim().toLowerCase();
			this.serwd = serwd.toLowerCase();
		}
		this.pageType = pageType.toLowerCase();
		this.chan = chan;
		this.cateId = cateId;

		if (!StringUtil.isEmpty(num)) {
			this.num = Integer.parseInt(num.trim());
		}
	}



	public String getPageType() {
		return pageType;
	}



	public void setPageType(String pageType) {
		this.pageType = pageType;
	}



	public String getCateId() {
		return cateId;
	}

	public void setCateId(String cateId) {
		this.cateId = cateId;
	}

	public String getSerwd() {
		return serwd;
	}

	public void setSerwd(String serwd) {
		this.serwd = serwd;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public int getChan() {
		return chan;
	}

	public void setChan(int chan) {
		this.chan = chan;
	}

}
