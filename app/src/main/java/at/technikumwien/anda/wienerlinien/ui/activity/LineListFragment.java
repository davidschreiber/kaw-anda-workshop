package at.technikumwien.anda.wienerlinien.ui.activity;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import at.technikumwien.anda.wienerlinien.Database;
import at.technikumwien.anda.wienerlinien.R;
import at.technikumwien.anda.wienerlinien.model.Line;

/**
 * A list fragment representing a list of Lines. This fragment
 * also supports tablet devices by allowing list items to be given an
 * 'activated' state upon selection. This helps indicate which item is
 * currently being viewed in a {@link LineDetailFragment}.
 * <p/>
 * Activities containing this fragment MUST implement the {@link Callbacks}
 * interface.
 */
public class LineListFragment extends ListFragment {

    /**
     * The serialization (saved instance state) Bundle key representing the
     * activated item position. Only used on tablets.
     */
    private static final String STATE_ACTIVATED_POSITION = "activated_position";

    /**
     * The fragment's current callback object, which is notified of list item
     * clicks.
     */
    private Callbacks mCallbacks = sDummyCallbacks;

    /**
     * The current activated item position. Only used on tablets.
     */
    private int mActivatedPosition = ListView.INVALID_POSITION;

    /**
     * A callback interface that all activities containing this fragment must
     * implement. This mechanism allows activities to be notified of item
     * selections.
     */
    public interface Callbacks {
        /**
         * Callback for when an item has been selected.
         */
        public void onItemSelected(long id);
    }

    /**
     * A dummy implementation of the {@link Callbacks} interface that does
     * nothing. Used only when this fragment is not attached to an activity.
     */
    private static Callbacks sDummyCallbacks = new Callbacks() {
        @Override
        public void onItemSelected(long id) {
            // do nothing
            Log.d("TAG", "dummy callback handles click");
        }
    };

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public LineListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Create and set adapter for displaying the lines
        LineAdapter adapter =
            new LineAdapter(Database.getInstance().getLines());

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
        if (!(activity instanceof Callbacks)) {
            throw new IllegalStateException("Activity must implement fragment's callbacks.");
        }

        mCallbacks = (Callbacks) activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();

        // Reset the active callbacks interface to the dummy implementation.
        mCallbacks = sDummyCallbacks;
    }

    @Override
    public void onListItemClick(ListView listView, View view, int position, long id) {
        super.onListItemClick(listView, view, position, id);

        // Notify the active callbacks interface (the activity, if the
        // fragment is attached to one) that an item has been selected.
        mCallbacks.onItemSelected(id);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mActivatedPosition != ListView.INVALID_POSITION) {
            // Serialize and persist the activated item position.
            outState.putInt(STATE_ACTIVATED_POSITION, mActivatedPosition);
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
            getListView().setItemChecked(mActivatedPosition, false);
        } else {
            getListView().setItemChecked(position, true);
        }

        mActivatedPosition = position;
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
            return getItem(position).getId();
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
            if(Build.VERSION.SDK_INT >= 16) {
                view.setBackground(new ColorDrawable(line.getColor()));
            } else {
                view.setBackgroundDrawable(new ColorDrawable(line.getColor()));
            }

            return view;
        }

    }
}
