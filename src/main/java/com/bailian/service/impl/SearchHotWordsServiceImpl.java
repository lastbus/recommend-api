package com.bailian.service.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.bailian.entity.Goods;
import com.bailian.entity.Keyword;
import com.bailian.entity.SearchHotWords;
import com.bailian.entity.SearchParameters;
import com.bailian.model.CombinedHotwords;
import com.bailian.service.DataService;
import com.bailian.service.SearchHotWordsService;
import com.bailian.utils.StringUtil;

@Service
public class SearchHotWordsServiceImpl implements SearchHotWordsService {

	private static class SearchType {
		static final String LOC_TYPE_INDEX = "index";
		static final String LOC_TYPE_SEARCH = "search";
		static final String LOC_TYPE_CATEGORY = "category";
		static final String LOC_TYPE_PRODUCT = "product";

	}

	@Autowired
	private DataService dataService;

	@Override
	public SearchHotWords getSerWords(SearchParameters sp) {
		String pageType = sp.getPageType();
		List<Keyword> kwList = new ArrayList<Keyword>();
		List<String> blackList = getSerBlackList(sp);
		int chan = sp.getChan();
		// app搜索页
		if (pageType.equalsIgnoreCase(SearchType.LOC_TYPE_SEARCH)
				&& (chan == 1 || chan == 2)) {
			if (chan == 2) {
				pageType = "h5Search";
			} else if (chan == 1) {
				pageType = "appSearch";
			}
			String key = "search_hotword_" + chan + "_" + pageType;
			String chanJsonWords = dataService.get(key);
			List<CombinedHotwords> chanList = JSON.parseArray(chanJsonWords,
					CombinedHotwords.class);
			if (chanList != null) {
				for (CombinedHotwords cw : chanList) {
					Keyword kw = new Keyword();
					kw.setKeyword(cw.getKeyword());
					kw.setUrl(cw.getUrl());
					if (!blackList.contains(cw.getKeyword())
							&& !kwList.contains(kw)) {
						kwList.add(kw);
					}
				}
			}
			if (kwList.size() < sp.getNum()) {
				String indexWords = dataService.get("search_index_hotwords");
				if (!StringUtil.isEmpty(indexWords)) {
					List<String> indList = StringUtil.split(indexWords);
					Random rand = new Random(System.currentTimeMillis());
					int start = rand.nextInt(indList.size() - 11);
					int i = start;
					while (kwList.size() < sp.getNum() && i < indList.size() ) {
						Keyword kw = new Keyword();
						kw.setKeyword(indList.get(i));
						if (!blackList.contains(indList.get(i))) {
							kwList.add(kw);
						}
						
						i++;
					}
					// oom
					indList = null;
				}
			}
			SearchHotWords shw = new SearchHotWords(sp, kwList);
			return shw;
		}

		// 首页 已测0803 hjt
		if (pageType.equalsIgnoreCase(SearchType.LOC_TYPE_INDEX)) {

			String key = "search_hotword_" + chan + "_" + pageType;
			String chanJsonWords = dataService.get(key);
			List<CombinedHotwords> chanList = JSON.parseArray(chanJsonWords,
					CombinedHotwords.class);

			key = "search_hotword_0_" + pageType;
			String allJsonWords = dataService.get(key);
			List<CombinedHotwords> allList = JSON.parseArray(allJsonWords,
					CombinedHotwords.class);
			if (chanList != null) {
				for (CombinedHotwords cw : chanList) {
					Keyword kw = new Keyword();
					kw.setKeyword(cw.getKeyword());
					kw.setUrl(cw.getUrl());
					if (!blackList.contains(cw.getKeyword())
							&& !kwList.contains(kw)) {
						kwList.add(kw);
					}
				}
				//oom
				chanList = null;
			}
			if (allList != null) {
				for (CombinedHotwords cw : allList) {
					Keyword kw = new Keyword();
					kw.setKeyword(cw.getKeyword());
					kw.setUrl(cw.getUrl());
					if (!blackList.contains(cw.getKeyword())
							&& !kwList.contains(kw)) {
						kwList.add(kw);
					}
				}
				allList = null;
			}

		}
		// 搜词
		if (pageType.equalsIgnoreCase(SearchType.LOC_TYPE_SEARCH) && chan == 3) {
			String serwd = sp.getSerwd();
			if (!StringUtil.isEmpty(serwd)) {
				String simWds = dataService.get("search_term_sim_" + serwd);
				List<String> simList = StringUtil.split(simWds);
				if (simList != null) {
					for (String wd : simList) {
						Keyword kw = new Keyword();
						kw.setKeyword(wd);
						try {
							kw.setUrl(URLEncoder.encode(
									"http://search.bl.com/k-" + wd + ".html",
									"UTF-8"));
						} catch (UnsupportedEncodingException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						if (!blackList.contains(kw.getKeyword())
								&& !kwList.contains(kw)) {
							kwList.add(kw);
						}

					}
					//oom
					simList = null;
				}
			}
			String category = dataService.get("search_term_category_" + serwd);
			List<CombinedHotwords> confWords = new ArrayList<CombinedHotwords>();
			if (category != null) {
				String[] cateArray = category.split("#");
				for (String cn : cateArray) {
					if (StringUtil.isEmpty(cn)) {
						continue;
					}
					String[] idn = cn.split(":");
					if (idn != null && idn.length == 2) {
						String categoryId = idn[0];
						List<CombinedHotwords> searchConfWords = getSearchConfWords(
								sp, categoryId);
						if (searchConfWords != null) {
							confWords.addAll(searchConfWords);
						}
					}
				}
			}

			if (confWords != null) {
				for (CombinedHotwords cw : confWords) {
					Keyword kw = new Keyword();
					kw.setKeyword(cw.getKeyword());
					kw.setUrl(cw.getUrl());
					if (!blackList.contains(cw.getKeyword())
							&& !kwList.contains(kw)) {
						kwList.add(0, kw);
					}
				}
				//oom
				confWords = null;
			}

		}
		// 品类 已测
		if (pageType.equalsIgnoreCase(SearchType.LOC_TYPE_CATEGORY)
				|| pageType.equals(SearchType.LOC_TYPE_PRODUCT)) {
			String cateId = sp.getCateId();
			String cwords = dataService.get("search_cate_hotwords_" + cateId);
			if (!StringUtil.isEmpty(cwords)) {
				List<String> tmpList = StringUtil.split(cwords);
				for (String wd : tmpList) {
					if (StringUtil.isEmpty(wd)) {
						continue;
					}
					Keyword kw = new Keyword();
					kw.setKeyword(wd);
					try {
						kw.setUrl(URLEncoder.encode("http://search.bl.com/k-"
								+ wd + ".html", "UTF-8"));
					} catch (UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if (!blackList.contains(kw.getKeyword())
							&& !kwList.contains(kw)) {
						kwList.add(kw);
					}
				}
				//oom
				tmpList = null; 
			}
			if (kwList.size() < sp.getNum()) {
				String subCateIds = dataService.get("search_sub_sch_cate_"
						+ cateId);
				if (subCateIds != null) {
					List<String> subCateList = StringUtil.split(subCateIds);
					int npc = (int) Math.ceil(sp.getNum() * 1.0
							/ subCateList.size());
					for (String cId : subCateList) {
						String tmp = dataService.get("search_cate_hotwords_"
								+ cId);
						if (tmp == null)
							continue;
						List<String> tmpList = StringUtil.split(tmp);
						int anum = 0;
						for (String wd : tmpList) {
							if (StringUtil.isEmpty(wd)) {
								continue;
							}
							Keyword kw = new Keyword();
							kw.setKeyword(wd);
							try {
								kw.setUrl(URLEncoder.encode(
										"http://search.bl.com/k-" + wd
												+ ".html", "UTF-8"));
							} catch (UnsupportedEncodingException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							if (!blackList.contains(kw.getKeyword())
									&& !kwList.contains(kw)) {
								kwList.add(kw);
								anum++;
							}
							if (anum >= npc) {
								break;
							}
						}
						//oom
						tmpList = null;

					}
					// 1031 oom
					subCateList = null;
				}

			}// 非末级类目
				// 类目配置词
			List<CombinedHotwords> searchConfWords = getSearchConfWords(sp,
					cateId);
			if (searchConfWords != null) {
				for (CombinedHotwords cw : searchConfWords) {
					Keyword kw = new Keyword();
					kw.setKeyword(cw.getKeyword());
					kw.setUrl(cw.getUrl());
					if (!blackList.contains(cw.getKeyword())
							&& !kwList.contains(kw)) {
						kwList.add(0, kw);
					}
					else
					{
						kwList.remove(kw);
						kwList.add(0, kw);
					}
				}
				// 1031 oom
				searchConfWords = null; 
			}
			

		}// category

		if (kwList.size() < sp.getNum()) {
			String indexWords = dataService.get("search_index_hotwords");
			if (!StringUtil.isEmpty(indexWords)) {
				List<String> indList = StringUtil.split(indexWords);
				Random rand = new Random(System.currentTimeMillis());
				int start = rand.nextInt(indList.size() - 11);
				for (int i = start; i < start + sp.getNum(); i++) {
					Keyword kw = new Keyword();
					kw.setKeyword(indList.get(i));
					try {
						kw.setUrl(URLEncoder.encode("http://search.bl.com/k-"
								+ indList.get(i) + ".html", "UTF-8"));
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
					if (!blackList.contains(kw.getKeyword())) {
						kwList.add(kw);
					}
				}
				//1031 oom
				indList = null;
			}
		}

		SearchHotWords shw = new SearchHotWords(sp, kwList);
		return shw;
	}

	/**
	 * 获取配置关键字
	 * 
	 * @param sp
	 * @return
	 */
	List<CombinedHotwords> getSearchConfWords(SearchParameters sp,
			String categoryId) {
		int chan = sp.getChan();
		String pageType = sp.getPageType();
		String key = "search_hotword_" + chan + "_" + pageType;
		if (categoryId != null) {
			key = "search_hotword_" + chan + "_" + pageType + "_" + categoryId;
		}
		String jsonWords = dataService.get(key);
		if (jsonWords != null) {
			List<CombinedHotwords> chList = JSON.parseArray(jsonWords,
					CombinedHotwords.class);
			return chList;
		}
		return null;
	}

	List<String> getSerBlackList(SearchParameters sp) {
		int chan = sp.getChan();
		String pageType = sp.getPageType();
		String key = "search_black_" + chan + "_" + pageType;
		String bwords = dataService.get(key);
		List<String> blackList = new ArrayList<String>();
		List<String> locList = new ArrayList<String>();
		if (bwords != null) {
			locList = StringUtil.split(bwords);
		}
		blackList.addAll(locList);

		key = "search_black_" + "0" + "_" + "0";
		bwords = dataService.get(key);
		locList = new ArrayList<String>();
		if (bwords != null) {
			locList = StringUtil.split(bwords);
		}
		blackList.addAll(locList);
		locList = null;
		key = "search_black_" + "0" + "_" + pageType;
		bwords = dataService.get(key);
		locList = new ArrayList<String>();
		if (bwords != null) {
			locList = StringUtil.split(bwords);
		}
		blackList.addAll(locList);
		//oom
		locList = null;
		key = "search_black_" + chan + "_" + "0";
		bwords = dataService.get(key);
		locList = new ArrayList<String>();
		if (bwords != null) {
			locList = StringUtil.split(bwords);
		}
		blackList.addAll(locList);
		//oom
		locList = null;
		return blackList;
	}

	boolean addHotword(List<String> list, List<String> blackList, String wd) {
		if ((blackList == null || !blackList.contains(wd.toLowerCase().trim()))
				&& !StringUtil.isEmpty(wd) && wd.trim().length() < 12
				&& !list.contains(wd.trim().toLowerCase())) {
			// list词长包含
			boolean cont = false;
			String sword = null;
			// for(String s:list)
			// {
			// //新加入短
			// if(s.contains(wd.trim()))
			// {
			// cont = true;
			// }else if(wd.trim().toLowerCase().contains(s))
			// {
			// sword = s;
			// break;
			// }
			// }
			list.add(wd.trim().toLowerCase());
			return true;
			// 不被任何词包含
			// if(!cont)
			// {
			// list.add(wd.trim().toLowerCase());
			// }
			// if(sword!=null)
			// {
			// list.remove(sword);
			// list.add(wd.trim().toLowerCase());
			// }

		}
		return false;

	}

}
