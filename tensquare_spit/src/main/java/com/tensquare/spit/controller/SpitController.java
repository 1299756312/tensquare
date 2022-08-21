package com.tensquare.spit.controller;

import com.tensquare.common.entity.PageResult;
import com.tensquare.common.entity.Result;
import com.tensquare.common.entity.StatusCode;
import com.tensquare.spit.pojo.Spit;
import com.tensquare.spit.service.SpitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/spit")
@CrossOrigin
public class SpitController {
    @Autowired
    SpitService spitService;
    @Autowired
    private RedisTemplate redisTemplate;
    /*** 查询全部数据 * @return */
    @GetMapping
    public Result findAll(){
        List<Spit> spits = spitService.findAll();
        return new Result(true, StatusCode.OK,"查询成功",spits);
    }
    /*** 根据ID查询 * @param id ID * @return */
    @RequestMapping(value="/{id}",method= RequestMethod.GET)
    public Result findOne(@PathVariable String id){
        return new Result(true,StatusCode.OK,"查询成 功",spitService.findById(id));
    }
    /*** 增加 * @param spit */
    @RequestMapping(method=RequestMethod.POST)
    public Result add(@RequestBody Spit spit ){
        spitService.add(spit); return new Result(true,StatusCode.OK,"增加成功");
    }
    /*** 修改 * @param spit */
    @RequestMapping(value="/{id}",method=RequestMethod.PUT)
    public Result update(@RequestBody Spit spit,
                         @PathVariable String id ) {
        spit.set_id(id); spitService.update(spit); return new Result(true,StatusCode.OK,"修改成功");
    }
    /*** 删除 * @param id */
    @RequestMapping(value="/{id}",method=RequestMethod.DELETE)
    public Result deleteById(@PathVariable String id ){
        spitService.deleteById(id); return new Result(true,StatusCode.OK,"删除成功");
    }
    /*** 根据上级ID查询吐槽分页数据 * @param page * @param size * @return */
    @RequestMapping(value="/comment/{parentId}/{page}/{size}",method=RequestMethod.GET)
    public Result findByParentid(@PathVariable String parentId,
                                 @PathVariable int page,
                                 @PathVariable int size) {
        Page<Spit> pageList = spitService.findByParentid(parentId,page, size);
        return new Result(true,StatusCode.OK,"查询成功",new PageResult<Spit>(pageList.getTotalElements(), pageList.getContent() ) );
    }
    /*** 点赞 * @param id * @return */
    @RequestMapping(value="/thumbup/{id}",method=RequestMethod.PUT)
    public Result updateThumbup(@PathVariable String id) {
        String userId = "2023";
        if(redisTemplate.opsForValue().get("thumbup_"+userId+"_"+ id)!=null){
            return new Result(false,StatusCode.REPERROR,"你已经点过赞了"); }

        spitService.updateThumbup(id);
        return new Result(true,StatusCode.OK,"点赞成功");
    }


}
