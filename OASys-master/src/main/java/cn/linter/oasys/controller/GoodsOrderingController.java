package cn.linter.oasys.controller;

import cn.linter.oasys.entity.Goods;
import cn.linter.oasys.entity.GoodsOrdering;
import cn.linter.oasys.entity.Response;
import cn.linter.oasys.entity.User;
import cn.linter.oasys.service.GoodsOrderingService;
import cn.linter.oasys.service.GoodsOrderingServiceImpl;
import cn.linter.oasys.utils.AnnotationNotExport;
import cn.linter.oasys.utils.ClassUtil;
import cn.linter.oasys.utils.ExcelUtils;
import com.github.pagehelper.PageInfo;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import org.apache.poi.hssf.util.HSSFColor;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/goodsOrdering")
public class GoodsOrderingController {
    private final GoodsOrderingService goodsOrderingService;
    @Value("${spring.upload-path}")
    private String path;

    @Autowired
    public GoodsOrderingController(GoodsOrderingService goodsOrderingService) {
        this.goodsOrderingService = goodsOrderingService;
    }

    @PostMapping("/import")
    public Response importData(@AuthenticationPrincipal User user, @RequestParam("file") MultipartFile multipartFile) throws IOException {
//        String fileName = multipartFile.getOriginalFilename();
//        String filePath = path + "/" + fileName;
//        Files.write(Paths.get(filePath), multipartFile.getBytes());
        try {
            Boolean res = goodsOrderingService.importData(multipartFile);
            if (res) {
                return new Response("success", "导入成功！");
            } else {
                return new Response("failure", "导入失败!检查数据Mftr. Part No 是否有对应商品");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Response("failure", "导入失败" + e.getMessage());
        }
    }

    @PostMapping("/importSimple")
    public Response importDataSimple(@AuthenticationPrincipal User user, @RequestParam("file") MultipartFile multipartFile) throws IOException {
//        String fileName = multipartFile.getOriginalFilename();
//        String filePath = path + "/" + fileName;
//        Files.write(Paths.get(filePath), multipartFile.getBytes());
        try {
            Boolean res = goodsOrderingService.importDataSimple(multipartFile);
            if (res) {
                return new Response("success", "导入成功！");
            } else {
                return new Response("failure", "导入失败!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Response("failure", "导入失败" + e.getMessage());
        }
    }

    @PostMapping("/update")
    public Response updateGoods(@RequestBody GoodsOrdering goods) {
        int result = goodsOrderingService.updateGoodsOrdering(goods);
        if (result == -1) {
            return new Response("error", "数据已存在！");
        }
        return new Response("success", "更新成功！");
    }

    @GetMapping("/getGoodsOrdering")
    public Response getGoodsOrdering(@RequestParam(value = "pageNumber", defaultValue = "1") int pageNumber,
                                     @RequestParam(value = "pageSize", defaultValue = "8") int pageSize,
                                     Timestamp begin,
                                     Timestamp end,
                                     String goodsPartNumber,
                                     String project,
                                     String customerPo
    ) {
        PageInfo<?> pageInfo = goodsOrderingService.getGoodsOrdering(pageNumber, pageSize, begin, end,
                goodsPartNumber, project, customerPo);
        return new Response("success", pageInfo.getTotal(), pageInfo.getList());
    }

    @PostMapping("/addOrdering")
    public Response addOrdering(@RequestBody GoodsOrdering goodsOrdering) {
        Boolean result = goodsOrderingService.addGoodsOrdering(goodsOrdering);
        if (!result) {
            return new Response("error", "order fail！");
        }
        return new Response("success", "order success！");
    }

    @PostMapping("/delete")
    public Response deleteGoods(@RequestBody Integer[] ids) {

        goodsOrderingService.deleteGoodsOrdering(ids);
        return new Response("success", "删除成功！");
    }

    @PostMapping("/deleteAll")
    public Response deleteAllGoodsOrder() {
        try {
            int result = goodsOrderingService.deleteAllGoodsOrdering();
            return new Response("success", "删除成功");
        } catch (Exception e) {
            return new Response("error", "删除失败！");
        }

    }

    @PostMapping(value = "/exportGoodsOrdering")
    public String exportGoods(
            Timestamp begin,
            Timestamp end,
            String goodsPartNumber,
            String project,
            String customerPo,
            String shippingDetails,
            String company,
            String shippingTo,
            HttpServletResponse response
    ) {
        List<?> list = goodsOrderingService.exportGoodsOrdering(1, Integer.MAX_VALUE,
                begin, end, goodsPartNumber, project, customerPo).getList();
        //调用Excel导出工具类
        //客户需要所有数据导出
        export(response, list,
                GoodsOrderingServiceImpl.exportExcelColumnNameArr
                , shippingDetails, company, shippingTo
        );
        return "success";
    }

    public static void export(HttpServletResponse response, List<?> importlist,
                              String[] attributeNames, String shippingDetails, String company, String shippingTo) {

        //获取数据集
        List<?> datalist = importlist;

        //声明一个工作薄
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFCellStyle cs = workbook.createCellStyle(); // 换行的关键，自定义单元格内容换行规则
        cs.setWrapText(true);
        //生成一个表格
        HSSFSheet sheet = workbook.createSheet();
        //设置表格默认列宽度为15个字节
        sheet.setDefaultColumnWidth((short) 18);
        //获取字段名数组
        //获取对象属性
        //获取对象get方法
        Field[] fields = new Field[0];
        List<Method> methodList = new ArrayList<>();
        //order detail
        GoodsOrdering first = new GoodsOrdering();
        if (!importlist.isEmpty()) {
            fields = ClassUtil.getClassAttribute(importlist.get(0));
            methodList = ClassUtil.getMethodGet(importlist.get(0));
            first = (GoodsOrdering) importlist.get(0);
        }
        int orderDetailRowIndex = 1;
        int shipDetailRowIndex = 6;
        int companyRowIndex = 9;
        int shippingToRowIndex = 12;
        int columnNameRowIndex = 15;
        int dataRowIndex = 16;
        HSSFCellStyle csTitle = workbook.createCellStyle();
        Font titleFont = workbook.createFont();
        titleFont.setColor(HSSFColor.HSSFColorPredefined.GREEN.getIndex());
        csTitle.setFont(titleFont);
        Row rowDetail = sheet.createRow(orderDetailRowIndex);
        Cell rowDetailCell = rowDetail.createCell(2);
        rowDetailCell.setCellType(CellType.STRING);
        rowDetailCell.setCellValue("Order Details:");
        rowDetailCell.setCellStyle(csTitle);
        Row rowPoNumber = sheet.createRow(orderDetailRowIndex + 1);
        Cell rowPoNumberCell = rowPoNumber.createCell(2);
        rowPoNumberCell.setCellType(CellType.STRING);
        rowPoNumberCell.setCellValue("Your PO Number:" + first.getCustomerPo());
        Row rowProject = sheet.createRow(orderDetailRowIndex + 2);
        Cell rowProjectCell = rowProject.createCell(2);
        rowProjectCell.setCellType(CellType.STRING);
        rowProjectCell.setCellValue("Project#:" + first.getProject());
        Row rowOrderDate = sheet.createRow(orderDetailRowIndex + 3);
        Cell rowOrderDateCell = rowOrderDate.createCell(2);
        rowOrderDateCell.setCellType(CellType.STRING);
        rowOrderDateCell.setCellValue("Order Date:" + first.getShipmentDate());

        //Shipping Detail
        Row rowShippingDetails = sheet.createRow(shipDetailRowIndex);
        Cell rowShippingDetailsCell = rowShippingDetails.createCell(2);
        rowShippingDetailsCell.setCellType(CellType.STRING);
        rowShippingDetailsCell.setCellValue("Shipping Detail:");
        rowShippingDetailsCell.setCellStyle(csTitle);
        Row rowShippingDetailsInfo = sheet.createRow(shipDetailRowIndex + 1);
        Cell rowShippingDetailsInfoCell = rowShippingDetailsInfo.createCell(2);
        rowShippingDetailsInfoCell.setCellType(CellType.STRING);
        if (shippingDetails == null) {
            shippingDetails = "";
        }
        shippingDetails = shippingDetails.replace("\n", "\r\n");
        rowShippingDetailsInfoCell.setCellValue(shippingDetails);
        rowShippingDetailsInfoCell.setCellStyle(cs);

        //Company
        Row rowCompany = sheet.createRow(companyRowIndex);
        Cell rowCompanyCell = rowCompany.createCell(2);
        rowCompanyCell.setCellType(CellType.STRING);
        rowCompanyCell.setCellValue("Company:");
        rowCompanyCell.setCellStyle(csTitle);
        Row rowCompanyInfo = sheet.createRow(companyRowIndex + 1);
        Cell rowCompanyInfoCell = rowCompanyInfo.createCell(2);
        rowCompanyInfoCell.setCellType(CellType.STRING);
        rowCompanyInfoCell.setCellValue(company);
        rowCompanyInfoCell.setCellStyle(cs);
        //Shipping to
        Row rowShippingTo = sheet.createRow(shippingToRowIndex);
        Cell rowShippingToCell = rowShippingTo.createCell(2);
        rowShippingToCell.setCellType(CellType.STRING);
        rowShippingToCell.setCellValue("Shipping to:");
        rowShippingToCell.setCellStyle(csTitle);
        Row rowShippingToInfo = sheet.createRow(shippingToRowIndex + 1);
        Cell rowShippingToInfoCell = rowShippingToInfo.createCell(2);
        rowShippingToInfoCell.setCellType(CellType.STRING);
        rowShippingToInfoCell.setCellValue(shippingTo);
        rowShippingToInfoCell.setCellStyle(cs);
        HSSFCellStyle tableStyle = workbook.createCellStyle();
        tableStyle.setBorderBottom(BorderStyle.THIN);
        tableStyle.setBorderLeft(BorderStyle.THIN);
        tableStyle.setBorderRight(BorderStyle.THIN);
        tableStyle.setBorderTop(BorderStyle.THIN);
        //循环字段名数组，创建标题行
        Row row = sheet.createRow(columnNameRowIndex);
        for (int j = 0; j < attributeNames.length; j++) {
            //创建列
            Cell cell = row.createCell(j + 1);
            //设置单元类型为String
            cell.setCellType(CellType.STRING);
            cell.setCellValue(ExcelUtils.transCellType(attributeNames[j]));
            cell.setCellStyle(tableStyle);
        }
        //数据填写
        Double totalExpend = 0.0;
        for (int i = 0; i < datalist.size(); i++) {
            //因为第一行已经用于创建标题行，故从第二行开始创建
            row = sheet.createRow(i + dataRowIndex);
            //如果是第一行就让其为标题行
            Object targetObj = datalist.get(i);
            GoodsOrdering o = (GoodsOrdering) targetObj;
            totalExpend += o.getExtendedPrice();
            int columnIndex = 0;
            for (int j = 0; j < fields.length; j++) {
                if (fields[j].getAnnotation(AnnotationNotExport.class) != null) {
                    continue;
                }
                //创建列
                Cell cell = row.createCell(columnIndex + 1);
                cell.setCellType(CellType.STRING);
                //
                try {
                    Object value = methodList.get(j).invoke(targetObj, new Object[]{});
                    cell.setCellValue(ExcelUtils.transCellType(value));
                    cell.setCellStyle(tableStyle);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
                columnIndex++;
            }
        }
        //Your Merchandise Total
        Row rowMerchandiseTotal = sheet.createRow(dataRowIndex + datalist.size() + 2);
        Cell rowMerchandiseTotalCell = rowMerchandiseTotal.createCell(5);
        rowMerchandiseTotalCell.setCellType(CellType.STRING);
        rowMerchandiseTotalCell.setCellValue("Your Merchandise Total");
        Cell rowMerchandiseTotalDollarCell = rowMerchandiseTotal.createCell(6);
        rowMerchandiseTotalDollarCell.setCellType(CellType.STRING);
        rowMerchandiseTotalDollarCell.setCellValue("$" + totalExpend);
        Row rowMerchandiseTotalSalesInUSD = sheet.createRow(dataRowIndex + datalist.size() + 3);
        Cell rowMerchandiseTotalSalesInUSDCell = rowMerchandiseTotalSalesInUSD.createCell(6);
        rowMerchandiseTotalSalesInUSDCell.setCellType(CellType.STRING);
        rowMerchandiseTotalSalesInUSDCell.setCellValue("Sales in USD");
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
}
