package at.technikumwien.anda.wienerlinien.data.model;

import java.util.List;

public class Station {

    // =============================================================================
    // Private members
    // =============================================================================

    private String name;

    private long latitude;

    private long longitude;

    /**
     * Umstiegsmöglichkeiten
     */
    private List<Line> transferLines;

    // =============================================================================
    // Constructor
    // =============================================================================

    public Station(String name, long latitude, long longitude, List<Line> transferLines) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.transferLines = transferLines;
    }

    // =============================================================================
    // Getter
    // =============================================================================

    public String getName() {
        return name;
    }

    public long getLatitude() {
        return latitude;
    }

    public long getLongitude() {
        return longitude;
    }

    public List<Line> getTransferLines() {
        return transferLines;
    }
}
