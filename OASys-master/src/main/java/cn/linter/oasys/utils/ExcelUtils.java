package cn.linter.oasys.utils;

import cn.linter.oasys.service.HospitalDepartmentServiceImpl;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;

public class ExcelUtils {
    /**
     * 将传入的数据导出excel表并下载
     *
     * @param response       返回的HttpServletResponse
     * @param importlist     要导出的对象的集合
     * @param attributeNames 含有每个对象属性在excel表中对应的标题字符串的数组（请按对象中属性排序调整字符串在数组中的位置）
     */
    public static void export(HttpServletResponse response, List<?> importlist, String[] attributeNames) {
        //获取数据集
        List<?> datalist = importlist;

        //声明一个工作薄
        HSSFWorkbook workbook = new HSSFWorkbook();
        //生成一个表格
        HSSFSheet sheet = workbook.createSheet();
        //设置表格默认列宽度为15个字节
        sheet.setDefaultColumnWidth((short) 18);

        //获取字段名数组
        //获取对象属性
        Field[] fields = ClassUtil.getClassAttribute(importlist.get(0));
        //获取对象get方法
        List<Method> methodList = ClassUtil.getMethodGet(importlist.get(0));

        //循环字段名数组，创建标题行
        Row row = sheet.createRow(0);
        for (int j = 0; j < attributeNames.length; j++) {
            //创建列
            Cell cell = row.createCell(j);
            //设置单元类型为String
            cell.setCellType(CellType.STRING);
            cell.setCellValue(transCellType(attributeNames[j]));
        }
        //创建普通行
        for (int i = 0; i < datalist.size(); i++) {
            //因为第一行已经用于创建标题行，故从第二行开始创建
            row = sheet.createRow(i + 1);
            //如果是第一行就让其为标题行
            Object targetObj = datalist.get(i);
            for (int j = 0; j < fields.length; j++) {
                //创建列
                Cell cell = row.createCell(j);
                cell.setCellType(CellType.STRING);
                //
                try {
                    Object value = methodList.get(j).invoke(targetObj, new Object[]{});
                    cell.setCellValue(transCellType(value));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
        response.setContentType("application/octet-stream");
        response.addHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        //默认Excel名称
        try {
            response.setHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode("test.xls", "utf-8"));
            OutputStream os = response.getOutputStream();
            workbook.write(os);
            os.write(1);
            os.flush();
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static List<Map<String, Object>> importExcel(String filePath, String[] columns, BiFunction<Object, String, Object> f) throws Exception {
        Workbook wb = null;
        if (filePath == null) {
            return null;
        }
        String extString = filePath.substring(filePath.lastIndexOf("."));
        InputStream is = new FileInputStream(filePath);
        if (".xls".equals(extString)) {
            wb = new HSSFWorkbook(is);
        } else if (".xlsx".equals(extString)) {
            wb = new XSSFWorkbook(is);
        } else {
            throw new Exception("文件后缀名不正确");
        }
        List<Map<String, Object>> list = new ArrayList<>();
        Sheet sheet = wb.getSheetAt(0);
        int rownum = sheet.getPhysicalNumberOfRows();
        //获取表字段对应的列号表
        Row row = sheet.getRow(0);
        int columnNum = row.getPhysicalNumberOfCells();
        List<ExcelColumnIndex> ecis = new ArrayList<>();
        for (String x : columns) {
            for (int j = 0; j < columnNum; j++) {
                String cellData = (String) getCellFormatValue(row.getCell(j));
                if (x.toLowerCase().equals(cellData.toLowerCase())) {
                    ecis.add(new ExcelColumnIndex(x.toLowerCase(), j));
                }
            }
        }
        for (int i = 1; i < rownum; i++) {
            Map<String, Object> map = new LinkedHashMap<>();
            row = sheet.getRow(i);
            if (row != null) {
                for (ExcelColumnIndex e : ecis) {
                    Object cellData = getCellFormatValue(row.getCell(e.getColumnIndex()));
                    String columnName = e.getColumnName();
                    if (f != null) {
                        cellData = f.apply(cellData, columnName);
                    }
                    map.put(columnName.replace(" ", ""), cellData);
                }
            } else {
                break;
            }
            list.add(map);
        }
        return list;
    }

    public static Object getCellFormatValue(Cell cell) {
        Object cellValue = null;
        if (cell != null) {
            //判断cell类型
            switch (cell.getCellType()) {
                case Cell.CELL_TYPE_NUMERIC: {
                    cellValue = String.valueOf(cell.getNumericCellValue());
                    break;
                }
                case Cell.CELL_TYPE_FORMULA: {
                    //判断cell是否为日期格式
                    if (DateUtil.isCellDateFormatted(cell)) {
                        //转换为日期格式YYYY-mm-dd
                        cellValue = cell.getDateCellValue();
                    } else {
                        //数字
                        cellValue = String.valueOf(cell.getNumericCellValue());
                    }
                    break;
                }
                case Cell.CELL_TYPE_STRING: {
                    cellValue = cell.getRichStringCellValue().getString();
                    break;
                }
                default:
                    cellValue = "";
            }
        } else {
            cellValue = "";
        }
        return cellValue;
    }

    private static String transCellType(Object value) {
        String str = null;
        if (value instanceof Date) {
            Date date = (Date) value;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            str = sdf.format(date);
        } else {
            str = String.valueOf(value);
            if (str == "null") {
                str = "";
            }
        }

        return str;
    }
}
