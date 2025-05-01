package com.abd;

import com.badlogic.gdx.math.Polygon;

public interface Collidable {
    public Polygon getPolygon();
    public void collisionEffect();
    public  CollisionType getCollisionType();



}
