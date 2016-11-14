package edu.kvcc.cis298.cis298assignment3;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

/**
 * Created by dhatt_000 on 11/14/2016.
 */

public class WineListFragment extends Fragment {

    //String key for saving whether to show the subtitle or not
    private static String SAVED_SUBTITLE_VISIBLE = "subtitle";

    private RecyclerView mWineRecyclerView;
    //Declare a new wineAdapter. This comes from the inner class
    //that is written below in this file.
    private WineAdapter mAdapter;

    //Bool to know whether the subtitle is being shown or not.
    private boolean mSubtitleVisible;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Tell the fragment manager that this fragment has an
        //options menu that should be displayed. This will ensure
        //that the fragment manager makes a call to the
        //onCreateOptionsMenu method, which does the work of
        //inflating the menu and displaying it.
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //Inflate the view from the layout file into a view variable
        View view = inflater.inflate(R.layout.fragment_wine_list, container, false);

        //Get a handle to the recycler view using findViewById
        mWineRecyclerView = (RecyclerView) view.findViewById(R.id.wine_recycler_view);

        //Set the layout manger for the recyclerview. It needs to know how to layout
        //the indiviual views that make up the recyclerView. This linear layout manager
        //will tell the recyclerView to lay them out in a vertical fashion.
        mWineRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        //Get the bool that represents whether to show the subtitle from the bundle
        if (savedInstanceState != null) {
            mSubtitleVisible = savedInstanceState.getBoolean(SAVED_SUBTITLE_VISIBLE);
        }

        //Call the updateUI method which will setup the recyclerView with data.
        updateUI();

        //return the view
        return view;
    }

    private class WineHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        //Add a title variable to the viewHolder
        private TextView mDescriptionTextView;
        private TextView mPriceTextView;
        private TextView mIdTextView;

        //Variable to hold a single wine
        private Wine mWine;

        //Constructor for the WineHolder.
        public WineHolder(View itemView) {
            //Call the parent constructor
            super(itemView);
            itemView.setOnClickListener(this);

            //Get the itemView that is passed into this constructor and assign it
            //to the class level variable.
            mDescriptionTextView = (TextView) itemView
                    .findViewById(R.id.list_item_wine_description_text_view);
            mIdTextView = (TextView) itemView
                    .findViewById(R.id.list_item_wine_id_text_view);
            mPriceTextView = (TextView) itemView
                    .findViewById(R.id.list_item_wine_price_check_box);
        }

        //Write a method in here to take in an instance of a wine
        //and then assign the wine properties to the various
        //view widgets
        public  void bindWine(Wine wine) {
            //Assign the passed in wine to the class level one
            mWine = wine;
            //Set the widget controls with the data from the crime.
            mDescriptionTextView.setText(mWine.getDescription());
            mIdTextView.setText(mWine.getId().toString());
            mPriceTextView.setText(Double.toString(mWine.getPrice()));
        }

        @Override
        public void onClick(View v) {
            Intent intent = WinePagerActivity.newIntent(getActivity(), mWine.getId());
            startActivity(intent);
        }
    }

    private class WineAdapter extends RecyclerView.Adapter<WineHolder> {
        //Setup local version of the list of wines
        private List<Wine> mWines;

        //Constructor to set the list of wines
        public WineAdapter(List<Wine> wines) {
            mWines = wines;
        }


        @Override
        public WineHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            //Get a reference to a layout inflator that can inflate our view
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            //Use the inflator to inflate the default android list view.
            //We did not write this layout file. It is a standard android one.
            View view = layoutInflater.inflate(R.layout.list_item_crime, parent, false);
            //Return a new crimeHolder and pass in the view we just created.
            return new WineHolder(view);
        }

        @Override
        public void onBindViewHolder(WineHolder holder, int position) {
            //Get the wine out of the list of wines that is declared
            //on the inner adapter class we wrote.
            Wine wine = mWines.get(position);
            //Set the data from the wine object
            //to the viewHolders various widgets.
            holder.bindWine(wine);
        }

        @Override
        public int getItemCount() {
            return mWines.size();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        //This will update the UI when returning to this fragment
        //from the detail view one.
        updateUI();
    }

    //Override method that gets called when the menu is created
    //This method will be called by the fragment manager. The
    //fragment manager will know to call this method because
    //in the onCreate method we set a bool on the fragment
    //indicating that this fragment has a menu that must be
    //displayed. The fragment manager will check that bool and then
    //call this method when creating the fragment.
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_crime_list, menu);

        MenuItem subtitleItem = menu.findItem(R.id.menu_item_show_subtitle);
        if (mSubtitleVisible) {
            subtitleItem.setTitle(R.string.hide_subtitle);
        } else {
            subtitleItem.setTitle(R.string.show_subtitle);
        }
    }

    //This method gets called everytime any item in the menu is selected
    //It is the job of the programmer to determine which item was selected
    //and then handle it appropriately
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //The method has the item that was selected come into the method
        //We can run a switch on the id of the menu item that was selected
        switch (item.getItemId()) {
            //if the id matches the add new wine item id, which is the id
            //from the layout file for the add wine item.
            case R.id.menu_item_new_crime:
                //make a new wine
                Wine wine = new Wine();
                //Get the singleton wine list, and add the new wine
                WineList.get(getActivity()).addWine(wine);
                //Make a new intent to start up the wine pager activity
                Intent intent = WinePagerActivity
                        .newIntent(getActivity(), wine.getId());
                //Start the activity with the new intent. No need to start
                //for a result because we do not need a result.
                startActivity(intent);
                //Return true to signify that no more processing is needed.
                return true;

            case R.id.menu_item_show_subtitle:
                mSubtitleVisible = !mSubtitleVisible;
                getActivity().invalidateOptionsMenu();
                updateSubtitle();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //Save the show subtitle bool to the bundle
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(SAVED_SUBTITLE_VISIBLE, mSubtitleVisible);
    }

    //****************************************
    //Private Methods
    //****************************************
    private void updateUI() {
        //This is using the static method on the WineList class
        //to return the singleton. This will get us our one and only one
        //instance of the WineList. There can be only one!!!
        WineList wineList = WineList.get(getActivity());

        //Get the list of wines from the singleton WineList.
        //This will give us a local reference to the list of wines
        //that we can send over to the WineAdapter.
        List<Wine> wines = wineList.getWines();

        if (mAdapter == null) {
            //Create a new WineAdapter and send the wines we just got
            //over to it. This way the adapter will have the list of wines
            //it can use to make new viewholders and bind data to the viewholder
            mAdapter = new WineAdapter(wines);

            //Set the adapter on the recyclerview.
            mWineRecyclerView.setAdapter(mAdapter);
        } else {
            //Notify the adapter that data may have changed and that
            //it should reload the data using the existing adapter.
            mAdapter.notifyDataSetChanged();
        }

        updateSubtitle();
    }

    private void updateSubtitle() {
        //Get the WineList
        WineList wineList = WineList.get(getActivity());
        //Get all the crimes in the crime lab and get the size of that list
        int wineCount = wineList.getWines().size();
        //Create a local string from the string resource that is
        //the subtitle format. The format may be incorrect depending on the
        //version of android you are developing on??? We have the format
        //set as a string denoted by %1$s, however it might need to be
        //%1$d. S is for string, d is for numbers
        String subtitle = getString(R.string.subtitle_format, wineCount);

        //If we are hiding the subtitle, set the subtitle to null.
        if (!mSubtitleVisible) {
            subtitle = null;
        }

        //Get a reference to the activity so that we can set the subtitle
        //of the action bar, which is also called the tool bar.
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.getSupportActionBar().setSubtitle(subtitle);
    }
}
