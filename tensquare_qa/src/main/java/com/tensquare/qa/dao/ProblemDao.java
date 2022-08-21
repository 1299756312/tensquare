package com.tensquare.qa.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.tensquare.qa.pojo.Problem;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * 数据访问接口
 * @author Administrator
 *
 */
public interface ProblemDao extends JpaRepository<Problem,String>,JpaSpecificationExecutor<Problem>{
    /*** 根据标签ID查询最新问题列表 * @param labelId * @param pageable * @return */
    @Query("select p from  Problem p where p.id in (select problemId from Pl where labelId = :labelId) order by p.replytime desc ")
     Page<Problem> findNewListByLabelId(String labelId, Pageable pageable);
    /*** 根据标签ID查询热门问题列表 * @param labelId * @param pageable * @return */
    @Query("select p from  Problem p where p.id in (select problemId from Pl where labelId = :labelId) order by p.reply desc ")
    public Page<Problem> findHotListByLabelId(String labelId, Pageable pageable);
    /*** 根据标签ID查询等待回答列表 * @param labelId * @param pageable * @return */
    @Query("select p from  Problem p where p.id in (select problemId from Pl where labelId = :labelId) and p.reply=0  order by p.reply desc ")
    public Page<Problem> findWaitListByLabelId(String labelId, Pageable pageable);
}
