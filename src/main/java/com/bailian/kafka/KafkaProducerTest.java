package com.bailian.kafka;

import com.alibaba.fastjson.JSON;

public class KafkaProducerTest {
	
	public static void main(String [] args) throws InterruptedException
	{
		ExposureObservable vrb = new ExposureObservable();
		ExposureObserver vro = new ExposureObserver(); 
		vrb.addObserver(vro);
		for(long i=0;i<10000000000000l;i++)
		{
			System.out.println(i + "<--add");
			vrb.AddRecord(i+"李克强指出，振兴东北要以更有力的举措抓好稳增长和保民生。要稳投资稳消费，围绕补短板、增后劲、惠民生，抓紧推进已纳入“十三五”规划和东北振兴三年滚动实施方案的项目建设，尤其要激发社会投资活力。积极发展服务业，培育养老、旅游、文化等新消费增长点，尽快扭转经济增速下行态势。要保就业保民生，政策和财政资金向促进就业和保障养老金支付倾斜，兜牢民生底线，维护社会和谐稳定。要抓重点企业抓特殊困难地区，有序退出过剩产能，支持资源枯竭产业衰退地区转型，精准施策促进升级发展。"
					+ "　　李克强说，要通过不断深化改革添动力。东北地区要全面对标国内先进地区，加快转变政府职能，更大力度推进简政放权、放管结合、优化服务改革，开展优化投资营商环境专项行动，推动“法治东北、信用东北”建设，实行企业投资项目管理负面清单制度，试点市场准入负面清单制度，保护各种所有制经济产权。坚持两个“毫不动摇”，推动国企深化改革，加快转型升级，出台深化东北地区国有企业改革专项工作方案，支持部分中央企业开展混合所有制改革试点，促进民营经济发展，增强民营企业发展信心，选择一批收益可预期的优质项目实施政府和社会资本合作模式，增强东北经济活力。");
		}
	}
}
