package com.example.blogapplication;

public class BlogDataModel {
    public String id;
    public String blog_title;
    public String blog_description;
    public String blog_image_url;
    public String blog_thumbnail;
    public boolean blog_is_saved;
    BlogDataModel(String id, String blog_title,String blog_description, String blog_image_url, String blog_thumbnail, boolean blog_is_saved){
        this.id = id;
        this.blog_title=blog_title;
        this.blog_description=blog_description;
        this.blog_image_url=blog_image_url;
        this.blog_is_saved = blog_is_saved;
        this.blog_thumbnail = blog_thumbnail;
    }
    public String getBlogTitle(){
        return blog_title;
    }
    public String getBlogDescription(){
        return blog_description;
    }
    public String[] getBlogImageUrl() {
        String[] urls = { blog_image_url };
        return urls;
    }
    public boolean getBlogIsSaved(){
        return blog_is_saved;
    }

    public void setBlogIsSaved(boolean isBlogSaved){
        this.blog_is_saved = isBlogSaved;
    }
}
