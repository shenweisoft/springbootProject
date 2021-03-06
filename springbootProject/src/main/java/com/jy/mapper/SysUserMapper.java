package com.jy.mapper;

import com.jy.model.SysUser;
import com.jy.vo.SysUserVO;

public interface SysUserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysUser record);

    int insertSelective(SysUser record);

    SysUser selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysUser record);

    int updateByPrimaryKey(SysUser record);

    SysUser querySysUser(SysUserVO sysUserVO);
}