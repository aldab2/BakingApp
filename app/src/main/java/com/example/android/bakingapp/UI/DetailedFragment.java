package com.example.android.bakingapp.UI;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.bakingapp.POJOs.Recipe;
import com.example.android.bakingapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailedFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_RECIPE = "recipe";
   // private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private Recipe recipe;
    //private String mParam2;

    private OnFragmentClickListener mListener;

    public DetailedFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
  /*  public static DetailedFragment newInstance(String param1, String param2) {
        DetailedFragment fragment = new DetailedFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }*/

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            recipe = (Recipe) getArguments().getSerializable(ARG_RECIPE);

           // mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    @BindView(R.id.rv_list_ing_steps)
    RecyclerView mRecyclerView;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detailed, container, false);
        ButterKnife.bind(this,view);
      if(recipe!=null){
          DetailedRecipeAdapter detailedRecipeAdapter = new DetailedRecipeAdapter(recipe,getContext());
          mRecyclerView.setAdapter(detailedRecipeAdapter);
          mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

      }
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onItemClickedPressed(int position) {
        if (mListener != null) {
            mListener.onRecipeIngredientSelected(position);
            mListener.onRecipeStepSelected(position);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentClickListener) {
            mListener = (OnFragmentClickListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentClickListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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
    public interface OnFragmentClickListener {
        // TODO: Update argument type and name
        void onRecipeIngredientSelected(int position);
        void onRecipeStepSelected(int position);
    }
}
