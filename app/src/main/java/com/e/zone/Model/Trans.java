package com.e.zone.Model;

public class Trans {
    String message;
    String date;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPlusminus() {
        return plusminus;
    }

    public void setPlusminus(String plusminus) {
        this.plusminus = plusminus;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    String plusminus;
    String amount;
}
