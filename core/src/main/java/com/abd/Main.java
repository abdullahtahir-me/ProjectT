package com.abd;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {
    ShapeRenderer shapeRenderer;
    Projectile projectile;
    @Override
    public void create() {
        shapeRenderer = new ShapeRenderer();
        projectile = new Projectile("Projectile 1",10,45,30,8,13);
    }

    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(1, 1, 1, 1);
        projectile.update(Gdx.graphics.getDeltaTime());
        if(projectile.isOutOfBounds()) projectile.reset();
        float drawX = projectile.getPositionX()*10;
        float drawY = projectile.getPositionY()*10;
        shapeRenderer.circle(drawX,drawY,10);
        shapeRenderer.end();
        projectile.render(shapeRenderer);

    }

    @Override
    public void dispose() {
        shapeRenderer.dispose();
    }
}
