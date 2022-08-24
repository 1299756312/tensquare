package com.tensquare.qa.annotation;

import java.lang.annotation.*;

// 标注这个类它可以标注的位置
@Target({ElementType.METHOD,ElementType.ANNOTATION_TYPE})
// 标注这个注解的注解保留时期
@Retention(RetentionPolicy.RUNTIME)
// 是否生成注解文档
@Documented
public @interface PermissionCheck {
    //自定义角色值，如果是多个角色，用逗号分割。
    public String role() default "";
}