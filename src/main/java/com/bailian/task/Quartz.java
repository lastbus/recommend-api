package com.bailian.task;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.bailian.configuration.RecommendConfiguration;
import com.bailian.service.ApiConfigService;
import com.bailian.service.DataBusService;
import com.bailian.service.RedisKeyService;

@Controller
public class Quartz {
	@Autowired
	private ApiConfigService apiConfService;

	@Autowired
	private RecommendConfiguration recConf;

	@Autowired
	DataBusService dataBusService;

	/**
	 * 定时任务，执行方法
	 * */
	public void tips() {
		String time = new SimpleDateFormat("MMM d，yyyy KK:mm:ss a",
				Locale.ENGLISH).format(System.currentTimeMillis());
	}

	public void loadConfiguration() {
		recConf.init();
		//每天更新一次
		loadPromotion();
	}

	public void loadPromotion() {
		
		// 促销商品
		dataBusService.promGoodsToReids();
		// 黑名单
		dataBusService.blackListToRedis();
		// 配置熱搜詞
		dataBusService.hotwordsToRedis();
		// 品类管理
		dataBusService.promCategoriesToRedis();
	}
}
