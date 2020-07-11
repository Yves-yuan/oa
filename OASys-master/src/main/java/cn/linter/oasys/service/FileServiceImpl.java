package cn.linter.oasys.service;

import cn.linter.oasys.entity.File;
import cn.linter.oasys.entity.Goods;
import cn.linter.oasys.entity.User;
import cn.linter.oasys.mapper.FileMapper;
import cn.linter.oasys.mapper.GoodsMapper;
import cn.linter.oasys.utils.ExcelColumnIndex;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.*;

@Service
public class FileServiceImpl implements FileService {
    private final FileMapper fileMapper;
    private final GoodsMapper goodsMapper;

    @Autowired
    public FileServiceImpl(FileMapper fileMapper, GoodsMapper goodsMapper) {
        this.fileMapper = fileMapper;
        this.goodsMapper = goodsMapper;
    }

    @Override
    public PageInfo<?> getFiles(int userId, int parentId, boolean personal, int pageNumber, int pageSize) {
        PageHelper.startPage(pageNumber, pageSize);
        return new PageInfo<>(fileMapper.selectFiles(userId, parentId, personal));
    }

    @Override
    public void addFolder(String folderName, int userId, int parentId, boolean personal) {
        File folder = new File();
        User user = new User();
        user.setId(userId);
        folder.setName(folderName);
        folder.setPath("/");
        folder.setSize("-");
        folder.setType("文件夹");
        folder.setUser(user);
        folder.setParentId(parentId);
        folder.setPersonal(personal);
        long time = System.currentTimeMillis();
        folder.setCreateTime(new Timestamp(time));
        fileMapper.insertFile(folder);
    }

    @Override
    public void uploadFile(String fileName, long fileSize, User user, int parentId, boolean personal) {
        File file = new File();
        int index = fileName.lastIndexOf('.');
        file.setName(fileName.substring(0, index));
        file.setPath("/file/" + fileName);
        file.setType(fileName.substring(index + 1));
        file.setSize(formatSize(fileSize));
        file.setParentId(parentId);
        file.setPersonal(personal);
        file.setUser(user);
        long time = System.currentTimeMillis();
        file.setCreateTime(new Timestamp(time));
        fileMapper.insertFile(file);
    }

////    public int importData(String filePath) throws Exception {
////        Workbook wb = null;
////        if (filePath == null) {
////            return -1;
////        }
////        String[] columns = GoodsServiceImpl.arr;
////        String extString = filePath.substring(filePath.lastIndexOf("."));
////        InputStream is = new FileInputStream(filePath);
////        if (".xls".equals(extString)) {
////            wb = new HSSFWorkbook(is);
////        } else if (".xlsx".equals(extString)) {
////            wb = new XSSFWorkbook(is);
////        } else {
////            throw new Exception("文件后缀名不正确");
////        }
////        List<Map<String, Object>> list = new ArrayList<>();
////        Sheet sheet = wb.getSheetAt(0);
////        int rownum = sheet.getPhysicalNumberOfRows();
////        //获取表字段对应的列号表
////        Row row = sheet.getRow(0);
////        int columnNum = row.getPhysicalNumberOfCells();
////        List<ExcelColumnIndex> ecis = new ArrayList<>();
////        for (String x : columns) {
////            for (int j = 0; j < columnNum; j++) {
////                String cellData = (String) getCellFormatValue(row.getCell(j));
////                if (x.toLowerCase().equals(cellData.toLowerCase())){
////                    ecis.add(new ExcelColumnIndex(x.toLowerCase(),j));
////                }
////            }
////        }
////        for (int i = 1; i < rownum; i++) {
////            Map<String, Object> map = new LinkedHashMap<>();
////            row = sheet.getRow(i);
////            if (row != null) {
////                for (ExcelColumnIndex e : ecis) {
////                    Object cellData = getCellFormatValue(row.getCell(e.getColumnIndex()));
////                    String columnName = e.getColumnName();
////                    if (columnName == "price") {
////                        String cs = (String) cellData;
////                        try {
////                            BigDecimal b = new BigDecimal(cs);
////                            cellData = b;
////                        } catch (Exception ex) {
////                            cellData = null;
////                        }
////                    }
////                    map.put(columnName.replace(" ", ""), cellData);
////                }
////            } else {
////                break;
////            }
////            list.add(map);
////        }
////        return goodsMapper.insertGoodsList(list);
////    }
//
//    public Object getCellFormatValue(Cell cell) {
//        Object cellValue = null;
//        if (cell != null) {
//            //判断cell类型
//            switch (cell.getCellType()) {
//                case Cell.CELL_TYPE_NUMERIC: {
//                    cellValue = String.valueOf(cell.getNumericCellValue());
//                    break;
//                }
//                case Cell.CELL_TYPE_FORMULA: {
//                    //判断cell是否为日期格式
//                    if (DateUtil.isCellDateFormatted(cell)) {
//                        //转换为日期格式YYYY-mm-dd
//                        cellValue = cell.getDateCellValue();
//                    } else {
//                        //数字
//                        cellValue = String.valueOf(cell.getNumericCellValue());
//                    }
//                    break;
//                }
//                case Cell.CELL_TYPE_STRING: {
//                    cellValue = cell.getRichStringCellValue().getString();
//                    break;
//                }
//                default:
//                    cellValue = "";
//            }
//        } else {
//            cellValue = "";
//        }
//        return cellValue;
//    }

    @Override
    public void renameFile(int id, String newName) {
        fileMapper.updateFile(id, newName);
    }

    @Override
    public void deleteFile(Integer[] ids) {
        String rootPath = new ApplicationHome(getClass()).getSource().getPath();
        for (Integer id : ids) {
            File file = fileMapper.selectFile(id);
            if (!file.getType().equals("文件夹")) {
                String path = rootPath + "/static" + file.getPath();
                java.io.File physicalFile = new java.io.File(path);
                physicalFile.delete();
            }
        }
        fileMapper.deleteFile(ids);
    }

    public String formatSize(long size) {
        if (size < 1024) {
            return size + "B";
        } else if (size < 1048576) {
            return (size >>= 10) + "KB";
        } else {
            return (size >>= 20) + "MB";
        }
    }
}
