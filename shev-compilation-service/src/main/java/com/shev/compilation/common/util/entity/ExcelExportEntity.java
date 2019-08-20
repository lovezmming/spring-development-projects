/*
 * Copyright (c) 2017 上海极值信息技术有限公司 All Rights Reserved.
 */
package com.shev.compilation.common.util.entity;

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
