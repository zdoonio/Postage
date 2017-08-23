package com.application.robat.postage;

/**
 * Created by domin on 20.08.2017.
 */
public class Row {
    public String name;
    public String title;
    public String description;
    public String timestamp;
    public String imageUrl;
    public String RowID;

    public Row(String name, String title, String description, String timestamp, String imageUrl, String RowID){
        this.name = name;
        this.title = title;
        this.description = description;
        this.timestamp = timestamp;
        this.imageUrl = imageUrl;
        this.RowID = RowID;
    }
}
