package com.bailian.entity;

import java.util.List;

import org.springframework.stereotype.Service;

import com.bailian.model.ApiAbtest;
import com.bailian.model.ApiPromotionalGoods;
import com.bailian.model.CategoryManagement;

/**
 * 栏位配置
 * @author haojutao
 *
 */

public class ApiConfiguration {
	
	//推廣商品
	public List<ApiPromotionalGoods> promtionList;
	
	/**
	 * 方法
	 */
	public List<ApiAbtest>  methodList;
	
	
	@Override
	public String toString() {
		return "ApiConfiguration [promtionList=" + promtionList
				+ ", methodList=" + methodList + "]";
	}
	public List<ApiAbtest> getMethodList() {
		return methodList;
	}
	public void setMethodList(List<ApiAbtest> methodList) {
		this.methodList = methodList;
	}
	
	public List<ApiPromotionalGoods> getPromtionList() {
		return promtionList;
	}
	public void setPromtionList(List<ApiPromotionalGoods> promtionList) {
		this.promtionList = promtionList;
	}




	
	
}
