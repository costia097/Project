package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

/**
 * Created by Mark Hychka on 06.01.2017.
 */
public class CutFrame extends JFrame {
    public CutFrame() {
        setTitle("File cutter");
        setSize(400, 400);
        getContentPane().setBackground(Color.WHITE);
        JMenuBar menubar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem exit = new JMenuItem("Exit");
        fileMenu.add(exit);
        menubar.add(fileMenu);
        setJMenuBar(menubar);
        JLabel label = new JLabel("Ready");
        getContentPane().add(label, BorderLayout.SOUTH);

        exit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        JPanel panel = new JPanel();
        JButton cut = new JButton("Cut");
        panel.add(cut);
        JButton restore = new JButton("Restore");
        panel.add(restore);
        add(panel);

        cut.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileopen = new JFileChooser();
                int ret = fileopen.showDialog(null, "Choose file");
                if (ret == JFileChooser.APPROVE_OPTION) {
                    File file = fileopen.getSelectedFile();
                    if(file.length()/9>=2_000_000_000)
                        JOptionPane.showMessageDialog(CutFrame.this,
                                "Too big size",
                                "Too big file",
                                JOptionPane.ERROR_MESSAGE);
                    else {
                        label.setText(file.getName());
                        try {
                            cutting(file);
                        } catch (IOException e1) {
                            JOptionPane.showMessageDialog(CutFrame.this,
                                    "File doesn't exist!",
                                    "File not found",
                                    JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            }
        });
        restore.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileopen = new JFileChooser();
                int ret = fileopen.showDialog(null, "Choose part");
                if (ret == JFileChooser.APPROVE_OPTION) {
                    File file = fileopen.getSelectedFile();
                    label.setText(file.getName());
                    try {
                        restoration(file);
                    } catch (IOException e1) {
                        JOptionPane.showMessageDialog(CutFrame.this,
                                "File doesn't exist!",
                                "File not found",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
    }
    private void cutting (File file) throws IOException {
        int i=1;
        try(FileInputStream in = new FileInputStream(file)){
            byte [] part = new byte[(int)(file.length()/9+1)];
            while(in.read(part)!=-1){
                try(FileOutputStream out = new FileOutputStream(file.getParent()+"\\"+i+file.getName())) {
                    i++;
                    out.write(part);
                }
            }
        }
    }
    private void restoration(File file) throws IOException{
        byte [] part = new byte[(int)file.length()];
        try(FileOutputStream out =  new FileOutputStream(file.getParent()+"\\"+"restored"+file.getName().substring(1,file.getName().length()))) {
            for (int j = 1; j < 10; j++) {
                try (FileInputStream input = new FileInputStream(file.getParent()+"\\"+j + file.getName().substring(1, file.getName().length()))) {
                    input.read(part);
                    out.write(part);
                }
            }
        }
    }
}
