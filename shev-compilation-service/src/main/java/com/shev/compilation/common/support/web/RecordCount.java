package com.shev.compilation.common.support.web;

import java.io.Serializable;

public class RecordCount implements Serializable
{
    /** serialVersionUID */
    private static final long serialVersionUID = -435328320159692760L;

    private int startCount;
    
    private int pageSize;
    
    private int totalCount;

    public RecordCount() {}

    public RecordCount(int start, int pageSize, int total)
    {
        if ((startCount = (start < 0 ? 0 : start)) >= total)
            startCount = (total - 1 >= 0 ? total - 1 : total);

        this.pageSize = pageSize;
        this.totalCount = total;
    }

    public RecordCount(RecordCount rc)
    {
        this(rc.getStartCount(), rc.getPageSize(), rc.getTotalCount());
    }

    public int getStartCount()
    {
        return startCount;
    }

    public void setStartCount(int count)
    {
        this.startCount = count;
    }

    public int getEndCount()
    {
        int end = startCount + pageSize;
        return (end > totalCount ? totalCount : end) - 1;
    }

    public int getTotalCount()
    {
        return totalCount;
    }

    public void setTotalCount(int count)
    {
        this.totalCount = count;
    }

    public int getPageSize()
    {
        return pageSize;
    }

    public void setPageSize(int count)
    {
        this.pageSize = count;
    }

    public long getPageNumber()
    {
        int pageSize = getPageSize();
        if (pageSize <= 0 || pageSize == Integer.MAX_VALUE)
            return 1;
        
        return (getStartCount() / pageSize) + 1;
    }

    public int getPageTotal()
    {
        int pageSize = getPageSize();
        if (pageSize <= 0 || pageSize == Integer.MAX_VALUE)
            return 1;
        
        int total = getTotalCount();
        if (total == 0)
            return 1;
        
        int rem = total % pageSize;
        return (total / pageSize) + (rem == 0 ? 0 : 1);
    }

    @Override
    public String toString()
    {
        return "RecordCount [startCount=" + startCount + ", pageSize=" + pageSize + ", totalCount=" + totalCount + "]";
    }
}
