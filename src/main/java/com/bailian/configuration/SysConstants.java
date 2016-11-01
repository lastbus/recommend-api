package com.bailian.configuration;

/**
 * 
 * @author haojutao
 *
 */
public class SysConstants {
	
	/**********************************************************************
	 * pc 端参数配置                                                                                                                                                 *
	 * 该值代表一个栏位                                                                                                                                          *
	 *********************************************************************/
	
	//pc 端看了又看栏位
	public static final int PC_GDP_COL_VIEW = 301;
	//pc 端大家正在买
	public static final int PC_RGDP_COL_HOT = 302;
	//pc 端看了最终买
	public static final int PC_GDP_COL_SHOP = 303;
	//pc 端根据浏览记录推荐
	public static final int PC_GDP_COL_HIST = 304;
	
	//pc猜你喜欢
	public static final int PC_INDEX_COL_GUESS = 305;
	//pc支付完成
	public static final int PC_INDEX_COL_AFTER_PAY = 306;

	
	/**********************************************************************
	 *H5 端参数配置 200                                                     *
	 *********************************************************************/
	//H5 端看了又看栏位
	public static final int H5_GDP_COL_VIEW = 201;
	//h5猜你喜欢
	public static final int H5_INDEX_COL_GUESS = 205;	
	//h5支付完成
	public static final int H5_INDEX_COL_AFTER_PAY = 206;

	
	/**********************************************************************
	 *APP 端参数配置 100                                                   *
	 *********************************************************************/
	//APP端看了又看栏位
	public static final int APP_GDP_COL_VIEW = 101;
	
	//APP端猜你喜欢
	public static final int APP_INDEX_COL_GUESS = 105;
	
	//App支付完成
	public static final int APP_INDEX_COL_AFTER_PAY = 106;
	
	//////////////////////////////////////////////////////////////////////////////////////
	// 来源配置                                                                                 //
	//////////////////////////////////////////////////////////////////////////////////////
	
	//来自ALS算法
	public static final int SOURCE_USER_ALS = 901;
	//来自user_cf
	public static final int SOURCE_USER_CF  = 902;
	
	//来自item_cf
	public static final int SOURCE_ITEM_CF = 903;
	
	//相似商品
	public static final int SOURCE_GOODS_FEATURE_SIMILIARY = 905;
	
	//看了又看
	public static final int SOURCE_GOODS_USER_VIEWED = 906;
	
	//看了最终买
	public  static final int SOURCE_GOODS_USER_VIEW_BOUGHT = 907;
	
	//买了还买(单品，同品类)
	public static final int SOURCE_GOODS_USER_BOUGHT = 908;
	
	//品类热卖
	public static final int SOURCE_GOODS_CATEGORY_HOTSALE = 909;
	
	//品类同时购买
	public static final int SROUCE_GOODS_CATEGORY_BOUGHT_TOGETHER = 910;
	
	//跨品类买了还买
	public static final int SOURCE_GOODS_CORSS_CATE_BOUGHT_ALSO_BOUGHT = 911;
	
	//新品
	public static final int SOURCE_GOODS_CATEGORY_NEW_ARRIVALS = 912;
	
	//折扣商品
	public static final int SOURCE_GOODS_CATEGORY_OFF_PRICE = 913;
	
	//周期购买
	public static final int SOURCE_GOODS_IN_BOUGHT_CYCLE = 914;
	
	//历史同期  气候 季节 
	public static final int SOURCE_GOODS_IN_HISTORICAL_PERIOD = 915;
	
	//热点事件 事件  天气
	public static final int SOURCE_GOODS_HOTSPOT = 916;
	
	//用户偏好品类商品
	public static final int SOURCE_USER_PREFERENCE_CATEGORY_GOODS = 917;
	
	//品类下随机商品
	public static final int SOURCE_GOODS_CATEGORY_RANDM_SELECTED = 918;
	
	//随机兄弟品类下商品
	public static final int SOURCE_GOODS_BROTHER_CATEGORY_RANDOM = 919;
	
	
	
	


}
