package com.example.android.bakingapp.POJOs;

/**
 * Created by Kingdom on 5/6/2019.
 */

public class RecipeClickedMessageEvent {

    Recipe recipe;

    int message_poistion;

    public RecipeClickedMessageEvent(Recipe recipe, int message_poistion) {
        this.recipe = recipe;

        this.message_poistion = message_poistion;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }



    public int getMessage_poistion() {
        return message_poistion;
    }

    public void setMessage_poistion(int message_poistion) {
        this.message_poistion = message_poistion;
    }
}
