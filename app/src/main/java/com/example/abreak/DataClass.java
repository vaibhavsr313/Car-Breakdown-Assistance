package com.example.abreak;

public class DataClass {

    private String dataName;
    private String dataAddress;
    private String dataNo;
    private String dataImage;
    private String dataEmail;

    public String getDataName() {
        return dataName;
    }

    public String getDataAddress() {
        return dataAddress;
    }

    public String getDataNo() {
        return dataNo;
    }

    public String getDataImage() {
        return dataImage;
    }

    public String getDataEmail() {
        return dataEmail;
    }

    public DataClass(String dataName, String dataAddress, String dataNo, String dataImage, String dataEmail) {
        this.dataName = dataName;
        this.dataAddress = dataAddress;
        this.dataNo = dataNo;
        this.dataImage = dataImage;
        this.dataEmail = dataEmail;
    }
}
