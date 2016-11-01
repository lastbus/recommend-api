package com.bailian.service.impl;

import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bailian.configuration.KafkaConf;
import com.bailian.entity.ApiParameter;
import com.bailian.entity.RecommendationResult;
import com.bailian.kafka.ExposureObservable;
import com.bailian.kafka.ExposureObserver;
import com.bailian.kafka.Producer;
import com.bailian.kafka.ThreadProducer;
import com.bailian.model.RecommendApi;
import com.bailian.service.TrackService;
import com.bailian.utils.StringUtil;


/**
 * kafka记录用户行为及曝光
 * 
 * @author haojutao
 *
 */
@Service
public class TrackServiceImpl implements TrackService {

	//内存old gen增长有关?
//	public  ExposureObservable expble ;
//	public  ExposureObserver expver;
	static ExecutorService singleThreadPool = Executors.newSingleThreadExecutor();
	

	@Override
	public void track(RecommendApi recApi, ApiParameter apiParameter,
			RecommendationResult recResult) {
		String api = apiParameter.getApi();
		int chan = 3;
		String schan = apiParameter.getChan();
		if (!StringUtil.isEmpty(schan)) {
			chan = Integer.parseInt(schan.trim());
		}
		String channel = "PC";
		switch (chan) {
		case 1:
			channel = "App";
			break;
		case 2:
			channel = "H5";
			break;
		case 3:
			channel = "PC";
			break;
		}
		// 0：不记录 1推荐结果 2浏览参数
		int trackType = recApi.getapiTrack();
		switch (trackType) {
		case 0:
			break;
		case 1:
			String memberId = apiParameter.getMemberId();
			JSONObject jo = new JSONObject();
			//memberId不为空track
			if (!StringUtil.isEmpty(memberId)) {
//				expble = new ExposureObservable();
//				expver = new ExposureObserver();
				//expble.addObserver(expver);
				jo.put("trackType", trackType);
				jo.put("actType", api);
				jo.put("channel", channel);
				jo.put("memberId", apiParameter.getMemberId());
				jo.put("eventDate", String.valueOf((new Date()).getTime()));
				jo.put("recResult", recResult);
				//expble.AddRecord(JSON.toJSON(jo).toString());
				singleThreadPool.execute(new ThreadProducer(null,KafkaConf.topic,JSON.toJSON(jo).toString()));
			}
			break;
		case 2:
//			expble = new ExposureObservable();
//			expver = new ExposureObserver();
//			expble.addObserver(expver);
			jo = new JSONObject();
			jo.put("trackType", trackType);
			jo.put("actType", api);
			jo.put("channel", channel);
			jo.put("cookieId", apiParameter.getCookieId());
			jo.put("memberId", apiParameter.getMemberId());
			jo.put("eventDate", String.valueOf((new Date()).getTime()));
			jo.put("goodsId", apiParameter.getGoodsId());
			//expble.AddRecord(JSON.toJSON(jo).toString());
			singleThreadPool.execute(new ThreadProducer(null,KafkaConf.topic,JSON.toJSON(jo).toString()));

			break;
		}

	}

}
