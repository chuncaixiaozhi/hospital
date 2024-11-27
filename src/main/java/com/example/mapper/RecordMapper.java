package com.example.mapper;

import com.example.entity.Record;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 操作record相关数据接口
*/
public interface RecordMapper {

    /**
      * 新增
    */
    int insert(Record record);

    /**
      * 删除
    */
    int deleteById(Integer id);

    /**
      * 修改
    */
    int updateById(Record record);

    /**
      * 根据ID查询
    */
    Record selectById(Integer id);

    /**
      * 查询所有
    */
    List<Record> selectAll(Record record);

    @Select("select * from record where username = #{username}")
    Record selectByUsername(String username);
}
