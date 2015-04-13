package at.technikumwien.anda.wienerlinien.data.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;

import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import at.technikumwien.anda.wienerlinien.data.model.Line;

public class WienerLinienContentProvider extends ContentProvider {

    // =============================================================================
    // Constants
    // =============================================================================

    /**
     * Provider's common URI authority.
     */
    public static final String URI_AUTHORITY = "at.technikumwien.anda.wienerlinien";

    /**
     * Uri for fetching all lines.
     */
    public static final Uri LINES_URI = Uri.parse("content://" + URI_AUTHORITY + "/lines");

    /**
     * Uri matcher result when all lines where queried.
     */
    private static final int ALL_LINES = 0;

    /**
     * Uri matcher result when single line was queried.
     */
    private static final int SINGLE_LINE = 1;

    /**
     * Column name definitions for line queries.
     */
    public static final class LINE_KEYS {
        /**
         * Unique ID of the line.
         */
        public static final String ID = "id";

        /**
         * Human readable name of the line (e.g. "U1").
         */
        public static final String NAME = "name";

        public static final String SORT = "sort";
        public static final String REALTIME = "realtime";
        public static final String TYPE = "type";
        public static final String STAND = "stand";
    }

    /**
     * Column names of the cursor returned when querying data from {@link #LINES_URI}.
     */
    public static final String[] LINE_COLUMN_NAMES = new String[]{LINE_KEYS.ID, LINE_KEYS.NAME, LINE_KEYS.SORT, LINE_KEYS.REALTIME, LINE_KEYS.TYPE, LINE_KEYS.STAND};

    // =============================================================================
    // Private members
    // =============================================================================

    /**
     * In memory array of lines.
     */
    private List<Line> lines;

    /**
     * Uri matcher for deciding which dataset was requested.
     */
    private static final UriMatcher uriMatcher;

    // =============================================================================
    // Constructor
    // =============================================================================

    /**
     * Empty constructor as needed.
     */
    public WienerLinienContentProvider() {
    }

    /**
     * Setup for the UriMatcher routes.
     */
    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(URI_AUTHORITY, "lines", ALL_LINES);
        uriMatcher.addURI(URI_AUTHORITY, "lines/#", SINGLE_LINE);
    }

    // =============================================================================
    // Supertype overrides
    // =============================================================================

    @Override
    public String getType(Uri uri) {
        // for the time being this provider only returns stations
        return "application/station";
    }

    @Override
    public boolean onCreate() {
        lines = new ArrayList<>();

        CsvMapper mapper = new CsvMapper();
        CsvSchema schema = CsvSchema.builder()
            .setUseHeader(true)
            .setColumnSeparator(';')
            .addNumberColumn("LINIEN_ID")
            .addColumn("BEZEICHNUNG")
            .addNumberColumn("REIHENFOLGE")
            .addNumberColumn("ECHTZEIT")
            .addColumn("VERKEHRSMITTEL")
            .addColumn("STAND")
            .build();

        try {
            Iterator<Line> lineIterator = mapper
                .reader(Line.class)
                .with(schema)
                .readValues(getContext().getAssets().open("wienerlinien-ogd-linien.csv"));

            while (lineIterator.hasNext()) {
                lines.add(lineIterator.next());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
//
//        // Create a list of stations for line U1
//        ArrayList<Station> u1Stations = new ArrayList<>();
//        u1Stations.add(new Station("Reumannplatz", 0, 0, null));
//        u1Stations.add(new Station("Keplerplatz", 0, 0, null));
//        u1Stations.add(new Station("Hauptbahnhof", 0, 0, null));
//        u1Stations.add(new Station("Taubstummengasse", 0, 0, null));
//        u1Stations.add(new Station("Karlsplatz", 0, 0, null));
//        u1Stations.add(new Station("Stephansplatz", 0, 0, null));
//        u1Stations.add(new Station("Schwedenplatz", 0, 0, null));
//        u1Stations.add(new Station("Leopoldau", 0, 0, null));
//
//        // Create the line U1 and add it's stations
//        final Line u1 = new Line(0, "U1", Color.parseColor("#E20A16"), u1Stations);
//
//        // Create lines u2, u3 and u4 without stations
//        final Line u2 = new Line(1, "U2", Color.parseColor("#764785"), null);
//        final Line u3 = new Line(2, "U3", Color.parseColor("#F76013"), null);
//        final Line u4 = new Line(3, "U4", Color.parseColor("#008131"), null);
//
//        // Create a list of stations for line U6
//        ArrayList<Station> u6Stations = new ArrayList<>();
//        u6Stations.add(new Station("Floridsdorf", 0, 0, null));
//        u6Stations.add(new Station("Neue Donau", 0, 0, null));
//        u6Stations.add(new Station("Handelskai", 0, 0, null));
//        u6Stations.add(new Station("Dresdnerstraße", 0, 0, null));
//        u6Stations.add(new Station("Spittelau", 0, 0, Collections.singletonList(u4)));
//
//
//        // Create line U6 with its stations
//        final Line u6 = new Line(4, "U6", Color.parseColor("#88471F"), u6Stations);
//
//        // Create line 31
//        final Line tram31 = new Line(5, "31", Color.parseColor("#999999"), null);
//
//        // add all lines to the mock database
//        lines.add(u1);
//        lines.add(u2);
//        lines.add(u3);
//        lines.add(u4);
//        lines.add(u6);
//        lines.add(tram31);

        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        MatrixCursor cursor = null;

        switch (uriMatcher.match(uri)) {
            case ALL_LINES:
                cursor = new MatrixCursor(WienerLinienContentProvider.LINE_COLUMN_NAMES, lines.size());

                for (Line line : lines) {
                    cursor.addRow(new Object[]{line.getId(), line.getName(), line.getSort(), line.getRealtime(), line.getType().toString(), line.getStand()});
                }
                break;
        }

        // return the cursor or null
        return cursor;
    }

    // =============================================================================
    // Unsupported provider methods
    // =============================================================================

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        throw new UnsupportedOperationException("Deletion is not supported by " + WienerLinienContentProvider.class.getSimpleName());
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        throw new UnsupportedOperationException("Inserting values is not supported by " + WienerLinienContentProvider.class.getSimpleName());
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        throw new UnsupportedOperationException("Updating values is not supported by " + WienerLinienContentProvider.class.getSimpleName());
    }


}
