/**
  * Copyright 2021 json.cn 
  */
package com.aye.weather.Bean;

/**
 * Auto-generated: 2021-09-14 21:11:55
 *
 * @author json.cn (i@json.cn)
 * @website http://www.json.cn/java2pojo/
 */

/*
title : "紫外线指数"
level : "弱"
desc : "辐射较弱，涂擦SPF12-15、PA+护肤品。"
 */
public class Index {

    private String title;
    private String level;
    private String desc;
    public void setTitle(String title) {
         this.title = title;
     }
     public String getTitle() {
         return title;
     }

    public void setLevel(String level) {
         this.level = level;
     }
     public String getLevel() {
         return level;
     }

    public void setDesc(String desc) {
         this.desc = desc;
     }
     public String getDesc() {
         return desc;
     }
}