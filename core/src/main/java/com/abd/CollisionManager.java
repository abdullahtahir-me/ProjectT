package com.abd;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
//THis class does collision between objects implementing collidable
import java.util.ArrayList;
//To do add the collision between terrain and projectile to the collision manager
public  class CollisionManager {
    //A list of collidable objects, each frame collision detection is done between items in this list
    public ArrayList<Collidable> collisionObjects = new ArrayList<>();
//Initially adds both players 1 and 2 to the list of collidables
    public CollisionManager(Collidable player1, Collidable player2) {
        this.collisionObjects.add(player1);
        this.collisionObjects.add(player2);
    }
    public void manageAndUpdateCollisions(){
        //Create a list of objects to remove after collision
        ArrayList<Collidable> toRemove = new ArrayList<>();
        //Iterate over the whole list and check for collision between objects
        for(int i = 0; i < collisionObjects.size();i++){
            Polygon polygon1 = collisionObjects.get(i).getPolygon();
            for (int j = i+1; j<collisionObjects.size();j++){
            Polygon polygon2 = collisionObjects.get(j).getPolygon();
                if(checkCollisionBetweenPolygons(polygon1,polygon2)){

                    collisionObjects.get(i).collisionEffect();
                    collisionObjects.get(j).collisionEffect();





                    //When the projectile collides with something i add it to an array list to be cleaned up later this to remove arraylist will then remove the projectile after the end
                    if(collisionObjects.get(i).getCollisionType()==CollisionType.PROJECTILE){
                        toRemove.add(collisionObjects.get(i));
                    } else if (collisionObjects.get(j).getCollisionType()==CollisionType.PROJECTILE) {
                        toRemove.add(collisionObjects.get(j));

                    }



                    //AFter each collision both of the players will stop firingso need to add it
                    //Setting both the player firing to false
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
