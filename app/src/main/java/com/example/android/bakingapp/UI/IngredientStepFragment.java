package com.example.android.bakingapp.UI;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.bakingapp.POJOs.Ingredient;
import com.example.android.bakingapp.POJOs.RecipeStep;
import com.example.android.bakingapp.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class IngredientStepFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_INGREDIENTS = "ingredients";
    private static final String ARG_STEPS = "steps";
    private static final String ARG_ONESTEP = "step";
    private static final String ARG_ACTION_TYPE = "action";
    //private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private ArrayList<Ingredient> ingredients;
    private ArrayList<RecipeStep> steps;
    private RecipeStep step;
    private String action;



    private OnFragmentInteractionListener mListener;

    public IngredientStepFragment() {
        // Required empty public constructor
    }


  /*  // TODO: Rename and change types and number of parameters
    public static IngredientStepFragment newInstance(String param1, String param2,String params3) {
        IngredientStepFragment fragment = new IngredientStepFragment();
        Bundle args = new Bundle();
        args.putString(ARG_INGREDIENTS, param1);
        args.putString(ARG_STEPS, param2);
        args.putString(ARG_ACTION_TYPE,params3);
        fragment.setArguments(args);
        return fragment;
    }*/

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            ingredients = (ArrayList<Ingredient>) getArguments().getSerializable(ARG_INGREDIENTS);
            steps = (ArrayList<RecipeStep>) getArguments().getSerializable(ARG_STEPS);
            action = getArguments().getString(ARG_ACTION_TYPE);
            step =(RecipeStep) getArguments().getSerializable(ARG_ONESTEP);
        }
    }
    @BindView(R.id.rv_ingredient)
    RecyclerView mRecyclerView;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ingredient_steps_list, container, false);
        ButterKnife.bind(this,view);
        IngredientStepsAdapter mAdapter = new IngredientStepsAdapter(getContext(),ingredients,step,action);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));



        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(int  position) {
        if (mListener != null) {
            mListener.onFragmentInteraction(position);
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
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(int position);
    }
}
