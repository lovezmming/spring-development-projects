package com.shev.itembank.common.base.util.entity;

import java.util.List;

public class ExcelExportFormatEntity
{
    private String sheetName;

    private List<List<String>> dataList;

    public String getSheetName()
    {
        return sheetName;
    }

    public void setSheetName(String sheetName)
    {
        this.sheetName = sheetName;
    }

    public List<List<String>> getDataList()
    {
        return dataList;
    }

    public void setDataList(List<List<String>> dataList)
    {
        this.dataList = dataList;
    }

}
