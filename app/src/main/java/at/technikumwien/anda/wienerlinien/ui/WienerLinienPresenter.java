package at.technikumwien.anda.wienerlinien.ui;

import android.graphics.Color;

import at.technikumwien.anda.wienerlinien.data.WienerLinienData;
import at.technikumwien.anda.wienerlinien.data.model.Line;

/**
 *
 */
public class WienerLinienPresenter {

    // =============================================================================
    // Static helper methods
    // =============================================================================

    /**
     * Returns the color for the given <code>line</code> which be used for UI.
     */
    public static int getBackgroundColorForLine(Line line) {
        // Try to get a specific color from the line ID.
        Integer color = getBackgroundColorFromLineId(line.getId());

        // If no specific color was defined use a color based on the type.
        if (color == null) {
            switch (line.getType()) {
                case ptTram:
                    color = Color.parseColor("#EC191F");
                    break;
                case ptBusNight:
                    color = Color.parseColor("#012455");
                    break;
                case ptBusCity:
                case ptMetro:
                    color = Color.parseColor("#868484");
                    break;
                case ptTrainS:
                    color = Color.parseColor("#271D87");
                    break;
                case ptTramWLB:
                case ptTramVRT:
                    color = Color.parseColor("#002E57");
                    break;
                default:
                    // this should never happen
                    color = Color.BLACK;
            }
        }

        return color;
    }

    private static Integer getBackgroundColorFromLineId(long lineId) {
        if (lineId == WienerLinienData.ID_U1) return Color.parseColor("#EC191F");
        else if (lineId == WienerLinienData.ID_U2) return Color.parseColor("#965AA0");
        else if (lineId == WienerLinienData.ID_U3) return Color.parseColor("#F4761D");
        else if (lineId == WienerLinienData.ID_U4) return Color.parseColor("#009B45");
        else if (lineId == WienerLinienData.ID_U6) return Color.parseColor("#8D5F32");
        else return null;
    }
}
