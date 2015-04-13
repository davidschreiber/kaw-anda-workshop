package at.technikumwien.anda.wienerlinien.data.model;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.List;
import java.util.Map;


/**
 * A Wiener Linien line.
 */
public class Line {

    // =============================================================================
    // Private members
    // =============================================================================

    private long id;

    private String name;

    private int sort;

    private int realtime;

    private Type type;

    private String stand;

    private List<Station> stations;

    // =============================================================================
    // Constructor
    // =============================================================================

    public Line(long id, String name, int sort, int realtime, Type type, String stand) {
        this.id = id;
        this.name = name;
        this.sort = sort;
        this.realtime = realtime;
        this.type = type;
        this.stand = stand;
    }

    public Line(long id, String name, List<Station> stations) {
        this.id = id;
        this.name = name;
        this.stations = stations;
    }

    // =============================================================================
    // Getters
    // =============================================================================

    public String getName() {
        return name;
    }

    public List<Station> getStations() {
        return stations;
    }

    public long getId() {
        return id;
    }

    public int getSort() {
        return sort;
    }

    public int getRealtime() {
        return realtime;
    }

    public Type getType() {
        return type;
    }

    public String getStand() {
        return stand;
    }

    // =============================================================================
    // Static helper methods
    // =============================================================================

    @JsonCreator
    public static Line factory(Map<String, String> jsonValues) {
        String[] values = jsonValues.values().toArray(new String[jsonValues.values().size()]);
        return new Line(Long.parseLong(values[0]), values[1], Integer.parseInt(values[2]), Integer.parseInt(values[3]), Type.valueOf(values[4]), values[5]);
    }

    // =============================================================================
    // Public classes
    // =============================================================================

    public static enum Type {
        ptTram, ptBusNight, ptBusCity, ptMetro, ptTrainS, ptTramWLB, ptTramVRT
    }
}
