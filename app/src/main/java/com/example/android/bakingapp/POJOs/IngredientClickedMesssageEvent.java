package com.example.android.bakingapp.POJOs;

import com.example.android.bakingapp.POJOs.Ingredient;
import com.example.android.bakingapp.POJOs.Recipe;

import java.util.ArrayList;

/**
 * Created by Kingdom on 5/6/2019.
 */

public class IngredientClickedMesssageEvent {
    ArrayList<Ingredient> ingredients;

    public IngredientClickedMesssageEvent(ArrayList<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public ArrayList<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(ArrayList<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }
}
