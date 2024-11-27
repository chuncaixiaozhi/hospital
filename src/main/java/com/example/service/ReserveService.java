package com.example.service;

import cn.hutool.core.date.DateUtil;
import com.example.common.enums.CallEnum;
import com.example.common.enums.ResultCodeEnum;
import com.example.common.enums.RoleEnum;
import com.example.entity.Account;
import com.example.entity.Reserve;
import com.example.exception.CustomException;
import com.example.mapper.ReserveMapper;
import com.example.utils.TokenUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 挂号信息表业务处理
 **/
@Service
public class ReserveService {

    @Resource
    private ReserveMapper reserveMapper;

    /**
     * 新增
     */
    public void add(Reserve reserve) {
        reserve.setTime(DateUtil.format(new Date(),"yyyy-MM-dd"));
        reserve.setStatus(CallEnum.STATUS_NO.status);
        List<Reserve> reserves = reserveMapper.selectAll(reserve);
        if(reserves.size()>0){
            throw new CustomException(ResultCodeEnum.RESERVE_EXIST_ERROR);
        }
        reserveMapper.insert(reserve);
    }

    /**
     * 删除
     */
    public void deleteById(Integer id) {
        reserveMapper.deleteById(id);
    }

    /**
     * 批量删除
     */
    public void deleteBatch(List<Integer> ids) {
        for (Integer id : ids) {
            reserveMapper.deleteById(id);
        }
    }

    /**
     * 修改
     */
    public void updateById(Reserve reserve) {
        reserveMapper.updateById(reserve);
    }

    /**
     * 根据ID查询
     */
    public Reserve selectById(Integer id) {
        return reserveMapper.selectById(id);
    }

    /**
     * 查询所有
     */
    public List<Reserve> selectAll(Reserve reserve) {
        return reserveMapper.selectAll(reserve);
    }

    /**
     * 分页查询
     */
    public PageInfo<Reserve> selectPage(Reserve reserve, Integer pageNum, Integer pageSize) {
        Account account = TokenUtils.getCurrentUser();
        reserve.setTime(DateUtil.format(new Date(),"yyyy-MM-dd"));
        if(RoleEnum.USER.name().equals(account.getRole())){

            reserve.setUserId(account.getId());
        }
        if(RoleEnum.DOCTOR.name().equals(account.getRole())){
            reserve.setDoctorId(account.getId());
        }
        PageHelper.startPage(pageNum, pageSize);
        List<Reserve> list = reserveMapper.selectAll(reserve);
        return PageInfo.of(list);
    }

}
