package com.abd;

import com.badlogic.gdx.*;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */

public class Main implements Screen {
    final MainGame game;
    public Main(MainGame game) {
        this.game = game;
    }

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
    public Music background;
    public Sound fire;
    @Override
    public void show() {

        terrain = new Terrain(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), Gdx.graphics.getHeight()/1.5f);
        batch = new SpriteBatch();
        image = new Texture("libgdx.png");
        player1 = new Player(Gdx.graphics.getWidth()/16,50*Gdx.graphics.getWidth()/1920,50*Gdx.graphics.getHeight()/1920,1,new Texture("player1.png"), terrain.getHeightMap(),100);
        player2 = new Player((int)(Gdx.graphics.getWidth()/1.2),50*Gdx.graphics.getWidth()/1920,50*Gdx.graphics.getHeight()/1920,1,new Texture("player2.png"), terrain.getHeightMap(),100);
        int randomBackgroundChooser = MathUtils.random(1,60);
        skyBackground = new Texture(String.format("60-Sky-gradiant-pack1/Sky_gradient_%d.png",randomBackgroundChooser));
        shapeRenderer = new ShapeRenderer();
        gui = new GUI(player1,player2);
        gui.setWeaponSelector1(player1.projectiles);
        gui.setWeaponSelector2(player2.projectiles);
        collisionManager = new CollisionManager(player1,player2);
        background = Gdx.audio.newMusic(Gdx.files.internal("background.mp3"));
        background.setLooping(true);
        background.setVolume(0.3f);  // lower volume for ambience
        background.play();
        fire = Gdx.audio.newSound(Gdx.files.internal("fire1.mp3"));


    }

    @Override
    public void render(float delta) {
        try {
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
                    fire.play(0.2f);
                }
                else {

                    collisionManager.collisionObjects.add(player2.projectiles[player2.currentProjectile]);
                    player2.isFiring = true;
                    fire.play(0.2f);
                }
            }

            //chkCollision(player1,player2);

            gui.render();
            if(turn ==0) player1.playerMove();
            else player2.playerMove();
            //This line of codes draw hitbox for the player and prjectiles
            shapeRenderer.begin(ShapeRenderer.ShapeType.Line);shapeRenderer.setColor(1, 1, 1, 1);shapeRenderer.polygon(player1.playerPolygon.getTransformedVertices());shapeRenderer.polygon(player2.playerPolygon.getTransformedVertices());
            shapeRenderer.polygon(player1.projectiles[player1.currentProjectile].projectilePolygon.getTransformedVertices());
            shapeRenderer.polygon(player2.projectiles[player2.currentProjectile].projectilePolygon.getTransformedVertices());
            shapeRenderer.end();
            if(turn ==0) player1.projectiles[player1.currentProjectile].render(shapeRenderer);
            else player2.projectiles[player2.currentProjectile].render(shapeRenderer);
            collisionManager.manageAndUpdateCollisions();
        } catch (Exception e) {
            e.getStackTrace();
        }
    }

    @Override
    public void dispose() {
        batch.dispose();
        image.dispose();
        shapeRenderer.dispose();
        gui.dispose();
        player1.dispose();
        player2.dispose();
        background.dispose();

    }
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


    @Override
    public void hide() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }
}
