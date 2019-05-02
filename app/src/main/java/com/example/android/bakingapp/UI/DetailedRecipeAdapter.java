package com.example.android.bakingapp.UI;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.bakingapp.POJOs.Data.RecipesHolder;
import com.example.android.bakingapp.POJOs.Ingredient;
import com.example.android.bakingapp.POJOs.Recipe;
import com.example.android.bakingapp.POJOs.RecipeStep;
import com.example.android.bakingapp.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Kingdom on 5/1/2019.
 */

public class DetailedRecipeAdapter extends RecyclerView.Adapter<DetailedRecipeAdapter.RecipeViewHolder> {

    private Context context;
    private Recipe recipe;

    public DetailedRecipeAdapter(Recipe recipe, Context context) {
        this.recipe = recipe;
        this.context = context;
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recipe_item, viewGroup, false);
        RecipeViewHolder recipeViewHolder = new RecipeViewHolder(view);
        return recipeViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int i) {
        i = holder.getAdapterPosition();
        if (recipe == null) {
           // Log.e("XXXX", "onBindViewHolder NULL RECIPE ??!!?!?!?!?");
        } else {
            if (i == 0) {
                holder.mTvRecipeItem.setText("Ingredients");
            } else {
                holder.mTvRecipeItem.setText("Step#" + i);
            }
        }
        //Log.e("XXXX", "position =  " + i);
    }

    @Override
    public int getItemCount() {
        if (recipe == null) {

            return 0;
        }
        int size = recipe.getSteps().size() + 1;

        return size;
    }

    class RecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, DetailedFragment.OnFragmentClickListener {
        @BindView(R.id.tv_recipe_item)
        TextView mTvRecipeItem;
        int clickedView = -8;


        public RecipeViewHolder(@NonNull View itemView) {

            super(itemView);

            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);


        }

        Toast mToast;

        @Override
        public void onClick(View view) {
            if (mToast != null)
                mToast.cancel();
            int adapterPosition = getAdapterPosition();
            mToast = Toast.makeText(view.getContext(), " positon" + adapterPosition + " Was clicked", Toast.LENGTH_SHORT);
            mToast.show();
            if (adapterPosition == 0) {
                // Todo Handle and open the ingredients Fragment
                onRecipeIngredientSelected(adapterPosition);
            } else {
                // Todo Handle and open the steps Fragment
                onRecipeStepSelected(adapterPosition);

            }
        }

        @Override
        public void onRecipeIngredientSelected(int position) {
            if (RecipesHolder.recipes.size() != 0) {
                Log.e("XXXXXX", "onRecipeIngredientSelected: Position is "+position);

                ArrayList<Ingredient> selectedIngredient = recipe.getIngredients();//RecipesHolder.recipes.get(position).getIngredients();
                Bundle b = new Bundle();
                b.putSerializable("ingredients", selectedIngredient);
                b.putString("action", "ingredients");

                IngredientStepFragment fragment = new IngredientStepFragment();
                fragment.setArguments(b);
                FragmentManager fm = ((AppCompatActivity) context).getSupportFragmentManager();
                fm.beginTransaction().replace(R.id.fragment_container, fragment)
                        .addToBackStack(null)
                        .commit();
            }
        }

        @Override
        public void onRecipeStepSelected(int position) {
            if (RecipesHolder.recipes.size() != 0) {
               // Log.e("XXXXXX", "onRecipeClick: We ARE Here in the Adapter Holder Not Zero");
                Log.e("XXXXXX", "onRecipeStepSelected: Position is "+position);

                RecipeStep selectedStep = recipe.getSteps().get(position-1);//RecipesHolder.recipes.get(position).getSteps();
                Bundle b = new Bundle();
                b.putSerializable("step", selectedStep);
                b.putString("action", "steps");

                IngredientStepFragment fragment = new IngredientStepFragment();
                fragment.setArguments(b);
                FragmentManager fm = ((AppCompatActivity) context).getSupportFragmentManager();
                fm.beginTransaction().replace(R.id.fragment_container, fragment)
                        .addToBackStack(null)
                        .commit();
            }
        }


    }
}
