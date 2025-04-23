package com.abd;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

public class GUI {

    Stage stage;
    Skin skin;
    Table table;
    Label angleText;
    Label initialVelocityText;
    Slider angleSlider;
    Slider initialVelocitySlider;
    TextButton textButton;
    ProgressBar healthBar1;
    ProgressBar healthBar2;
    SelectBox<Projectile> weaponSelector1;
    SelectBox<Projectile> weaponSelector2;

    public boolean fire=false;
    Projectile projectile;

    public GUI(Projectile projectile) {
        this.projectile = projectile;
        stage = new Stage(new ExtendViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        Gdx.input.setInputProcessor(stage);
        skin = new Skin(Gdx.files.internal("skin/cleanCrispy/clean-crispy-ui.json"));
        table = new Table(skin);
        table.setFillParent(true);

        healthBar1 = new ProgressBar(0, 100, 1, false, skin);
        healthBar2 = new ProgressBar(0, 100, 1, false, skin);

        angleText = new Label("Enter the angle: ",skin);
        angleText.setFontScale(1.5f);
        angleText.setSize(200, 40);

        angleSlider = new Slider(0, 180, 1f, false, skin);
        angleSlider.setSize(200, 40);
        angleSlider.setValue(90);

        initialVelocityText = new Label("Enter the Velocity: ",skin);
        initialVelocityText.setFontScale(1.5f);
        initialVelocityText.setSize(200, 40);

        initialVelocitySlider = new Slider(0, 150, 1f, false, skin);
        initialVelocitySlider.setSize(200, 40);
        initialVelocitySlider.setValue(50);

        weaponSelector1= new SelectBox<>(skin);
        weaponSelector1.setSize(200, 40);
        weaponSelector2 = new SelectBox<>(skin);
        weaponSelector2.setSize(200, 40);

        textButton = new TextButton("Submit",skin);
        textButton.setSize(200, 40);

        textButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                int angle = 180 - (int) angleSlider.getValue();
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


        healthBar1.setValue(20f);
        healthBar2.setValue(40f);
        table.add(healthBar1).expandX().left().top().pad(15);
        table.add(healthBar2).expandX().right().top().pad(15);
        table.row();
        table.add(weaponSelector1).expandY().left().top().pad(15);
        table.add(weaponSelector2).expandY().right().top().pad(15);
        table.row();
        table.add(angleText).colspan(2).center().padBottom(15);
        table.row();
        table.add(angleSlider).colspan(2).center().padBottom(15);
        table.row();
        table.add(initialVelocityText).colspan(2).center().padBottom(15);
        table.row();
        table.add(initialVelocitySlider).colspan(2).center().padBottom(15);
        table.row();
        table.add(textButton).colspan(2).padBottom(20).expandX().center();
        stage.addActor(table);

    }

    public void trailInitializer(){
        if(angleSlider.isDragging()) projectile.trail=true;
        else projectile.setAngleRadian(180-angleSlider.getValue());
        if(initialVelocitySlider.isDragging()) projectile.trail=true;
        else projectile.setInitialVelocity(initialVelocitySlider.getValue());
    }

    public void render() {
        stage.act(Gdx.graphics.getDeltaTime());
        trailInitializer();
        stage.draw();
    }

    public void setWeaponSelector1(Projectile[] weapons) {
        weaponSelector1.setItems(weapons);
    }
    public void setWeaponSelector2(Projectile[] weapons) {
        weaponSelector2.setItems(weapons);
    }

    public void dispose() {
        stage.dispose();
        skin.dispose();
    }
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }
}
