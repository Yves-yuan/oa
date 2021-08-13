package cn.linter.oasys.service;

import cn.linter.oasys.entity.Goods;
import cn.linter.oasys.entity.GoodsOrdering;
import cn.linter.oasys.mapper.GoodsMapper;
import cn.linter.oasys.mapper.GoodsOrderingMapper;
import cn.linter.oasys.utils.ExcelColumnIndex;
import cn.linter.oasys.utils.ExcelUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Predicate;

@Service
public class GoodsOrderingServiceImpl implements GoodsOrderingService {
    private final GoodsOrderingMapper goodsOrderingMapper;
    private final GoodsMapper goodsMapper;
    public static String[] excelColumnNameArr = new String[]{"Item No", "Mftr. Part No ", "Description ",
            "Qty shipped", "Unit Price", "Extended Price"
//            , "Customer PO#", "Project#", "Shipment date"
    };
    public static String[] exportExcelColumnNameArr = new String[]{"Item No", "Mftr. Part No ", "Description ",
            "Qty shipped", "Unit Price", "Extended Price"
            , "Customer PO#", "Project#", "Shipment date"
    };

    public static String[] dbColumnNameArr = new String[]{"itemNo", "goodsPartNumber", "desc", "qtyShipped",
            "unitPrice", "extendedPrice"
//            , "customerPo", "project", "shipmentDate"
    };
    public static String[] excelSimpleImportColumnNameArr = new String[]{"Item No", "Mftr. Part No", "Qty shipped"};
    public static String[] dbSimpleColumnNameArr = new String[]{"itemNo", "goodsPartNumber", "qtyShipped"};

    @Autowired
    public GoodsOrderingServiceImpl(GoodsOrderingMapper goodsOrderMapper, GoodsMapper goodsMapper) {
        this.goodsOrderingMapper = goodsOrderMapper;
        this.goodsMapper = goodsMapper;
    }

    @Override
    public GoodsOrdering getGoodsOrderingById(int id) {
        return goodsOrderingMapper.selectGoodsOrderingById(id);
    }

    @Override
    public PageInfo<?> getGoodsOrdering(int pageNumber, int pageSize, Timestamp begin, Timestamp end, String goodsPartNumber,
                                        String project, String customPo) {
        PageHelper.startPage(pageNumber, pageSize);
        HashMap<String, Object> m = new HashMap<>();
        m.put("pageNumber", pageNumber);
        m.put("pageSize", pageSize);
        m.put("begin", begin);
        m.put("end", end);
        m.put("goodsPartNumber", goodsPartNumber);
        m.put("project", project);
        m.put("customerPo", customPo);
        return new PageInfo<>(goodsOrderingMapper.selectGoodsOrdering(m));
    }

    @Override
    public PageInfo<?> exportGoodsOrdering(int pageNumber, int pageSize, Timestamp begin, Timestamp end, String goodsPartNumber,
                                           String project, String customPo) {
        PageHelper.startPage(pageNumber, pageSize);
        HashMap<String, Object> m = new HashMap<>();
        m.put("pageNumber", pageNumber);
        m.put("pageSize", pageSize);
        m.put("begin", begin);
        m.put("end", end);
        m.put("goodsPartNumber", goodsPartNumber);
        m.put("project", project);
        m.put("customerPo", customPo);
        return new PageInfo<>(goodsOrderingMapper.exportGoodsOrdering(m));
    }

    @Override
    public int updateGoodsOrdering(GoodsOrdering goods) {
        return goodsOrderingMapper.updateGoodsOrdering(goods);
    }

    @Override
    public Boolean addGoodsOrdering(GoodsOrdering goods) {
        Boolean res = packing(goods);
        return res;
    }

    @Override
    public Boolean importData(MultipartFile multipartFile) throws Exception {
        Workbook wb = null;
        String fileName = multipartFile.getOriginalFilename();
        String extString = fileName.substring(fileName.lastIndexOf("."));
        InputStream is = multipartFile.getInputStream();
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
        for (int i = 0; i < excelColumnNameArr.length; i++) {
            String excelColumnName = excelColumnNameArr[i];
            String dbColumnName = dbColumnNameArr[i];
            for (int j = 0; j < columnNum; j++) {
                String cellData = (String) ExcelUtils.getCellFormatValue(row.getCell(j));
                if (excelColumnName.equalsIgnoreCase(cellData)) {
                    ecis.add(new ExcelColumnIndex(excelColumnName.toLowerCase(), dbColumnName, j));
                }
            }
        }
        for (int i = 1; i < rownum; i++) {
            Map<String, Object> map = new LinkedHashMap<>();
            row = sheet.getRow(i);
            if (row != null) {
                for (ExcelColumnIndex e : ecis) {
                    String columnName = e.getExcelColumnName();
                    String dbName = e.getDbColumnName();
                    Object cellData;
                    if (columnName.equals("Shipment date")) {
                        Cell cell = row.getCell(e.getColumnIndex());
                        if (cell != null) {
                            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            cellData = format.format(row.getCell(e.getColumnIndex()).getDateCellValue());
                        } else {
                            cellData = "";
                        }

                    } else {
                        cellData = ExcelUtils.getCellFormatValue(row.getCell(e.getColumnIndex()));
                    }
                    map.put(dbName, cellData);
                }
            } else {
                break;
            }
            list.add(map);
        }
        return packing(list);
    }

    @Override
    public Boolean importDataSimple(MultipartFile multipartFile) throws Exception {
        Workbook wb = null;
        String fileName = multipartFile.getOriginalFilename();

        String extString = fileName.substring(fileName.lastIndexOf("."));
        InputStream is = multipartFile.getInputStream();
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
        int arrLength = excelSimpleImportColumnNameArr.length;
        //读取固定信息
//        Row tmp1 =  sheet.getRow(1);
//        Cell tmp1c = tmp1.getCell(1);
//        String customPo = tmp1c.getStringCellValue();
        String customPo = (String) ExcelUtils.getCellFormatValue(sheet.getRow(0).getCell(1));
        String project = (String) ExcelUtils.getCellFormatValue(sheet.getRow(1).getCell(1));
        Timestamp shipmentDate = new Timestamp(sheet.getRow(2).getCell(1).getDateCellValue().getTime());
        //获取表字段对应的列号表，第五列开始读取
        int columnNameRowIndex = 4;
        Row row = sheet.getRow(columnNameRowIndex);
        int columnNum = excelSimpleImportColumnNameArr.length;
        List<ExcelColumnIndex> ecis = new ArrayList<>();
        for (int i = 0; i < arrLength; i++) {
            String excelColumnName = excelSimpleImportColumnNameArr[i];
            String dbColumnName = dbSimpleColumnNameArr[i];
            for (int j = 0; j < columnNum; j++) {
                String cellData = (String) ExcelUtils.getCellFormatValue(row.getCell(j));
                if (cellData.toLowerCase().contains(excelColumnName.toLowerCase())) {
                    ecis.add(new ExcelColumnIndex(excelColumnName.toLowerCase(), dbColumnName, j));
                }
            }
        }
        for (int i = columnNameRowIndex + 1; i < rownum; i++) {
            Map<String, Object> map = new LinkedHashMap<>();
            row = sheet.getRow(i);
            if (row != null) {
                for (ExcelColumnIndex e : ecis) {
                    String columnName = e.getExcelColumnName();
                    String dbName = e.getDbColumnName();
                    Object cellData;
                    cellData = ExcelUtils.getCellFormatValue(row.getCell(e.getColumnIndex()));
                    map.put(dbName, cellData);
                }
                map.put("customerPo", customPo);
                map.put("project", project);
                map.put("shipmentDate", shipmentDate);
            } else {
                break;
            }
            list.add(map);
        }
        return packing(list);
    }


    public boolean packing(GoodsOrdering o) {
        String partNo = o.getGoodsPartNumber();
        int packingNumber = o.getQtyShipped();
        List<Goods> goods = goodsMapper.selectGoodsByPartNumber(partNo);
        Integer totalNumber = 0;
        for (Goods g : goods) {
            if (g.getStockQty() != null) {
                totalNumber += g.getStockQty();
            }
        }
        if (totalNumber < packingNumber) {
            return false;
        }
        Collections.sort(goods, new Comparator<Goods>() {
            @Override
            public int compare(Goods o1, Goods o2) {
                if (o1.getDate() == null) {
                    return -1;
                } else if (o2.getDate() == null) {
                    return 1;
                }
                if (o1.getDate().getTime() < o2.getDate().getTime()) {
                    return -1;
                }
                return 0;
            }
        });
        Goods choose = null;
        for (Goods g : goods) {
            if (g.getStockQty() >= packingNumber) {
                g.setStockQty(g.getStockQty() - packingNumber);
                packingNumber = 0;
            } else {
                packingNumber = packingNumber - g.getStockQty();
                g.setStockQty(0);
            }
            goodsMapper.updateGoods(g);
            if (packingNumber == 0) {
                choose = g;
                break;
            }
        }
        o.setGoodsId(choose.getId());
        o.setDesc(choose.getDescription());
        o.setUnitPrice(choose.getPrice().doubleValue());
        o.setExtendedPrice(choose.getPrice().doubleValue() * o.getQtyShipped());
        goodsOrderingMapper.insertGoodsOrdering(o);
        return true;
    }

    public boolean packing(List<Map<String, Object>> list) throws Exception {
        list.removeIf(stringObjectMap -> stringObjectMap.get("itemNo").equals(""));
        Map<String, Integer> total = new HashMap<>();
        for (Map<String, Object> order : list) {
            String partNo = order.get("goodsPartNumber").toString();
            if(partNo.equals("")){
                continue;
            }
            int number;
            try {
                number = (int) Double.parseDouble(order.get("qtyShipped").toString());
            } catch (Exception e) {
                throw new Exception("part number " + partNo + " qtyShipped is not valid");
            }
            int sum = total.getOrDefault(partNo, 0) + number;
            total.put(partNo, sum);
        }
        for (Map.Entry<String, Integer> es : total.entrySet()) {
            String partNum = es.getKey();
            Integer packingNumber = es.getValue();
            List<Goods> goods = goodsMapper.selectGoodsByPartNumber(partNum);
            Integer totalNumber = 0;
            for (Goods g : goods) {
                if (g.getStockQty() != null) {
                    totalNumber += g.getStockQty();
                }
            }
            if (totalNumber < packingNumber) {
                throw new Exception("库存不够");
            }
        }
        for (Map<String, Object> order : list) {
            String partNo = order.get("goodsPartNumber").toString();
            if(partNo.equals("")){
                continue;
            }
            int number;
            try {
                number = (int) Double.parseDouble(order.get("qtyShipped").toString());
            } catch (Exception e) {
                throw new Exception("part number " + partNo + " qtyShipped is not valid");
            }
            List<Goods> goods = goodsMapper.selectGoodsByPartNumber(partNo);
            Collections.sort(goods, new Comparator<Goods>() {
                @Override
                public int compare(Goods o1, Goods o2) {
                    if (o1.getDate() == null) {
                        return -1;
                    } else if (o2.getDate() == null) {
                        return 1;
                    }
                    if (o1.getDate().getTime() < o2.getDate().getTime()) {
                        return -1;
                    }
                    return 0;
                }
            });
            Goods choose = null;
            for (Goods g : goods) {
                if (g.getStockQty() == null) {
                    continue;
                }
                if (g.getStockQty() >= number) {
                    g.setStockQty(g.getStockQty() - number);
                    number = 0;
                } else {
                    number = number - g.getStockQty();
                    g.setStockQty(0);
                }
                goodsMapper.updateGoods(g);
                if (number == 0) {
                    choose = g;
                    break;
                }
            }
            order.put("goodsId", choose.getId());
            order.put("desc", choose.getDescription());
            order.put("unitPrice", choose.getPrice());
            order.put("extendedPrice", choose.getPrice().doubleValue() * (int) Double.parseDouble(order.get("qtyShipped").toString()));
        }
        goodsOrderingMapper.insertGoodsOrderingList(list);
        return true;
    }

    @Override
    public void deleteGoodsOrdering(Integer[] ids) {
        try {
            for (Integer id : ids) {
                GoodsOrdering order = getGoodsOrderingById(id);
                Integer goodsId = order.getGoodsId();
                Goods g = goodsMapper.selectGoodsById(goodsId);
                g.setStockQty(g.getStockQty() + order.getQtyShipped());
                goodsMapper.updateGoods(g);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        goodsOrderingMapper.deleteGoodsOrdering(ids);
    }

    @Override
    public int deleteAllGoodsOrdering() {
        try {
            HashMap<String, Object> m = new HashMap<>();
            List<GoodsOrdering> orders = goodsOrderingMapper.selectGoodsOrdering(m);
            for (GoodsOrdering order : orders) {
                Integer goodsId = order.getGoodsId();
                Goods g = goodsMapper.selectGoodsById(goodsId);
                g.setStockQty(g.getStockQty() + order.getQtyShipped());
                goodsMapper.updateGoods(g);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return goodsOrderingMapper.deleteAllGoodsOrdering();
    }

}
