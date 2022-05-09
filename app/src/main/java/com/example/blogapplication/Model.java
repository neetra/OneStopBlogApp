package com.example.blogapplication;

public class Model {
    public String blogId;
    public String blogTitle;
    public String blogDescrition;
    public String blogImageLink;
    public String blogThumbNailLink;

    public Model(String blogId, String blogTitle, String blogDescrition, String blogImageLink, String blogThumbNailLink) {
        this.blogId = blogId;
        this.blogTitle = blogTitle;
        this.blogDescrition = blogDescrition;
        this.blogImageLink = blogImageLink;
        this.blogThumbNailLink = blogThumbNailLink;
    }

}
