package com.alexgl00.proyectodb.character;

import java.util.List;

public class Character {

    private String id;
    private String name;
    private String image;
    private List<Transformation> transformations;

    public Character(){

    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<Transformation> getTransformations() {
        return transformations;
    }

    public void setTransfomations(List<Transformation> transfomations) {
        this.transformations = transfomations;
    }
}
