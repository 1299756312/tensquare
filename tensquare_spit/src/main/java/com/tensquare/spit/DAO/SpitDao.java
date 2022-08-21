package com.tensquare.spit.DAO;

import com.tensquare.spit.pojo.Spit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.Mapping;

/***
 * 吐槽数据访问层 *
 * @author Administrator
 **/
@Repository
public interface SpitDao extends MongoRepository<Spit, String>{
    /*** 根据上级ID查询吐槽列表（分页） * @param parentid * @param pageable * @return */
     Page<Spit> findByParentid(String parentid, Pageable pageable);

}
