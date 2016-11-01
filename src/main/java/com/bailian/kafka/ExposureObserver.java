package com.bailian.kafka;

import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.bailian.configuration.KafkaConf;

public class ExposureObserver implements Observer {
	static ExecutorService singleThreadPool = Executors.newSingleThreadExecutor();
	//static ExecutorService fixedThreadPool = Executors.newFixedThreadPool(3);
	@Override
	public void update(Observable obv, Object ob) {
		ExposureObservable vr = (ExposureObservable) obv;
//		Producer prd = new Producer(vr,null,KafkaConf.topic);
//		prd.start();		

		singleThreadPool.execute(new Producer(vr,null,KafkaConf.topic) );
//		fixedThreadPool.execute(new Producer(vr,null,KafkaConf.topic) );
	
				
		
	}
}