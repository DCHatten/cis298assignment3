package edu.kvcc.cis298.cis298assignment3;

/**
 * Created by dhatt_000 on 11/14/2016.
 */

public class WineListActivity extends SingleFragmentActivity {
    @Override
    protected WineFragment createFragment() {
        return new WineListFragment();
    }
}
