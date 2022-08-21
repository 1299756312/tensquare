package com.tensquare.base.service;

import com.tensquare.base.dao.LabelDAO;
import com.tensquare.base.entity.Label;
import com.tensquare.util.IdWorker;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class LabelService {
private final LabelDAO labelDAO;
    private final IdWorker idWorker;
    public LabelService(LabelDAO labelDAO, IdWorker idWorker) {
        this.labelDAO = labelDAO;
        this.idWorker = idWorker;
    }


    public List<Label> findAll() {
        List<Label> labels = labelDAO.findAll();
        return labels;
    }
    public Label findById(String id){
      return   labelDAO.findById(id).get();
    }
    public void save(Label label){
        label.setId(String.valueOf(idWorker.nextId()));
        labelDAO.save(label);
    }
    public void update(Label label){
        labelDAO.save(label);
    }
    public void delById(String id){
        labelDAO.deleteById(id);
    }

    /**
     * 构建条件查询
     * @param searchMap
     * @return
     */
    private Specification<Label> createSpecification(Map searchMap){
        return new Specification<Label>() {
            @Override
            public Predicate toPredicate(Root<Label> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                List<Predicate> predicateList=new ArrayList<Predicate>();
                if(searchMap.get("labelname")!=null &&!"".equals(searchMap.get("labelname"))){
                    predicateList.add(cb.like(root.get("labelname").as(String.class),"%"+searchMap.get("labelname")+"%"));
                }
                if(searchMap.get("state")!=null &&!"".equals(searchMap.get("state"))){
                    predicateList.add(cb.equal(root.get("state").as(String.class),(String)searchMap.get("state")));
                }
                if(searchMap.get("recommend")!=null&&!"".equals(searchMap.get("recommend"))){
                    predicateList.add(cb.equal(root.get("recommend").as(String.class),(String)searchMap.get("recommend")));
                }
                return  cb.and(predicateList.toArray(new Predicate[predicateList.size()]));


            }

        };



    }
    /**
     * 分页条件查询
     */
    public Page<Label> findSearch(Map<String,Object> searchMap,int page,int size){
        Specification<Label> specification = createSpecification(searchMap);
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        return labelDAO.findAll(specification,pageRequest);

    }




}
