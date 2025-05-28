package com.example.pumpkinquest;

import java.awt.Point;

public class Camera {
    public Point position;
    public int screenWidth, screenHeight;
    int playerWidth, playerHeight;

    public Camera(int screenWidth, int screenHeight, int width , int height) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.position = new Point(0, 0);
        this.playerWidth = width;
        this.playerHeight = height;
    }

    public Point worldToScreen(Point worldPos) {
        int screenX = (screenWidth / 2) + (worldPos.x - position.x);
        int screenY = (screenHeight / 2) + (worldPos.y - position.y);
        return new Point(screenX, screenY);
    }




}
