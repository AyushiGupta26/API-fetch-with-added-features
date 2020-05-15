package com.example.digitaltechnologiesassignment;

public class ListItem {
    private String key;
    private String name;
    private String price;
    private String weight;
    private String img;

    public ListItem(String name, String price, String weight, String img) {
        this.name = name;
        this.price = price;
        this.weight = weight;
        this.img = img;
    }

    public ListItem(){

    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public String getWeight() {
        return weight;
    }

    public String getImg() {
        return img;
    }
}
