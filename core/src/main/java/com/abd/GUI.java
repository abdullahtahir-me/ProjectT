package com.abd;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
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
   // Player player1;
    //Player player2;
    Player[] players = new Player[2];
    public GUI(Player player1, Player player2) {
        //this.player1 =player1;
        //this.player2 =player2;
        players[0] = player1;
        players[1] = player2;

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

        textButton = new TextButton("Fire",skin);
        textButton.setSize(200, 40);

        textButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                int angle = 180 - (int) angleSlider.getValue();
                int initialVelocity = (int)initialVelocitySlider.getValue();
                System.out.println("Angle text: " + angle);
                System.out.println("Velocity text: " + initialVelocity);
                players[Main.turn].projectiles[players[Main.turn].currentProjectile].setAngleRadian(angle);
                players[Main.turn].projectiles[players[Main.turn].currentProjectile].setInitialVelocity(initialVelocity);
                players[Main.turn].isFiring=true;
            }
        });

        angleSlider.addListener(new ClickListener(){
            public void clicked(InputEvent e, float x, float y) {
                players[Main.turn].projectiles[players[Main.turn].currentProjectile].trail=true;
            }
        });
        initialVelocitySlider.addListener(new ClickListener(){
            public void clicked(InputEvent e, float x, float y) {
                players[Main.turn].projectiles[players[Main.turn].currentProjectile].trail=true;
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
        table.add(angleText).colspan(2).left().padBottom(15).padLeft(10);
        table.row();
        table.add(angleSlider).colspan(2).left().padBottom(15).padLeft(10);
        table.row();
        table.add(initialVelocityText).colspan(2).left().padBottom(15).padLeft(10);
        table.row();
        table.add(initialVelocitySlider).colspan(2).left().padBottom(15).padLeft(10);
        table.row();
        table.add(textButton).colspan(2).padBottom(20).expandX().left().padLeft(10);
        stage.addActor(table);

    }

    public void trailInitializer(){
        if(angleSlider.isDragging()) players[Main.turn].projectiles[players[Main.turn].currentProjectile].trail=true;
        else players[Main.turn].projectiles[players[Main.turn].currentProjectile].setAngleRadian(180-angleSlider.getValue());
        if(initialVelocitySlider.isDragging()) players[Main.turn].projectiles[players[Main.turn].currentProjectile].trail=true;
        else players[Main.turn].projectiles[players[Main.turn].currentProjectile].setInitialVelocity(initialVelocitySlider.getValue());
        updateAngle();
        updateVelocity();
    }
    public void updateAngle(){
        float angle = players[Main.turn].projectiles[players[Main.turn].currentProjectile].getAngleRadian();
        if(Gdx.input.isKeyPressed(Input.Keys.DOWN)){
            if (angle<=180) {
                players[Main.turn].projectiles[players[Main.turn].currentProjectile].trail = true;
                angle += 1;
                players[Main.turn].projectiles[players[Main.turn].currentProjectile].setAngleRadian(angle);
                angleSlider.setValue(180 - angle);
            }
        }
        if(Gdx.input.isKeyPressed(Input.Keys.UP)){
            if (angle>=0) {
                players[Main.turn].projectiles[players[Main.turn].currentProjectile].trail = true;
                angle -= 1;
                players[Main.turn].projectiles[players[Main.turn].currentProjectile].setAngleRadian(angle);
                angleSlider.setValue(180 - angle);
            }
        }
    }
    public void updateVelocity(){
        float velocity = players[Main.turn].projectiles[players[Main.turn].currentProjectile].getInitialVelocity();
        if(Gdx.input.isKeyPressed(Input.Keys.PAGE_UP)){
            if (velocity<=150) {
                players[Main.turn].projectiles[players[Main.turn].currentProjectile].trail = true;
                velocity += 1;
                players[Main.turn].projectiles[players[Main.turn].currentProjectile].setInitialVelocity(velocity);
                initialVelocitySlider.setValue(velocity);
            }
        }
        if(Gdx.input.isKeyPressed(Input.Keys.PAGE_DOWN)){
            if (velocity>=0) {
                players[Main.turn].projectiles[players[Main.turn].currentProjectile].trail = true;
                velocity -= 1;
                players[Main.turn].projectiles[players[Main.turn].currentProjectile].setInitialVelocity(velocity);
                initialVelocitySlider.setValue(velocity);
            }
        }
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
