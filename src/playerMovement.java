import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Map;

public class playerMovement {

    JLabel player;
    ArrayList<JLabel> obstacles;
    Map<String, ImageIcon> playerImages;
    int x, y, step, FPS, moveTime, moveDir;
    String direction;
    boolean upPressed, downPressed, leftPressed, rightPressed;

    public playerMovement(JLabel player, ArrayList<JLabel> obstacles, Map<String, ImageIcon> playerImages, int x, int y, int step, int FPS, String direction, boolean upPressed, boolean downPressed, boolean leftPressed, boolean rightPressed) {
        this.player = player;
        this.obstacles = obstacles;
        this.playerImages = playerImages;
        this.x = x;
        this.y = y;
        this.step = step;
        this.FPS = FPS;
        this.direction = direction;
        this.moveTime = 0;
        this.moveDir = 1;
        this.upPressed = upPressed;
        this.downPressed = downPressed;
        this.leftPressed = leftPressed;
        this.rightPressed = rightPressed;
    }

    public void playerPosition() {
        int newX = x, newY = y;

        if (upPressed && leftPressed && !isCollision(x - step, y + step)) {
            newX -= step / Math.sqrt(2);
            newY -= step / Math.sqrt(2);
            direction = "up";
        } else if (upPressed && rightPressed && !isCollision(x + step, y - step)) {
            newX += step / Math.sqrt(2);
            newY -= step / Math.sqrt(2);
            direction = "up";
        } else if (downPressed && leftPressed && !isCollision(x - step, y + step)) {
            newX -= step / Math.sqrt(2);
            newY += step / Math.sqrt(2);
            direction = "down";
        } else if (downPressed && rightPressed && !isCollision(x + step, y + step)) {
            newX += step / Math.sqrt(2);
            newY += step / Math.sqrt(2);
            direction = "down";
        } else if (upPressed && !isCollision(x, y - step)) {
            newY -= step;
            direction = "up";
        } else if (downPressed && !isCollision(x, y + step)) {
            newY += step;
            direction = "down";
        } else if (leftPressed && !isCollision(x - step, y)) {
            newX -= step;
            direction = "left";
        } else if (rightPressed && !isCollision(x + step, y)) {
            newX += step;
            direction = "right";
        }

        if (!isCollision(newX, newY)) {
            x = newX;
            y = newY;
        }

        String imageName;
        if (moveDir == 1) {
            imageName = direction + "Standing";
        } else if (moveDir == 2) {
            imageName = direction + "Right";
        } else if (moveDir == 3) {
            imageName = direction + "Standing";
        } else {
            imageName = direction + "Left";
        }

        player.setIcon(playerImages.get(imageName));
        moveTime++;

        if (upPressed || downPressed || leftPressed || rightPressed) {
            if (moveTime >= (FPS / 5)) {
                moveDir = (moveDir % 4) + 1;
                moveTime = 0;
            }
        } else if (moveTime >= (FPS / 5)) {
            moveDir = 1;
            moveTime = 0;
        }

        player.setLocation(x, y);
        player.repaint();
    }

    private boolean isCollision(int x, int y) {
        Rectangle playerBounds = player.getBounds();
        playerBounds.setLocation(x, y);

        for (JLabel obstacle : obstacles) {
            if (obstacle.getBounds().intersects(playerBounds)) {
                return true;
            }
        }
        return false;
    }

    public void setUpPressed(boolean upPressed) {
        this.upPressed = upPressed;
    }

    public void setDownPressed(boolean downPressed) {
        this.downPressed = downPressed;
    }

    public void setLeftPressed(boolean leftPressed) {
        this.leftPressed = leftPressed;
    }

    public void setRightPressed(boolean rightPressed) {
        this.rightPressed = rightPressed;
    }
}