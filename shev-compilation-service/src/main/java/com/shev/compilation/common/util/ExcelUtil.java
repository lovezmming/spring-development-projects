package com.shev.compilation.common.util;

import com.shev.compilation.common.util.entity.ExcelExportEntity;
import com.shev.compilation.common.util.entity.ExcelExportFormatEntity;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;

import java.beans.PropertyDescriptor;
import java.io.*;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.*;

public class ExcelUtil
{
    public static final String HEADERS_KEY = "headers";

    public static final String PATTERN_KEY = "pattern";

    public static final String DATASET_KEY = "dataset";

    @SuppressWarnings("resource")
    public static Map<Integer, Map<Integer, List<Object>>> getXSSFData(File file) throws Exception
    {
        Map<Integer, Map<Integer, List<Object>>> resultMap = new HashMap<Integer, Map<Integer, List<Object>>>();
        FileInputStream input = null;
        XSSFWorkbook workbook = null;
        try
        {
            input = new FileInputStream(file);
            workbook = new XSSFWorkbook(input);
            Iterator<Sheet> sheetIterator = workbook.iterator();
            int sheetNum = 0;
            while (sheetIterator.hasNext())
            {
                XSSFSheet sheet = (XSSFSheet) sheetIterator.next();
                Map<Integer, List<Object>> sheetMap = new HashMap<Integer, List<Object>>();
                int maxRowNum = sheet.getLastRowNum();
                int maxColumnNum = 0;
                XSSFRow firstRow = sheet.getRow(0);
                if (firstRow != null)
                {
                    maxColumnNum = firstRow.getPhysicalNumberOfCells();
                    for (int i = 0; i <= maxRowNum; i++)
                    {
                        XSSFRow row = sheet.getRow(i);
                        if (row == null)
                        {
                            continue;
                        }
                        if (maxColumnNum < row.getPhysicalNumberOfCells())
                        {
                            maxColumnNum = row.getPhysicalNumberOfCells();
                        }
                    }
                } else
                {
                    resultMap.put(sheetNum, sheetMap);
                    continue;
                }
                for (int rownum = 0; rownum <= maxRowNum; rownum++)
                {
                    List<Object> rowList = new ArrayList<Object>();
                    XSSFRow row = sheet.getRow(rownum);
                    if (row == null)
                    {
                        continue;
                    }
                    for (int columnNum = 0; columnNum < maxColumnNum; columnNum++)
                    {
                        XSSFCell cell = row.getCell(columnNum);
                        if (cell != null)
                        {
                            short cellDataFormat = cell.getCellStyle().getDataFormat();
                            if (cellDataFormat == 14)
                            {
                                Date cellDate = cell.getDateCellValue();
                                rowList.add(DateTimeUtil.formatDate(cellDate));
                            } else
                            {
                                rowList.add(dataFormat(cell.toString()));
                            }
                        } else
                        {
                            rowList.add(null);
                        }
                    }
                    sheetMap.put(rownum, rowList);
                }
                resultMap.put(sheetNum, sheetMap);
                sheetNum++;
            }
        } catch (Exception e)
        {
            throw e;
        } finally
        {
            if (input != null)
                input.close();
            if (workbook != null)
                workbook = null;
        }
        return resultMap;
    }

    @SuppressWarnings("resource")
    public static Map<String, Map<Integer, List<Object>>> getXSSFDataBySheetName(File file) throws Exception
    {
        Map<String, Map<Integer, List<Object>>> resultMap = new HashMap<String, Map<Integer, List<Object>>>();
        FileInputStream input = null;
        XSSFWorkbook workbook = null;
        try
        {
            input = new FileInputStream(file);
            workbook = new XSSFWorkbook(input);
            Iterator<Sheet> sheetIterator = workbook.iterator();
            while (sheetIterator.hasNext())
            {
                XSSFSheet sheet = (XSSFSheet) sheetIterator.next();
                String sheetName = sheet.getSheetName();
                Map<Integer, List<Object>> sheetMap = new HashMap<Integer, List<Object>>();
                int maxRowNum = sheet.getLastRowNum();
                XSSFRow firstRow = sheet.getRow(0);
                int maxColumnNum = 0;
                if (firstRow != null)
                {
                    maxColumnNum = firstRow.getPhysicalNumberOfCells();
                    for (int i = 0; i <= maxRowNum; i++)
                    {
                        XSSFRow row = sheet.getRow(i);
                        if (row == null)
                        {
                            continue;
                        }
                        if (maxColumnNum < row.getPhysicalNumberOfCells())
                        {
                            maxColumnNum = row.getPhysicalNumberOfCells();
                        }
                    }
                } else
                {
                    resultMap.put(sheetName, sheetMap);
                    continue;
                }
                for (int rownum = 0; rownum <= maxRowNum; rownum++)
                {
                    List<Object> rowList = new ArrayList<Object>();
                    XSSFRow row = sheet.getRow(rownum);
                    if (row == null)
                    {
                        continue;
                    }
                    for (int columnNum = 0; columnNum < maxColumnNum; columnNum++)
                    {
                        XSSFCell cell = row.getCell(columnNum);
                        if (cell != null)
                        {
                            short cellDataFormat = cell.getCellStyle().getDataFormat();
                            if (cellDataFormat == 14)
                            {
                                Date cellDate = cell.getDateCellValue();
                                rowList.add(DateTimeUtil.formatDate(cellDate));
                            } else
                            {
                                rowList.add(dataFormat(cell.toString()));
                            }
                        } else
                        {
                            rowList.add(null);
                        }
                    }
                    sheetMap.put(rownum, rowList);
                }
                resultMap.put(sheetName, sheetMap);
            }
        } catch (Exception e)
        {
            throw e;
        } finally
        {
            if (input != null)
                input.close();
            if (workbook != null)
                workbook = null;
        }
        return resultMap;
    }

    public static Map<String, Map<Integer, List<Object>>> getHSSFDataBySheetName(File file) throws Exception
    {
        Map<String, Map<Integer, List<Object>>> resultMap = new HashMap<String, Map<Integer, List<Object>>>();
        FileInputStream input = null;
        HSSFWorkbook workbook = null;
        try
        {
            input = new FileInputStream(file);
            workbook = new HSSFWorkbook(input);
            Iterator<Sheet> sheetIterator = workbook.iterator();
            while (sheetIterator.hasNext())
            {
                HSSFSheet sheet = (HSSFSheet) sheetIterator.next();
                String sheetName = sheet.getSheetName();
                Map<Integer, List<Object>> sheetMap = new HashMap<Integer, List<Object>>();
                int maxRowNum = sheet.getLastRowNum();
                HSSFRow firstRow = sheet.getRow(0);
                int maxColumnNum = 0;
                if (firstRow != null)
                {
                    maxColumnNum = firstRow.getPhysicalNumberOfCells();
                    for (int i = 0; i <= maxRowNum; i++)
                    {
                        HSSFRow row = sheet.getRow(i);
                        if (row == null)
                        {
                            continue;
                        }
                        if (maxColumnNum < row.getPhysicalNumberOfCells())
                        {
                            maxColumnNum = row.getPhysicalNumberOfCells();
                        }
                    }
                } else
                {
                    resultMap.put(sheetName, sheetMap);
                    continue;
                }
                for (int rownum = 0; rownum <= maxRowNum; rownum++)
                {
                    List<Object> rowList = new ArrayList<Object>();
                    HSSFRow row = sheet.getRow(rownum);
                    if (row == null)
                    {
                        continue;
                    }
                    for (int columnNum = 0; columnNum < maxColumnNum; columnNum++)
                    {
                        HSSFCell cell = row.getCell(columnNum);
                        if (cell != null)
                        {
                            short cellDataFormat = cell.getCellStyle().getDataFormat();
                            if (cellDataFormat == 14)
                            {
                                Date cellDate = cell.getDateCellValue();
                                rowList.add(DateTimeUtil.formatDate(cellDate));
                            } else
                            {
                                rowList.add(dataFormat(cell.toString()));
                            }
                        } else
                        {
                            rowList.add(null);
                        }
                    }
                    sheetMap.put(rownum, rowList);
                }
                resultMap.put(sheetName, sheetMap);
            }
        } catch (Exception e)
        {
            throw e;
        } finally
        {
            if (input != null)
                input.close();
            if (workbook != null)
                workbook.close();
        }
        return resultMap;
    }

    public static <T> File exportExcel(String title, List<ExcelHeader> headers, Collection<T> dataset, String pattern) throws Exception
    {
        File file = new File(title + ".xlsx");
        XSSFWorkbook workbook = new XSSFWorkbook();
        FileOutputStream fos = null;
        try
        {
            XSSFSheet sheet = workbook.createSheet(title);
            for (short i = 0; i < headers.size(); i++)
            {
                sheet.setColumnWidth(i, headers.get(i).getWidth() == null ? 2500 : headers.get(i).getWidth());
            }

            // head
            XSSFRow row = sheet.createRow(0);
            for (short i = 0; i < headers.size(); i++)
            {
                XSSFCell cell = row.createCell(i);
                XSSFRichTextString text = new XSSFRichTextString(headers.get(i).getName());
                cell.setCellValue(text);
            }

            // data
            Iterator<T> it = dataset.iterator();
            int index = 0;
            while (it.hasNext())
            {
                index++;
                row = sheet.createRow(index);
                T t = (T) it.next();
                // 利用反射，根据javabean属性的先后顺序，动态调用getXxx()方法得到属性值
                Field[] fields = t.getClass().getDeclaredFields();
                for (short i = 0; i < fields.length; i++)
                {
                    XSSFCell cell = row.createCell(i);
                    Field field = fields[i];
                    String fieldName = field.getName();
                    PropertyDescriptor prop = new PropertyDescriptor(fieldName, t.getClass());
                    Object value = prop.getReadMethod().invoke(t);
                    String textValue = null;
                    if (value instanceof Date)
                    {
                        textValue = DateTimeUtil.formatDate((Date) value, pattern);
                    } else if (value instanceof Long)
                    {
                        textValue = ((Long) value).longValue() + "";
                    } else
                    {
                        textValue = (value != null ? value.toString() : "");
                    }
                    XSSFRichTextString richString = new XSSFRichTextString(textValue);
                    cell.setCellValue(richString);
                }
            }
            fos = new FileOutputStream(file);
            workbook.write(fos);
        } catch (Exception e)
        {
            throw e;
        } finally
        {
            if (fos != null)
            {
                fos.close();
            }
            workbook.close();
        }
        return file;
    }

    public static class ExcelHeader
    {
        private String name;

        private Integer width;

        public ExcelHeader(String name, Integer width)
        {
            super();
            this.name = name;
            this.width = width;
        }

        public ExcelHeader()
        {
            super();
        }

        public ExcelHeader(String name)
        {
            super();
            this.name = name;
        }

        public String getName()
        {
            return name;
        }

        public void setName(String name)
        {
            this.name = name;
        }

        public Integer getWidth()
        {
            return width;
        }

        public void setWidth(Integer width)
        {
            this.width = width;
        }

    }

    public static boolean exportData(List<List<String>> list, File file, String sheetName) throws Exception
    {
        OutputStream os = null;
        Workbook book = null;
        try
        {
            os = new FileOutputStream(file);
            book = new XSSFWorkbook();
            Sheet sheet = book.createSheet(sheetName);
            int rowN = 0;
            for (List<String> subList : list)
            {
                Row row = sheet.createRow(rowN);
                int cellN = 0;
                for (String value : subList)
                {
                    row.createCell(cellN).setCellValue(value);
                    cellN++;
                }
                rowN++;
            }
            book.write(os);
        } catch (Exception e)
        {
            throw e;
        } finally
        {
            if (book != null)
                book.close();
            if (os != null)
                os.close();
        }

        return true;
    }

    @SuppressWarnings("unchecked")
    public static <T> File exportExcel(String title, List<ExcelExportEntity> excelExportEntityList) throws Exception
    {
        File file = new File(title + ".xlsx");
        XSSFWorkbook workbook = new XSSFWorkbook();
        FileOutputStream fos = null;
        try
        {
            if (excelExportEntityList != null)
            {
                for (ExcelExportEntity excelExportEntity : excelExportEntityList)
                {
                    String sheetName = excelExportEntity.getSheetName();
                    XSSFSheet sheet = workbook.createSheet(sheetName);
                    Map<String, Object> sheetData = excelExportEntity.getSheetData();
                    List<ExcelHeader> headers = (List<ExcelHeader>) sheetData.get(HEADERS_KEY);
                    String pattern = (String) sheetData.get(PATTERN_KEY);
                    if (TextUtil.isEmpty(pattern))
                    {
                        pattern = DateTimeUtil.YYYY_MM_DD;
                    }
                    Collection<T> dataset = (Collection<T>) sheetData.get(DATASET_KEY);

                    for (short i = 0; i < headers.size(); i++)
                    {
                        sheet.setColumnWidth(i, headers.get(i).getWidth() == null ? 2500 : headers.get(i).getWidth());
                    }

                    // head
                    XSSFRow row = sheet.createRow(0);
                    for (short i = 0; i < headers.size(); i++)
                    {
                        XSSFCell cell = row.createCell(i);
                        XSSFRichTextString text = new XSSFRichTextString(headers.get(i).getName());
                        cell.setCellValue(text);
                    }

                    // data
                    Iterator<T> it = dataset.iterator();
                    int index = 0;
                    while (it.hasNext())
                    {
                        index++;
                        row = sheet.createRow(index);
                        T t = (T) it.next();
                        // 利用反射，根据javabean属性的先后顺序，动态调用getXxx()方法得到属性值
                        Field[] fields = t.getClass().getDeclaredFields();
                        for (short i = 0; i < fields.length; i++)
                        {
                            XSSFCell cell = row.createCell(i);
                            Field field = fields[i];
                            String fieldName = field.getName();
                            PropertyDescriptor prop = new PropertyDescriptor(fieldName, t.getClass());
                            Object value = prop.getReadMethod().invoke(t);
                            String textValue = null;
                            if (value instanceof Date)
                            {
                                textValue = DateTimeUtil.formatDate((Date) value, pattern);
                            } else if (value instanceof Long)
                            {
                                textValue = ((Long) value).longValue() + "";
                            } else
                            {
                                textValue = (value != null ? value.toString() : "");
                            }
                            XSSFRichTextString richString = new XSSFRichTextString(textValue);
                            cell.setCellValue(richString);
                        }
                    }
                }
                fos = new FileOutputStream(file);
                workbook.write(fos);
            }
        } catch (Exception e)
        {
            throw e;
        } finally
        {
            if (fos != null)
            {
                fos.close();
            }
            workbook.close();
        }
        return file;
    }

    public static File exportExcelByStringData(String title, List<ExcelExportFormatEntity> excelExportFormatEntityList) throws Exception
    {
        File file = new File(title + ".xlsx");
        XSSFWorkbook workbook = new XSSFWorkbook();
        FileOutputStream fos = null;
        try
        {
            if (excelExportFormatEntityList != null)
            {
                for (ExcelExportFormatEntity excelExportFormatEntity : excelExportFormatEntityList)
                {
                    String sheetName = excelExportFormatEntity.getSheetName();
                    List<List<String>> dataList = excelExportFormatEntity.getDataList();
                    XSSFSheet sheet = workbook.createSheet(sheetName);
                    if (dataList == null || dataList.size() == 0)
                    {
                        continue;
                    }
                    int rowN = 0;
                    for (List<String> subList : dataList)
                    {
                        Row row = sheet.createRow(rowN);
                        int cellN = 0;
                        for (String value : subList)
                        {
                            row.createCell(cellN).setCellValue(value);
                            cellN++;
                        }
                        rowN++;
                    }
                }
                fos = new FileOutputStream(file);
                workbook.write(fos);
            }
        } catch (Exception e)
        {
            throw e;
        } finally
        {
            if (fos != null)
            {
                fos.close();
            }
            workbook.close();
        }
        return file;
    }

    private static String dataFormat(String data)
    {
        if (data.matches("[0-9]+\\.[0]+") || data.matches("[0-9]+\\.[0-9]*[Ee][+-]?[0-9]*"))
        {
            double doubleData = Double.parseDouble(data);
            BigDecimal bigDecimal = new BigDecimal(doubleData);
            data = bigDecimal.toPlainString();
        }
        return data;
    }

    @SuppressWarnings({ "resource", "deprecation" })
    public static void copyExcelSheet(List<String> fromFilePaths, String toFilePath, List<String> sheetNames, String excelName) throws Exception
    {
        XSSFWorkbook wbCreat = new XSSFWorkbook();
        if (fromFilePaths != null && fromFilePaths.size() > 0)
        {
            for (String fromFilePath : fromFilePaths)
            {
                InputStream in = new FileInputStream(fromFilePath);
                XSSFWorkbook wb = new XSSFWorkbook(in);
                int sheetNum = wb.getNumberOfSheets();
                for (int i = 0; i < sheetNum; i++)
                {
                    XSSFSheet sheet = wb.getSheetAt(i);
                    if (sheetNames.contains(sheet.getSheetName()))
                    {
                        XSSFSheet sheetCreat = wbCreat.createSheet(sheet.getSheetName());
                        int sheetMergerCount = sheet.getNumMergedRegions();
                        for (int ii = 0; ii < sheetMergerCount; ii++)
                        {
                            CellRangeAddress cellRangeAddress = sheet.getMergedRegion(ii);
                            sheetCreat.addMergedRegion(cellRangeAddress);
                        }
                        int firstRow = sheet.getFirstRowNum();
                        int lastRow = sheet.getLastRowNum();
                        Boolean numberFlag = false;
                        if ("排除时间".equals(sheet.getSheetName()))
                        {
                            numberFlag = true;
                        }
                        for (int iii = firstRow; iii <= lastRow; iii++)
                        {
                            XSSFRow rowCreat = sheetCreat.createRow(iii);
                            XSSFRow row = sheet.getRow(iii);
                            //                            int firstCell = row.getFirstCellNum();
                            int lastCell = row.getLastCellNum();
                            for (int j = 0; j < lastCell; j++)
                            {
                                XSSFCell toXSSFCell = rowCreat.createCell(j);
                                XSSFCell fromXSSFCell = row.getCell(j);
                                if (fromXSSFCell != null)
                                {
                                    if (XSSFCell.CELL_TYPE_NUMERIC == fromXSSFCell.getCellType())
                                    {
                                        if (numberFlag == Boolean.TRUE && j < 4)
                                        {
                                            String val = String.valueOf(fromXSSFCell.getNumericCellValue());
                                            toXSSFCell.setCellValue(val.substring(0, val.lastIndexOf(".")));
                                        } else
                                        {
                                            XSSFDataFormat df = wbCreat.createDataFormat();
                                            XSSFCellStyle contentStyle = wbCreat.createCellStyle();
                                            contentStyle.setDataFormat(df.getFormat("#,#0"));
                                            toXSSFCell.setCellStyle(contentStyle);
                                            toXSSFCell.setCellValue(fromXSSFCell.getNumericCellValue());
                                        }
                                    } else
                                    {
                                        toXSSFCell.setCellValue(fromXSSFCell.getStringCellValue());
                                    }
                                }
                            }
                        }
                    }

                }
            }
        }
        FileOutputStream fileOut = new FileOutputStream(toFilePath + excelName + ".xlsx");
        wbCreat.write(fileOut);
        fileOut.close();
    }
/*
    public static void main(String[] args)
    {
        List<String> fromFilePaths = new ArrayList<String>();
        fromFilePaths.add("E:/testData/固定规则.xlsx");
        fromFilePaths.add("E:/testData/其他约束规则.xlsx");
        List<String> sheetNames = new ArrayList<String>();
        sheetNames.add("固定规则");
        sheetNames.add("指定时间");
        sheetNames.add("排除时间");
        try
        {
            copyExcelSheet(fromFilePaths, "E:/testData/", sheetNames, "其他约束");
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
*/
}
