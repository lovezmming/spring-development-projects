/*
 * Copyright (c) 2016 上海极值信息技术有限公司 All Rights Reserved.
 */
package com.shev.compilation.common.support.compare;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Comparator;
import java.util.Date;

public class UpdateTimeDescComparator implements Comparator<Object>, Serializable
{

    /** serialVersionUID */
    private static final long serialVersionUID = 3031599509264988198L;

    /**
     * @see Comparator#compare(Object, Object)
     */
    @Override
    public int compare(Object o1, Object o2)
    {
        try
        {
            Field field1 = o1.getClass().getDeclaredField("updateTime");
            Field field2 = o2.getClass().getDeclaredField("updateTime");
            field1.setAccessible(true);
            field2.setAccessible(true);
            Date updateTime1 = (Date) field1.get(o1);
            Date updateTime2 = (Date) field2.get(o2);
            
            if (updateTime1 == updateTime2)
                return 0;
            if (updateTime1 == null)
                return 1;
            if (updateTime2 == null)
                return -1;
            return updateTime2.compareTo(updateTime1);
        } catch (Exception e) {}
        
        return 0;
    }

}
