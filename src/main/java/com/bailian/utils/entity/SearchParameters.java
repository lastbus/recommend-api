package com.bailian.utils.entity;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import com.bailian.utils.StringUtil;

/**
 * 
 * @author haojutao
 *
 */
public class SearchParameters {

	String locaton;
	int chan;
	String cateId;
	String serwd;
	int num;

	public SearchParameters(String location, int chan, String cateId,
			String serwd, String num) {
		System.out.println(serwd);
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
		this.locaton = location.toLowerCase();
		this.chan = chan;
		this.cateId = cateId;

		if (!StringUtil.isEmpty(num)) {
			this.num = Integer.parseInt(num.trim());
		}
	}

	public String getLocaton() {
		return locaton;
	}

	public void setLocaton(String locaton) {
		this.locaton = locaton;
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
