package at.technikumwien.anda.wienerlinien.model;

import java.util.List;

/**
 * A Wiener Linien line.
 */
public class Line {

    // =============================================================================
    // Private members
    // =============================================================================

    private long id;

    private String name;

    private int color;

    private List<Station> stations;

    // =============================================================================
    // Constructor
    // =============================================================================

    public Line(long id, String name, int color, List<Station> stations) {
        this.id = id;
        this.name = name;
        this.color = color;
        this.stations = stations;
    }

    // =============================================================================
    // Getters
    // =============================================================================

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getColor() {
        return color;
    }

    public List<Station> getStations() {
        return stations;
    }
}
