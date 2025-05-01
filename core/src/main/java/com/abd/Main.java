package com.abd;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */

public class Main extends ApplicationAdapter {

    ShapeRenderer shapeRenderer;
    GUI gui;
    private SpriteBatch batch;
    private Texture image;
    Texture skyBackground;
    Terrain terrain;
    Player player1;
    Player player2;
    CollisionManager collisionManager;
    public  static int turn = 1;

    @Override
    public void create() {

        terrain = new Terrain(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), Gdx.graphics.getHeight()/1.5f);
        batch = new SpriteBatch();
        image = new Texture("libgdx.png");
        player1 = new Player(100,50,50,1,new Texture("player1.png"), terrain.getHeightMap(),100);
        player2 = new Player(1600,50,50,1,new Texture("player2.png"), terrain.getHeightMap(),100);
        int randomBackgroundChooser = MathUtils.random(1,60);
        skyBackground = new Texture(String.format("60-Sky-gradiant-pack1/Sky_gradient_%d.png",randomBackgroundChooser));
        shapeRenderer = new ShapeRenderer();
        gui = new GUI(player1,player2);
        gui.setWeaponSelector1(player1.projectiles);
        gui.setWeaponSelector2(player2.projectiles);
        collisionManager = new CollisionManager(player1,player2);


    }

    @Override
    public void render() {
        toogleFullScreen();

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        player1.fireAndUpdateProjectile(terrain);
        player2.fireAndUpdateProjectile(terrain);
        batch.begin();
        batch.draw(skyBackground, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.draw(terrain.terrainTexture, 0, 0);
        batch.end();

        player1.render(batch);
        player1.projectiles[player1.currentProjectile].drawProjectiles(batch);
        player2.render(batch);
        player2.projectiles[player2.currentProjectile].drawProjectiles(batch);
        if(Gdx.input.isKeyPressed(Input.Keys.SPACE)&&!(player1.isFiring|| player2.isFiring)) {
            if(turn ==0) {
                collisionManager.collisionObjects.add(player1.projectiles[player1.currentProjectile]);
                player1.isFiring = true;
            }
            else {

                collisionManager.collisionObjects.add(player2.projectiles[player2.currentProjectile]);
                player2.isFiring = true;
            }
        }

        //chkCollision(player1,player2);

        gui.render();
        if(turn ==0) player1.playerMove();
        else player2.playerMove();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(1, 1, 1, 1);



        shapeRenderer.end();
        if(turn ==0) player1.projectiles[player1.currentProjectile].render(shapeRenderer);
        else player2.projectiles[player2.currentProjectile].render(shapeRenderer);
        collisionManager.manageAndUpdateCollisions();
    }

    @Override
    public void dispose() {
        batch.dispose();
        image.dispose();
        shapeRenderer.dispose();
        gui.dispose();
        player1.dispose();
        player2.dispose();

    }

//    public void chkCollision(Player player1, Player player2) {
//        if (Intersector.overlapConvexPolygons(player1.playerPolygon, player2.playerPolygon)){
//            System.out.println("collision detected between two points");
//        }
//        if (Intersector.overlapConvexPolygons(player1.playerPolygon, player1.projectiles[player1.currentProjectile].projectilePolygon)){
//            System.out.println("collision detected between player1 and projectiles");
//            //dispose();
////            if(gui.healthBar1.getValue()==100f) gui.healthBar1.setValue(50f);
////            else if(gui.healthBar1.getValue()==50f) dispose();
//            player1.projectiles[player1.currentProjectile].reset();
//            player1.isFiring = false;
//            if(turn ==0) turn=1;
//            else turn=0;
//        }
//        if (Intersector.overlapConvexPolygons(player2.playerPolygon, player2.projectiles[player2.currentProjectile].projectilePolygon)){
//            System.out.println("collision detected between player2 and projectiles");
//            //dispose();
////            if(gui.healthBar2.getValue()==100f) gui.healthBar2.setValue(50f);
////            else if (gui.healthBar2.getValue()==50f) dispose();
//            player2.projectiles[player2.currentProjectile].reset();
//            player2.isFiring = false;
//            if(turn ==0) turn=1;
//            else turn=0;
//        }
//        if (Intersector.overlapConvexPolygons(player1.playerPolygon, player2.projectiles[player2.currentProjectile].projectilePolygon)){
//            System.out.println("collision detected between player1 and projectiles");
//            //dispose();
////            if(gui.healthBar1.getValue()==100f) gui.healthBar1.setValue(50f);
////            else if(gui.healthBar1.getValue()==50f) dispose();
//            player2.projectiles[player2.currentProjectile].reset();
//            player2.isFiring = false;
//            if(turn ==0) turn=1;
//            else turn=0;
//        }
//        if (Intersector.overlapConvexPolygons(player2.playerPolygon, player1.projectiles[player1.currentProjectile].projectilePolygon)){
//            System.out.println("collision detected between player2 and projectiles");
//            //dispose();
////            if(gui.healthBar2.getValue()==100f) gui.healthBar2.setValue(50f);
////            else if (gui.healthBar2.getValue()==50f) dispose();
//            player1.projectiles[player1.currentProjectile].reset();
//            player1.isFiring = false;
//            if(turn ==0) turn=1;
//            else turn=0;
//        }
//    }

    public void toogleFullScreen() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.F11)) {
            if (Gdx.graphics.isFullscreen()) {
                Gdx.graphics.setWindowedMode(1280, 720);
            } else {
                Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
            }
        }
    }
    @Override
    public void resize(int width, int height) {
        // Resize viewport when window is resized
        gui.resize(width, height);  // Automatically handles aspect ratio
    }

}
