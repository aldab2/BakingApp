package com.example.android.bakingapp.UI;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.android.bakingapp.POJOs.Ingredient;
import com.example.android.bakingapp.POJOs.Recipe;
import com.example.android.bakingapp.POJOs.RecipeStep;
import com.example.android.bakingapp.R;
import com.example.android.bakingapp.Utils.MySessionCallback;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.UriUtil;
import com.google.android.exoplayer2.util.Util;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Kingdom on 5/2/2019.
 */

public class IngredientStepsAdapter extends RecyclerView.Adapter<IngredientStepsAdapter.IngredientViewHolder> {
    private Context context;
    private ArrayList<Ingredient> ingredients;
    private ArrayList<RecipeStep> Recipesteps;
    private RecipeStep step;
    private static final String ARG_INGREDIENTS = "ingredients";
    private static final String ARG_STEPS = "steps";



    private String action;



    public IngredientStepsAdapter(Context context, ArrayList<Ingredient> ingredients, RecipeStep step,String action) {
        this.context = context;
        this.ingredients = ingredients;
        this.step= step;
        this.action = action;
    }


    @NonNull
    @Override
    public IngredientViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view=null;

             view = inflater.inflate(R.layout.ingredient_step_item, viewGroup, false);


        IngredientViewHolder viewHolder = new IngredientViewHolder(view);

        return viewHolder;
    }


    @Override
    public void onViewRecycled(@NonNull IngredientViewHolder holder) {
        super.onViewRecycled(holder);
        if(holder.mExoPlayer!=null)
        holder.releasePlayer();
    }



    @Override
    public void onViewDetachedFromWindow(@NonNull IngredientViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        if(holder.mExoPlayer!=null)
        holder.releasePlayer();
    }


    @Override
    public void onBindViewHolder(@NonNull IngredientViewHolder holder, int position) {
        Log.e("XXX", "Get REady +"+position );
      if(holder.mTvIngredient != null && holder.mTvMeasure!=null && holder.mTvQuantity!=null && action.equals("ingredients")){
          Log.e("XXX", "BOOM Inside Ingredient Block " );
          holder.mTvQuantity.setText(ingredients.get(position).getQuantity()+"");
          holder.mTvMeasure.setText(ingredients.get(position).getMeasure());
          holder.mTvIngredient.setText(ingredients.get(position).getIngredient());
          holder.mLinearLayout.setVisibility(View.GONE);
          holder.mCardView.setVisibility(View.VISIBLE);


      }
      else if( holder.mTvStepShortDec!=null && holder.mTvStepDesc!=null && action.equals("steps") )

      {
          Log.e("XXXXXX", "We are in the step thingee " );
          holder.mLinearLayout.setVisibility(View.VISIBLE);
          holder.mCardView.setVisibility(View.GONE);
          holder.mTvStepShortDec.setText(step.getShortDescription());
          holder.mTvStepDesc.setText(step.getDescription());







      }
      else
          Log.e("XXXXXX", "FATAL ERROR Some view are null " );


    }

    @Override
    public int getItemCount() {
        if (action.equals(ARG_INGREDIENTS)){
        if (ingredients== null)
        return 0;
        else return ingredients.size();}
        if(action.equals(ARG_STEPS)){
            if (step==null)
                return -1;
        else return 1;}
        else return -1;
    }

    class IngredientViewHolder extends  RecyclerView.ViewHolder implements ExoPlayer.EventListener{
        @BindView(R.id.tv_ingredient_quantity)
        TextView mTvQuantity;
        @BindView(R.id.tv_ingredient_measure)
        TextView mTvMeasure;
        @BindView(R.id.tv_ingredient_ingredient)
        TextView mTvIngredient;
        @BindView(R.id.cv_ingredient_item)
        CardView mCardView;
        @BindView(R.id.exoview_step)
        SimpleExoPlayerView mPlayerView;
        @BindView(R.id.ll_step_item)
        LinearLayout mLinearLayout;
        @BindView(R.id.tv_step_short_desc)
                TextView mTvStepShortDec;
        @BindView(R.id.tv_step_desc)
                TextView mTvStepDesc;


        MediaSessionCompat mMediaSession;
        PlaybackStateCompat.Builder mStateBuilder;
        ExoPlayer mExoPlayer;

        public IngredientViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);

            initializeMediaSession();
            if(step !=null) {
                Uri uri = Uri.parse(step.getVidURL());
                initializePlayer(uri);
            }


        }


        private void initializeMediaSession() {

            // Create a MediaSessionCompat.
            mMediaSession = new MediaSessionCompat(context, " ");

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
        private void initializePlayer(Uri mediaUri) {
            if (mExoPlayer == null) {
                // Create an instance of the ExoPlayer.
                TrackSelector trackSelector = new DefaultTrackSelector();
                LoadControl loadControl = new DefaultLoadControl();
                mExoPlayer = ExoPlayerFactory.newSimpleInstance(context, trackSelector, loadControl);
                mPlayerView.setPlayer(mExoPlayer);

                // Set the ExoPlayer.EventListener to this activity.
                mExoPlayer.addListener(this);

                // Prepare the MediaSource.
                String userAgent = Util.getUserAgent(context, "ClassicalMusicQuiz");
                MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(
                        context, userAgent), new DefaultExtractorsFactory(), null, null);
                mExoPlayer.prepare(mediaSource);
                mExoPlayer.setPlayWhenReady(true);
            }
        }
        private void releasePlayer() {
            Log.e("XXXXXX", "releasePlayer: Releasing ?" );
            mExoPlayer.stop();
            mExoPlayer.release();
            mExoPlayer = null;
            mMediaSession.setActive(false);
        }

    }
}
