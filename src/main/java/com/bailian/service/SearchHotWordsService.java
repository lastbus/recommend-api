package com.bailian.service;

import com.bailian.entity.SearchHotWords;
import com.bailian.entity.SearchParameters;

public interface SearchHotWordsService {
	
	public SearchHotWords getSerWords(SearchParameters sp);

}
