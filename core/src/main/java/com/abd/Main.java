package com.abd;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;

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
    public  static int turn = 1;

    @Override
    public void create() {
        terrain = new Terrain(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), Gdx.graphics.getHeight()/1.5f);
        batch = new SpriteBatch();
        image = new Texture("libgdx.png");
        player1 = new Player(100,50,50,1,new Texture("player1.png"), terrain.getHeightMap());
        player2 = new Player(1600,50,50,1,new Texture("player2.png"), terrain.getHeightMap());
        int randomBackgroundChooser = MathUtils.random(1,60);
        skyBackground = new Texture(String.format("60-Sky-gradiant-pack1/Sky_gradient_%d.png",randomBackgroundChooser));
        shapeRenderer = new ShapeRenderer();
        gui = new GUI(player1,player2);
        gui.setWeaponSelector1(player1.projectiles);
        gui.setWeaponSelector2(player2.projectiles);
    }

    @Override
    public void render() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.F11)) {
            if (Gdx.graphics.isFullscreen()) {
                Gdx.graphics.setWindowedMode(1280, 720);
            } else {
                Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
            }
        }


        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        player1.fireAndUpdateProjectile(terrain);
        player2.fireAndUpdateProjectile(terrain);
        batch.begin();
        batch.draw(skyBackground, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.draw(terrain.terrainTexture, 0, 0);
        batch.draw(
            new TextureRegion(player1.getPicture())     //Player 1
            , player1.getPosX(), player1.getPosY(),
            0, 0,
            player1.getWidth(), player1.getHeight()
            , 1, 1,
            player1.angle * MathUtils.radiansToDegrees);
        batch.draw(
            new TextureRegion(player2.getPicture())
            , player2.getPosX(), player2.getPosY(),
            0, 0,
            player2.getWidth(), player2.getHeight()
            , 1, 1,
            player2.angle * MathUtils.radiansToDegrees);
        if(turn==0){
            batch.draw(
                new TextureRegion(player1.projectiles[player1.currentProjectile].getTexture())     //Player 1
                , player1.projectiles[player1.currentProjectile].getCurrentPositionX(), player1.projectiles[player1.currentProjectile].getCurrentPositionY(),
                player1.projectiles[player1.currentProjectile].getProjectieWidth()/2f, player1.projectiles[player1.currentProjectile].getProjectieHeight()/2f,
                player1.projectiles[player1.currentProjectile].getProjectieWidth(),  player1.projectiles[player1.currentProjectile].getProjectieHeight(),
                1, 1,
               player1.projectiles[player1.currentProjectile].currentAngleCalculator()
            );
        }
        else{
            batch.draw(
                new TextureRegion(player2.projectiles[player1.currentProjectile].getTexture())     //Player 1
                , player2.projectiles[player2.currentProjectile].getCurrentPositionX(), player2.projectiles[player2.currentProjectile].getCurrentPositionY(),
                player2.projectiles[player2.currentProjectile].getProjectieWidth()/2f, player2.projectiles[player2.currentProjectile].getProjectieHeight()/2f,
                player2.projectiles[player2.currentProjectile].getProjectieWidth(),  player2.projectiles[player2.currentProjectile].getProjectieHeight(),
                1, 1,
                player2.projectiles[player2.currentProjectile].currentAngleCalculator()
            );
        }
        batch.end();
        if(Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            if(turn ==0) player1.isFiring = true;
            else player2.isFiring = true;
        }

        gui.render();
        if(turn ==0) player1.playerMove();
        else player2.playerMove();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(1, 1, 1, 1);



//        if(turn ==0) shapeRenderer.rect(player1.projectiles[player1.currentProjectile].getCurrentPositionX()
//            ,player1.projectiles[player1.currentProjectile].getCurrentPositionY()
//            ,player1.projectiles[player1.currentProjectile].getProjectieWidth()
//            ,player1.projectiles[player1.currentProjectile].getProjectieHeight());// Draw the projectile
//        else shapeRenderer.rect(player2.projectiles[player2.currentProjectile].getCurrentPositionX(),player2.projectiles[player2.currentProjectile].getCurrentPositionY(),player2.projectiles[player2.currentProjectile].getProjectieWidth(),player2.projectiles[player2.currentProjectile].getProjectieHeight());// Draw the projectile
        shapeRenderer.end();
        if(turn ==0) player1.projectiles[player1.currentProjectile].render(shapeRenderer);
        else player2.projectiles[player2.currentProjectile].render(shapeRenderer);
    }

    @Override
    public void dispose() {
        batch.dispose();
        image.dispose();
        shapeRenderer.dispose();
        gui.dispose();
        player1.dispose();
    }

    @Override
    public void resize(int width, int height) {
        // Resize viewport when window is resized
        gui.resize(width, height);  // Automatically handles aspect ratio
    }
}
