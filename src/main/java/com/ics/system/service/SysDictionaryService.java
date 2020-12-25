package com.ics.system.service;

import com.baomidou.mybatisplus.service.IService;
import com.ics.system.model.SysDictionary;

import java.util.List;

/**
 * <p>
 * 数据字典表 服务类
 * </p>
 *
 * @author yi
 * @since 2018-05-10
 */
public interface SysDictionaryService extends IService<SysDictionary> {
    /**
     * 根据字典编码查询字典项id列表
     * @author suxiangrong
     * @date 2020/10/16
     * @param dictCode
     * @return java.util.List<java.lang.String>
     */
    List<String> getIdListByDictCode(String dictCode);
}
