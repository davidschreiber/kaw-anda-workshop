package at.technikumwien.anda.wienerlinien.ui.activity;

import android.test.ActivityInstrumentationTestCase2;
import android.util.DisplayMetrics;
import android.view.View;

import at.technikumwien.anda.wienerlinien.R;

import static org.assertj.android.api.Assertions.assertThat;


public class LineListActivityTest extends ActivityInstrumentationTestCase2<LineListActivity> {

    // =============================================================================
    // Constructor
    // =============================================================================

    public LineListActivityTest() {
        super(LineListActivity.class);
    }

    // =============================================================================
    // Setup
    // =============================================================================

    @Override
    public void setUp() throws Exception {
        super.setUp();
        // Espresso will not launch our activity for us, we must launch it via getActivity().
        getActivity();
    }

    // =============================================================================
    // Tests
    // =============================================================================

    public void testListExists() {
        final LineListActivity activity = getActivity();

        // list is always visible
        assertThat(activity.findViewById(android.R.id.list)).isNotNull();

        // details are visible if smallest screen width is >= 600
        final View lineDetailContainer = activity.findViewById(R.id.line_detail_container);
        final DisplayMetrics displayMetrics = activity.getResources().getDisplayMetrics();
        float dpHeight = displayMetrics.heightPixels / displayMetrics.density;
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        float smallestWidth = Math.min(dpWidth, dpHeight);
        if(smallestWidth < 600) {
            assertThat(lineDetailContainer).isNull();
        } else {
            assertThat(lineDetailContainer).isNotNull();
        }
    }
}