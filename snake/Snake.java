package com.codegym.games.snake;

import com.codegym.engine.cell.*;

import java.util.ArrayList;
import java.util.List;

public class Snake {

    private static final String HEAD_SIGN = "\uD83D\uDC7E";
    private static final String BODY_SIGN = "\u26AB";
    private List<GameObject> snakeParts = new ArrayList<>();
    public boolean isAlive = true;
    private Direction direction = Direction.LEFT;


    public Snake(int x, int y) {
        snakeParts.add(new GameObject(x,y));
        snakeParts.add(new GameObject(x+1,y));
        snakeParts.add(new GameObject(x+2,y));
    }

    public void draw(Game game){
        Color color;
        if (isAlive){
            color = Color.BLACK;
        } else {
            color = Color.RED;
        }
        for (int i = 0; i < snakeParts.size(); i++){
        GameObject part = snakeParts.get(i);
        String sign;
        if (i != 0){
            sign = BODY_SIGN;
        } else {
            sign = HEAD_SIGN;
        }
        game.setCellValueEx(part.x, part.y, Color.NONE,sign,color,75);
        }
    }

    public void setDirection(Direction direction) {
        if ((this.direction == Direction.LEFT || this.direction == Direction.RIGHT) && snakeParts.get(0).x == snakeParts.get(1).x) {
            return;
        }
        if ((this.direction == Direction.UP || this.direction == Direction.DOWN) && snakeParts.get(0).y == snakeParts.get(1).y) {
            return;
        }
        if( direction == Direction.LEFT && this.direction != Direction.RIGHT ){
            this.direction = direction;
        }
        else if(direction == Direction.RIGHT && this.direction !=Direction.LEFT){
            this.direction = direction;
        }else if(direction == Direction.UP && this.direction != Direction.DOWN){
            this.direction = direction;
        }
        else if(direction == Direction.DOWN && this.direction != Direction.UP){
            this.direction = direction;
        }
    }

    public void move(Apple apple){
        GameObject snakeHead = createNewHead();

        if (snakeHead.x >= SnakeGame.HEIGHT || snakeHead.x < 0 || snakeHead.y >= SnakeGame.HEIGHT || snakeHead.y < 0){
            isAlive = false;
        } else if (checkCollision(snakeHead)){
            isAlive = false;
        } else if (snakeHead.x == apple.x && snakeHead.y == apple.y){
            apple.isAlive = false;
            snakeParts.add(0,snakeHead);
        }else {
            snakeParts.add(0,snakeHead);
            removeTail();
        }

    }
    public GameObject createNewHead(){
        GameObject oldHead = snakeParts.get(0);
        if (direction == Direction.LEFT){
            return new GameObject(oldHead.x -1,oldHead.y);
        } else if (direction == Direction.DOWN){
            return new GameObject(oldHead.x,oldHead.y+1);
        } else if (direction == Direction.UP){
            return new GameObject(oldHead.x, oldHead.y-1);
        } else {
            return new GameObject(oldHead.x + 1, oldHead.y);
        }
    }
    public void removeTail(){
        snakeParts.remove(snakeParts.size()-1);
    }

    public boolean checkCollision(GameObject gameObject){
       for (GameObject parts : this.snakeParts){
           if (parts.x == gameObject.x && parts.y == gameObject.y){
               return true;
           }
       }
       return false;
    }
    public int getLength(){
        return snakeParts.size();
    }
}
