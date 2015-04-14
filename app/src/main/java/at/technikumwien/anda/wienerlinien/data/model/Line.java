package at.technikumwien.anda.wienerlinien.data.model;

import java.util.List;

/**
 * A Wiener Linien line.
 */
public class Line {

    // =============================================================================
    // Private members
    // =============================================================================

    /**
     * The unique identifier of this line.
     */
    private long id;

    /**
     * The name of this line (e.g. "U1").
     */
    private String name;

    /**
     * The background color for this line.
     */
    private int color;

    /**
     * A list of stations that this line consists of.
     */
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
