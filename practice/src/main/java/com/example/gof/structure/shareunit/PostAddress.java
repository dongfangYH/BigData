package com.example.gof.structure.shareunit;

public class PostAddress {
    private String ip;
    private String country;
    public PostAddress(String ip, String country) {
        this.ip = ip;
        this.country = country;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
