package com.example.chatgpt;

import com.google.gson.annotations.SerializedName;

public class Message {

    public static String SENT_BY_ME="me";
    public  static String SENT_BY_BOT="bot";


    String  message;
    String  sendbyme;

    public Message(String message, String sendbyme) {
        this.message = message;
        this.sendbyme = sendbyme;
    }

    public Message() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSendbyme() {
        return sendbyme;
    }

    public void setSendbyme(String sendbyme) {
        this.sendbyme = sendbyme;
    }
}
