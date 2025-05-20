package com.abd;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

public class MainMenuScreen implements Screen {
    final MainGame game;
    public MainMenuScreen(MainGame game) {
        this.game = game;
    }

    Texture skyBackground;
    Stage stage;
    Skin skin;
    Label projectLabel;
    Label nameLabel1;
    Label nameLabel2;
    Label playLabel;

    public int randomBackgroundChooser;
    @Override
    public void show() {
        randomBackgroundChooser = MathUtils.random(1,60);
        skyBackground = new Texture(String.format("60-Sky-gradiant-pack1/Sky_gradient_%d.png",randomBackgroundChooser));
        stage = new Stage(new ExtendViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        Gdx.input.setInputProcessor(stage);
        skin = new Skin(Gdx.files.internal("skin/cleanCrispy/clean-crispy-ui.json"));
        projectLabel = new Label("ProjectT",skin);
        projectLabel.setFontScale(4f);
        projectLabel.setSize(250, 60);
        projectLabel.setPosition(Gdx.graphics.getWidth()/2-projectLabel.getWidth()/2, Gdx.graphics.getHeight()/2+100);
        playLabel = new Label("Press Space to play",skin);
        playLabel.setFontScale(3f);
        playLabel.setSize(400, 60);
        playLabel.setPosition(Gdx.graphics.getWidth()/2-playLabel.getWidth()/2, Gdx.graphics.getHeight()/2);
        nameLabel1 = new Label("Abdul Rehman",skin);
        nameLabel1.setFontScale(2f);
        nameLabel1.setSize(170, 60);
        nameLabel1.setPosition(Gdx.graphics.getWidth()/2-nameLabel1.getWidth()/2, Gdx.graphics.getHeight()/2-75);
        nameLabel2 = new Label("Abdullah",skin);
        nameLabel2.setFontScale(2f);
        nameLabel2.setSize(110, 60);
        nameLabel2.setPosition(Gdx.graphics.getWidth()/2-nameLabel2.getWidth()/2, Gdx.graphics.getHeight()/2-120);

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();
        game.batch.draw(skyBackground, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        game.batch.end();

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            game.setScreen(new Main(game,randomBackgroundChooser));
        }

        stage.addActor(projectLabel);
        stage.addActor(nameLabel1);
        stage.addActor(nameLabel2);
        stage.addActor(playLabel);
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        // Called when screen is no longer visible
    }

    @Override
    public void dispose() {
        skyBackground.dispose();
        stage.dispose();
        skin.dispose();
    }
}
