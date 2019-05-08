package com.example.android.bakingapp;

import android.os.Bundle;

import android.support.v4.app.FragmentManager;

import com.example.android.bakingapp.POJOs.Recipe;
import com.example.android.bakingapp.UI.DetailedFragment;
import com.example.android.bakingapp.UI.MainActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

/**
 * Created by Kingdom on 5/9/2019.
 */

@RunWith(AndroidJUnit4.class)
public class SomeTest {
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


        Thread.sleep(10000);
        Espresso.onView(ViewMatchers.withId(R.id.rv_list_ing_steps))
                .perform( RecyclerViewActions.actionOnItemAtPosition(1, ViewActions.click()));
    }
}
