package com.example.android.bakingapp;

import android.os.Bundle;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v4.app.FragmentManager;

import com.example.android.bakingapp.POJOs.Recipe;
import com.example.android.bakingapp.UI.DetailedFragment;
import com.example.android.bakingapp.UI.MainActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by Kingdom on 5/9/2019.
 */

@RunWith(AndroidJUnit4.class)
public class ClickingStepTest {
    @Rule
    public ActivityTestRule<MainActivity> mActivitiyTestRule = new ActivityTestRule<MainActivity>(MainActivity.class);


    Recipe recipe = new Recipe(1,"Cookie",0);

    @Before
    public void setupFragment(){

        Recipe selectedRicipe = recipe;
        Bundle b = new Bundle();
        b.putSerializable("recipe", selectedRicipe);

        DetailedFragment detailedFragment = new DetailedFragment();
        detailedFragment.setArguments(b);
        FragmentManager fm = mActivitiyTestRule.getActivity().getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.fragment_container, detailedFragment)
                .addToBackStack(null)
                .commit();

    }

    @Test
    public void clickingIngredients_OpensTheCorrectFragment() throws InterruptedException {
        Espresso.onView(ViewMatchers.withId(R.id.rv_list_ing_steps)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));


        Espresso.onView(ViewMatchers.withId(R.id.rv_list_ing_steps))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, ViewActions.click()));


        Espresso.onView(ViewMatchers.withId(R.id.tv_step_short_desc))
                .check(ViewAssertions.matches(ViewMatchers.withText(recipe.getSteps().get(0).getShortDescription())));

        Espresso.onView(ViewMatchers.withId(R.id.tv_step_desc))
                .check(ViewAssertions.matches(ViewMatchers.withText(recipe.getSteps().get(0).getDescription())));


    }
}
