package at.technikumwien.anda.wienerlinien.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import at.technikumwien.anda.wienerlinien.R;
import at.technikumwien.anda.wienerlinien.ui.activity.LineDetailActivity;
import at.technikumwien.anda.wienerlinien.ui.activity.LineListActivity;

/**
 * A fragment representing a single Line detail screen.
 * This fragment is either contained in a {@link LineListActivity}
 * in two-pane mode (on tablets) or a {@link LineDetailActivity}
 * on handsets.
 */
public class LineDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public LineDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_line_detail, container, false);
    }
}
