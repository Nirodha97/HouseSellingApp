package com.nirodha.houseapp;

public class Item extends Model {
    private int idImage;
    private String name, description;

    public Item(int idImage, String name, String description) {
        this.idImage = idImage;
        this.name = name;
        this.description = description;
    }

    public int getIdImage() {
        return idImage;
    }

    public void setIdImage(int idImage) {
        this.idImage = idImage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
