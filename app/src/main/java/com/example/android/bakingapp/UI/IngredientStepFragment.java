package com.example.android.bakingapp.UI;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.bakingapp.POJOs.Data.RecipesHolder;
import com.example.android.bakingapp.POJOs.Ingredient;
import com.example.android.bakingapp.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link IngredientStepFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link IngredientStepFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class IngredientStepFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_INGREDIENTS = "ingredients";
    //private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private ArrayList<Ingredient> ingredients;



    private OnFragmentInteractionListener mListener;

    public IngredientStepFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment IngredientStepFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static IngredientStepFragment newInstance(String param1, String param2) {
        IngredientStepFragment fragment = new IngredientStepFragment();
        Bundle args = new Bundle();
        args.putString(ARG_INGREDIENTS, param1);
        //args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            ingredients = (ArrayList<Ingredient>) getArguments().getSerializable(ARG_INGREDIENTS);

        }
    }
    @BindView(R.id.rv_ingredient)
    RecyclerView mRecyclerView;
    @BindView(R.id.btn_next)
    FloatingActionButton mNextButton;
    @BindView(R.id.btn_prev)
    FloatingActionButton mPrevButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ingredient_steps_list, container, false);
        ButterKnife.bind(this,view);
        IngredientAdapter mAdapter = new IngredientAdapter(getContext(),ingredients);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = ((AppCompatActivity) getContext()).getSupportFragmentManager();
                ArrayList<Ingredient> ingredients = RecipesHolder.recipes.get(2).getIngredients();
                IngredientStepFragment fragment = new IngredientStepFragment();
                Bundle b = new Bundle();
                b.putSerializable("ingredients",ingredients);
                fragment.setArguments(b);
                fm.beginTransaction()
                        .replace(R.id.fragment_container,fragment)
                        .commit();
            }
        });


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
