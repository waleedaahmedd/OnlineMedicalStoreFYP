package com.example.onlinemedicalstore;

import java.io.Serializable;

public class DeliveryDetailModel implements Serializable {

    private String receiverHouse, receiverLocation, receiverName, receiverPhone;

    public DeliveryDetailModel() {
    }

    public String getReceiverHouse() {
        return receiverHouse;
    }

    public void setReceiverHouse(String receiverHouse) {
        this.receiverHouse = receiverHouse;
    }

    public String getReceiverLocation() {
        return receiverLocation;
    }

    public void setReceiverLocation(String receiverLocation) {
        this.receiverLocation = receiverLocation;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getReceiverPhone() {
        return receiverPhone;
    }

    public void setReceiverPhone(String receiverPhone) {
        this.receiverPhone = receiverPhone;
    }




}
