package com.example.android.bakingapp;



import android.support.test.espresso.Espresso;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v4.app.FragmentManager;

import com.example.android.bakingapp.POJOs.Recipe;
import com.example.android.bakingapp.UI.MainActivity;
import com.example.android.bakingapp.UI.RecipesListFragment;

import org.hamcrest.CoreMatchers;
import org.hamcrest.Matchers;
import org.hamcrest.core.AllOf;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.EnumSet;



/**
 * Created by Kingdom on 5/6/2019.
 */

@RunWith(AndroidJUnit4.class)
public class StartingAppTest {
    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void  setupRecipeListFragment(){

        RecipesListFragment recipesListFragment = new RecipesListFragment();
        FragmentManager fm = mActivityTestRule.getActivity().getSupportFragmentManager();
        fm.beginTransaction().add(
                R.id.fragment_container, recipesListFragment
        ).commit();




    }


    @Test
    public void startingApp_OpensRecipeRecyclerView() {

       Espresso.onView(AllOf.allOf(ViewMatchers.withId(R.id.rv_list_recipe),ViewMatchers.hasFocus())).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));



    }


}
