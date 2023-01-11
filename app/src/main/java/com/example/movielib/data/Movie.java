package com.example.movielib.data;

public class Movie {
    private int id;
    private String name;
    private String image;
//    private boolean visited;
//    private String description;

    public Movie(MovieJson movieJson){
        this.id = movieJson.id;
        this.name = movieJson.title;
        this.image = movieJson.img;
        //        this.description = description;
        //        this.visited = visited;
    }




    public String getName() {
        return name;
    }

    public int getId(){
        return id;
    }

    public String getImage() {
        return image;
    }

//    public void setVisited(Boolean visited){
//        this.visited = visited;
//    }

//    public String getDescription() {
//        return description;
//    }



//    public boolean getVisited(){
//        return visited;
//    }
}
