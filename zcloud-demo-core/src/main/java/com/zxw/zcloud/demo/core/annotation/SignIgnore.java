package com.zxw.zcloud.demo.core.annotation;

import java.lang.annotation.*;

/**
 * 签名忽略注解
 *
 * @author zouxw
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SignIgnore {
}