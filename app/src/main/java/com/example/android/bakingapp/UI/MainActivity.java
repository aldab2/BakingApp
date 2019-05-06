package com.example.android.bakingapp.UI;

import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import com.example.android.bakingapp.POJOs.IngredientClickedMesssageEvent;
import com.example.android.bakingapp.POJOs.Data.PublicDataHolder;
import com.example.android.bakingapp.POJOs.Ingredient;
import com.example.android.bakingapp.POJOs.NextPrevButtonEvent;
import com.example.android.bakingapp.POJOs.Recipe;
import com.example.android.bakingapp.POJOs.RecipeClickedMessageEvent;
import com.example.android.bakingapp.POJOs.RecipeStep;
import com.example.android.bakingapp.POJOs.StepClickedEvent;
import com.example.android.bakingapp.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity  {
    String TAG = MainActivity.class.getSimpleName();
    int secondFrameVisibility;

MediaSessionCompat mMediaSession;
    PlaybackStateCompat.Builder mStateBuilder;
    @BindView(R.id.fragment2_container)
    @Nullable
    FrameLayout secondFrameLayout;
    private  boolean isTablet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        isTablet = findViewById(R.id.ll_tablet) !=null;
        EventBus.getDefault().register(this);

        if (savedInstanceState!= null) {
            isTablet = savedInstanceState.getBoolean("istablet");

            if (secondFrameLayout!=null)
            secondFrameVisibility = savedInstanceState.getInt("visibility");
            if (secondFrameLayout != null)
            secondFrameLayout.setVisibility(secondFrameVisibility);
            Log.e(TAG, "onCreate: isTablet: "+isTablet );

        }
        if (isTablet) {
            ButterKnife.bind(this);
            secondFrameLayout.setVisibility(View.GONE);

        }







        if(savedInstanceState == null) {
            Log.e(TAG, "onCreate: Creating RecipeListFragment ");
                RecipesListFragment recipesListFragment = new RecipesListFragment();
                FragmentManager fm = getSupportFragmentManager();
                fm.beginTransaction().add(
                        R.id.fragment_container, recipesListFragment
                ).commit();

        }






    }

    @Subscribe
    public void onEvent(RecipeClickedMessageEvent event){
        Log.e(TAG, "onEvent: Ooooh Common! " );
        if(event.getRecipe()!=null) {
            if (!isTablet) {

                Recipe selectedRicipe = event.getRecipe();
                Bundle b = new Bundle();
                b.putSerializable("recipe", selectedRicipe);

                DetailedFragment detailedFragment = new DetailedFragment();
                detailedFragment.setArguments(b);
                FragmentManager fm = getSupportFragmentManager();
                fm.beginTransaction().replace(R.id.fragment_container, detailedFragment)
                        .addToBackStack(null)
                        .commit();
            }
            else{



                Recipe selectedRicipe = event.getRecipe();
                Log.e(TAG, "onEvent: Recipe 1 Name is "+selectedRicipe.getName() );
                Bundle b = new Bundle();
                b.putSerializable("recipe", selectedRicipe);

                DetailedFragment detailedFragment = new DetailedFragment();
                detailedFragment.setArguments(b);
                FragmentManager fm = getSupportFragmentManager();
                fm.beginTransaction().replace(R.id.fragment_container, detailedFragment)
                        .addToBackStack(null)
                        .commit();


                secondFrameLayout.setVisibility(View.VISIBLE);
                Log.e(TAG, "onEvent: Recipe 2 Name is "+selectedRicipe.getName() );

                ArrayList<Ingredient> selectedIngredient = selectedRicipe.getIngredients();//recipe.getIngredients();//PublicDataHolder.recipes.get(position).getIngredients();
                Bundle b2 = new Bundle();
                b2.putSerializable("ingredients", selectedIngredient);
                b2.putString("action", "ingredients");

                IngredientStepFragment fragment = new IngredientStepFragment();
                fragment.setArguments(b2);
                FragmentManager fm2 = getSupportFragmentManager();

                    fm2.beginTransaction().replace(R.id.fragment2_container, fragment)
                            /*.addToBackStack(null)*/
                            .commit();



            }
        }



    }
    @Subscribe
    public void onEvent(IngredientClickedMesssageEvent event){


        if (event.getIngredients()!=null ) {


            ArrayList<Ingredient> selectedIngredient = event.getIngredients();//recipe.getIngredients();//PublicDataHolder.recipes.get(position).getIngredients();
            Bundle b = new Bundle();
            b.putSerializable("ingredients", selectedIngredient);
            b.putString("action", "ingredients");

            IngredientStepFragment fragment = new IngredientStepFragment();
            fragment.setArguments(b);
            FragmentManager fm = getSupportFragmentManager();
            if (!isTablet)
            fm.beginTransaction().replace(R.id.fragment_container, fragment)
                    .addToBackStack(null)
                    .commit();
            else
                fm.beginTransaction().replace(R.id.fragment2_container, fragment)
                        /*.addToBackStack(null)*/
                        .commit();
        }


    }
    @Subscribe
    public void onEvent(StepClickedEvent event){
        Log.e(TAG, "onEvent: isTablet: "+isTablet );
        int position = event.getPosition();
        if (event.getSteps()!=null && event.getSteps().size()>=0) {

            // Log.d("XXXXXX", "onRecipeClick: We ARE Here in the Adapter Holder Not Zero");
            Log.d("XXXXXX", "onRecipeStepSelected: Position is "+position);

            RecipeStep selectedStep = event.getSteps().get(position-1);//PublicDataHolder.recipes.get(position).getSteps();
            Bundle b = new Bundle();

            b.putString("url",selectedStep.getVidURL());
            b.putString("short_description",selectedStep.getShortDescription());
            b.putString("description",selectedStep.getDescription());
            b.putInt("stepID",selectedStep.getId());
            b.putSerializable("recipe",event.getRecipe());
               /* b.putSerializable("step", selectedStep);
                b.putString("action", "steps");*/

            VideoFragment fragment = new VideoFragment();
            fragment.setArguments(b);
            FragmentManager fm = getSupportFragmentManager();
            Log.e(TAG, "onEvent: is tablet: "+isTablet );
            if(!isTablet)
            fm.beginTransaction().replace(R.id.fragment_container, fragment)
                    .addToBackStack(null)
                    .commit();
            else
                fm.beginTransaction().replace(R.id.fragment2_container, fragment)
                        /*.addToBackStack(null)*/
                        .commit();


        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        Log.e(TAG, "onSaveInstanceState: isTablet: "+isTablet );
        outState.putBoolean("istablet",isTablet);
        if (secondFrameLayout != null)
        outState.putInt("visibility",secondFrameLayout.getVisibility());
        super.onSaveInstanceState(outState);
    }


    @Subscribe
    public void onEvent(NextPrevButtonEvent event) {
        Bundle b = new Bundle();
        RecipeStep selectedStep = event.getSelectedStep();
        Recipe recipe = event.getRecipe();
        b.putString("url",selectedStep.getVidURL());
        b.putString("short_description",selectedStep.getShortDescription());
        b.putString("description",selectedStep.getDescription());
        b.putInt("stepID",selectedStep.getId());
        b.putSerializable("recipe",recipe);
               /* b.putSerializable("step", selectedStep);
                b.putString("action", "steps");*/

        VideoFragment fragment = new VideoFragment();
        fragment.setArguments(b);
        FragmentManager fm = getSupportFragmentManager();
        if (!isTablet)
        fm.beginTransaction().replace(R.id.fragment_container, fragment)
                .commit();

        else  fm.beginTransaction().replace(R.id.fragment2_container, fragment)
                .commit();
    }

    @Override
    public void onBackPressed() {
      //  Log.d("XXXXX", "onBackPressed: "+getSupportFragmentManager().getBackStackEntryCount() );
        //TODO (9) : Use popBackStack to return to the previous fragment and if it is the first fragment call the parent class's constructor (super)
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
            if (isTablet){
               /* if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                    getSupportFragmentManager().popBackStack();
                }*/
               secondFrameLayout.setVisibility(View.GONE);
            }

        } else {
            super.onBackPressed();
        }

    }


    @Override
    protected void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
