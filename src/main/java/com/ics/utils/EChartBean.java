package com.ics.utils;

import java.util.List;

/**
 * 统计报表的模型
 *
 * @author yi
 * @data 2017年11月29日 下午8:15:30
 */
public class EChartBean {
	
	/**
	 * x轴坐标
	 */
	private List<?> xAxiDataList; 
   
	/**
	 * 数据
	 */
	private List<?> series; 
	
	public List<?> getSeries() {
		return series;
	}
	public void setSeries(List<?> series) {
		this.series = series;
	}
	public List<?> getxAxiDataList() {
		return xAxiDataList;
	}
	public void setxAxiDataList(List<?> xAxiDataList) {
		this.xAxiDataList = xAxiDataList;
	}
}
