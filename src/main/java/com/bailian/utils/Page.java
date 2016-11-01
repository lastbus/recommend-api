package com.bailian.utils;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

/**
 * @ClassName: Page
 * @Description: 分页
 * @author zhangwenming
 * @date 2015年12月10日 上午11:11:36
 * @param <E>
 */
public class Page<E> {

	private static final Logger logger = LoggerFactory.getLogger(Page.class);

	private int pageNum;
	private int pageSize;
	private int startRow;
	private int endRow;
	private long totalRecords;
	private int totalPage;
	private List<E> result;

	/**
	 * @Description: 开始分页, 设置分页信息
	 * @param clazz
	 *            返回对一个的javabean Class
	 * @param pageNum
	 * @param pageSize
	 * @return Page<T>
	 * @date 2015年12月10日 上午11:11:26
	 * @throws
	 */
	public static <T> Page<T> startPage(Class<T> clazz, int pageNum,
			int pageSize) {
		return PageHelper.startPage(clazz, pageNum, pageSize);
	}

	/**
	 * @Description: 转换为分页信息
	 * @param origList
	 *            原始的bean 集合
	 * @return Page<T>
	 * @date 2015年12月10日 上午11:17:58
	 * @throws
	 */
	public Page<E> toPage(List<E> origList) {
		this.setResult(origList);
		return this;
	}

	/**
	 * @param <T>
	 * @Description: bean属性拷贝（含分页信息）
	 * @param clazz
	 *            需要转为的新的bean Class
	 * @param origList
	 *            原始的bean 集合
	 * @return Page<T>
	 * @date 2015年12月10日 上午11:12:51
	 * @throws
	 */
	public <T> Page<T> toPage(Class<T> clazz, List<E> origList) {
		// bean集合 属性拷贝
		List<T> destList = new ArrayList<T>();
		T newInstance = null;
		for (E orig : origList) {
			try {
				newInstance = clazz.newInstance();
				BeanUtils.copyProperties(newInstance, orig);
				destList.add(newInstance);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}

		// 分页信息拷贝
		Page<T> newPage = new Page<T>(this.pageNum, this.pageSize,
				this.startRow, this.endRow, this.totalRecords, this.totalPage);
		newPage.setResult(destList);
		return newPage;
	}

	public Page() {
		super();
	}

	public Page(int pageNum, int pageSize) {
		this.pageNum = pageNum;
		this.pageSize = pageSize;
		this.startRow = pageNum > 0 ? (pageNum - 1) * pageSize : 0;
		this.endRow = pageNum * pageSize;
	}
	
	public Page(int pageNum, int pageSize, int startRow, int endRow,
			long totalRecords, int totalPage) {
		super();
		this.pageNum = pageNum;
		this.pageSize = pageSize;
		this.startRow = startRow;
		this.endRow = endRow;
		this.totalRecords = totalRecords;
		this.totalPage = totalPage;
	}

	public List<E> getResult() {
		return result;
	}

	public void setResult(List<E> result) {
		this.result = result;
	}

	public int getEndRow() {
		return endRow;
	}

	public void setEndRow(int endRow) {
		this.endRow = endRow;
	}

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getStartRow() {
		return startRow;
	}

	public void setStartRow(int startRow) {
		this.startRow = startRow;
	}

	public long getTotalRecords() {
		return totalRecords;
	}

	public void setTotalRecords(long totalRecords) {
		this.totalRecords = totalRecords;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	@Override
	public String toString() {
		return "Page{pageNum=" + pageNum + ", pageSize=" + pageSize
				+ ", startRow=" + startRow + ", endRow=" + endRow
				+ ", totalRecords=" + totalRecords + ", totalPage=" + totalPage
				+ ", result=" + result + "}";
	}



}