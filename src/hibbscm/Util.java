package hibbscm;

import java.awt.*;

public class Util {

    public static void doInRadius(int radius, RadiusAction action) {
        doInRadius(radius, 0, 0, action);
    }

    public static void doInRadius(int radius, Coordinate center, RadiusAction action) {
        doInRadius(radius, center.x, center.y, action);
    }

    public static void doInRadius(int radius, int centerX, int centerY, RadiusAction action) {
        for(int y = centerY - radius; y <= centerY + radius; y++) {
            for(int x = centerX - radius; x <= centerX + radius; x++) {
                action.executeOn(x, y);
            }
        }
    }

    public static Color fade(Color c) {
        int saturation = 64;
        int boldness = 255 - saturation;
        int r = Math.min(c.getRed() + boldness, 255);
        int g = Math.min(c.getGreen() + boldness, 255);
        int b = Math.min(c.getBlue() + boldness, 255);
        return new Color(r, g, b);
    }

    public interface RadiusAction {
        void executeOn(int x, int y);
    }
}
