package com.abd;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

public class GUI {

    Stage stage;
    Skin skin;
    Label angleText;
    Label initialVelocityText;
    Slider angleSlider;
    Slider initialVelocitySlider;
    TextButton textButton;


    public boolean fire=false;
    Projectile projectile;



    public GUI(Projectile projectile) {
        this.projectile = projectile;
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

    public void trailInitializer(){
        if(angleSlider.isDragging()) projectile.trail=true;
        else projectile.setAngleRadian(angleSlider.getValue());
        if(initialVelocitySlider.isDragging()) projectile.trail=true;
        else projectile.setInitialVelocity(initialVelocitySlider.getValue());
    }

    public void render() {
        stage.act(Gdx.graphics.getDeltaTime());
        trailInitializer();
        stage.draw();
    }

    public void dispose() {
        stage.dispose();
        skin.dispose();
    }
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }
}
