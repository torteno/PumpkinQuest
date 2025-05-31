package com.example.pumpkinquest;

import java.awt.Point;

public class Camera {

    // gets the position of the camera in the world, the screen width and height, and the player width and height

    public Point position;
    public int screenWidth, screenHeight;
    int playerWidth, playerHeight;

    public Camera(int screenWidth, int screenHeight, int width , int height) {

        // Initialize the camera with the screen dimensions and player dimensions
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.position = new Point(0, 0);
        this.playerWidth = width;
        this.playerHeight = height;
    }

    public Point worldToScreen(Point worldPos) {

        // Convert world coordinates to screen coordinates, with the camera position as the origin which is the center of the screen
        // Gets the difference between the world position and the camera position, then adds half the screen width and height to center the camera
        // And returns the screen coordinates as a Point object
        int screenX = (screenWidth / 2) + (worldPos.x - position.x);
        int screenY = (screenHeight / 2) + (worldPos.y - position.y);
        return new Point(screenX, screenY);
    }




}
