package de.tallaron.tcp.entity;

import java.io.Serializable;

public class Status implements Serializable {

    private String status;

    public Status(String s) {
        status = s;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Status))
            return false;
        return status.equals(obj.toString());
    }

    @Override
    public int hashCode() {
        return super.hashCode(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String toString() {
        return status;
    }

}
