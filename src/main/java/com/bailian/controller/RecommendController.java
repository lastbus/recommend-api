package com.bailian.controller;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bailian.configuration.RecommendConfiguration;
import com.bailian.entity.ApiParameter;
import com.bailian.entity.RecommendationResult;
import com.bailian.service.ApiConfigService;
import com.bailian.service.DataBusService;
import com.bailian.service.DataService;
import com.bailian.service.MethodService;
import com.bailian.service.UserFeedbackService;
import com.bailian.service.impl.RecEngServiceImpl;
import com.bailian.utils.StringUtil;

/**
 * 統一接口入口
 * @author haojutao
 *
 */
@Controller
@RequestMapping("/")
public class RecommendController {

	private static final Logger logger = LoggerFactory
			.getLogger(RecommendController.class);

	@Autowired
	private UserFeedbackService feedback;
	
	@Autowired
	private RecEngServiceImpl engService;
	
	@Autowired
	private MethodService methodService;
	@Autowired
	private DataService dataService;
	
	@Autowired
	ApiConfigService config;
	
	@Autowired
	DataBusService dataBusService;

	@Autowired
	private RecommendConfiguration recConf;
	
	/**
	 * 用户反馈
	 * @param mId
	 * @param gId
	 * @return
	 */
	@RequestMapping(value = "/dislike")
	@ResponseBody
	public Map<String,String> dislike(@RequestParam(value = "mId", required = true) String mId,
			@RequestParam(value = "gId", required = false) String gId)
			{
		feedback.dislike(mId, gId);
		Map<String,String> msgMap = new HashMap<String,String>();
		msgMap.put("api","dislike");
		msgMap.put("statusCode", "200");
		return msgMap;
		
	}	
	
	
	@RequestMapping(value = "/rec")
	@ResponseBody
	public RecommendationResult getRec(
			@RequestParam(value = "api", required = true) String api,
			@RequestParam(value = "chan", required = true) int chan,
			@RequestParam(value = "memberId", required = false) String memberId,
			@RequestParam(value = "cookieId", required = false) String cookieId,
			@RequestParam(value = "goodsId", required = false) String goodsId,
			@RequestParam(value = "items", required = false) String items,
			@RequestParam(value = "pNum", required = false) String pNum,
			@RequestParam(value = "pSize", required = false) String pSize
			) {
		int num = 0;
		int size = 0;
		if(!StringUtil.isEmpty(pNum))
		{
			num = Integer.parseInt(pNum);
		}
		if(!StringUtil.isEmpty(pSize))
		{
		
			size = Integer.parseInt(pSize);
		}
		ApiParameter parameter = new ApiParameter(api.trim().toLowerCase(),chan,memberId, cookieId, goodsId, items,num,size);
		return engService.getResult(parameter);
	}
	
	/**
	 * 初始化配置接口
	 */
	@RequestMapping(value = "/init")
	@ResponseBody
	public String init()
	{
		recConf.init();
		
		String ip = null;
		try {
			ip = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		//促销商品
		dataBusService.promGoodsToReids();
		//黑名单
		dataBusService.blackListToRedis();
		//配置熱搜詞
		dataBusService.hotwordsToRedis();
		//品类管理
		dataBusService.promCategoriesToRedis();
		return ip+" reload success";
		
	}

}
