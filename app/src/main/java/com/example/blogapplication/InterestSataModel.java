package com.example.blogapplication;

public class InterestSataModel {
    public Integer tag_id;
    public String tag_name;
    InterestSataModel(Integer tag_id,String tag_name){
        this.tag_id=tag_id;
        this.tag_name=tag_name;
    }
    public String getTagName(){
        return tag_name;
    }
    public Integer getTag_id(){
        return tag_id;
    }
}
