package com.example.service;

import cn.hutool.core.date.DateUtil;
import com.example.common.enums.RoleEnum;
import com.example.entity.Account;
import com.example.entity.Record;
import com.example.mapper.RecordMapper;
import com.example.utils.TokenUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 *  就诊记录    表业务处理
 **/
@Service
public class RecordService {

    @Resource
    private RecordMapper recordMapper;

    /**
     * 新增
     */
    public void add(Record record) {
        String time = DateUtil.format(new Date(),"yyyy-MM-dd");
        record.setTime(time);
        recordMapper.insert(record);
    }

    /**
     * 删除
     */
    public void deleteById(Integer id) {

        recordMapper.deleteById(id);
    }

    /**
     * 批量删除
     */
    public void deleteBatch(List<Integer> ids) {
        for (Integer id : ids) {
            recordMapper.deleteById(id);
        }
    }

    /**
     * 修改
     */
    public void updateById(Record record) {
        recordMapper.updateById(record);
    }

    /**
     * 根据ID查询
     */
    public Record selectById(Integer id) {
        return recordMapper.selectById(id);
    }

    /**
     * 查询所有
     */
    public List<Record> selectAll(Record record) {
        return recordMapper.selectAll(record);
    }

    /**
     * 分页查询
     */
    public PageInfo<Record> selectPage(Record record, Integer pageNum, Integer pageSize) {
        Account account =TokenUtils.getCurrentUser();
        if(RoleEnum.DOCTOR.name().equals(account.getRole())){
            record.setDoctorId(account.getId());
        }
        PageHelper.startPage(pageNum, pageSize);
        List<Record> list = recordMapper.selectAll(record);
        return PageInfo.of(list);
    }

}
