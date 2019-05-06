package com.example.android.bakingapp.POJOs;

/**
 * Created by Kingdom on 5/6/2019.
 */

public class NextPrevButtonEvent {
    Recipe recipe;
    RecipeStep selectedStep;

    public NextPrevButtonEvent(Recipe recipe, RecipeStep selectedStep) {
        this.recipe = recipe;
        this.selectedStep = selectedStep;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public RecipeStep getSelectedStep() {
        return selectedStep;
    }

    public void setSelectedStep(RecipeStep selectedStep) {
        this.selectedStep = selectedStep;
    }
}
