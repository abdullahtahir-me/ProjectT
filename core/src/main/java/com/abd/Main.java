package com.abd;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */

public class Main extends ApplicationAdapter {

    ShapeRenderer shapeRenderer;
    Projectile projectile;
    Stage stage;
    Skin skin;
    Label angleText;
    Label initialVelocityText;
    Slider angleSlider;
    Slider initialVelocitySlider;
    TextButton textButton;
    private boolean fire=false;
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
        System.out.println(player1.getPosX());
        projectile = new Projectile("Projectile 1",10,45,30, (float) player1.getPosX() /10 , (float) player1.getPosY() /10+ player1.getHeight());
        stage = new Stage(new ExtendViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));

        Gdx.input.setInputProcessor(stage);
        skin = new Skin(Gdx.files.internal("skin/cleanCrispy/clean-crispy-ui.json"));

        angleText = new Label("Enter the angle: ",skin);
        angleText.setFontScale(1.5f);
        angleText.setSize(200, 40);
        angleText.setPosition(Gdx.graphics.getWidth()-250, 350);

        angleSlider = new Slider(0, 180, 1f, false, skin);
        angleSlider.setSize(200, 40);
        angleSlider.setPosition(Gdx.graphics.getWidth()-250, 300);

        initialVelocityText = new Label("Enter the Velocity: ",skin);
        initialVelocityText.setFontScale(1.5f);
        initialVelocityText.setSize(200, 40);
        initialVelocityText.setPosition(Gdx.graphics.getWidth()-250, 250);

        initialVelocitySlider = new Slider(0, 90, 1f, false, skin);
        initialVelocitySlider.setSize(200, 40);
        initialVelocitySlider.setPosition(Gdx.graphics.getWidth()-250, 200);

        textButton = new TextButton("Submit",skin);
        textButton.setSize(200, 40);
        textButton.setPosition(Gdx.graphics.getWidth()-250, 100);


        textButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                int angle = (int) angleSlider.getValue();
                int initialVelocity = (int)initialVelocitySlider.getValue();
                System.out.println("Angle text: " + angle);
                System.out.println("Velocity text: " + initialVelocity);
                projectile.setAngleRadian(angle);
                projectile.setInitialVelocity(initialVelocity);
                fire=true;
            }
        });

        angleSlider.addListener(new ClickListener(){
            public void clicked(InputEvent e, float x, float y) {
            projectile.trail=true;
            }
        });
        initialVelocitySlider.addListener(new ClickListener(){
            public void clicked(InputEvent e, float x, float y) {
            projectile.trail=true;
            }
        });



        stage.addActor(angleText);
        stage.addActor(angleSlider);
        stage.addActor(initialVelocityText);
        stage.addActor(initialVelocitySlider);
        stage.addActor(textButton);

    }

    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glClearColor(0, 0, 0, 1);
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

        player1.playerMove(Direction.LEFT);
        projectile.setStartX((float) player1.getPosX() /10);
        projectile.setStartY((float) player1.getPosY() /10);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(1, 1, 1, 1);
        trailGenerator();
        fireProjectile();
        shapeRenderer.end();
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
        projectile.render(shapeRenderer);

    }

    public void fireProjectile(){
        if(fire) {

            projectile.update(Gdx.graphics.getDeltaTime()*3);
            if (projectile.isOutOfBounds(terrain.getHeightMap())) {
                projectile.reset();
                System.out.println("New projectile launched");
            }
            float drawX = projectile.getCurrentPositionX() * 10;
            float drawY = projectile.getCurrentPositionY()  *10;
            shapeRenderer.circle(drawX, drawY, 10);
        }
    }

    public void trailGenerator(){
        if(angleSlider.isDragging()) projectile.trail=true;
        else projectile.setAngleRadian(angleSlider.getValue());
        if(initialVelocitySlider.isDragging()) projectile.trail=true;
        else projectile.setInitialVelocity(initialVelocitySlider.getValue());
    }

    @Override
    public void dispose() {
        batch.dispose();
        image.dispose();
        shapeRenderer.dispose();
        stage.dispose();
        skin.dispose();
    }
}
