package com.example.gof.structure.shareunit;

public class Post {
    private String id;
    private String title;
    private String content;
    private PostAddress postAddress;

    public Post(String id, String title, String content, String ip, String country) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.postAddress = PostAddressFactory.getPostAddress(ip, country);
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getIp(){
        if (null != postAddress){
            return postAddress.getIp();
        }
        return null;
    }

    public String getCountry(){
        if (null != postAddress){
            return postAddress.getCountry();
        }
        return null;
    }
}
