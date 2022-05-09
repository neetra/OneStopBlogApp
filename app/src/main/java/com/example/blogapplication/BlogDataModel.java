package com.example.blogapplication;

public class BlogDataModel {
    public String blog_title;
    public String blog_description;
    public String blog_image_url;
    public boolean blog_is_saved;
    BlogDataModel(String blog_title,String blog_description, String blog_image_url, boolean blog_is_saved){
        this.blog_title=blog_title;
        this.blog_description=blog_description;
        this.blog_image_url=blog_image_url;
        this.blog_is_saved = blog_is_saved;
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
