package com.alexgl00.proyectodb;

public class FavoriteCharacter {
    private String name;
    private String id;
    private int imageResId;

    public FavoriteCharacter(String name, String id, int imageResId) {
        this.name = name;
        this.id = id;
        this.imageResId = imageResId;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public int getImageResId() {
        return imageResId;
    }
}
