package com.pb.hackathon.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class Vehicle implements Serializable {

    private String regNumber;

    private String licenseNo;

    private BigDecimal volume;

    private BigDecimal weight;

    private String shipperId;

    public String getRegNumber() {
        return regNumber;
    }

    public void setRegNumber(String regNumber) {
        this.regNumber = regNumber;
    }

    public String getLicenseNo() {
        return licenseNo;
    }

    public void setLicenseNo(String licenseNo) {
        this.licenseNo = licenseNo;
    }

    public BigDecimal getVolume() {
        return volume;
    }

    public void setVolume(BigDecimal volume) {
        this.volume = volume;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public String getShipperId() {
        return shipperId;
    }

    public void setShipperId(String shipperId) {
        this.shipperId = shipperId;
    }
}
