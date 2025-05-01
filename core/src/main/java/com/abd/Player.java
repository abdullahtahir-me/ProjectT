package com.abd;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;

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
    int slopePoints = 50;
    public int totalProjectiles = 10;
    Boolean isFiring = false;
    int currentProjectile = 1;

    Polygon playerPolygon;
   // Polygon projectilePolygon;

    public Player(int posX, int width, int height, float speed, Texture picture, float[] heightMap) {
        this.posX = posX;
        this.posY = (int) (heightMap[this.posX]);
        this.width = width;
        this.height = height;
        this.speed = speed;
        this.picture = picture;
        this.heightMap = heightMap;
        projectiles = new Projectile[totalProjectiles];
        initializeProjectiles();
        playerPolygon = new Polygon(new float[]{0, 0, getWidth(), 0, getWidth(), getHeight(), 0, getHeight()});

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

    public void playerMove() { // Moves the player and sets his angle
        if (posX >= heightMap.length - width - 1) {//If the position of player is to the far right wrap him arround
            posX = 1;
        } else if (posX <= 0) {
            posX = heightMap.length - width - 2; // if the position is to the left wrap around the -2 is so it doesnt get stuck in a loop of movingg levft and right
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && posX >= 0 && posX < heightMap.length - width - 1) {
            posX += speed;
            posY = (int) heightMap[posX];
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && posX > 0 && posX < heightMap.length - width - 1) {
            posX -= speed;
            posY = (int) heightMap[posX];
        }
        updateSlope();
    }

    public void updateSlope() {
        if (posX > 0 && posX < heightMap.length - width - 1 - slopePoints) {
            angle = (heightMap[posX + slopePoints] - heightMap[posX]) / (slopePoints);
            angle = MathUtils.atan(angle);
        }
    }

    public void initializeProjectiles() {
        for (int i = 0; i < totalProjectiles; i++) {
            projectiles[i] = new Projectile("Projectile" + i, 50, 45, 30, (float) getPosX(), (float) getPosY() + getHeight(), 30, 25, new Texture("tank_bullet.png"));
            System.out.println(projectiles[i].name);
        }
    }

    public void fireAndUpdateProjectile(Terrain terrain) {
        projectiles[currentProjectile].setStartX(posX + (float) width / 2);
        projectiles[currentProjectile].setStartY(posY + height);
        if (isFiring) {
            projectiles[currentProjectile].update(Gdx.graphics.getDeltaTime() * 13);

            if (projectiles[currentProjectile].isOutOfBounds(heightMap) || projectiles[currentProjectile].hasCollidedWithTerrain(heightMap)) {
                projectiles[currentProjectile].reset();
                isFiring = false;
                if (Main.turn == 0) {
                    Main.turn = 1;
                } else if (Main.turn == 1) {
                    Main.turn = 0;

                }


                terrain.initizalizeTerrainPixmap(terrain.getHeightMap()); //Just recreating the terrain based on newly updated hjeight map
                System.out.println("New projectile launched");
            }
        }
    }

    public void render(SpriteBatch batch) {
        drawPlayerAndPolygon(batch);
        //projectiles[currentProjectile].drawProjectiles(batch);
    }

    public void drawPlayerAndPolygon(SpriteBatch batch) {
        batch.begin();
        batch.draw(
            new TextureRegion(getPicture()),     //Player 1
            getPosX(), getPosY(),
            0, 0,
            getWidth(), getHeight()
            , 1, 1,
            angle * MathUtils.radiansToDegrees
        );

        playerPolygon.setPosition(getPosX(), getPosY());
        playerPolygon.setRotation(angle * MathUtils.radiansToDegrees);
        batch.end();
    }


    public void dispose(){
        projectiles = null;
    }
}
