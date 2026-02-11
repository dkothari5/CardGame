package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;

public class Gameview extends JFrame
{
    private final int WINDOW_WIDTH = 1000;
    private final int WINDOW_HEIGHT = 800;
    private final int TITLE_BAR_HEIGHT = 23;
    private Game backend;
    public Gameview(Game backend)
    {
        this.backend = backend;
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setTitle("Cheat");
        this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        this.setVisible(true);
    }
    public void paint(Graphics g)
    {
        System.out.println("Hello");
    }




}