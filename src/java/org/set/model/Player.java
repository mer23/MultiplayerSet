/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.set.model;

import java.io.Serializable;

/**
 *
 * @author mer23
 */
public class Player implements Serializable {
    
    public enum Status {
        CONNECTED, MATCHROOM, WAITING, READY, PLAYING
    }
    
    private Status status;
    private String name= "QQQ";
    
    public Player() {
        this.status= Status.CONNECTED;
    }
    
    public Status getStatus() {
        return this.status;
    }
    
    public void setStatus(Status newStatus) {
        this.status= newStatus;
    }
    
    public void setName(String name) {
        this.name= name;
    }
    
    public String getName() {
        return this.name;
    }
}
