package com.shev.itembank.common.base.util.entity;

import java.util.Map;

public class ExcelExportEntity
{
    private String sheetName;

    private Map<String, Object> sheetData;

    public String getSheetName()
    {
        return sheetName;
    }

    public void setSheetName(String sheetName)
    {
        this.sheetName = sheetName;
    }

    public Map<String, Object> getSheetData()
    {
        return sheetData;
    }

    public void setSheetData(Map<String, Object> sheetData)
    {
        this.sheetData = sheetData;
    }

}
