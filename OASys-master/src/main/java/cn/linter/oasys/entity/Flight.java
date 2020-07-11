package cn.linter.oasys.entity;

import org.apache.ibatis.type.Alias;

import java.sql.Date;
import java.sql.Timestamp;

@Alias("Flight")
public class Flight {
    private int id;
    private String flightCompanyName;
    private String flightNo;
    private String cityFromName;
    private String depInfo;
    private Date depDate;
    private Timestamp depTime;
    private String cityToName;
    private String flightStatus;
    private String arrInfo;
    private Timestamp arrTime;
    private Double price;
    private Double punctualRate;


    public Date getDepDate() {
        return depDate;
    }

    public void setDepDate(Date depDate) {
        this.depDate = depDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFlightCompanyName() {
        return flightCompanyName;
    }

    public void setFlightCompanyName(String flightCompanyName) {
        this.flightCompanyName = flightCompanyName;
    }

    public String getFlightNo() {
        return flightNo;
    }

    public void setFlightNo(String flightNo) {
        this.flightNo = flightNo;
    }

    public String getCityFromName() {
        return cityFromName;
    }

    public void setCityFromName(String cityFromName) {
        this.cityFromName = cityFromName;
    }

    public String getDepInfo() {
        return depInfo;
    }

    public void setDepInfo(String depInfo) {
        this.depInfo = depInfo;
    }

    public Timestamp getDepTime() {
        return depTime;
    }

    public void setDepTime(Timestamp depTime) {
        this.depTime = depTime;
    }

    public String getCityToName() {
        return cityToName;
    }

    public void setCityToName(String cityToName) {
        this.cityToName = cityToName;
    }

    public String getFlightStatus() {
        return flightStatus;
    }

    public void setFlightStatus(String flightStatus) {
        this.flightStatus = flightStatus;
    }

    public String getArrInfo() {
        return arrInfo;
    }

    public void setArrInfo(String arrInfo) {
        this.arrInfo = arrInfo;
    }

    public Timestamp getArrTime() {
        return arrTime;
    }

    public void setArrTime(Timestamp arrTime) {
        this.arrTime = arrTime;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getPunctualRate() {
        return punctualRate;
    }

    public void setPunctualRate(Double punctualRate) {
        this.punctualRate = punctualRate;
    }
}
