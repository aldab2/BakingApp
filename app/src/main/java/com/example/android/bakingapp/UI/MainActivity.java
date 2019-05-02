package com.example.android.bakingapp.UI;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;

import com.example.android.bakingapp.POJOs.Data.RecipesHolder;
import com.example.android.bakingapp.POJOs.Recipe;
import com.example.android.bakingapp.R;
import com.example.android.bakingapp.Utils.RecipeJsonUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements DetailedFragment.OnFragmentClickListener,RecipesListFragment.OnRicipeClickListener {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState == null) {

            RecipesListFragment recipesListFragment = new RecipesListFragment();
            FragmentManager fm = getSupportFragmentManager();
            fm.beginTransaction().add(
                    R.id.fragment_container, recipesListFragment
            ).commit();
        }






    }

    @Override
    public void onRecipeClick(int position) {
        Log.e("XXXXXX", "onRecipeClick: We ARE IN MainActivity" );
        Recipe selectedRicipe = RecipesHolder.recipes.get(position);
        Bundle b = new Bundle();
        b.putSerializable("recipe",selectedRicipe);

        DetailedFragment detailedFragment = new DetailedFragment();
        detailedFragment.setArguments(b);
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.fragment_container,detailedFragment)
        .commit();


    }


    // Todo Change this method's name
    @Override
    public void onRecipeSelected(int position) {

    }

    @Override
    public void onBackPressed() {
        Log.e("XXXXX", "onBackPressed: "+getSupportFragmentManager().getBackStackEntryCount() );
        //TODO (9) : Use popBackStack to return to the previous fragment and if it is the first fragment call the parent class's constructor (super)
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }

    }
}
