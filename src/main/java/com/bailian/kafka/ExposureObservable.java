package com.bailian.kafka;

import java.util.LinkedList;
import java.util.Observable;
import java.util.Queue;

import com.bailian.configuration.KafkaConf;

public class ExposureObservable extends Observable {

	KafkaConf kafkaConf = new KafkaConf();;

	//public static Queue<String> recQueue = new LinkedList<String>();

	public static MyQueue recQueue = new MyQueue();
	public void AddRecord(String eo) {
	   if(recQueue.getSize()<1000)
		{
		   recQueue.add(eo);
		}	
		if (recQueue.getSize() > 10) {
			this.setChanged();
			this.notifyObservers(this);
		}
	}

	public String get() {
		if(recQueue.getSize() > 0)
		{
			return recQueue.remove();
		}
		return null;
	}
}
