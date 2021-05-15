package cn.linter.oasys.utils;

public class ExcelColumnIndex {
    private String excelColumnName;
    private String dbColumnName;
    private int columnIndex;

    public ExcelColumnIndex(String excelColumnName,String dbColumnName, int columnIndex) {
        this.excelColumnName = excelColumnName;
        this.dbColumnName = dbColumnName;
        this.columnIndex = columnIndex;
    }

    public String getDbColumnName() {
        return dbColumnName;
    }

    public void setDbColumnName(String dbColumnName) {
        this.dbColumnName = dbColumnName;
    }

    public String getExcelColumnName() {
        return excelColumnName;
    }

    public void setExcelColumnName(String excelColumnName) {
        this.excelColumnName = excelColumnName;
    }

    public int getColumnIndex() {
        return columnIndex;
    }

    public void setColumnIndex(int columnIndex) {
        this.columnIndex = columnIndex;
    }
}
