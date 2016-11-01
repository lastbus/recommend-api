package com.bailian.utils;

/**
 * @Description: 登录状态码
 * @user zhangwenming
 * @date 2016年1月15日 下午4:00:01
 */
public enum LoginStatusCode {

	failed("登录失败/异常", "0"), 
	success("登录成功", "1"), 
	lock("状态锁定", "2")
	;

	private String name;
	private String index;

	private LoginStatusCode(String name, String index) {
		this.name = name;
		this.index = index;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIndex() {
		return index;
	}

	public void setIndex(String index) {
		this.index = index;
	}

}
