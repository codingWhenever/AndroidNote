package com.sz.leo.androidnote.chapter11.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author：leo
 * @date：2019/7/2
 * @email：lei.lu@e-at.com
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface getViewTo {
    int value() default -1;
}
