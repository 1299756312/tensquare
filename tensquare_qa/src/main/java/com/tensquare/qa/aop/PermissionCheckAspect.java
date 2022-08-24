package com.tensquare.qa.aop;




import com.tensquare.qa.PermissionService;
import com.tensquare.qa.annotation.PermissionCheck;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

/**
 * @author LWW
 * @site www.lww.com
 * @company
 * @create 2019-12-16 14:20
 */
@Aspect
@Component
@Slf4j
public class PermissionCheckAspect {
    @Resource
    private PermissionService permissionService;

    //切入点表达式决定了用注解方式的方法切还是针对某个路径下的所有类和方法进行切，方法必须是返回void类型
    @Pointcut(value = "@annotation(com.tensquare.qa.annotation.PermissionCheck)")
    private void permissionCheckCut(){};

    //定义了切面的处理逻辑。即方法上加了@PermissionCheck
    @Around("permissionCheckCut()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable{
        log.info("====================进入AOP============================");
        //1.记录日志信息
        Signature signature = pjp.getSignature();
        String className = pjp.getTarget().getClass().getSimpleName();
        String methodName = signature.getName();
        log.info("className:{},methodName:{}",className,methodName);

        //2.角色权限校验
        MethodSignature methodSignature = (MethodSignature)signature;
        Method targetMethod = methodSignature.getMethod();
        if (targetMethod.isAnnotationPresent(PermissionCheck.class)){
            //获取方法上注解中表明的权限
            PermissionCheck permission =targetMethod.getAnnotation(PermissionCheck.class);
            String role =permission.role();
            log.info("当前接口请求的用户角色role:{}",role);
            if(StringUtils.isNotEmpty(role)){
                String[] roles = role.split(",");//接口允许的角色
                List<String> list = Arrays.asList(roles);
                //根据id从数据库中查询管理员权限(可用前台传过来的id作为参数)
                List<String> listPermission = permissionService.queryPermission();
                //打印管理员权限
                log.info("管理员的角色:"+String.valueOf(listPermission));
                //将注解上标明的权限与查出来的权限进行比对
                if(list != null && listPermission !=null){
                    for (String l : list){
                        if(listPermission.contains(l)){
                            log.info("AOP权限角色校验通过，进入业务层处理！");
                            //3.执行业务逻辑，放行
                            return pjp.proceed();
                        }else{
                            throw  new RuntimeException("权限认证失败，请重新认证");

                        }
                    }
                }else{
                    throw  new RuntimeException("权限认证失败，请重新认证");
                }


            }
        }
        throw  new RuntimeException("权限认证失败，请重新认证");
    }

}