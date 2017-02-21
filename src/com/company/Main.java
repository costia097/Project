package com.company;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
	// write your code here
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                CutFrame frame = new CutFrame();
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
            }
        });
    }
}
