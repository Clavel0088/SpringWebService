/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

/**
 *
 * @author Phael
 */
public class NullResult {
    private int httpCode;
    private String message;
    private Object data;
    private boolean error;

    public NullResult(int httpCode, String message, Object data, boolean error) {
        this.httpCode = httpCode;
        this.message = message;
        this.data = data;
        this.error = error;
    }

    public NullResult(int httpCode, String message, Utilisateur data) {
        this.httpCode = httpCode;
        this.message = message;
        this.data = data;
    }
    

    public NullResult(int httpCode, String message) {
        this.httpCode = httpCode;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getHttpCode() {
        return httpCode;
    }

    public void setHttpCode(int httpCode) {
        this.httpCode = httpCode;
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
