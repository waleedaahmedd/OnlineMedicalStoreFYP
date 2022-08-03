package com.example.onlinemedicalstore;

import java.util.List;

public class OrderModel {
    private int cartPrice;
    private String RefNumber;
    private String Status;
    private String DateAndTime;
    // private List<CartMedicineModel> Medicines;

    public String getRefNumber() {
        return RefNumber;
    }

    public void setRefNumber(String refNumber) {
        RefNumber = refNumber;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getDateAndTime() {
        return DateAndTime;
    }

    public void setDateAndTime(String dateAndTime) {
        DateAndTime = dateAndTime;
    }
    /* public List<CartMedicineModel> getMedicines() {
        return Medicines;
    }*/

  /*  public void setMedicines(List<CartMedicineModel> medicines) {
        Medicines = medicines;
    }*/

    public int getCartPrice() {
        return cartPrice;
    }

    public void setCartPrice(int cartPrice) {
        this.cartPrice = cartPrice;
    }
}
