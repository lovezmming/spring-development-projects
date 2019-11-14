package com.shev.itembank.common.base.compare;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Comparator;

public class SeqAscComparator implements Comparator<Object>, Serializable
{

    /** serialVersionUID */
    private static final long serialVersionUID = -7031027116400892380L;

    @Override
    public int compare(Object o1, Object o2)
    {
        try
        {
            Field field1 = o1.getClass().getDeclaredField("seq");
            Field field2 = o2.getClass().getDeclaredField("seq");
            field1.setAccessible(true);
            field2.setAccessible(true);
            Integer seq1 = (Integer) field1.get(o1);
            Integer seq2 = (Integer) field2.get(o2);
            
            if (seq1 == seq2)
                return 0;
            if (seq1 == null)
                return 1;
            if (seq2 == null)
                return -1;
            return seq1.compareTo(seq2);
        } catch (Exception e) {}
        
        return 0;
    }
    
}
