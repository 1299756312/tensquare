package com.tensquare.base.controller;

import com.tensquare.base.entity.Label;
import com.tensquare.base.service.LabelService;

import com.tensquare.common.entity.PageResult;
import com.tensquare.common.entity.Result;
import com.tensquare.common.entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/label")
public class LabelController {

 private final LabelService labelService;

    public LabelController(LabelService labelService) {
        this.labelService = labelService;
    }

    @GetMapping
    public Result findAll(){
        List<Label> list= labelService.findAll();
        return new Result(true, StatusCode.OK,"查询成功",list);
    }
    @GetMapping("/{labelId}")
    public Result findById(@PathVariable("labelId") String labelId){
        Label label = labelService.findById(labelId);
        return new Result(true, StatusCode.OK,"查询成功",label);
    }
    @PostMapping
    public Result save(@RequestBody Label label){
        labelService.save(label);
        return new Result(true, StatusCode.OK,"添加成功");
    }
    @DeleteMapping
    public Result delById(@RequestBody Label label){
               labelService.delById(label.getId());
        return new Result(true, StatusCode.OK,"删除成功");
    }
    @PutMapping("/{labelId}")
    public Result putById(@RequestBody Label label,@PathVariable String labelId){
        label.setId(labelId);
             labelService.update(label);

        return new Result(true, StatusCode.OK,"更新成功");
    }
    /*** 分页条件查询 * @param searchMap * @return */
    @PostMapping("/search/{page}/{size}")
    public Result findSearch(@RequestBody Map<String,Object> searchMap,@PathVariable int page,@PathVariable int size){
        Page<Label> labels = labelService.findSearch(searchMap, page, size);
        return new Result(true,StatusCode.OK,"查询成功",new PageResult<Label>(labels.getTotalElements(),labels.getContent()));
    }
}
