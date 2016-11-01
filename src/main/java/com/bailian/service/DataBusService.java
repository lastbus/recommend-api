package com.bailian.service;

/**
 * 
 * @author haojutao
 *
 */
public interface DataBusService {

	public void promGoodsToReids(); 
	public void blackListToRedis();
	public void hotwordsToRedis();
	public void promCategoriesToRedis();
	
	


}
