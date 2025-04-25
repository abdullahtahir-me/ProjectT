package com.abd;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;

public class Projectile {
    private final float G = 9.8f;
    public boolean trail=false;

    private Texture texture;
    //private  float angleInRadian;
    private float currentAngle;
    public void setCurrentAngle(float currentAngle) {
        this.currentAngle = currentAngle;
    }

    public float getCurrentAngle() {
        return currentAngle;
    }

    public String name;
    private float power;

    private  int projectieWidth;

    public int getProjectieWidth() {
        return projectieWidth;
    }

    public void setProjectieWidth(int projectieWidth) {
        this.projectieWidth = projectieWidth;
    }

    public int getProjectieHeight() {
        return projectieHeight;
    }

    public void setProjectieHeight(int projectieHeight) {
        this.projectieHeight = projectieHeight;
    }

    private  int projectieHeight;


    public void setStartX(float startX) {
        this.startX = startX;
    }

    public void setStartY(float startY) {
        this.startY = startY;
    }

    private float initialAngleInRadian;  //used for the sin and cos function during whole trajectory so it should not be changed
    private float initialAngleInDegree;  //used for the sin and cos function during whole trajectory so it should not be changed
    private float initialVelocity;

    public void setInitialVelocity(float initialVelocity) {
        this.initialVelocity = initialVelocity;
    }
    public float getInitialVelocity() {
        return initialVelocity;
    }
    public void setInitialAngleInRadian(float angleInRadian) {
        this.initialAngleInRadian = angleInRadian;
        System.out.println(angleInRadian);
    }
    public float getInitialAngleInRadian() {
        return initialAngleInRadian;
    }
    public void setInitialAngleInDegree(float angleInDegree) {
        this.initialAngleInDegree = angleInDegree;
        //System.out.println("Initial angle in degree in the setter: "+initialAngleInDegree);
        initialAngleInRadian=((float) Math.toRadians(initialAngleInDegree));
        //System.out.println("Initial angle in radian in the setter: "+initialAngleInRadian);
    }
    public float getInitialAngleInDegree() {
        return initialAngleInDegree;
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

    public Projectile(String name, float power, float angleDegree, float initialVelocity, float startX, float startY,int projectileWidth,int projectileHeight,Texture texture) {
        this.name = name;
        this.power = power;
        this.initialAngleInDegree = angleDegree;
        //System.out.println("Initial angle in degree in the constructor: "+initialAngleInDegree);
        this.initialAngleInRadian = (float) Math.toRadians(this.initialAngleInDegree);
        //System.out.println("Initial angle in radian in the constructor: "+initialAngleInRadian);
        this.initialVelocity = initialVelocity;
        this.startX = startX;
        this.startY = startY;
        this.projectieWidth = projectileWidth;
        this.projectieHeight = projectileHeight;
        this.texture = texture;

    }

    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    private float totalTime = 0;
    public void update(float deltaTime) { // When it is called it fires and update projectile
        currentPositionX = startX + (float) (initialVelocity * Math.cos(initialAngleInRadian)*totalTime);//x position at i elapsed time
        currentPositionY = (float) (startY + (float) (initialVelocity * Math.sin(initialAngleInRadian) * totalTime)-(0.5*G*totalTime*totalTime));//y position at i elapsed time
        totalTime += deltaTime;
    }
    public boolean isOutOfBounds(float[] heightMap) {//Return False means that character has not collided yet
        int error = 10;
        if (currentPositionY <0|| currentPositionX <=0 ||currentPositionX >= heightMap.length - 1) {
            return true;

        }
        else if((int)heightMap[(int)currentPositionX]-error>=(int)currentPositionY){
            System.out.println("collision detected at ("+currentPositionX + " , " + currentPositionY+" )");
            destroyTerrain(heightMap);
            return  true;
        }
        System.out.println("current position ("+currentPositionX + " , " + currentPositionY+" )");
        System.out.println("current position at height map ("+heightMap[(int) currentPositionX]  );

        return false;//
    }
    // Code for the bullet trail now
    public float calculateFlightTime() {
        return  (float)(2*initialVelocity*Math.sin(initialAngleInRadian))/G;
    }
    public float calculateX(float time){
        return (startX + (float) (initialVelocity * Math.cos(initialAngleInRadian)*time));
    }
    public float calculateY(float time){
        return (float) (startY + (float) (initialVelocity * Math.sin(initialAngleInRadian) * time)-(0.5*G*time*time));
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
        currentAngleCalculator();
    }
    @Override
    public String toString() {
        return name +" Lvl: " + power;
    }

    public void reset(){
        currentPositionX = 0;
        currentPositionY = 0;
        totalTime = 0;
    }
    public void destroyTerrain(float[] heightMap){
        float destructionDampner = power; //
        for (int i = (int)MathUtils.clamp(currentPositionX - power,1,heightMap.length-1); i < (int)(currentPositionX ); i++) {
            heightMap[i] = heightMap[i] -(power-destructionDampner);
            destructionDampner--;
        }
        destructionDampner = 0;
        for (int i = (int)(currentPositionX); i <(int)MathUtils.clamp(currentPositionX + power,1,heightMap.length-1); i++) {
            heightMap[i] = heightMap[i] -(power-destructionDampner);
            destructionDampner++;
        }
    }

    public float currentAngleCalculator(){
        float initialAngle = getInitialAngleInDegree();
        float totalChangeInAngle = (90-initialAngle)/calculateFlightTime();
         currentAngle = initialAngle+totalChangeInAngle*totalTime;
         if(totalTime>=calculateFlightTime()/2){
             currentAngle = -currentAngle;
         }
         return  currentAngle;

    }
}
