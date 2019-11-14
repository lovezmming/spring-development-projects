package com.shev.itembank.common.base.compare;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Comparator;

public class CodeAscComparator implements Comparator<Object>, Serializable
{
    /** serialVersionUID */
    private static final long serialVersionUID = 5630292671937183170L;

    @Override
    public int compare(Object o1, Object o2)
    {
        try
        {
            Field field1 = o1.getClass().getDeclaredField("coreId");
            Field field2 = o2.getClass().getDeclaredField("coreId");
            field1.setAccessible(true);
            field2.setAccessible(true);
            Integer code1 = (Integer) field1.get(o1);
            Integer code2 = (Integer) field2.get(o2);
            
            if (code1 == code2)
                return 0;
            if (code1 == null)
                return 1;
            if (code2 == null)
                return -1;
            return code1.compareTo(code2);
        } catch (Exception e) {}
        
        return 0;
    }
    
}
