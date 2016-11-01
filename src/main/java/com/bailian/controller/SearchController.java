package com.bailian.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bailian.entity.SearchHotWords;
import com.bailian.entity.SearchParameters;
import com.bailian.service.DataBusService;
import com.bailian.service.SearchHotWordsService;
import com.bailian.utils.StringUtil;

@Controller
@RequestMapping("/")
public class SearchController {
	
	@Autowired
	private SearchHotWordsService searchService;
	
	@Autowired
	private DataBusService dbService;
	
	
	/**
	 * 前台类目
	 * @param locaton
	 * @param pageType
	 * @param num
	 * @return
	 */
	@RequestMapping(value = "/serwd")
	@ResponseBody
	public SearchHotWords getWords(
			@RequestParam(value = "pageType", required = true) String pageType,
			@RequestParam(value = "chan", required = true) int chan,
			@RequestParam(value = "cateId", required = false) String cateId,
			@RequestParam(value = "term", required = false) String term,
			@RequestParam(value = "num", required = false) String num
			) {
		if(StringUtil.isEmpty(num))
		{
			num = "10";
		}
		SearchParameters sp = new	SearchParameters( pageType, chan, cateId, term, num);
		return searchService.getSerWords(sp);	
	}

	
	@RequestMapping(value = "/loadwords")
	@ResponseBody
	public String init()
	{
		dbService.blackListToRedis();
		dbService.hotwordsToRedis();
		return "hotword load completed";
	}
}
