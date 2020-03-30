/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 *
 * @author 10188023
 */
public class Board extends JPanel {

    public static final int NUM_ROWS = 22;
    public static final int NUM_COLS = 10;
    public static int INITIAL_DELTA_TIME = 500;
    private Tetrominoes[][] playBoard;
    private Shape currentShape;
    private int currentRow;
    private int currentCol;
    private Timer timer;
    private int deltaTime;
    private MyKeyAdapter keyAdepter;
    private ScoreBoardIncrementer scoreBoard;
    private int scoreFilter;

    public void setScoreboard(ScoreBoardIncrementer scoreboard) {
        this.scoreBoard = scoreboard;

    }

    private Board() {
        super();
        playBoard = new Tetrominoes[NUM_ROWS][NUM_COLS];
        deltaTime = INITIAL_DELTA_TIME;
        keyAdepter = new MyKeyAdapter();

        setFocusable(true);
        createTimmer();
        initGame();

    }

    public Board(ScoreBoardIncrementer scoreBoard) {
        this();
        setScoreboard(scoreBoard);
    }

    private void initGame() {
        scoreFilter=500;
        cleanBoard();
        setScoreboard(scoreBoard);
        newShape();
        timer.start();
        
        addKeyListener(keyAdepter);

    }
  
    private void createTimmer() {
        timer = new Timer(deltaTime, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
             
                try {
                    gameLoop();
                } catch (IOException ex) {
                    Logger.getLogger(Board.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        });
    }
    private void gameOver() throws IOException{
       
       timer.stop();
       closeBoard();
       scoreBoard.checkRecord();
    }
    
    private void gameLoop() throws IOException {
        
      if (colisions(currentRow + 1)) {
            if(!copyCurrentShapeToBoard()){
                gameOver();
            }else{
                checkLines();
                newShape(); 
            }   
        } else {
            currentRow++;
            repaint();
        }
      
      

    }

    private boolean copyCurrentShapeToBoard() {
        int col = 0;
        int row = 0;
        for (int i = 0; i < 4; i++) {
            row = currentRow + currentShape.getY(i);
            col = currentCol + currentShape.getX(i);
            if (row < 0) {
                return false;
            } else {
                playBoard[row][col] = currentShape.getShape();
            }
        }
        return true;
    }
    
    private void checkScore(){
        while(scoreBoard.getScore()>=scoreFilter){
            scoreFilter += 500;
            deltaTime=deltaTime-50;
        
        }
        
    }
 
    public void checkLines() {

        int points = 0;
        for (int row = playBoard.length - 1; row >= 0; row--) {
            int line = 0;
            for (int col = 0; col < playBoard[0].length; col++) {
                if (playBoard[row][col] == Tetrominoes.NoShape) {
                    break;
                }else{
                    line++;
                }      
            }
            if (line == playBoard[0].length) {
                points++;
                deleteLine(row);
                row++;
            }
        }
        if (points != 0) {
            score(points);
            checkScore();
        }

    }

    private void score(int points) {
        int aditionalPoints=0;
        if (points == 2) {
            aditionalPoints = ((100 * points) / 4);
        } else if (points ==3) {
            aditionalPoints = (((100 * points) / 2));
        } else if (points == 4) {
            aditionalPoints=((100 * points));
        } 
         scoreBoard.incrementScore((100 * points)+aditionalPoints);
    }

    public void deleteLine(int deleteRow) {
        for (int row = deleteRow; row >= 0; row--) {
            for (int col = 0; col < playBoard[0].length; col++) {
                if (row == 0) {
                    playBoard[row][col] = Tetrominoes.NoShape;
                } else {
                    playBoard[row][col] = playBoard[row - 1][col];
                }

            }
            repaint();
        }
    }

    private boolean colisions(int newRow) {
        if (newRow + currentShape.maxY() >= NUM_ROWS) {
            return true;
        } else {
            for (int i = 0; i < 4; i++) {
                int row = newRow + currentShape.getY(i);
                int col = currentCol + currentShape.getX(i);
                if (row >= 0) {
                    if (playBoard[row][col] != Tetrominoes.NoShape) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean canMove(Shape shape, int newCol) {

        if (newCol + shape.minX() < 0) {
            return false;
        }

        if (newCol + shape.maxX() > NUM_COLS - 1) {
            return false;
        }
        for (int i = 0; i < 4; i++) {
            int row = currentRow + shape.getY(i);
            int col = newCol + shape.getX(i);
            if (row >= 0) {
                if (playBoard[row][col] != Tetrominoes.NoShape) {
                    return false;
                }
            }
        }
        return true;
    }

    class MyKeyAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {

                case KeyEvent.VK_LEFT:

                    if (canMove(currentShape, currentCol - 1) && timer.isRunning()) {
                            moveLeft();   
                    }
                    break;

                case KeyEvent.VK_RIGHT:
                    if (canMove(currentShape, currentCol + 1) && timer.isRunning()) {
                            moveRight();                 
                    }
                    break;

                case KeyEvent.VK_UP:
                    if (timer.isRunning()) {
                        moveBotside();
                    }
                    break;

                case KeyEvent.VK_DOWN:
                    if (timer.isRunning() && !colisions(currentRow+1)) {
                        moveDown();
                    }
                    break;

                case KeyEvent.VK_P:
                    pause();
                    break;
                case KeyEvent.VK_SPACE:
                    if (timer.isRunning()) {
                        swap();
                    }
                    break;
            }
            repaint();
        }

        private void moveLeft() {
            currentCol--;

        }

        private void moveRight() {
            currentCol++;
        }

        private void moveDown() {
            currentRow++;
        }

        public void pause() {

            if (timer.isRunning()) {
                timer.stop();
            } else {
                timer.start();
            }
        }

        

    }
    
    private void swap() {
            Shape newShape=currentShape.rotateLeft();
            if(canMove(newShape,currentCol) && currentShape.getShape()!=Tetrominoes.SquareShape){
                currentShape= newShape;
            }
            
        }
    
    private void moveBotside(){
        
        while(!colisions(currentRow+1)){
            currentRow++;
        }
        
    }

    private void newShape() {
        currentRow = 0;
        currentCol = NUM_COLS / 2;
        currentShape = new Shape();
    }
    
    private void cleanBoard() {
        for (int row = 0; row < playBoard.length; row++) {
            for (int col = 0; col < playBoard[0].length; col++) {
                playBoard[row][col] = Tetrominoes.NoShape;
            }
        }
    }
    private void closeBoard(){
      
        currentShape=new Shape(Tetrominoes.OutShape);
          timer = new Timer(10, new ActionListener() {       
            int row = 0;
            int col = 0;
            int increment = 1;
    @Override
    public void actionPerformed(ActionEvent e) {
        playBoard[row][col] = Tetrominoes.OutShape;
        col += increment;
        if (col > NUM_COLS - 1 ) {
            row++;
            increment = -1;
            col--;
        } else {
            if (col < 0) {
                row++;
                increment = 1;
                col = 0;
            }
        }
        if (row == NUM_ROWS ) {
            System.out.println("Stop");
            timer.stop();
            
        }
        repaint();
        }
    });
        
        timer.start();
}
    private int squareWidth() {
        return getWidth() / NUM_COLS;
    }

    private int squareHeight() {
        return getHeight() / NUM_ROWS;
    }

    private void paintShape(Graphics2D g2d) {
        for (int i = 0; i < 4; i++) {
            int x = currentCol + currentShape.getX(i);
            int y = currentRow + currentShape.getY(i);
            drawSquare(g2d, y, x, currentShape.getShape());
        }
    }

    private void paintPlayBoard(Graphics2D g2d) {
        for (int row = 0; row < playBoard.length; row++) {
            for (int col = 0; col < playBoard[0].length; col++) {
                drawSquare(g2d, row, col, playBoard[row][col]);
            }
        }
    }
    private void paintPlayBoardLoose(Graphics2D g2d) {
        for (int row = 0; row < playBoard.length; row++) {
            for (int col = 0; col < playBoard[0].length; col++) {
                drawSquare(g2d, row, col, Tetrominoes.NoShape);
                repaint();
            }
        }
    }

    private void drawSquare(Graphics2D g, int row, int col, Tetrominoes shape) {
        Color colors[] = {new Color(30, 30, 30),new Color(128, 128, 128), new Color(204, 102, 102),
            new Color(102, 204, 102), new Color(102, 102, 204),
            new Color(204, 204, 102), new Color(204, 102, 204),
            new Color(102, 204, 204), new Color(218, 170, 0)};
        int x = col * squareWidth();
        int y = row * squareHeight();
        Color color = colors[shape.ordinal()];
        g.setColor(color);
        g.fillRect(x + 1, y + 1, squareWidth() - 2, squareHeight() - 2);
        g.setColor(color.brighter());
        g.drawLine(x, y + squareHeight() - 1, x, y);
        g.drawLine(x, y, x + squareWidth() - 1, y);
        g.setColor(color.darker());
        g.drawLine(x + 1, y + squareHeight() - 1, x + squareWidth() - 1, y + squareHeight() - 1);
        g.drawLine(x + squareWidth() - 1, y + squareHeight() - 1, x + squareWidth() - 1, y + 1);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        paintPlayBoard(g2d);
        paintShape(g2d);
    }
   
}
