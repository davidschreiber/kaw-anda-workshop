package at.technikumwien.anda.wienerlinien.data.model;

import java.util.List;

/**
 * A Wiener Linien line.
 */
public class Line {

    // =============================================================================
    // Private members
    // =============================================================================

    private String name;

    private int color;

    private List<Station> stations;

    // =============================================================================
    // Constructor
    // =============================================================================

    public Line(String name, int color, List<Station> stations) {
        this.name = name;
        this.color = color;
        this.stations = stations;
    }

    // =============================================================================
    // Getters
    // =============================================================================

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
