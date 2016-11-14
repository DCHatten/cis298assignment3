package edu.kvcc.cis298.cis298assignment3;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.List;
import java.util.UUID;

/**
 * Created by dhatt_000 on 11/14/2016.
 */

public class WinePagerActivity extends AppCompatActivity {

    private static final String EXTRA_WINE_ID =
            "edu.kvcc.cis298.cis298assignment3.wine_id";

    public static Intent newIntent(Context packageContext, UUID crimeId) {
        Intent intent = new Intent(packageContext, WinePagerActivity.class);
        intent.putExtra(EXTRA_WINE_ID, crimeId);
        return intent;
    }

    private ViewPager mViewPager;
    private List<Wine> mWines;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wine_pager);

        //Fetch the crimeId out of the extra. It was put into the extra in
        //the static method at the top of this file.
        UUID crimeId = (UUID) getIntent()
                .getSerializableExtra(EXTRA_WINE_ID);

        mViewPager = (ViewPager) findViewById(R.id.activity_wine_pager_view_pager);

        mWines = WineList.get(this).getWines();
        FragmentManager fragmentManager = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
            @Override
            public Fragment getItem(int position) {
                //Get the crime from the crimelab that we want to have displayed
                Wine wine = mWines.get(position);
                //Use the public static method on the CrimeFragment class to get
                //a new fragment that will be displayed.
                //The work for creating the method we are calling here was done
                //in chapter 10.
                return WineFragment.newInstance(wine.getId());
            }

            @Override
            public int getCount() {
                return mWines.size();
            }
        });

        //Run a loop to from 0 to the size of the list.
        //If we find a match between the current index's crime id
        //and the crimeId we are looking for, we will use that
        //index to set the current item on the view pager.
        //At least we are breaking after we find a match.
        for (int i = 0; i < mWines.size(); i++) {
            if (mWines.get(i).getId().equals(crimeId)) {
                mViewPager.setCurrentItem(i);
                break;
            }
        }
    }
}
