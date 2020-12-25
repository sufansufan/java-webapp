package com.ics.utils;

/**
 * <p>Title: PagingBean</p>
 * <p>Description: 分页公共类</p>
 * @author yi
 * @date 2017年10月25日 下午4:34:00
 */
public class PagingBean extends JsonResult{


	private int page;
	private int limit;
	private int start;
	
	/**
	 * 数据总数
	 */
	private int count;
	/**
	 * 总页数
	 */
	private int pages;

	
	public PagingBean() {}
	
	public PagingBean(int page, int limit, int start, int count, int pages) {
		this.page = page;
		this.limit = limit;
		this.start = start;
		this.count = count;
		this.pages = pages;
	}
	
	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
	
	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	/**
	 * @Title: page
	 * @Description: 设置分页
	 * @param bean
	 * @param page
	 * @param limit
	 * @return
	 */
	public static PagingBean page(int page, int limit, int count){
		
		if(page==0) page=1;
		int start = (page-1)*limit;
		
		int pages = count/limit;
		if(count%limit>0) pages++;
		
		PagingBean bean = new PagingBean(page, limit, start, count, pages) ;
		
		return bean;
	}

	public int getPages() {
		return pages;
	}

	public void setPages(int pages) {
		this.pages = pages;
	}
}
