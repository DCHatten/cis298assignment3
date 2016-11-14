package edu.kvcc.cis298.cis298assignment3;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import java.util.UUID;

public class WineActivity extends SingleFragmentActivity {

    private static final String EXTRA_WINE_ID =
            "edu.kvcc.cis298.cis298assignment3.wine_ID";

    public static Intent newIntent(Context packageContext, UUID wineId) {
        Intent intent = new Intent(packageContext, WineActivity.class);
        intent.putExtra(EXTRA_WINE_ID, wineId);
        return intent;
    }

    @Override
    protected WineFragment createFragment() {
        UUID wineId = (UUID) getIntent()
                .getSerializableExtra(EXTRA_WINE_ID);
        return WineFragment.newInstance(wineId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
