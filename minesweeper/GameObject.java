package com.codegym.games.minesweeper;
// Klasa tworzaca obiekt gry
public class GameObject {
    //Deklaracja zmiennych
    public int x;
    public int y;
    public boolean isMine;
    public int countMineNeighbors;
    public boolean isOpen;
    public boolean isFlag;
    //Deklaracja konstruktora
    public GameObject(int x, int y,boolean isMine) {
        this.x = x;
        this.y = y;
        this.isMine = isMine;
    }
}
