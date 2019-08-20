/*
 * Copyright (c) 2016 上海极值信息技术有限公司 All Rights Reserved.
 */
package com.shev.compilation.common.support.compare;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Comparator;
import java.util.Date;

public class CreateTimeDescComparator implements Comparator<Object>, Serializable
{

    /** serialVersionUID */
    private static final long serialVersionUID = -7031027116400892380L;

    @Override
    public int compare(Object o1, Object o2)
    {
        try
        {
            Field field1 = o1.getClass().getDeclaredField("createTime");
            Field field2 = o2.getClass().getDeclaredField("createTime");
            field1.setAccessible(true);
            field2.setAccessible(true);
            Date createTime1 = (Date) field1.get(o1);
            Date createTime2 = (Date) field2.get(o2);
            
            if (createTime1 == createTime2)
                return 0;
            if (createTime1 == null)
                return 1;
            if (createTime2 == null)
                return -1;
            return createTime2.compareTo(createTime1);
        } catch (Exception e)
        {}
        return 0;
    }
    
}
