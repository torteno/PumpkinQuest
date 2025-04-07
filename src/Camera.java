import java.awt.Point;

public class Camera {
    public Point position;
    public int screenWidth, screenHeight;

    public Camera(int screenWidth, int screenHeight) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.position = new Point(0, 0);
    }

    public Point worldToScreen(Point worldPos) {
        int screenX = (screenWidth / 2) + (worldPos.x - position.x);
        int screenY = (screenHeight / 2) + (worldPos.y - position.y);
        return new Point(screenX, screenY);
    }




}
