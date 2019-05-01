package com.example.android.bakingapp.POJOs;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Kingdom on 5/1/2019.
 */

public class Recipe implements Serializable {
    private int id;
    private String name;
    private ArrayList<Ingredient> ingredients;
    private ArrayList<RecipeStep> steps;
    private int servings;
    private String imagePath;

    public Recipe() {
    }

    public Recipe(int id, String name, int servings) {
        this.id = id;
        this.name = name;
        this.servings = servings;
    }

    public Recipe(int id, String name, ArrayList<Ingredient> ingredients, ArrayList<RecipeStep> steps, int servings) {
        this.id = id;
        this.name = name;
        this.ingredients = ingredients;
        this.steps = steps;
        this.servings = servings;
    }

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

    public ArrayList<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(ArrayList<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public ArrayList<RecipeStep> getSteps() {
        return steps;
    }

    public void setSteps(ArrayList<RecipeStep> steps) {
        this.steps = steps;
    }

    public int getServings() {
        return servings;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
