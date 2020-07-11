package cn.linter.oasys.entity;

import org.apache.ibatis.type.Alias;

import java.sql.Timestamp;

@Alias("TrainTicket")
public class TrainTicket {
    private int id;
    private String fromName;
    private String toName;
    private String trainNum;
    private Timestamp depTime;
    private Timestamp arrTime;
    private String remarks;
    private String price;

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFromName() {
        return fromName;
    }

    public void setFromName(String fromName) {
        this.fromName = fromName;
    }

    public String getToName() {
        return toName;
    }

    public void setToName(String toName) {
        this.toName = toName;
    }

    public String getTrainNum() {
        return trainNum;
    }

    public void setTrainNum(String trainNum) {
        this.trainNum = trainNum;
    }

    public Timestamp getDepTime() {
        return depTime;
    }

    public void setDepTime(Timestamp depTime) {
        this.depTime = depTime;
    }

    public Timestamp getArrTime() {
        return arrTime;
    }

    public void setArrTime(Timestamp arrTime) {
        this.arrTime = arrTime;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
