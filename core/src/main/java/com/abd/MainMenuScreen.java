package com.abd;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;

public class MainMenuScreen implements Screen {
    final MyGame game;
    public MainMenuScreen(MyGame game) {
        this.game = game;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();
        game.font.draw(game.batch, "Main Menu", 100, 100);
        game.batch.end();
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            game.setScreen(new GameScreen(game));
        }

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
        // Dispose of assets here
    }
}
