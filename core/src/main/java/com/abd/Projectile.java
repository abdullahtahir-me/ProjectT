package com.abd;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Projectile {
    private float G = 9.8f;
    public boolean trail=false;

    private String name;
    private float power;

    public void setStartX(float startX) {
        this.startX = startX;
    }

    public void setStartY(float startY) {
        this.startY = startY;
    }

    private float angleRadian;
    private float initialVelocity;

    public void setInitialVelocity(float initialVelocity) {
        this.initialVelocity = initialVelocity;
    }
    public void setAngleRadian(float angleDegrees) {
        this.angleRadian = (float)Math.toRadians(angleDegrees);
    }

    private float startX;
    private float startY;

    private float currentPositionX;//will use this
    private float currentPositionY;// will use this

    public float getCurrentPositionX() {
        return currentPositionX;
    }
    public float getCurrentPositionY() {
        return currentPositionY;
    }

    public Projectile(String name, float power, float angleDegree, float initialVelocity, float startX, float startY) {
        this.name = name;
        this.power = power;
        this.angleRadian = (float) Math.toRadians(angleDegree);
        this.initialVelocity = initialVelocity;
        this.startX = startX;
        this.startY = startY;
    }

    private float totalTime = 0;
    public void update(float deltaTime) { // When it is callled it fires and update projectile
        currentPositionX = startX + (float) (initialVelocity * Math.cos(angleRadian)*totalTime);//x position at i elapsed time
        currentPositionY = (float) (startY + (float) (initialVelocity * Math.sin(angleRadian) * totalTime)-(0.5*G*totalTime*totalTime));//y position at i elapsed time
        totalTime += deltaTime;
    }
    public boolean isOutOfBounds(float[] heightMap) {//Return False means that character has not collided yet

        if (currentPositionY <0|| currentPositionX <=0 ||currentPositionX >= heightMap.length - 1) {
            return true;
        }
        else if((int)heightMap[(int)currentPositionX]-10>=(int)currentPositionY){
            System.out.println("collision detected at ("+currentPositionX + " , " + currentPositionY+" )");
            return  true;
        }
        System.out.println("current position ("+currentPositionX + " , " + currentPositionY+" )");
        System.out.println("current position at height map ("+heightMap[(int) currentPositionX]  );

        return false;//
    }
// Code for the bullet trail now
    public float calculateFlightTime() {
        return  (float)(2*initialVelocity*Math.sin(angleRadian))/G;
    }
    public float calculateX(float time){
        return (startX + (float) (initialVelocity * Math.cos(angleRadian)*time));
    }
    public float calculateY(float time){
        return (float) (startY + (float) (initialVelocity * Math.sin(angleRadian) * time)-(0.5*G*time*time));
    }

    public void render(ShapeRenderer shapeRenderer) {
        if (trail) {
            float flightTime = calculateFlightTime();

            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(1, 0, 0, 1);
            shapeRenderer.circle(calculateX(flightTime * 0.05f), calculateY(flightTime * 0.05f), 5);
            shapeRenderer.circle(calculateX(flightTime * 0.1f), calculateY(flightTime * 0.1f), 5);
            shapeRenderer.circle(calculateX(flightTime * 0.15f), calculateY(flightTime * 0.15f), 5);
            shapeRenderer.circle(calculateX(flightTime * 0.2f), calculateY(flightTime * 0.2f), 5);
            shapeRenderer.end();
        }
    }


    public void reset(){
        currentPositionX = 0;
        currentPositionY = 0;
        totalTime = 0;
    }
}
