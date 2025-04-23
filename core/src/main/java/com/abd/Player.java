package com.abd;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;

public class Player {

    Projectile[] projectiles;

    private int posX;
    private int posY;
    private int width;
    private int height;
    private float speed;

    Texture picture;
    float[] heightMap;
    float angle;
    int slopePoints= 25;
    public int totalProjectiles=10;

    public Player(int posX, int width, int height,float speed, Texture picture,float[] heightMap) {
        this.posX = posX;
        this.posY = (int) (heightMap[this.posX]);
        this.width = width;
        this.height = height;
        this.speed = speed;
        this.picture = picture;
        this.heightMap = heightMap;
        projectiles = new Projectile[totalProjectiles];
        initializeProjectiles();

    }
    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public float getAngle() {
        return angle;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Texture getPicture() {
        return picture;
    }

    public void setPicture(Texture picture) {
        this.picture = picture;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public float[] getHeightMap() {
        return heightMap;
    }

    public void setHeightMap(float[] heightMap) {
        this.heightMap = heightMap;
    }

 public void playerMove(){ // Moves the player and sets his angle
        if(posX>=heightMap.length-width-1){//If the position of player is to the far right wrap him arround
            posX = 1;
        }
        else if(posX<= 0){
            posX = heightMap.length - width -2; // if the position is to the left wrap around the -2 is so it doesnt get stuck in a loop of movingg levft and right
        }

        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT) &&posX>=0&&posX < heightMap.length - width-1 ) {
            posX+=speed;
            posY = (int) heightMap[posX];
        }
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT) &&posX>0&&posX < heightMap.length - width-1 ){
            posX-=speed;
            posY = (int)heightMap[posX];
        }
            updateSlope();

 }
 public void updateSlope(){

        if(posX>0&&posX < heightMap.length - width-1 - slopePoints ){
            angle = (heightMap[posX+slopePoints]-heightMap[posX])/(slopePoints);
            angle = MathUtils.atan(angle);
        }


 }


 public void initializeProjectiles(){
        for(int i = 0; i < totalProjectiles; i++){
            projectiles[i] = new Projectile("Projectile"+i,30,45,30, (float) getPosX()  , (float) getPosY() + getHeight());
            System.out.println(projectiles[i].name);
        }
 }
 public void dispose(){
        projectiles = null;
 }
}
