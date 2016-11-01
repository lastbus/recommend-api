package com.bailian.configuration;


public class KafkaConf {
	//test
//	public static  String bootstrap_servers="10.201.129.74:9092,10.201.129.75:9092,10.201.129.81:9092";
//	public static String metadata_broker_list="10.201.129.74:9092,10.201.129.75:9092,10.201.129.81:9092";
//	public static String topic = "recommend_trace";	

	//pre
//	public static  String bootstrap_servers="10.201.48.103:9092,10.201.48.104:9092,10.201.48.105:9092";
//	public static String metadata_broker_list="10.201.48.103:9092,10.201.48.104:9092,10.201.48.105:9092";

	// online
	public static String bootstrap_servers="10.201.48.39:9092,10.201.48.40:9092,10.201.48.41:9092";
	public static String metadata_broker_list="10.201.48.39:9092,10.201.48.40:9092,10.201.48.41:9092";
	public static String topic = "recommend_track";
	
	public static int kafka_write_num=1;
	public static int kafka_cache_size=1;

	//online


	
}
