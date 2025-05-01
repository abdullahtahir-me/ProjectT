package com.abd;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;

import java.util.ArrayList;

public  class CollisionManager {
    public ArrayList<Collidable> collisionObjects = new ArrayList<>();

    public CollisionManager(Collidable player1, Collidable player2) {
        this.collisionObjects.add(player1);
        this.collisionObjects.add(player2);
    }
    public void manageAndUpdateCollisions(){
        ArrayList<Collidable> toRemove = new ArrayList<>();
        for(int i = 0; i < collisionObjects.size();i++){
            Polygon polygon1 = collisionObjects.get(i).getPolygon();
            for (int j = i+1; j<collisionObjects.size();j++){
            Polygon polygon2 = collisionObjects.get(j).getPolygon();
                if(checkCollisionBetweenPolygons(polygon1,polygon2)){

                    collisionObjects.get(i).collisionEffect();
                    collisionObjects.get(j).collisionEffect();
//                    if(collisionObjects.get(i).getCollisionType()==CollisionType.PLAYER){
//                        Player player = (Player)collisionObjects.get(i);
//                        player.isFiring = false;
//                    }else if (collisionObjects.get(j).getCollisionType()==CollisionType.PLAYER){
//                        Player player = (Player)collisionObjects.get(j);
//                        player.isFiring = false;
//                    }
                    if(collisionObjects.get(i).getCollisionType()==CollisionType.PROJECTILE){
                       // collisionObjects.remove(i);
                        toRemove.add(collisionObjects.get(i));
                    } else if (collisionObjects.get(j).getCollisionType()==CollisionType.PROJECTILE) {
                        toRemove.add(collisionObjects.get(j));
                        //collisionObjects.remove(j);
                    }
                    Player player1 = (Player)collisionObjects.get(0);
                    player1.isFiring = false;
                    Player player2 = (Player)collisionObjects.get(1);
                    player2.isFiring = false;
                    System.out.println("Collidining");


                }
            }
        }
        collisionObjects.removeAll(toRemove);
       // toRemove.clear();


    }


    private boolean checkCollisionBetweenPolygons(Polygon polygon1, Polygon polygon2) {
        if (Intersector.overlapConvexPolygons(polygon1, polygon2)){
           return true;

        }
        else return false;
    }
}
