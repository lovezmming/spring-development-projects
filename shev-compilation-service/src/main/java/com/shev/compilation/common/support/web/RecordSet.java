package com.shev.compilation.common.support.web;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class RecordSet extends RecordCount
{

    /** serialVersionUID */
    private static final long serialVersionUID = 3490400620036270197L;

    private Object[] values;

    public RecordSet()
    {
        super(0, 0, 0);
        setValues(new Object[0]);
    }

    public RecordSet(RecordCount rc, Object[] values)
    {
        super(rc.getStartCount(), rc.getPageSize(), rc.getTotalCount());
        setValues(values == null ? new Object[0] : values);
    }

    public RecordSet(Collection<Object> values)
    {
        super(0, values.size(), values.size());
        setValues(values == null ? new Object[0] : values.toArray());
    }

    public RecordSet(List<Object> values)
    {
        super(0, values.size(), values.size());
        setValues(values == null ? new Object[0] : values.toArray());
    }

    public RecordSet(int start, int max, int total, Object[] values)
    {
        super(start, max, total);
        setValues(values == null ? new Object[0] : values);
    }

    public Object firstValue()
    {
        return (this.values.length == 0 ? null : this.values[0]);
    }

    public Object[] getValues()
    {
        return this.values;
    }

    public void setValues(Object[] values)
    {
        this.values = values;
    }

    public List<Object> asList()
    {
        return Arrays.asList(getValues());
    }

    @SuppressWarnings("unchecked")
    public <T> List<T> RecordSetToList()
    {
        if (getValues() == null || getValues().length == 0)
            return new ArrayList<T>();
        return Arrays.stream(getValues()).map(o -> (T) o).collect(Collectors.toList());
    }
}
