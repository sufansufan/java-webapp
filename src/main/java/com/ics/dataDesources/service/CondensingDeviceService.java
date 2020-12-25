package com.ics.dataDesources.service;

import com.ics.dataDesources.model.CondensingDevice;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

/**
 * 冷凝机设备信息 服务类
 * @author jjz
 *
 * 2019年11月26日
 */
public interface CondensingDeviceService extends IService<CondensingDevice> {
	Page<CondensingDevice> selectRelationPageList(Page<CondensingDevice> page, @Param("ew") Wrapper<CondensingDevice> wrapper);

	List<CondensingDevice> selectRelationList(@Param("ew") Wrapper<CondensingDevice> wrapper);
}
