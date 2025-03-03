package com.tco.usermanagement;

import java.time.*; 

public class UserCredential {
    private int id;
    private String nickname;
    private String emailAddress;
    private String password;
    private LocalDate accountCreationDate;

    public UserCredential(int id, String nickname, String emailAddress, String password, LocalDate accountCreationDate) 
    {
        this.id = id;
        this.nickname = nickname;
        this.emailAddress = emailAddress;
        this.password = password;
        this.accountCreationDate = accountCreationDate;
    }
    public UserCredential(String nickname, String emailAddress, String password ) 
    {
        this(-1, nickname, emailAddress, password, LocalDate.now());
    }

    public boolean verifyCredential (String userEmailAddress, String userPassword){
        return (userEmailAddress == emailAddress && userPassword == password);
    }
    
    public int getId(){
        return this.id;
    }
    
    public void setId(int id){
        if(this.id == -1) {
            this.id = id;
        }
    }
    
    public String getNickname(){
        return this.nickname;
    }
    
    public void setNickname(String nickname){
        this.nickname = nickname;
    }
    
    public String getEmailAddress(){
        return this.emailAddress;
    }
    
    public void setEmailAddress(String emailAddress){
        this.emailAddress = emailAddress;
    }
    
    public void setPassword(String password){
        this.password = password;
    }
    
    public LocalDate getAccountCreationDate(){
        return this.accountCreationDate;
    }
}
