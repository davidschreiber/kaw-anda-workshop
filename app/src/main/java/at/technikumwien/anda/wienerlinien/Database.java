package at.technikumwien.anda.wienerlinien;

import android.graphics.Color;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import at.technikumwien.anda.wienerlinien.model.Line;
import at.technikumwien.anda.wienerlinien.model.Station;

/**
 * In-memory mock database (will be replaced by real WienerLinien data at some point).
 */
public class Database {

    // =============================================================================
    // Private members
    // =============================================================================

    private static Database Singleton;

    private ArrayList<Line> lines;

    // =============================================================================
    // Constructor
    // =============================================================================

    public static Database getInstance() {
        if (Singleton == null) {
            Singleton = new Database();
        }

        return Singleton;
    }

    private Database() {
        lines = new ArrayList<>();

        // Create a list of stations for line U1
        ArrayList<Station> u1Stations = new ArrayList<>();
        u1Stations.add(new Station("Reumannplatz", 0, 0, null));
        u1Stations.add(new Station("Keplerplatz", 0, 0, null));
        u1Stations.add(new Station("Hauptbahnhof", 0, 0, null));
        u1Stations.add(new Station("Taubstummengasse", 0, 0, null));
        u1Stations.add(new Station("Karlsplatz", 0, 0, null));
        u1Stations.add(new Station("Stephansplatz", 0, 0, null));
        u1Stations.add(new Station("Schwedenplatz", 0, 0, null));
        u1Stations.add(new Station("Leopoldau", 0, 0, null));

        // Create the line U1 and add it's stations
        final Line u1 = new Line(1L, "U1", Color.parseColor("#E20A16"), u1Stations);

        // Create lines u2, u3 and u4 without stations
        final Line u2 = new Line(2L, "U2", Color.parseColor("#764785"), null);
        final Line u3 = new Line(3L, "U3", Color.parseColor("#F76013"), null);
        final Line u4 = new Line(4L, "U4", Color.parseColor("#008131"), null);

        // Create a list of stations for line U6
        ArrayList<Station> u6Stations = new ArrayList<>();
        u6Stations.add(new Station("Floridsdorf", 0, 0, null));
        u6Stations.add(new Station("Neue Donau", 0, 0, null));
        u6Stations.add(new Station("Handelskai", 0, 0, null));
        u6Stations.add(new Station("Dresdnerstraße", 0, 0, null));
        u6Stations.add(new Station("Spittelau", 0, 0, Collections.singletonList(u4)));


        // Create line U6 with its stations
        final Line u6 = new Line(5L, "U6", Color.parseColor("#88471F"), u6Stations);

        // Create line 31
        final Line tram31 = new Line(6L, "31", Color.parseColor("#999999"), null);


        // add all lines to the mock database
        lines.add(u1);
        lines.add(u2);
        lines.add(u3);
        lines.add(u4);
        lines.add(u6);
        lines.add(tram31);
    }

    // =============================================================================
    // Public methods
    // =============================================================================

    public List<Line> getLines() {
        return lines;
    }

    public Line getLine(final long lineId) {
        for (Line line : lines) {
            if (line.getId() == lineId) {
                return line;
            }
        }

        return null;
    }
}
