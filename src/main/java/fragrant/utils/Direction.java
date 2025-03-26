package fragrant.utils;

import static java.lang.Math.*;

public class Direction {
    private final double x, y, z;
    private final float yaw;

    public Direction(float yaw, float pitch) {
        this.yaw = yaw;
        this.x = cos(yaw) * cos(pitch);
        this.y = sin(pitch);
        this.z = sin(yaw) * cos(pitch);
    }

    public String getCompassDirection() {
        double angle = toDegrees(yaw);
        angle = (angle + 360) % 360;
        if (angle < 22.5) return "North";
        if (angle < 67.5) return "Northeast";
        if (angle < 112.5) return "East";
        if (angle < 157.5) return "Southeast";
        if (angle < 202.5) return "South";
        if (angle < 247.5) return "Southwest";
        if (angle < 292.5) return "West";
        if (angle < 337.5) return "Northwest";
        return "North";
    }

    @Override
    public String toString() {
        return String.format("%s(%.1f rad) Vector{x=%.2f y=%.2f z=%.2f}",
                getCompassDirection(),
                toDegrees(yaw),
                x, y, z
        );
    }
}