package com.example.android.bakingapp.UI;

import android.content.Context;
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

import com.example.android.bakingapp.POJOs.Data.PublicDataHolder;
import com.example.android.bakingapp.POJOs.Ingredient;
import com.example.android.bakingapp.POJOs.IngredientClickedMesssageEvent;
import com.example.android.bakingapp.POJOs.Recipe;
import com.example.android.bakingapp.POJOs.RecipeStep;
import com.example.android.bakingapp.POJOs.StepClickedEvent;
import com.example.android.bakingapp.R;

import org.greenrobot.eventbus.EventBus;

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
           // Log.d("XXXX", "onBindViewHolder NULL RECIPE ??!!?!?!?!?");
        } else {
            if (i == 0) {
                holder.mTvRecipeItem.setText("Ingredients");
            } else {
                holder.mTvRecipeItem.setText("Step#" + i);
            }
        }
        //Log.d("XXXX", "position =  " + i);
    }

    @Override
    public int getItemCount() {
        if (recipe == null) {

            return 0;
        }
        int size = recipe.getSteps().size() + 1;

        return size;
    }

    class RecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
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
                EventBus.getDefault().post(new IngredientClickedMesssageEvent(recipe.getIngredients()));

            } else {
                // Todo Handle and open the steps Fragment
                EventBus.getDefault().post(new StepClickedEvent(recipe.getSteps(),recipe,getAdapterPosition()));

            }
        }








    }
}
