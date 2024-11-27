package com.example.service;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import com.example.common.enums.RoleEnum;
import com.example.entity.Account;
import com.example.entity.Registration;
import com.example.mapper.RegistrationMapper;
import com.example.utils.TokenUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 公告信息表业务处理
 **/
@Service
public class RegistrationService {

    @Resource
    private RegistrationMapper registrationMapper;

    /**
     * 新增
     */
    public void add(Registration registration) {
 //       Registration dbRegistration = registrationMapper.selectByUserIdInHospital(registration.getUserId());
        List<Registration> registrations = registrationMapper.selectAll(registration);
        if(CollectionUtil.isEmpty(registrations)){
            registrationMapper.insert(registration);
        }
    }

    /**
     * 删除
     */
    public void deleteById(Integer id) {
        registrationMapper.deleteById(id);
    }

    /**
     * 批量删除
     */
    public void deleteBatch(List<Integer> ids) {
        for (Integer id : ids) {
            registrationMapper.deleteById(id);
        }
    }

    /**
     * 修改
     */
    public void updateById(Registration registration) {
        registrationMapper.updateById(registration);
    }

    /**
     * 根据ID查询
     */
    public Registration selectById(Integer id) {
        return registrationMapper.selectById(id);
    }

    /**
     * 查询所有
     */
    public List<Registration> selectAll(Registration registration) {
        return registrationMapper.selectAll(registration);
    }

    /**
     * 分页查询
     */
    public PageInfo<Registration> selectPage(Registration registration, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        Account account =TokenUtils.getCurrentUser();
        if(RoleEnum.USER.name().equals(account.getRole())){
            registration.setUserId(account.getId());
        }
        List<Registration> list = registrationMapper.selectAll(registration);
        return PageInfo.of(list);
    }

}
