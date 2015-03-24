package at.technikumwien.anda.wienerlinien.ui.activity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import at.technikumwien.anda.wienerlinien.R;
import at.technikumwien.anda.wienerlinien.model.Line;


public class LineListActivity extends ActionBarActivity {

    // =============================================================================
    // Views
    // =============================================================================

    // =============================================================================
    // Supertype overrides
    // =============================================================================

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line_list);

        // Extract ListView from layout
        ListView listView = (ListView) findViewById(R.id.listView);

        // Create hard-coded list of lines
        ArrayList<Line> lines = new ArrayList<>();
        lines.add(new Line("U1", Color.parseColor("#E20A16")));
        lines.add(new Line("U2", Color.parseColor("#764785")));
        lines.add(new Line("U3", Color.parseColor("#F76013")));
        lines.add(new Line("U4", Color.parseColor("#008131")));
        lines.add(new Line("U6", Color.parseColor("#88471F")));
        lines.add(new Line("31", Color.parseColor("#999999")));

        // Create an set adapter for displaying the lines
        LineAdapter adapter = new LineAdapter(lines);
        listView.setAdapter(adapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_line_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // =============================================================================
    // Inner classes
    // =============================================================================

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
            view.setBackground(new ColorDrawable(line.getColor()));

            return view;
        }

    }

}