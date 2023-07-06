package com.musicneo.db;

public class MusicBean {
    private int id;
    private String name;
    private String url;
    private String path;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public MusicBean(String name, String url, String path) {
        this.name = name;
        this.url = url;
        this.path = path;
    }

    public MusicBean(int id, String name, String url, String path) {
        this.id = id;
        this.name = name;
        this.url = url;
        this.path = path;
    }
}
