package com.bailian.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import redis.clients.jedis.JedisCluster;

import com.bailian.entity.RecommendationResult;
import com.bailian.service.ApiConfigService;


@Controller
@RequestMapping("/")
public class DeprecatedRecController {

	private static final Logger logger = LoggerFactory.getLogger(DeprecatedRecController.class);

	@Autowired
	RecommendController recController;

	/**
	 * pc端猜你喜欢 
	 * @param mId
	 * @return
	 */
	@RequestMapping(value = "/pcgl")
	@ResponseBody
	public RecommendationResult getPCGL(@RequestParam(value = "mId", required = false) String mId) {
		
		return recController.getRec("pcgl", 3, mId, null, null, null, "1", "30");
	}
	
	@RequestMapping(value = "/view")
	@ResponseBody
	public RecommendationResult getView(@RequestParam("gId") String goods_id,
			@RequestParam(value = "cookieId", required = false) String cookieId) {
		return recController.getRec("view", 3, null, cookieId, goods_id, null, null, null);
	}
	@RequestMapping(value = "/shop")
	@ResponseBody
	public RecommendationResult getShop(@RequestParam("gId") String goods_id,
			@RequestParam(value = "cookieId", required = false) String cookieId) {
		return recController.getRec("shop", 3, null, cookieId, goods_id, null, null, null);
	}	
	
	@RequestMapping(value = "/hotsale")
	@ResponseBody
	public RecommendationResult getHotsale(@RequestParam("gId") String goods_id,
			@RequestParam(value = "cookieId", required = false) String cookieId) {
		return recController.getRec("hotsale", 3, null, cookieId, goods_id, null, null, null);
	}	
	
	
	@RequestMapping(value = "/histgl")
	@ResponseBody
	public RecommendationResult getHistgl(@RequestParam("items") String items,
			@RequestParam(value = "cookieId", required = false) String cookieId) {
		return recController.getRec("histgl", 3, null, cookieId, null, items, null, null);
	}
	
	@RequestMapping(value = "/appgl")
	@ResponseBody
	public RecommendationResult getAppgl(@RequestParam(value = "mId", required = false) String mId,
			@RequestParam(value = "pNum", required = true) int pNum,
			@RequestParam(value = "pSize", required = true) int pSize) {
		return recController.getRec("gyl",1, mId, null, null, null, String.valueOf(pNum), String.valueOf(pSize));
	}	
	
	@RequestMapping(value = "/dpgl")
	@ResponseBody
	public RecommendationResult getDpgl(@RequestParam(value = "mId", required = false) String mId,
			@RequestParam(value = "gId", required = true) String gId,
			@RequestParam(value = "chan", required = true) int chan) {
		return recController.getRec("dpgl", chan, mId, null, gId, null, null, null);
	}
	
	@RequestMapping(value = "/mshop")
	@ResponseBody
	public RecommendationResult getmshop(@RequestParam(value = "mId", required = false) String mId,
			@RequestParam(value = "gId", required = true) String gId,
			@RequestParam(value = "chan", required = true) int chan) {
		return recController.getRec("mshop", chan, mId, null, gId, null, null, null);
	}
	
	@RequestMapping(value = "/bab")
	@ResponseBody
	public RecommendationResult getBab(@RequestParam(value = "mId", required = false) String mId,
			@RequestParam(value = "gId", required = true) String gId,
			@RequestParam(value = "chan", required = true) int chan) {
		return recController.getRec("bab", chan, mId, null, gId, null, null, null);
	}	
	
	@RequestMapping(value = "/mhotsale")
	@ResponseBody
	public RecommendationResult getmhotsale(@RequestParam(value = "mId", required = false) String mId,
			@RequestParam(value = "gId", required = true) String gId,
			@RequestParam(value = "chan", required = true) int chan) {
		return recController.getRec("mhotsale", chan, mId, null, gId, null, null, null);
	}	

	@RequestMapping(value = "/brec")
	@ResponseBody
	public RecommendationResult getBrec(@RequestParam(value = "mId", required = false) String mId,
			@RequestParam(value = "pNum", required = true) int pNum,
			@RequestParam(value = "pSize", required = true) int pSize,
			@RequestParam(value = "chan", required = true) int chan) {
		return recController.getRec("brec", chan, mId, null, null, null, String.valueOf(pNum), String.valueOf(pSize));
	}	
	
	@RequestMapping(value = "/afpay")
	@ResponseBody
	public RecommendationResult getAfPay(@RequestParam(value = "mId", required = true) String mId,
			@RequestParam(value = "items", required = false) String items,
			@RequestParam(value = "chan", required = true) int chan) {
		return recController.getRec("afpay",chan, mId, null, null, null, "1", "36");
	}	

}
