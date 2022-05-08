package com.example.blogapplication;

public class BlogDataModel {
    public String blog_title;
    public String blog_description;
    BlogDataModel(String blog_title,String blog_description){
        this.blog_title=blog_title;
        this.blog_description=blog_description;
    }
    public String getBlogTitle(){
        return blog_title;
    }
    public String getBlogDescription(){
        return blog_description;
    }
}
