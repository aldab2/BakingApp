package com.example.android.bakingapp.UI;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.android.bakingapp.POJOs.NextPrevButtonEvent;
import com.example.android.bakingapp.POJOs.Recipe;
import com.example.android.bakingapp.POJOs.RecipeStep;
import com.example.android.bakingapp.R;
import com.example.android.bakingapp.Utils.MySessionCallback;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link VideoFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link VideoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VideoFragment extends Fragment implements ExoPlayer.EventListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
     static final String ARG_URL = "url";
     static final String ARG_SHORT_DESC = "short_description";
     static final String ARG_DESC = "description";
     static final String ARG_STEP_ID = "stepID";
     static final String ARG_RECIPE = "recipe";
     static final String ARG_PLAYER_POSITION = "player_position";


    // TODO: Rename and change types of parameters
    private String VidURL;
    private String shortDesc;
    private String description;
    private int stepId;
    private Recipe recipe;
    private long position;
    MediaSessionCompat mMediaSession;
    PlaybackStateCompat.Builder mStateBuilder;
    SimpleExoPlayer mExoPlayer;

    private OnFragmentInteractionListener mListener;

    public VideoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment VideoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static VideoFragment newInstance(String param1, String param2) {
        VideoFragment fragment = new VideoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_URL, param1);
        args.putString(ARG_SHORT_DESC, param2);
        args.putString(ARG_DESC, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            VidURL = getArguments().getString(ARG_URL);
            shortDesc = getArguments().getString(ARG_SHORT_DESC);
            description = getArguments().getString(ARG_DESC);
            stepId = getArguments().getInt(ARG_STEP_ID);
            recipe = (Recipe) getArguments().getSerializable(ARG_RECIPE);
            if (savedInstanceState!=null){
                position = savedInstanceState.getLong(ARG_PLAYER_POSITION,0);
            }

        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        if (mExoPlayer!=null)
        outState.putLong(ARG_PLAYER_POSITION,mExoPlayer.getCurrentPosition());
        super.onSaveInstanceState(outState);
    }

    @BindView(R.id.exoview_step)
    SimpleExoPlayerView mPlayerView;
    @BindView(R.id.tv_step_short_desc)
    TextView mTvShortDesc;
    @BindView(R.id.tv_step_desc)
    TextView mTvDescription;
    @BindView(R.id.btn_next)
    FloatingActionButton mNextButton;
    @BindView(R.id.btn_prev)
    FloatingActionButton mPrevButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_video, container, false);
        ButterKnife.bind(this,view);
        Log.e("YYYYY", "onCreate of Vidoe Fragment shortDisc:"+shortDesc );
        mTvShortDesc.setText(shortDesc);
        mTvDescription.setText(description);
        Log.e("URLIS", "onCreateView: "+VidURL );
       if(!VidURL.equals("")) {
           mPlayerView.setVisibility(View.VISIBLE);
           initializeMediaSession();
           Uri uri = Uri.parse(VidURL);
           initializePlayer(uri);
       }
       else
           mPlayerView.setVisibility(View.GONE);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Todo implement this
                if(mExoPlayer!= null)
                releasePlayer();
                stepId = (stepId +1)% recipe.getSteps().size();
                RecipeStep selectedStep = recipe.getSteps().get(stepId);//PublicDataHolder.recipes.get(position).getSteps();
               // toNextStep(selectedStep);
                EventBus.getDefault().post(new NextPrevButtonEvent(recipe,selectedStep));


            }
        });
        mPrevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Todo implement this
                if(mExoPlayer!= null)
                    releasePlayer();
                stepId =    (stepId -1)% recipe.getSteps().size();
                if (stepId<0)
                    stepId=0;
                RecipeStep selectedStep = recipe.getSteps().get(stepId);//PublicDataHolder.recipes.get(position).getSteps();
               // toNextStep(selectedStep);
                EventBus.getDefault().post(new NextPrevButtonEvent(recipe,selectedStep));
            }
        });



        return view;
    }

    /*private void toNextStep(RecipeStep selectedStep) {
        Bundle b = new Bundle();

        b.putString("url",selectedStep.getVidURL());
        b.putString("short_description",selectedStep.getShortDescription());
        b.putString("description",selectedStep.getDescription());
        b.putInt("stepID",selectedStep.getId());
        b.putSerializable("recipe",recipe);
               *//* b.putSerializable("step", selectedStep);
                b.putString("action", "steps");*//*

        VideoFragment fragment = new VideoFragment();
        fragment.setArguments(b);
        FragmentManager fm = ((AppCompatActivity) getContext()).getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.fragment_container, fragment)
                .commit();
    }*/


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mExoPlayer!= null)
        releasePlayer();
    }
    /*  @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }*/

  /*  @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }*/

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
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
     private void initializeMediaSession() {

            // Create a MediaSessionCompat.
            mMediaSession = new MediaSessionCompat(getContext(), " ");

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
                mExoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector, loadControl);
                mPlayerView.setPlayer(mExoPlayer);

                // Set the ExoPlayer.EventListener to this activity.
                mExoPlayer.addListener(this);

                // Prepare the MediaSource.
                String userAgent = Util.getUserAgent(getContext(), "ClassicalMusicQuiz");
                MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(
                        getContext(), userAgent), new DefaultExtractorsFactory(), null, null);

                mExoPlayer.prepare(mediaSource);
                mExoPlayer.seekTo(position);
                mExoPlayer.setPlayWhenReady(true);
            }
        }
        private void releasePlayer() {
            Log.d("XXXXXX", "releasePlayer: Releasing ?" );
            mExoPlayer.stop();
            mExoPlayer.release();
            mExoPlayer = null;
            mMediaSession.setActive(false);
        }
}
