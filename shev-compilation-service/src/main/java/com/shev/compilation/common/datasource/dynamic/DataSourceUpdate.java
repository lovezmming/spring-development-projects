package com.shev.compilation.common.datasource.dynamic;

import com.shev.compilation.common.datasource.Enum.DataSourceName;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD})
@Documented
public @interface DataSourceUpdate
{
    DataSourceName value() default DataSourceName.ACCOUNT_UPDATE;
}