package com.ics.system.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.ics.system.mapper.SysDictionaryMapper;
import com.ics.system.model.SysDictionary;
import com.ics.system.model.SysOrg;
import com.ics.system.service.SysDictionaryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.List;

/**
 * <p>
 * 数据字典表 服务实现类
 * </p>
 *
 * @author yi
 * @since 2018-05-10
 */
@Service
public class SysDictionaryServiceImpl extends ServiceImpl<SysDictionaryMapper, SysDictionary> implements SysDictionaryService {

    @Autowired
    private SysDictionaryMapper sysDictionaryMapper;

    /**
     * 根据字典编码查询字典项id列表
     * @author suxiangrong
     * @date 2020/10/16
     * @param dictCode
     * @return java.util.List<java.lang.String>
     */
    @Override
    public List<String> getIdListByDictCode(String dictCode) {
        List<String> idList = new ArrayList<String>();
        EntityWrapper<SysDictionary> ew = new EntityWrapper<>();
        ew.setEntity(new SysDictionary());
        ew.eq("dict_code", dictCode);
        List<SysDictionary> dictionaryList = sysDictionaryMapper.selectList(ew);
        if (dictionaryList!=null && dictionaryList.size()>0) {
            for (int i=0; i<dictionaryList.size(); ++i) {
                idList.add(dictionaryList.get(i).getId());
            }
        }
        return idList;
    }
}
