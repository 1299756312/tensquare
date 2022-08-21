package com.tensquare.recruit.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.tensquare.recruit.pojo.Recruit;

import java.util.List;

/**
 * 数据访问接口
 * @author Administrator
 *
 */
public interface RecruitDao extends JpaRepository<Recruit,String>,JpaSpecificationExecutor<Recruit>{
    /** * 查询最新职位列表(按创建日期降序排序) * @return */
    public List<Recruit> findTop4ByStateOrderByCreatetimeDesc(String state);
    /*** 最新职位列表 * @param state * @return */
    public List<Recruit> findTop12ByStateNotOrderByCreatetimeDesc(String state);
}
