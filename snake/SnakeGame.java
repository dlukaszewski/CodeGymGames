package com.codegym.games.snake;


import com.codegym.engine.cell.*;

public class SnakeGame extends Game {

    public static final int WIDTH = 15;
    public static final int HEIGHT = 15;
    private static final int GOAL = 28;
    private Snake snake;
    private Apple apple;
    private int turnDelay;
    private boolean isGameStopped;
    private int score;

    @Override
    public void initialize() {
        setScreenSize(WIDTH, HEIGHT);
        createGame();
    }




    private void createGame(){
        turnDelay = 300;
        score = 0;
        setTurnTimer(turnDelay);
        snake = new Snake(WIDTH/2,HEIGHT/2);
        createNewApple();
        isGameStopped = false;
        setScore(score);
        drawScene();
    }
    private void drawScene(){
        for (int x = 0; x < WIDTH; x++){
            for (int y = 0; y < HEIGHT; y++){
                setCellValueEx(x,y, Color.DARKSEAGREEN,"");
            }
        }
        snake.draw(this);
        apple.draw(this);
    }

    @Override
    public void onTurn(int step) {
        snake.move(apple);
        if (!snake.isAlive){
            gameOver();
        }
        if (snake.getLength() >GOAL ){
            win();
        }
        if (!apple.isAlive){
            score += 5;
            setScore(score);
            turnDelay -= 5;
            setTurnTimer(turnDelay);
            createNewApple();
        }
        drawScene();
    }

    @Override
    public void onKeyPress(Key key) {
        if (key == Key.SPACE && isGameStopped){
            createGame();
        }
        if (key == Key.LEFT){
            snake.setDirection(Direction.LEFT);
        } else if (key == Key.DOWN){
            snake.setDirection(Direction.DOWN);
        } else if (key == Key.UP){
            snake.setDirection(Direction.UP);
        } else if (key == Key.RIGHT ){
            snake.setDirection(Direction.RIGHT);
        } 
    }
    private void createNewApple(){
        Apple newApple;
        do {
            int x = getRandomNumber(WIDTH);
            int y = getRandomNumber(HEIGHT);
            newApple = new Apple(x,y);
        }
        while (this.snake.checkCollision(newApple));
            this.apple = newApple;
    }
    private void gameOver(){
        stopTurnTimer();
        isGameStopped = true;
        showMessageDialog(Color.YELLOW,"GAME OVER",Color.RED,50);
    }
    private void win(){
        stopTurnTimer();
        isGameStopped = true;
        showMessageDialog(Color.YELLOW, "YOU WIN",Color.RED,10);
    }
}
