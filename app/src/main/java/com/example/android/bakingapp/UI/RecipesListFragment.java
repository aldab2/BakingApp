package com.example.android.bakingapp.UI;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.android.bakingapp.POJOs.Data.PublicDataHolder;
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

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnRicipeClickListener} interface
 * to handle interaction events.
 * Use the {@link RecipesListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecipesListFragment extends Fragment {
    String TAG = RecipesListFragment.class.getSimpleName();
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_LIST_RECIPES = "recipes";
    //private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
   //private ArrayList<Recipe> recipes;
    @BindView(R.id.rv_list_recipe)
    RecyclerView mRecylerView;

    @BindView(R.id.pb_loading)
    ProgressBar mProressBar;


    RecipeAdapter mAdapter;




    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RecipesListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RecipesListFragment newInstance(String param1, String param2) {
        RecipesListFragment fragment = new RecipesListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_LIST_RECIPES, param1);
        //args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            PublicDataHolder.recipes = (ArrayList<Recipe>) getArguments().getSerializable(ARG_LIST_RECIPES);
            //mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_recipes_list, container, false);
        ButterKnife.bind(this,view);

        mRecylerView.setAdapter(mAdapter);
        int cols = calculateNoOfColumns(getContext());
        mRecylerView.setLayoutManager(new GridLayoutManager(getContext(),cols));





        URL url=null;
        try {
            url = new URL("https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json");
        }
        catch (MalformedURLException mue){
            mue.printStackTrace();
        }
        new LoadListFragment().execute(url);



        return view;
    }

      class LoadListFragment extends AsyncTask<URL,Void,ArrayList<Recipe>>{

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
            mAdapter = new RecipeAdapter(recipes,getContext());
            mAdapter.notifyDataSetChanged();
            mRecylerView.setAdapter(mAdapter);



        }
    }







    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */




    public static int calculateNoOfColumns(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        int noOfColumns = 0;
        Log.d("XXXX", "dpWidth = : "+dpWidth );
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
}
