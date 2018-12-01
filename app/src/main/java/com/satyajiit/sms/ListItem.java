package com.satyajiit.sms;

public class ListItem {

    private String Number;
    private String Message;
    private String Count;

    public String getNumber() {
        return Number;
    }

    public String getCount() {
        return Count;
    }

    public void setCount(String count) {
        Count = count;
    }

    public void setNumber(String number) {
        Number = number;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }
}