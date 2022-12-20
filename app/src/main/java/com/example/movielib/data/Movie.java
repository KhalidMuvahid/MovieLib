package com.example.movielib.data;

public class Movie {
    private String name;
    private String description;
    private int image;
    private boolean visited;

    public Movie(String name,String description,int image,boolean visited){
        this.name = name;
        this.description = description;
        this.image = image;
        this.visited = visited;
    }




    public String getName() {
        return name;
    }

    public void setVisited(Boolean visited){
        this.visited = visited;
    }

    public String getDescription() {
        return description;
    }

    public int getImage() {
        return image;
    }

    public boolean getVisited(){
        return visited;
    }
}
