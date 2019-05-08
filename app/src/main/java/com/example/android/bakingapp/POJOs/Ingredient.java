package com.example.android.bakingapp.POJOs;

import java.io.Serializable;

/**
 * Created by Kingdom on 5/1/2019.
 */

public class Ingredient implements Serializable {
    private double quantity;
    private String measure;
    private String ingredient;

    public Ingredient(double quantity, String measure, String ingredient) {
        this.quantity = quantity;
        this.measure = measure;
        this.ingredient = ingredient;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    @Override
    public String toString() {
        return "Ingredient:" +ingredient+"\n"
                +"Quantity: "+quantity+"\n"
                +"Measure: "+measure+"\n";
    }
}
