package com.ics.utils;

/**
 * 分页类
 */
public class PageBean {

	public static final String PAGING_BEAN = "_paging_bean";

	/**
	 * 每页数据容量
	 */
	public static Integer DEFAULT_PAGE_SIZE = 10;

	/**
	 * 最多显示页码数
	 */
	public static final int SHOW_PAGES = 6;

	/**
	 * 每页开始的索引号
	 */
	public Integer start;

	/**
	 * 页码大小
	 */
	private Integer pageSize;

	/**
	 * 总记录数
	 */
	private Integer totalItems;
	
	/**
	 * 当前页数
	 */
	private Integer pageNo;
	
	/**
	 * 总页数
	 * 
	 * @return
	 */
	private Integer pageNum;
	
	/**
	 * 是否是首页
	 * 
	 * @return
	 */
	private boolean hasPrePage;
	
	/**
	 * 是否是末页
	 * 
	 * @return
	 */
	private boolean hasNextPage;

	private Integer limit;

	public Integer getPageNo() {
		return pageNo;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}

	public PageBean(int start, int limit) {
		this.pageSize = limit;
		this.start = start;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getTotalItems() {
		return totalItems;
	}

	public Integer getStart() {
		return start;
	}

	public void setStart(Integer start) {
		this.start = start;
	}

	public void setTotalItems(Integer totalItems) {
		this.totalItems = totalItems;
	}

	public void setTotalItems(int totalItems) {
		this.totalItems = totalItems;

	}

	public int getFirstResult() {
		return start;
	}

	public Integer getPageNum() {
		return pageNum;
	}

	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}

	public boolean isHasPrePage() {
		return hasPrePage;
	}

	public void setHasPrePage(boolean hasPrePage) {
		this.hasPrePage = hasPrePage;
	}

	public boolean isHasNextPage() {
		return hasNextPage;
	}

	public void setHasNextPage(boolean hasNextPage) {
		this.hasNextPage = hasNextPage;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

}