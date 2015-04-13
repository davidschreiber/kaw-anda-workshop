package at.technikumwien.anda.wienerlinien.ui.fragment;

import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import at.technikumwien.anda.wienerlinien.R;
import at.technikumwien.anda.wienerlinien.data.model.Line;
import at.technikumwien.anda.wienerlinien.data.provider.WienerLinienContentProvider;
import at.technikumwien.anda.wienerlinien.ui.WienerLinienPresenter;

/**
 * A list fragment representing a list of Lines. This fragment
 * also supports tablet devices by allowing list items to be given an
 * 'activated' state upon selection. This helps indicate which item is
 * currently being viewed in a {@link LineDetailFragment}.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnLineSelectedCallback}
 * interface.
 */
public class LineListFragment extends ListFragment {

    // =============================================================================
    // Constants
    // =============================================================================

    /**
     * The serialization (saved instance state) Bundle key representing the
     * activated item position. Only used on tablets.
     */
    private static final String STATE_ACTIVATED_POSITION = "activated_position";

    /**
     * A dummy implementation of the {@link OnLineSelectedCallback} interface that does
     * nothing. Used only when this fragment is not attached to an activity.
     */
    private static OnLineSelectedCallback dummyCallback = new OnLineSelectedCallback() {
        @Override
        public void onLineSelected(String id) {
        }
    };

    // =============================================================================
    // Private members
    // =============================================================================

    /**
     * The current activated item position. Only used on tablets.
     */
    private int activatedPosition = ListView.INVALID_POSITION;

    /**
     * This callback is used to notify of when the user selected a line.
     */
    private OnLineSelectedCallback lineSelectedCallback = dummyCallback;

    // =============================================================================
    // Public interfaces
    // =============================================================================

    /**
     * The callback used to tell embedding activities that the user selected a line from the
     * list.
     */
    public interface OnLineSelectedCallback {

        /**
         * Callback for when an item has been selected.
         */
        void onLineSelected(String id);
    }

    // =============================================================================
    // Constructor
    // =============================================================================

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public LineListFragment() {
    }

    // =============================================================================
    // Supertype overrides
    // =============================================================================

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get the content resolver for accessing data.
        ContentResolver cr = getActivity().getContentResolver();

        // Define the requested data.
        final String[] projection = {
            WienerLinienContentProvider.LINE_KEYS.ID,
            WienerLinienContentProvider.LINE_KEYS.NAME,
            WienerLinienContentProvider.LINE_KEYS.SORT,
            WienerLinienContentProvider.LINE_KEYS.REALTIME,
            WienerLinienContentProvider.LINE_KEYS.TYPE,
            WienerLinienContentProvider.LINE_KEYS.STAND,
        };

        // Query from the content provider.
        final Cursor result = cr.query(WienerLinienContentProvider.LINES_URI, projection, null, null, null);

        // Create line objects from the cursor result.
        List<Line> lines = new ArrayList<>();

        // As long as there are more lines, extract them from the cursor.
        while (result.moveToNext()) {
            lines.add(new Line(result.getLong(0), result.getString(1), result.getInt(2), result.getInt(3), Line.Type.valueOf(result.getString(4)), result.getString(5)));
        }

        // Cleanly close the cursor object.
        result.close();

        // Create an set adapter for displaying the lines
        LineAdapter adapter = new LineAdapter(lines);

        // Provide list view with line adapter
        setListAdapter(adapter);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Restore the previously serialized activated item position.
        if (savedInstanceState != null
            && savedInstanceState.containsKey(STATE_ACTIVATED_POSITION)) {
            setActivatedPosition(savedInstanceState.getInt(STATE_ACTIVATED_POSITION));
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // Activities containing this fragment must implement its callbacks.
        if (!(activity instanceof OnLineSelectedCallback)) {
            throw new IllegalStateException("Activity must implement fragment's callbacks.");
        }

        lineSelectedCallback = (OnLineSelectedCallback) activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();

        // Reset the active callbacks interface to the dummy implementation.
        lineSelectedCallback = dummyCallback;
    }

    @Override
    public void onListItemClick(ListView listView, View view, int position, long id) {
        super.onListItemClick(listView, view, position, id);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (activatedPosition != ListView.INVALID_POSITION) {
            // Serialize and persist the activated item position.
            outState.putInt(STATE_ACTIVATED_POSITION, activatedPosition);
        }
    }

    /**
     * Turns on activate-on-click mode. When this mode is on, list items will be
     * given the 'activated' state when touched.
     */
    public void setActivateOnItemClick(boolean activateOnItemClick) {
        // When setting CHOICE_MODE_SINGLE, ListView will automatically
        // give items the 'activated' state when touched.
        getListView().setChoiceMode(activateOnItemClick
            ? ListView.CHOICE_MODE_SINGLE
            : ListView.CHOICE_MODE_NONE);
    }

    private void setActivatedPosition(int position) {
        if (position == ListView.INVALID_POSITION) {
            getListView().setItemChecked(activatedPosition, false);
        } else {
            getListView().setItemChecked(position, true);
        }

        activatedPosition = position;
    }

    /**
     * Simple adapter for displaying a list of lines
     */
    public static class LineAdapter extends BaseAdapter {

        // =============================================================================
        // Private members
        // =============================================================================

        private List<Line> lines;

        // =============================================================================
        // Constructor
        // =============================================================================

        public LineAdapter(List<Line> lines) {
            if (lines == null) {
                throw new NullPointerException("lines has to be an initialized List object");
            }

            this.lines = lines;
        }

        // =============================================================================
        // Supertype overrides
        // =============================================================================

        @Override public int getCount() {
            return lines.size();
        }

        @Override public Line getItem(int position) {
            return lines.get(position);
        }

        @Override public long getItemId(int position) {
            return position;
        }

        @Override public View getView(int position, View convertView, ViewGroup parent) {
            TextView view = (TextView) convertView;

            // If no convert view was provided create a new view by inflation
            if (view == null) {
                view = (TextView) LayoutInflater.from(parent.getContext()).inflate(R.layout.item_line, parent, false);
            }

            final Line line = getItem(position);

            // Set line name
            view.setText(line.getName());

            // set line color
            final int lineBackground = WienerLinienPresenter.getBackgroundColorForLine(line);
            if (Build.VERSION.SDK_INT >= 16) {
                view.setBackground(new ColorDrawable(lineBackground));
            } else {
                //noinspection deprecation
                view.setBackgroundDrawable(new ColorDrawable(lineBackground));
            }

            return view;
        }

    }
}
