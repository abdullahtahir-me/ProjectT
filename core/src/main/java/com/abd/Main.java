package com.abd;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */

public class Main extends ApplicationAdapter {

    ShapeRenderer shapeRenderer;
    Projectile projectile;
    Stage stage;
    Skin skin;
    TextField angleText;
    TextField initialVelocityText;
    TextButton textButton;
    private boolean fire=false;


    @Override
    public void create() {
        shapeRenderer = new ShapeRenderer();
        projectile = new Projectile("Projectile 1",10,45,30,8,13);
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        skin = new Skin(Gdx.files.internal("skin/cleanCrispy/clean-crispy-ui.json"));

        angleText = new TextField("Enter the angle: ",skin);
        angleText.setSize(200, 40);
        angleText.setPosition(Gdx.graphics.getWidth()-250, 400);

        initialVelocityText = new TextField("Enter the Velocity: ",skin);
        initialVelocityText.setSize(200, 40);
        initialVelocityText.setPosition(Gdx.graphics.getWidth()-250, 200);

        textButton = new TextButton("Submit",skin);
        textButton.setSize(200, 40);
        textButton.setPosition(Gdx.graphics.getWidth()-250, 100);


        textButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                int angle = Integer.parseInt(angleText.getText().substring(17));
                int initialVelocity = Integer.parseInt(initialVelocityText.getText().substring(20));
                System.out.println("Angle text: " + angle);
                System.out.println("Velocity text: " + initialVelocity);
                projectile.setAngleRadian(angle);
                projectile.setInitialVelocity(initialVelocity);
                fire=true;
            }
        });

        angleText.addListener(new ClickListener(){
            public void clicked(InputEvent e, float x, float y) {
            projectile.trail=true;
            }
        });
        initialVelocityText.addListener(new ClickListener(){
            public void clicked(InputEvent e, float x, float y) {
            projectile.trail=true;
            }
        });



        stage.addActor(angleText);
        stage.addActor(initialVelocityText);
        stage.addActor(textButton);

    }

    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(1, 1, 1, 1);
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

    @Override
    public void dispose() {
        shapeRenderer.dispose();
        stage.dispose();
        skin.dispose();
    }
}
