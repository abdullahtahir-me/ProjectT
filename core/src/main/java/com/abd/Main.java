package com.abd;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;

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


    @Override
    public void create() {
        shapeRenderer = new ShapeRenderer();
        projectile = new Projectile("Projectile 1",10,45,30,8,13);
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        skin = new Skin(Gdx.files.internal("skin/cleanCrispy/clean-crispy-ui.json"));

        angleText = new Label("Enter the angle: ",skin);
        angleText.setFontScale(1.5f);
        angleText.setSize(200, 40);
        angleText.setPosition(Gdx.graphics.getWidth()-250, 350);

        angleSlider = new Slider(0, 90, 1f, false, skin);
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
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(1, 1, 1, 1);
        trailGenerator();
        fire();
        shapeRenderer.end();
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
        projectile.render(shapeRenderer);

    }

    public void fire(){
        if(fire) {

            projectile.update(Gdx.graphics.getDeltaTime());
            if (projectile.isOutOfBounds()) projectile.reset();
            float drawX = projectile.getPositionX() * 10;
            float drawY = projectile.getPositionY() * 10;
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
        shapeRenderer.dispose();
        stage.dispose();
        skin.dispose();
    }
}
