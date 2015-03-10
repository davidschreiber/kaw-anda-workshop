package at.technikumwien.anda.wienerlinien.model;

import android.graphics.Color;

/**
 * A Wiener Linien line.
 */
public class Line {

    // =============================================================================
    // Private members
    // =============================================================================

    private String name;

    private int color;

    // =============================================================================
    // Constructor
    // =============================================================================

    public Line(String name, int color) {
        this.name = name;
        this.color = color;
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
}
