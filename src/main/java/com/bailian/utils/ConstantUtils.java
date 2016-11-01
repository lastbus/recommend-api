package com.bailian.utils;

/**
 * @ClassName: ConstantUtils 
 * @Description: 常量类
 * @author zhangwenming
 * @date 2015年11月20日 下午2:14:26
 */
public class ConstantUtils {


	public static final String CURRENT_USER = "user";
	public static final String CURRENT_HYPHEN = "-";
	public static final String CURRENT_BLANK = "";

	/**  后台没有找到数据 */
	public static final String NO_QUERY_DATA = "没有查询到数据";
	public static final String GOODS_LEV1_NO_DATA = "一级品类没有数据";
	
	/**
	 * 返回码
	 */
	public abstract class ReturnConstant{
		/** controller响应 成功 */
		public static final String RESP_SUCCESS = "success";
		/** controller响应 失败 */
		public static final String RESP_FAILED = "failed";
		/** controller响应 系统异常 */
		public static final String RESP_SYSERROR = "syserror";
		/** controller响应 参数错误 */
		public static final String RESP_PARAMERROR = "paramerror";
	}
	
	/**
	 * 产品类型
	 */
	public abstract class SysProductType {
		
		/** 一级品类 */
		public static final String GOODS_CATE9_LEV1 = "goods_cate9_lev1";
		
		/** 二级品类 */
		
	}
	
	
	public abstract class COMSIDName {
		public static final String LH_LOGISTICS = "联华物流";
		public static final String XD_LOGISTICS = "现代物流";
	}
	
	public abstract class PartTran {
		// 渠道
		public static final String QD = "qd";
		// 业态
		public static final String YT = "yt";
		// 频道
		public static final String PD = "pd";
	}
	
}
