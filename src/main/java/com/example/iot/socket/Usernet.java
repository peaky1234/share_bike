package com.example.iot.socket;

import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;

public class Usernet {
    private int id;//暂时没有用到
    private String password; //暂时没有用到
    private String message;
    private Socket socket;
    public void send(String message, HashMap<Integer,Usernet> map) throws IOException {
        try {
            socket.getOutputStream().write(message.getBytes());
        } catch (IOException ignored) {

        }

    }//发送功能
    public Usernet() {
        super();}

    public String toString() {
        return "Usernet [id=" + id + ", password=" + password + ", message=" + message + ", socket=" + socket + "]";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }


    public Usernet(int id, Socket socket) {
        this.id = id;
        this.socket = socket;
    }

}

