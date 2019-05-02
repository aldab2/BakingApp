package com.example.android.bakingapp.UI;

import android.content.Context;
import android.media.session.MediaSession;
import android.os.AsyncTask;
import android.support.v4.app.FragmentManager;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
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
import com.example.android.bakingapp.Utils.MySessionCallback;
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

MediaSessionCompat mMediaSession;
    PlaybackStateCompat.Builder mStateBuilder;
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
       // Log.e("XXXXXX", "onRecipeClick: We ARE IN MainActivity" );
        Recipe selectedRicipe = RecipesHolder.recipes.get(position);
        Bundle b = new Bundle();
        b.putSerializable("recipe",selectedRicipe);

        DetailedFragment detailedFragment = new DetailedFragment();
        detailedFragment.setArguments(b);
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.fragment_container,detailedFragment)
        .commit();
        initializeMediaSession();

    }


    // Todo Change this method's name
    @Override
    public void onRecipeIngredientSelected(int position) {

    }

    @Override
    public void onRecipeStepSelected(int position) {

    }

    @Override
    public void onBackPressed() {
      //  Log.e("XXXXX", "onBackPressed: "+getSupportFragmentManager().getBackStackEntryCount() );
        //TODO (9) : Use popBackStack to return to the previous fragment and if it is the first fragment call the parent class's constructor (super)
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }

    }
    private void initializeMediaSession() {

        // Create a MediaSessionCompat.
        mMediaSession = new MediaSessionCompat(this, " ");

        // Enable callbacks from MediaButtons and TransportControls.
        mMediaSession.setFlags(
                MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS |
                        MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS);

        // Do not let MediaButtons restart the player when the app is not visible.
        mMediaSession.setMediaButtonReceiver(null);

        // Set an initial PlaybackState with ACTION_PLAY, so media buttons can start the player.
        mStateBuilder = new PlaybackStateCompat.Builder()
                .setActions(
                        PlaybackStateCompat.ACTION_PLAY |
                                PlaybackStateCompat.ACTION_PAUSE |
                                PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS |
                                PlaybackStateCompat.ACTION_PLAY_PAUSE);

        mMediaSession.setPlaybackState(mStateBuilder.build());


        // MySessionCallback has methods that handle callbacks from a media controller.
        mMediaSession.setCallback(new MySessionCallback());

        // Start the Media Session since the activity is active.
        mMediaSession.setActive(true);

    }
}
