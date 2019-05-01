package com.example.android.bakingapp.UI;

import android.content.Context;
import android.os.AsyncTask;
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
    @BindView(R.id.rv_list_recipe)
    RecyclerView mRecylerView;

    @BindView(R.id.pb_loading)
    ProgressBar mProressBar;

    ArrayList<Recipe> dummyRecipes= new ArrayList<>();
    RecipeAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mAdapter = new RecipeAdapter(dummyRecipes,this);
        mAdapter.recipes = dummyRecipes;
        mAdapter.notifyDataSetChanged();
        ArrayAdapter<Recipe> arrayAdapter = new ArrayAdapter<Recipe>(this,R.layout.recipe_card,dummyRecipes);
        mRecylerView.setAdapter(mAdapter);
        int cols = calculateNoOfColumns(this);
        mRecylerView.setLayoutManager(new GridLayoutManager(this,cols));
        URL url=null;
        try {
             url = new URL("https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json");
        }
        catch (MalformedURLException mue){
            mue.printStackTrace();
        }

        new AsyncTask<URL,Void,ArrayList<Recipe>>(){

            protected void onPreExecute() {
                mProressBar.setVisibility(ProgressBar.VISIBLE);

                super.onPreExecute();
            }

            @Override
            protected ArrayList<Recipe> doInBackground(URL... urls) {
                URL url = urls[0];
                ArrayList<Recipe> recipes =null;
                try {
                    String jsonResult = getResponseFromHttpUrl(url);
                    recipes  = RecipeJsonUtils.reviewJsonParse(jsonResult);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return recipes;
            }

            @Override
            protected void onPostExecute(ArrayList<Recipe> recipes) {
                super.onPostExecute(recipes);
                mProressBar.setVisibility(View.INVISIBLE);
                mAdapter = new RecipeAdapter(recipes,getApplicationContext());
                mRecylerView.setAdapter(mAdapter);

            }
        }.execute(url);
        RecipeJsonUtils.reviewJsonParse("");


    }
    public static int calculateNoOfColumns(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        int noOfColumns = 0;
        Log.e("XXXX", "dpWidth = : "+dpWidth );
        if(dpWidth <=599){
             noOfColumns=1;
        }else {
        int scalingFactor = 200;
         noOfColumns = (int) (dpWidth / scalingFactor);
        if(noOfColumns < 2)
            noOfColumns = 2;
        }
        return noOfColumns;
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        Log.d("XXXXXXXX", "getResponseFromHttpUrl: "+url );
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.connect();

        try {

            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");


            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                Log.d("NetworkUtils", "getResponseFromHttpUrl:  HAS Input");
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }

    @Override
    public void onRecipeSelected(int position) {

    }
}
