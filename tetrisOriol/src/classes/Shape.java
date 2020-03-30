/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

/**
 *
 * @author 10188023
 */
public class Shape {

    private static final int[][][] coordsTable = new int[][][]{{{0, 0}, {0, 0}, {0, 0}, {0, 0}},
    {{0, 0}, {0, 0}, {0, 0}, {0, 0}},
    {{0, -1}, {0, 0}, {-1, 0}, {-1, 1}},
    {{0, -1}, {0, 0}, {1, 0}, {1, 1}},
    {{0, -1}, {0, 0}, {0, 1}, {0, 2}},
    {{-1, 0}, {0, 0}, {1, 0}, {0, 1}},
    {{0, 0}, {1, 0}, {0, 1}, {1, 1}},
    {{-1, -1}, {0, -1}, {0, 0}, {0, 1}},
    {{1, -1}, {0, -1}, {0, 0}, {0, 1}}};
    private Tetrominoes pieceShape;
    private int coords[][];
 
    public Shape() {
        coords = new int[4][2];
        setRandomShape();
  
    }

    public void setShape(Tetrominoes shapeType) {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 2; j++) {
                coords[i][j] = coordsTable[shapeType.ordinal()][i][j];
            }
        }
        pieceShape = shapeType;
    }
    

    private void setX(int index, int x) {
        coords[index][0] = x;
    }

    private void setY(int index, int y) {
        coords[index][1] = y;
    }

    public int getX(int index) {
        int x = coords[index][0];
        return x;
    }

    public int getY(int index) {
        int y = coords[index][1];
        return y;
    }

    public Tetrominoes getShape() {
        return pieceShape;
    }
   
    public void setRandomShape() {
        int rand = (int) (Math.random() * 7)+2;
        Tetrominoes[] values = Tetrominoes.values();
        setShape(values[rand]);
    }

    public int minX() {
        int minX = coords[0][0];
        for (int i = 1; i < 4; i++) {
            if (coords[i][0] < minX) {
                minX = coords[i][0];
            }
        }
        return minX;
    }

    public int maxY() {
        int maxY = coords[0][1];
        for (int i = 1; i < 4; i++) {
            if (coords[i][1] > maxY) {
                maxY = coords[i][1];
            }
        }
        return maxY;
    }

    public int minY() {
        int minX = coords[0][1];
        for (int i = 1; i < 4; i++) {
            if (coords[i][1] < minX) {
                minX = coords[i][1];
            }
        }
        return minX;
    }

    public int maxX() {
        int maxX = coords[0][0];
        for (int i = 1; i < 4; i++) {
            if (coords[i][0] > maxX) {
                maxX = coords[i][0];
            }
        }
        return maxX;
    }
    public Shape(Tetrominoes shapeType){
        this();
        setShape(shapeType);
    }
    
    public Shape rotateRight(){
       Shape newShape = new Shape(pieceShape);
        for(int i = 0; i < 4; i++) {
            newShape.setX(i, getY(i));
            newShape.setY(i, -getX(i));
        }
        return newShape;
    }
    public Shape rotateLeft(){
       Shape newShape = new Shape(pieceShape);
        for(int i = 0; i < 4; i++) {
            newShape.setX(i, -getY(i));
            newShape.setY(i, getX(i));
        }
        return newShape;
    }
    

}
