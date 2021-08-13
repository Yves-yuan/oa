package cn.linter.oasys.entity;

import cn.linter.oasys.utils.AnnotationNotExport;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;
import java.sql.Timestamp;

@Alias("GoodsOrdering")
public class GoodsOrdering implements Serializable {
    @AnnotationNotExport
    private int id;
    @AnnotationNotExport
    private int goodsId;
    private int itemNo;
    private String goodsPartNumber;
//    private int number;
    private String desc;
    private int qtyShipped;

    private Double unitPrice;
    private Double extendedPrice;
    private String customerPo;
    private String project;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Timestamp shipmentDate;

    public int getItemNo() {
        return itemNo;
    }

    public void setItemNo(int itemNo) {
        this.itemNo = itemNo;
    }

    public int getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(int goodsId) {
        this.goodsId = goodsId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

//    public int getNumber() {
//        return number;
//    }
//
//    public void setNumber(int number) {
//        this.number = number;
//    }

    public String getGoodsPartNumber() {
        return goodsPartNumber;
    }

    public void setGoodsPartNumber(String goodsPartNumber) {
        this.goodsPartNumber = goodsPartNumber;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getQtyShipped() {
        return qtyShipped;
    }

    public void setQtyShipped(int qtyShipped) {
        this.qtyShipped = qtyShipped;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Double getExtendedPrice() {
        return extendedPrice;
    }

    public void setExtendedPrice(Double extendedPrice) {
        this.extendedPrice = extendedPrice;
    }

    public String getCustomerPo() {
        return customerPo;
    }

    public void setCustomerPo(String customerPo) {
        this.customerPo = customerPo;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public Timestamp getShipmentDate() {
        return shipmentDate;
    }

    public void setShipmentDate(Timestamp shipmentDate) {
        this.shipmentDate = shipmentDate;
    }
}
