package com.bailian.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bailian.configuration.RecommendConfiguration;
import com.bailian.entity.ApiParameter;
import com.bailian.entity.Goods;
import com.bailian.model.SourceData;
import com.bailian.service.DataService;
import com.bailian.service.GoodsService;
@Service
public class GoodsServiceImpl implements GoodsService {

	@Autowired
	DataService dataServcie;
	
	@Override
	public Goods get(String goodsId,int chan) {
		SourceData sourcedata = null;
		switch(chan)
		{
		case 1:
			sourcedata = RecommendConfiguration.soureKeyMap.get("ORIGINAL_APP");
			break;
		case 2:
			sourcedata = RecommendConfiguration.soureKeyMap.get("ORIGINAL_H5");
			break;
		case 3:
			sourcedata = RecommendConfiguration.soureKeyMap.get("ORIGINAL_PC");
			break;
		}
		Map<String,String> gmap = dataServcie.hgetAll(sourcedata.getKeyPrefix()+goodsId);
		
		if(gmap!=null && gmap.size()!=0){
			Goods goods = new Goods(gmap);
			return goods;
		}
		return null;
	}

	@Override
	public boolean isNew(String goodsId, int chan) {
		Goods goods = get( goodsId, chan);
		return goods==null;
	}

	@Override
	public List<Goods> forSaleFilter(List<String> goodsList, int chan,String sourceCode,ApiParameter apiParameter) {
		
		if(goodsList == null || goodsList.size()==0)
			return null;
		List<Goods> resGoodsList = new ArrayList<Goods>();
		for(String gId:goodsList)
		{
			Goods goods = get(gId,chan);
			//sale_status==4可售 0 下架
			if(goods == null ||goods.getSale_status() != 4 ||goods.getStock() != 1 ||goods.getGoods_sales_name()==null)
			{
				continue;
			}
			goods.setFrom(sourceCode);
			if(!resGoodsList.contains(goods))
			{
				//试用品要求同一业态
				if(apiParameter.getApi().equals("trial"))
				{
					String goodsId = apiParameter.getGoodsId();
					Goods trialGoods = get(goodsId,chan);
					if(trialGoods == null)
					{
						return null;
					}
					int comSid = trialGoods.getCom_sid();
					//非商家
					if(comSid != 4000)
					{
						int rcomSid = goods.getCom_sid();
						//要求业态一致
						if(comSid == rcomSid)
						{
							resGoodsList.add(goods);
						}
					}
					else{
						resGoodsList.add(goods);
					}
				}
				else
				{
					String goodsId = apiParameter.getGoodsId();
					
					//1027 屏蔽掉商家商品
					//无输入商品参数栏位：猜你喜欢之类接口
					if(goodsId == null)
					{
						int rcomSid = goods.getCom_sid();
						//业态4000为MP
						if(rcomSid != 4000)
						{
							resGoodsList.add(goods);
						}
					}
					else{
						resGoodsList.add(goods);
					}
				}
			}
		}
		//1025 oom
		goodsList = null;
		return resGoodsList;
	}

}
