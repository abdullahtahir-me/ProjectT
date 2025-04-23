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
    //Projectile projectile;
    GUI gui;

    private SpriteBatch batch;
    private Texture image;

    Texture skyBackground;

    Terrain terrain;
    Player player1;


    @Override
    public void create() {
        terrain = new Terrain(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), Gdx.graphics.getHeight()/1.5f);
        batch = new SpriteBatch();
        image = new Texture("libgdx.png");
        player1 = new Player(500,25,25,1,new Texture("daniel.png"), terrain.getHeightMap());

        int randomBackgroundChooser = MathUtils.random(1,60);
        skyBackground = new Texture(String.format("60-Sky-gradiant-pack1/Sky_gradient_%d.png",randomBackgroundChooser));
        shapeRenderer = new ShapeRenderer();
        //projectile = new Projectile("Projectile 1",10,45,30, (float) player1.getPosX()  , (float) player1.getPosY() + player1.getHeight());
        gui = new GUI(player1,player1);
        gui.setWeaponSelector1(player1.projectiles);
        gui.setWeaponSelector2(player1.projectiles);
    }

    @Override
    public void render() {

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        player1.fireAndUpdateProjectile(terrain);
        batch.begin();
        batch.draw(skyBackground, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.draw(terrain.terrainTexture, 0, 0);
        batch.draw(
            new TextureRegion(player1.getPicture())
            , player1.getPosX(), player1.getPosY(),
            0, 0,
            player1.getWidth(), player1.getHeight()
            , 1, 1,
            player1.angle * MathUtils.radiansToDegrees);
        batch.end();
        if(Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            player1.isFiring = true;
        }

        gui.render();
        player1.playerMove();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(1, 1, 1, 1);
        shapeRenderer.circle(player1.projectiles[player1.currentProjectile].getCurrentPositionX(),player1.projectiles[player1.currentProjectile].getCurrentPositionY(),10);// Draw the projectile
          //  fireProjectile();

        shapeRenderer.end();
        player1.projectiles[player1.currentProjectile].render(shapeRenderer);

    }

//    public void fireProjectile(){
//        if(gui.fire) {//taking the fire bool from the gui class when the user click the launch button
//
//            player1.projectiles[1].update(Gdx.graphics.getDeltaTime()*13);
//            if (player1.projectiles[1].isOutOfBounds(terrain.getHeightMap())) {
//                player1.projectiles[1].reset();
//                gui.fire = false;
//                terrain.initizalizeTerrainPixmap(terrain.getHeightMap()); //Just recreating the terrain based on newly updated hjeight map
//                System.out.println("New projectile launched");
//            }
//            float drawX = player1.projectiles[1].getCurrentPositionX() ;
//            float drawY = player1.projectiles[1].getCurrentPositionY() ;
//            shapeRenderer.circle(drawX, drawY, 10);
//        }
//    }

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
