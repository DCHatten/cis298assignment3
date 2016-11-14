package edu.kvcc.cis298.cis298assignment3;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import java.util.Date;
import java.util.UUID;

/**
 * Created by dhatt_000 on 11/14/2016.
 */

public class WineFragment extends Fragment {

    private static final String ARG_WINE_ID = "wine_id";
    private static final String DIALOG_DATE = "DialogDate";

    private static final int REQUEST_DATE = 0;

    private Wine mWine;
    private EditText mIdField;
    private EditText mDescriptionField;
    private EditText mCaseField;
    private EditText mPriceField;
    private CheckBox mSolvedCheckbox;

    //This method allows any class the ability to call this method
    //and get a new properly formatted fragment. Since we want any
    //new fragment to have the information from a specific crime
    //we will require that this method be used, to create the new
    //fragment.
    public static WineFragment newInstance(UUID wineId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_WINE_ID, wineId);

        WineFragment fragment = new WineFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Get the UUID out from the fragment arguments.
        //In order to get the UUID out from the arguments we need
        //to use getSerializableExtra. That method allows us to get
        //out an object that was stored as an Arg. The catch is that
        //the object we want to store must implement the Serializable
        //interface in order to be able to be sent in the args, and then
        //retrived with getSerializableExtra.

        //The getArguments method will return a bundle object that was
        //set when the fragment got created. We have created a static
        //method at the top of this file to get a new fragment created.
        //That method accepts a UUID and then stores it in the args
        //for the new fragment. We then get the UUID out right here.
        UUID wineId = (UUID) getArguments().getSerializable(ARG_WINE_ID);

        mWine = WineList.get(getActivity()).getWine(wineId);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        //Use the inflator to inflate the layout file we want to use with this fragment
        //The first parameter is the layout resource. The second is the passed in
        //container that is the parent widget.
        //The last parameter is whether or not to statically assign the fragment
        //to the parent (FrameLayout) container.
        View v = inflater.inflate(R.layout.fragment_wine, container, false);

        //Get a reference to the EditText Widget in the layout file.
        //instead of just calling findViewById, which is a activity method,
        //we need to call the findViewById that is part of the view we just created.
        //aside from that, it operates the same.
        mDescriptionField = (EditText)v.findViewById(R.id.wine_description);
        mDescriptionField.setText(mWine.getDescription());
        mDescriptionField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //We aren't doing anything here.
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mWine.setDescription(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                //We aren't doing anything here either.
            }
        });

        //Set ID Edit text
        mIdField = (EditText)v.findViewById(R.id.wine_id);
        mIdField.setText(mWine.getId().toString());
        mIdField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //We aren't doing anything here
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count){
                mWine.setId(UUID.fromString(s.toString()));
            }
            @Override
            public void afterTextChanged(Editable s){
                //We aren't doing anything here either
            }
        });

        mSolvedCheckbox = (CheckBox)v.findViewById(R.id.wine_is_active);
        mSolvedCheckbox.setChecked(mWine.isActive());
        mSolvedCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //Set the crimes solve property
                mWine.setActive(isChecked);
            }
        });

        return v;
    }


}
