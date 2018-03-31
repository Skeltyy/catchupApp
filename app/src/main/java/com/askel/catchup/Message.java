package com.askel.catchup;

public class Message {
    public String content, username;
    public Message(){

    }
    public Message(String content, String username){
        this.content=content;
        this.username=username;
    }
    public String getUsername(){
        return username;
    }
    public void setUsername(String username){
        this.username=username;
    }

    public String getContent(){

        return content;
    }
    public void setContent(String content){
        this.content=content;
    }
}

