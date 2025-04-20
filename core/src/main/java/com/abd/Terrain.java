package com.abd;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;

public class Terrain {
    private Pixmap terrainPixelMap;
    private int pixmapWidth;
    private int PixmapHeight;
    private float[] heightMap;
    TextureRegion terrainTexture;

    public Pixmap getTerrainPixelMap() {
        return terrainPixelMap;
    }

    public float getPixmapWidth() {
        return pixmapWidth;
    }

    public float getPixmapHeight() {
        return PixmapHeight;
    }

    public TextureRegion getTerrainTexture() {
        return terrainTexture;
    }

    public float[] getHeightMap() {
        return heightMap;
    }
    public float heightAtPosX(int index){
        return heightMap[index];

    }






    public Terrain(int pixmapWidth, int PixmapHeight,float upperHeight ){
        this.pixmapWidth = pixmapWidth;
        this.PixmapHeight = PixmapHeight;

        //float upperHeight =  (screenHeight/1.5f);// upper height for the generated terrain
        //int pixelMapWidth = screenWidth;// width of the generalted pixel map must be power of 2 +!
        heightMap = new float[(int)this.pixmapWidth]; // Will store the height over the entire width
        heightMap[0] = MathUtils.random(upperHeight);// setting initial condion for recursion
        heightMap[heightMap.length-1] = MathUtils.random(upperHeight);
        generateHeightMap(heightMap,0,heightMap.length-1,MathUtils.random(upperHeight),0.49f);//Creates a heightMap
        for(int i = 0; i < heightMap.length;i++){ // fixing the damn heightmap going below ground
            if(heightMap[i]<0){
                heightMap[i] = 0;
            }
        }
        initizalizeTerrainPixmap(heightMap);//Creates a terrain based on the height map




        terrainTexture = new TextureRegion(new Texture(terrainPixelMap));

        terrainTexture.flip(false,true);
    }

    private  void generateHeightMap(float[] heightMap, int left, int right, float randomDisplacement, float roughness){
        if(right-left<=1){
            return;
        }
        int middle = (left+right)/2;//Determine the x coordinate to place the height in
        heightMap[middle] = (heightMap[left]+heightMap[right])/2 + MathUtils.random(-randomDisplacement,randomDisplacement);//Creates a random height
        randomDisplacement*=roughness;
        generateHeightMap(heightMap,left,middle, randomDisplacement,roughness);//recursively generate height with points
        generateHeightMap(heightMap,middle,right, randomDisplacement,roughness);// The order is extremely important due to the break condiotion
    }
    private void initizalizeTerrainPixmap(float[] heightMap){
        terrainPixelMap = new Pixmap( pixmapWidth,  PixmapHeight, Pixmap.Format.RGBA8888);//THe A allows the storage of an alpha channel
        terrainPixelMap.setColor(0f, 0f, 255f, 0f);//Making the pixel map be first transperent
        terrainPixelMap.fill();
        terrainPixelMap.setColor(Color.BLACK);
        for(int i = 0 ; i < pixmapWidth ;i++){
            int clampedHeight = (int)MathUtils.clamp(heightMap[i], 0, PixmapHeight - 1);// maybe add math.ceil//I have changed the max height from screen height -1 to pixmap height -1 chamge in case of errors
            for (int j = 0; j < clampedHeight;j++){
                terrainPixelMap.drawPixel(i,j);
            }
        }

    }
}
