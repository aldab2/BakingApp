package com.example.android.bakingapp.UI;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.bakingapp.POJOs.Ingredient;
import com.example.android.bakingapp.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Kingdom on 5/2/2019.
 */

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.IngredientViewHolder> {
    private Context context;
    private ArrayList<Ingredient> ingredients;


    public IngredientAdapter(Context context, ArrayList<Ingredient> ingredients) {
        this.context = context;
        this.ingredients = ingredients;
    }

    @NonNull
    @Override
    public IngredientViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.ingredient_item,viewGroup,false);
        IngredientViewHolder viewHolder = new IngredientViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientViewHolder holder, int position) {
      if(holder.mTvIngredient != null && holder.mTvMeasure!=null && holder.mTvQuantity!=null){
          holder.mTvQuantity.setText(ingredients.get(position).getQuantity()+"");
          holder.mTvMeasure.setText(ingredients.get(position).getMeasure());
          holder.mTvIngredient.setText(ingredients.get(position).getIngredient());
      }
      else
          Log.e("XXXXXX", "FATAL ERROR Some view are null " );


    }

    @Override
    public int getItemCount() {
        if (ingredients== null)
        return 0;
        else return ingredients.size();
    }

    class IngredientViewHolder extends  RecyclerView.ViewHolder{
        @BindView(R.id.tv_ingredient_quantity)
        TextView mTvQuantity;
        @BindView(R.id.tv_ingredient_measure)
        TextView mTvMeasure;
        @BindView(R.id.tv_ingredient_ingredient)
        TextView mTvIngredient;

        public IngredientViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);

        }
    }
}
