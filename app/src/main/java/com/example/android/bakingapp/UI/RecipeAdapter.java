package com.example.android.bakingapp.UI;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.bakingapp.POJOs.Recipe;
import com.example.android.bakingapp.R;

import java.util.ArrayList;

/**
 * Created by abdullah on 18/03/19.
 */

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {



    int viewHolderCount= 10;
     Recipe recipeItem;
     ArrayList<Recipe> recipes= new ArrayList<>();
     private  Context context;

     // TODO make a constructer that takes a movie ArrayList
    public RecipeAdapter(){

    }
    public RecipeAdapter(ArrayList<Recipe> recipes, Context context){
        Log.e("XXXXX", "Initialzing Adapter with size = "+recipes.size() );
    this.recipes =recipes;
    this.context = context;
    viewHolderCount = recipes.size();
    }



    @Override
    public RecipeViewHolder onCreateViewHolder(ViewGroup viewGroup , int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.recipe_card;

        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;
        recipeItem= new Recipe();
        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        RecipeViewHolder viewHolder = new RecipeViewHolder(view);

        //viewHolder.viewHolderIndex.setText("ViewHolder index: " + viewHolderCount);



        //viewHolderCount++;

        return viewHolder;


    }

    @Override
    public void onBindViewHolder(RecipeViewHolder holder, int position) {
            holder.bind(holder);
    }

    @Override
    public int getItemCount() {
        return viewHolderCount  ;
    }



    // TODO implement View.onClickListner
    class RecipeViewHolder extends  RecyclerView.ViewHolder implements View.OnClickListener   {
            TextView mRecipeName;
            int clickedViewPosition=-99;
        public RecipeViewHolder(View view){
            super(view);

            mRecipeName = (TextView) view.findViewById(R.id.tv_recipe_name);

            // TODO Complete This Section
            // TODO (7) Call setOnClickListener on the View passed into the constructor (use 'this' as the OnClickListener)
            view.setOnClickListener(this);
        }


        public void bind(RecipeViewHolder holder){
             clickedViewPosition = holder.getAdapterPosition();

            mRecipeName = (TextView) holder.itemView.findViewById(R.id.tv_recipe_name);
            mRecipeName.setText(recipes.get(clickedViewPosition).getName());

        }
        Toast mToast;

        @Override
        public void onClick(View view) {

           if (mToast!=null)
               mToast.cancel();
            mToast =  Toast.makeText(view.getContext(),""+mRecipeName.getText().toString()+"With positon"+clickedViewPosition+" Was clicked",Toast.LENGTH_SHORT);
            mToast.show();
                Bundle b = new Bundle();

                b.putSerializable("recipe",recipes.get(clickedViewPosition));

            DetailedFragment detailedFragment = new DetailedFragment();
            detailedFragment.setArguments(b);

            FragmentManager fm = ((AppCompatActivity)context).getSupportFragmentManager();
            //fm.beginTransaction().add()
            //






        }
    }
    public int getViewHolderCount() {
        return viewHolderCount;
    }

    public void setViewHolderCount(int viewHolderCount) {
        this.viewHolderCount = viewHolderCount;
    }
}
