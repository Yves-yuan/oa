package cn.linter.oasys.entity;

import org.apache.ibatis.type.Alias;

import java.io.Serializable;
import java.math.BigDecimal;

@Alias("Goods")
public class Goods implements Serializable {
    private int id;
    private String tagid;
    private String componentType;
    private String subType;
    private String manufacturer;
    private String manufacturerPartNumber;
    private String description;
    private String date;
    private String stockQty;
    private String annualStock;
    private String autoReplenishRate;
    private String leadTime;
    private BigDecimal price;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getAnnualStock() {
        return annualStock;
    }

    public void setAnnualStock(String annualStock) {
        this.annualStock = annualStock;
    }

    public String getAutoReplenishRate() {
        return autoReplenishRate;
    }

    public void setAutoReplenishRate(String autoReplenishRate) {
        this.autoReplenishRate = autoReplenishRate;
    }

    public String getLeadTime() {
        return leadTime;
    }

    public void setLeadTime(String leadTime) {
        this.leadTime = leadTime;
    }

    public String getComponentType() {
        return componentType;
    }

    public void setComponentType(String componentType) {
        this.componentType = componentType;
    }

    public String getSubType() {
        return subType;
    }

    public void setSubType(String subType) {
        this.subType = subType;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getTagid() {
        return tagid;
    }

    public void setTagid(String tagid) {
        this.tagid = tagid;
    }

    public String getManufacturerPartNumber() {
        return manufacturerPartNumber;
    }

    public void setManufacturerPartNumber(String manufacturerPartNumber) {
        this.manufacturerPartNumber = manufacturerPartNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStockQty() {
        return stockQty;
    }

    public void setStockQty(String stockQty) {
        this.stockQty = stockQty;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
