package at.technikumwien.anda.wienerlinien.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import at.technikumwien.anda.wienerlinien.Database;
import at.technikumwien.anda.wienerlinien.R;
import at.technikumwien.anda.wienerlinien.model.Line;

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
    public static final String ARG_LINE_ID = "line_id";

    /**
     * The line this fragment is presenting.
     */
    private Line line;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public LineDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_LINE_ID)) {
            // Load the line specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            final long lineId = getArguments().getLong(ARG_LINE_ID);


            line = Database.getInstance().getLine(lineId);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_line_detail, container, false);

        // Show the dummy content as text in a TextView.
        if (line != null) {
            ((TextView) rootView.findViewById(R.id.line_detail)).setText(line.getName());
        }

        return rootView;
    }
}
