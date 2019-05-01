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

public class MainActivity extends AppCompatActivity implements DetailedFragment.OnFragmentClickListener {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecipesListFragment recipesListFragment = new RecipesListFragment();
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().add(
                R.id.fragment_container,recipesListFragment
        ).commit();






    }


    @Override
    public void onRecipeSelected(int position) {

    }
}
