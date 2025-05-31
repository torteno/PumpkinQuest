package com.example.pumpkinquest;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Map;

public class playerMovement {

    // declares the variables for direction, player and directions, position, and points
    String savedDirection;
    JLabel player;
    ArrayList<JLabel> obstacles;
    Map<String, ImageIcon> playerImages;
    int x, y, step, FPS, moveTime, moveDir;
    String direction;
    boolean upPressed, downPressed, leftPressed, rightPressed;
    Point playerWorldPos;



    public playerMovement(JLabel player, ArrayList<JLabel> obstacles, Map<String, ImageIcon> playerImages, int x, int y, int step, int FPS, String direction, boolean upPressed, boolean downPressed, boolean leftPressed, boolean rightPressed, Point playerWorldPos) {
        //sets each variable to the data given from the frame class

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
        this.playerWorldPos = playerWorldPos;



    }

    public void playerPosition() {
        int newX = x, newY = y;
        int newWorldX = playerWorldPos.x;
        int newWorldY = playerWorldPos.y;


        // if statements for each direction, every 45 degrees, and detects for if there is a collsion and sets the new X and NewY variables dependant on
        // which buttons you are pressing

        if (upPressed && leftPressed && !isCollision(x - step, y - step)) {
            newX -= step / Math.sqrt(2);
            newY -= step / Math.sqrt(2);
            playerWorldPos.x -= step / Math.sqrt(2);
            playerWorldPos.y -= step / Math.sqrt(2);
            direction = "up";
        } else if (upPressed && rightPressed && !isCollision(x + step, y - step)) {
            newX += step / Math.sqrt(2);
            newY -= step / Math.sqrt(2);
            playerWorldPos.x += step / Math.sqrt(2);
            playerWorldPos.y -= step / Math.sqrt(2);
            direction = "up";
        } else if (downPressed && leftPressed && !isCollision(x - step, y + step)) {
            newX -= step / Math.sqrt(2);
            newY += step / Math.sqrt(2);
            playerWorldPos.x -= step / Math.sqrt(2);
            playerWorldPos.y += step / Math.sqrt(2);
            direction = "down";
        } else if (downPressed && rightPressed && !isCollision(x + step, y + step)) {
            newX += step / Math.sqrt(2);
            newY += step / Math.sqrt(2);
            playerWorldPos.x += step / Math.sqrt(2);
            playerWorldPos.y += step / Math.sqrt(2);
            direction = "down";
        } else if (upPressed && !isCollision(x, y - step)) {
            newY -= step;
            playerWorldPos.y -= step;
            direction = "up";
        } else if (downPressed && !isCollision(x, y + step)) {
            newY += step;
            playerWorldPos.y += step;
            direction = "down";
        } else if (leftPressed && !isCollision(x - step, y)) {
            newX -= step;
            playerWorldPos.x -= step;
            direction = "left";
        } else if (rightPressed && !isCollision(x + step, y)) {
            newX += step;
            playerWorldPos.x += step;
            direction = "right";
        }



        // if there isnt a collision

        if (!isCollision(newX, newY)) {


            //sets the player world positon to playerWorldPos.x, playerWorldPos.y

            playerWorldPos.setLocation(playerWorldPos.x, playerWorldPos.y);

            //moves dependant on where the player is facing

        } else {
            if (upPressed) {
                playerWorldPos.y += step;
            } else if (downPressed) {
                playerWorldPos.y -= step;
            }
            if (leftPressed) {
                playerWorldPos.x += step;
            } else if (rightPressed) {
                playerWorldPos.x -= step;
            }
        }
// Changes which player image is displayed, depending on which “frame” the character is in. Works in all directions as it gets the current direction, and then adds the specific frame.
        String imageName;
        if (moveDir == 1) {
            imageName = direction + "Standing";
        } else if (moveDir == 2) {
            imageName = direction + "Fore";
        } else if (moveDir == 3) {
            imageName = direction + "Standing";
        } else {
            imageName = direction + "Back";
        }
// Sets the icon of the player to the current frame
        player.setIcon(playerImages.get(imageName));
        moveTime++;

// Cycles between frames (changes counter by one every frame, and when it gets to 1/5 seconds, it goes back to one
        if (upPressed || downPressed || leftPressed || rightPressed) {
            if (moveTime >= (FPS / 5)) {
                moveDir = (moveDir % 4) + 1;
                moveTime = 0;
            }
// Sets the character back to standing so they don’t just freeze half way through a step
        } else if (moveTime >= (FPS / 5)) {
            moveDir = 1;
            moveTime = 0;
        }


        player.repaint(); // repaints the player
    }

    //checks if the player will overlap with one of the Jlabels that within the array for the obstacle and iterates through to check

    private boolean isCollision(int x, int y) {
        Rectangle playerBounds = new Rectangle(x, y, player.getWidth(), player.getHeight());

        for (JLabel obstacle : obstacles) {
            if (obstacle.getBounds().intersects(playerBounds)) {
                return true;
            }
        }
        return false;
    }



    // setsUp Pressed to the value of upPressed from the frame class

    public void setUpPressed(boolean upPressed) {

        this.upPressed = upPressed;
    }

    // setsDown Pressed to the value of DownPressed from the frame class

    public void setDownPressed(boolean downPressed) {

        this.downPressed = downPressed;
    }

    // setsLeft Pressed to the value of LeftPressed from the frame class

    public void setLeftPressed(boolean leftPressed) {

        this.leftPressed = leftPressed;
    }

    // setsRIght Pressed to the value of rightPressed from the frame class

    public void setRightPressed(boolean rightPressed) {

        this.rightPressed = rightPressed;
    }
}