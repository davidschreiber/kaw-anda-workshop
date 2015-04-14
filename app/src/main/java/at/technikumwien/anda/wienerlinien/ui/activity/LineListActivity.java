package at.technikumwien.anda.wienerlinien.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;


import at.technikumwien.anda.wienerlinien.R;
import at.technikumwien.anda.wienerlinien.ui.fragment.LineDetailFragment;
import at.technikumwien.anda.wienerlinien.ui.fragment.LineListFragment;

/**
 * An activity representing a list of Lines. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link LineDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 * <p/>
 * The activity makes heavy use of fragments. The list of items is a
 * {@link LineListFragment} and the item details
 * (if present) is a {@link LineDetailFragment}.
 * <p/>
 * This activity also implements the required
 * {@link LineListFragment.Callbacks} interface
 * to listen for item selections.
 */
public class LineListActivity extends FragmentActivity
    implements LineListFragment.Callbacks {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line_list);

        if (findViewById(R.id.line_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-large and
            // res/values-sw600dp). If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;

            // In two-pane mode, list items should be given the
            // 'activated' state when touched.
            ((LineListFragment) getSupportFragmentManager()
                .findFragmentById(R.id.line_list))
                .setActivateOnItemClick(true);
        }

        // TODO: If exposing deep links into your app, handle intents here.
    }

    /**
     * Callback method from {@link LineListFragment.Callbacks}
     * indicating that the item with the given ID was selected.
     */
    @Override
    public void onItemSelected(String id) {
        if (mTwoPane) {
            // In two-pane mode, show the detail view in this activity by
            // adding or replacing the detail fragment using a
            // fragment transaction.
            Bundle arguments = new Bundle();
            arguments.putString(LineDetailFragment.ARG_ITEM_ID, id);
            LineDetailFragment fragment = new LineDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                .replace(R.id.line_detail_container, fragment)
                .commit();

        } else {
            // In single-pane mode, simply start the detail activity
            // for the selected item ID.
            Intent detailIntent = new Intent(this, LineDetailActivity.class);
            detailIntent.putExtra(LineDetailFragment.ARG_ITEM_ID, id);
            startActivity(detailIntent);
        }
    }
}
