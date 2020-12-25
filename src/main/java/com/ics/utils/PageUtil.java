package com.ics.utils;

import org.apache.commons.lang.math.NumberUtils;

import com.alibaba.druid.util.StringUtils;

/**
 * 对分页进行了封装，方便调用
 * 
 * @author wull
 * 
 */
public class PageUtil {

	protected static PageBean pageBean;

	/**
	 * 公用的分页方法
	 * 
	 * @param s_start
	 * @param s_limit
	 * @param count
	 */
	public static PageBean page(String s_start, String s_limit, int count,
			int defaultPageSize) {
		int start = 1;
		int limit = defaultPageSize;
		if (!StringUtils.isEmpty(s_start)) {
			start = NumberUtils.toInt(s_start);
		}
		if (!StringUtils.isEmpty(s_limit)) {
			limit = NumberUtils.toInt(s_limit);
		}
		start = (start - 1) * limit;
		pageBean = new PageBean(start, limit);
		pageBean.setTotalItems(count);// 设置总记录数
		int pageNo = start / limit;
		pageBean.setPageNo(pageNo + 1);// 当前页数
		pageBean.setStart(start);
		pageBean.setLimit(limit);
		int pageNum = 0;
		if (count % limit == 0) {
			pageNum = count / limit;
		} else {
			pageNum = (count / limit) + 1;
		}
		pageBean.setPageNum(pageNum);
		if ((pageNo + 1) == 1) {
			pageBean.setHasPrePage(false);
		} else {
			pageBean.setHasPrePage(true);
		}
		if ((pageNo + 1) == pageNum) {
			pageBean.setHasNextPage(false);
		} else {
			if (pageNum > 0) {
				pageBean.setHasNextPage(true);
			} else {
				pageBean.setHasNextPage(false);
			}

		}
		return pageBean;
	}
	
}