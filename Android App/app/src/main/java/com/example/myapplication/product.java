package com.example.myapplication;

public class product {
    public String name; //Name
    public String loc; //Name
    public String cuisine; //Name
    public String star; //Name

    public product(String name,String loc, String cuisine, String star)
    {
        this.name = name;
        this.loc = loc;
        this.cuisine = cuisine;
        this.star = star;

    }

    public String getName() {
        return name;
    }
public String getloc() {
        return loc;
    }
public String getCuisine() {
        return cuisine;
    }
public String getStar() {
        return star;
    }

}
