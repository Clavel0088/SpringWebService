/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

/**
 *
 * @author PHAEL
 */
public class WsResult {
    
    private int httpCode;
    private String message;
    private Object data;
    private boolean error;

    public WsResult(int httpCode, String message, Object data, boolean error) {
        this.httpCode = httpCode;
        this.message = message;
        this.data = data;
        this.error = error;
    }

    public WsResult() {
    }
    
    

    public int getHttpCode() {
        return httpCode;
    }

    public void setHttpCode(int httpCode) {
        this.httpCode = httpCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }
    
    
}
