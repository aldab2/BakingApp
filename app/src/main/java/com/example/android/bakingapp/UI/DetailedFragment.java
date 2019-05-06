package com.example.android.bakingapp.UI;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.bakingapp.POJOs.Data.PublicDataHolder;
import com.example.android.bakingapp.POJOs.Recipe;
import com.example.android.bakingapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailedFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_RECIPE = "recipe";
   // private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private Recipe recipe;
    //private String mParam2;



    public DetailedFragment() {
        // Required empty public constructor
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            recipe = (Recipe) getArguments().getSerializable(ARG_RECIPE);


        }
    }
    @BindView(R.id.rv_list_ing_steps)
    RecyclerView mRecyclerView;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detailed, container, false);
        ButterKnife.bind(this,view);
      if(recipe!=null){

          DetailedRecipeAdapter detailedRecipeAdapter = new DetailedRecipeAdapter(recipe,getContext());
          mRecyclerView.setAdapter(detailedRecipeAdapter);
          mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

      }
        return view;
    }








}
