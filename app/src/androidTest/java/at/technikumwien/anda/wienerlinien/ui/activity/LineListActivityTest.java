package at.technikumwien.anda.wienerlinien.ui.activity;

import android.test.ActivityInstrumentationTestCase2;

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

    public void testLaunchActivity() {
        assertThat(getActivity().findViewById(android.R.id.list)).isNotNull();
    }
}