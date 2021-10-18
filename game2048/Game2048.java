package com.codegym.games.game2048;

import com.codegym.engine.cell.Color;
import com.codegym.engine.cell.Game;
import com.codegym.engine.cell.Key;

import java.util.HashMap;
import java.util.Map;

public class Game2048 extends Game {

    private static final int SIDE = 4;
    private int [][]gameField = new int[SIDE][SIDE];
    private boolean isGameStopped = false;
    private int score;


    @Override
    public void initialize() {
        setScreenSize(SIDE,SIDE);
        createGame();
        drawScene();
    }
    private void createGame(){
        gameField = new int[SIDE][SIDE];
        createNewNumber();
        createNewNumber();
    }
    private void drawScene(){
        for (int i = 0; i< SIDE;i++){
            for (int j = 0; j< SIDE; j++){
                setCellColoredNumber(j,i,gameField[i][j]);
            }
        }
    }
    private void createNewNumber(){
        boolean created = false;
        int max = 2048;
        do{
            int x = getRandomNumber(SIDE);
            int y = getRandomNumber(SIDE);
            if (gameField[x][y] == 0){
                gameField[x][y] = getRandomNumber(10);
                if (gameField[x][y]<9){
                    gameField[x][y] = 2;
                } else {
                    gameField[x][y] = 4;
                }
                created = true;
            }
            if (max == getMaxTileValue()){
                win();
            }


        } while (!created);
    }
    private Color getColorByValue(int value){
        Color color = Color.NONE;
        Map<Integer, Color> map = new HashMap<>();
        map.put(0,Color.WHITE);
        map.put(2,Color.PLUM);
        map.put(4,Color.SLATEBLUE);
        map.put(8,Color.DODGERBLUE);
        map.put(16,Color.DARKTURQUOISE);
        map.put(32,Color.MEDIUMSEAGREEN);
        map.put(64,Color.LIMEGREEN);
        map.put(128,Color.DARKORANGE);
        map.put(256,Color.SALMON);
        map.put(512,Color.ORANGERED);
        map.put(1024,Color.DEEPPINK);
        map.put(2048,Color.MEDIUMVIOLETRED);

        for (Map.Entry<Integer,Color> colors: map.entrySet()){
            if (value == colors.getKey()){
                color = colors.getValue();
            }
        }
        return color;
    }
    private void setCellColoredNumber(int x, int y, int value){
        String s;
        if (value > 0){
            s = "" + value;
        } else {
            s = "";
        }
        setCellValueEx(x,y,getColorByValue(value),s);
    }
    private boolean compressRow(int [] row) {
        int position = 0;
        boolean changes = false;
        for (int x = 0; x < SIDE; x++) {
            if (row[x] > 0) {
                if (x != position) {
                    row[position] = row[x];
                    row[x] = 0;
                    changes = true;
                }
                position++;
            }
        }
        return changes;       
    }
    private boolean mergeRow(int [] row) {
        boolean changes = false;
        for (int x = 0; x <row.length -1; x++){
                if (row[x] != 0 && row[x] == row[x+1]){
                     row[x] += row[x+1];
                     score += row[x];
                     setScore(score);
                     row[x+1] = 0;
                     changes = true;
                }
        }
        return changes;
    }

    @Override
    public void onKeyPress(Key key) {
        if (isGameStopped){
            if (key == Key.SPACE){
                isGameStopped = false;
                createGame();
                drawScene();
                score = 0;
                setScore(score);
            }
            return;
        }
        if (!canUserMove()){
            gameOver();
            if (key == Key.SPACE){
                isGameStopped = false;
                createGame();
                drawScene();
            }
            return;
        }
        if (key == Key.LEFT){
            moveLeft();
        }  else if (key == Key.RIGHT){
            moveRight();
        } else if (key == Key.UP){
            moveUp();
        } else if (key == Key.DOWN){
            moveDown();
        } else {
            return;
        }
        drawScene();
    }
    private void moveLeft(){
        boolean needNumber = false;
        for (int row [] : gameField){
            boolean wasCompressed = compressRow(row);
            boolean wasMerged = mergeRow(row);
            if (wasMerged){
                compressRow(row);
            }
            if (wasCompressed || wasMerged){
                needNumber = true;
            }
        }
        if (needNumber){
            createNewNumber();
        }
    }
    private void moveRight(){
        rotateClockwise();
        rotateClockwise();
        moveLeft();
        rotateClockwise();
        rotateClockwise();
    }
    private void moveUp(){
        rotateClockwise();
        rotateClockwise();
        rotateClockwise();
        moveLeft();
        rotateClockwise();
    }
    private void moveDown(){
        rotateClockwise();
        moveLeft();
        rotateClockwise();
        rotateClockwise();
        rotateClockwise();
    }
    private void rotateClockwise(){
        int [][] rotatedRow = new int[SIDE][SIDE];

        for (int i = 0; i< SIDE; i++){
            for (int j = 0;j<SIDE; j++){
                rotatedRow[j][SIDE - 1 - i] = gameField[i][j];
            }
        }
        gameField = rotatedRow;
    }
    private int getMaxTileValue(){
        int maxValue = gameField[0][0];
        for (int row []: gameField){
            for (int value: row){
                maxValue = Math.max(maxValue,value);
            }
        }


        return maxValue;
    }
    private void win(){
        isGameStopped = true;
        showMessageDialog(Color.YELLOW, "You WIN",Color.DARKSEAGREEN,100);
    }
    private void gameOver(){
        isGameStopped = true;
        showMessageDialog(Color.YELLOW, "Game Over",Color.DARKRED,100);
    }
    private boolean canUserMove(){
        boolean canMove = false;
        for (int i = 0; i< SIDE; i++){
            for (int j = 0; j< SIDE; j++){
                if (gameField[i][j] == 0){
                    canMove = true;
                } else if (i < SIDE - 1 && gameField[i][j] == gameField[i + 1][j]){
                    canMove = true;
                } else if (j < SIDE - 1 && gameField[i][j] == gameField[i][j+1]){
                    canMove = true;
                }
            }
        }
        return canMove;
    }
}
