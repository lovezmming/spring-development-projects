package com.shev.compilation.common.support.valid;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface RequestValidate
{
    boolean timestampValidate() default true;
}
