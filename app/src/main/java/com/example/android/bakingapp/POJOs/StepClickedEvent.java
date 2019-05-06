package com.example.android.bakingapp.POJOs;

import java.util.ArrayList;

/**
 * Created by Kingdom on 5/6/2019.
 */

public class StepClickedEvent {

    ArrayList<RecipeStep> steps;
    Recipe recipe;
    int position;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public StepClickedEvent(ArrayList<RecipeStep> steps,Recipe recipe, int position) {
        this.steps = steps;
        this.position = position;
        this.recipe = recipe;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public ArrayList<RecipeStep> getSteps() {
        return steps;
    }

    public void setSteps(ArrayList<RecipeStep> steps) {
        this.steps = steps;
    }
}
