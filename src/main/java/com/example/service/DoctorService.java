package com.example.service;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.example.common.Constants;
import com.example.common.enums.ResultCodeEnum;
import com.example.common.enums.RoleEnum;
import com.example.entity.*;
import com.example.exception.CustomException;
import com.example.mapper.DepartmentMapper;
import com.example.mapper.DoctorMapper;
import com.example.mapper.PlanMapper;
import com.example.mapper.ReserveMapper;
import com.example.utils.TokenUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * 医生业务处理
 **/
@Service
public class DoctorService {

    @Resource
    private DoctorMapper doctorMapper;
    @Resource
    private DepartmentMapper departmentMapper;
    @Resource
    private ReserveMapper reserveMapper;
    @Resource
    private PlanMapper planMapper;
    /**
     * 新增
     */
    public void add(Doctor doctor) {
        Doctor dbDoctor = doctorMapper.selectByUsername(doctor.getUsername());
        if (ObjectUtil.isNotNull(dbDoctor)) {
            throw new CustomException(ResultCodeEnum.USER_EXIST_ERROR);
        }
        if (ObjectUtil.isEmpty(doctor.getPassword())) {
            doctor.setPassword(Constants.USER_DEFAULT_PASSWORD);
        }
        if (ObjectUtil.isEmpty(doctor.getName())) {
            doctor.setName(doctor.getUsername());
        }
        doctor.setRole(RoleEnum.DOCTOR.name());
        doctorMapper.insert(doctor);
    }

    /**
     * 删除
     */
    public void deleteById(Integer id) {
        doctorMapper.deleteById(id);
    }

    /**
     * 批量删除
     */
    public void deleteBatch(List<Integer> ids) {
        for (Integer id : ids) {
            doctorMapper.deleteById(id);
        }
    }

    /**
     * 修改
     */
    public void updateById(Doctor doctor) {
        doctorMapper.updateById(doctor);
    }

    /**
     * 根据ID查询
     */
    public Doctor selectById(Integer id) {
        return doctorMapper.selectById(id);
    }

    /**
     * 查询所有
     */
    public List<Doctor> selectAll(Doctor doctor) {
        return doctorMapper.selectAll(doctor);
    }

    /**
     * 分页查询
     */
    public PageInfo<Doctor> selectPage(Doctor doctor, Integer pageNum, Integer pageSize) {
        //doctor.setWeek(getTodayWeek());
        PageHelper.startPage(pageNum, pageSize);
        List<Doctor> list = doctorMapper.selectAll2(doctor);
        return PageInfo.of(list);
    }
    /**
     * 预约挂号信息查询
     */
    public PageInfo<Doctor> selectPage2(Doctor doctor, Integer pageNum, Integer pageSize) {
        String today = DateUtil.format(new Date(),"yyyy-MM-dd");
        String todayWeek = getTodayWeek();
        doctor.setWeek(todayWeek);
        PageHelper.startPage(pageNum, pageSize);
        List<Doctor> list = doctorMapper.selectAll(doctor);
        //查询在诊医生的还要查询剩余多少号
        for (Doctor dbDoctor : list) {
            //
            Reserve reserve = new Reserve();
            reserve.setDoctorId(dbDoctor.getId());
            reserve.setTime(today);
            //已经挂了多少号
            List<Reserve> reserves = reserveMapper.selectAll(reserve);
            //总共多少号
            Plan plan = planMapper.selectByDoctorIdAndWeek(dbDoctor.getId(),todayWeek);
            dbDoctor.setNum(plan.getNum()-reserves.size());
        }
        return PageInfo.of(list);
    }
    // 今天是星期几
    private String getTodayWeek(){
        LocalDate today = LocalDate.now();
        DayOfWeek dayOfWeek = today.getDayOfWeek();
        return dayOfWeek.getDisplayName(TextStyle.FULL_STANDALONE, Locale.CHINA);
    }
    /**
     * 登录
     */
    public Account login(Account account) {
        Doctor dbDoctor = doctorMapper.selectByUsername(account.getUsername());
        if (ObjectUtil.isNull(dbDoctor)) {
            throw new CustomException(ResultCodeEnum.USER_NOT_EXIST_ERROR);
        }
        if (!account.getPassword().equals(dbDoctor.getPassword())) {
            throw new CustomException(ResultCodeEnum.USER_ACCOUNT_ERROR);
        }
         //  生成token
        String tokenData = dbDoctor.getId() + "-" + RoleEnum.DOCTOR.name();
        String token = TokenUtils.createToken(tokenData, dbDoctor.getPassword());
        dbDoctor.setToken(token);
        if(ObjectUtil.isNotEmpty(dbDoctor.getDepartmentId())){
            Department department = departmentMapper.selectById(dbDoctor.getDepartmentId());
            if(ObjectUtil.isNotEmpty(department)) {
                dbDoctor.setDepartmentName(department.getName());
            }
        }
        return dbDoctor;
    }

    /**
     * 修改密码
     */
    public void updatePassword(Account account) {
        Doctor dbDoctor = doctorMapper.selectByUsername(account.getUsername());
        if (ObjectUtil.isNull(dbDoctor)) {
            throw new CustomException(ResultCodeEnum.USER_NOT_EXIST_ERROR);
        }
        if (!account.getPassword().equals(dbDoctor.getPassword())) {
            throw new CustomException(ResultCodeEnum.PARAM_PASSWORD_ERROR);
        }
        dbDoctor.setPassword(account.getNewPassword());
        doctorMapper.updateById(dbDoctor);
    }

}
