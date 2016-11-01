package com.bailian.kafka;

import java.util.Properties;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.bailian.configuration.KafkaConf;

//替代观察者模式
public class ThreadProducer implements Runnable{

	private static KafkaProducer<Integer, String> producer;
	//private static String topic;
	private String msg;

	public ThreadProducer(String topic, String client_id,String msg) {
		if (producer == null) {
			Properties props = new Properties();
			props.put("metadata.broker.list", KafkaConf.metadata_broker_list);
			props.put("bootstrap.servers", KafkaConf.bootstrap_servers);
			props.put("client.id", client_id);
			props.put("key.serializer", "org.apache.kafka.common.serialization.IntegerSerializer");
			props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
			producer = new KafkaProducer<Integer, String>(props);
		}
		//this.topic = topic;
		this.msg = msg;
	}

	public void run() {
		{
			producer.send(
					new ProducerRecord<Integer, String>(KafkaConf.topic, msg),
					new DemoCallBack2(1L, 2, "track"));
		}

		
	}
}

class DemoCallBack2 implements Callback {

	private long startTime;
	private int key;
	private String message;

	public DemoCallBack2(long startTime, int key, String message) {
		this.startTime = startTime;
		this.key = key;
		this.message = message;
	}

	/**
	 * A callback method the user can implement to provide asynchronous handling
	 * of request completion. This method will be called when the record sent to
	 * the server has been acknowledged. Exactly one of the arguments will be
	 * non-null.
	 *
	 * @param metadata
	 *            The metadata for the record that was sent (i.e. the partition
	 *            and offset). Null if an error occurred.
	 * @param exception
	 *            The exception thrown during processing of this record. Null if
	 *            no error occurred.
	 */
	public void onCompletion(RecordMetadata metadata, Exception exception) {
		long elapsedTime = System.currentTimeMillis() - startTime;
		if (metadata != null) {
			// System.out.println("message(" + key + ", " + message + ") sent to
			// partition(" + metadata.partition() + "), "
			// + "offset(" + metadata.offset() + ") in " + elapsedTime + " ms");
		} else {
			exception.printStackTrace();
		}
	}
}