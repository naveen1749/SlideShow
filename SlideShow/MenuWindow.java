package SlideShow;

import javax.swing.*;
import java.awt.*;

public class MenuWindow extends JFrame {

    JLabel labelofPath;
    JPanel menuPanel;

    Cursor cursor;
    JTextArea pathta,speedta;
    JButton okay;

    Listeners listeners;

    MenuWindow(){

        cursor=new Cursor(Cursor.CROSSHAIR_CURSOR);
        listeners= new Listeners(this);
        initializeMenu();
        this.setVisible(true);
    }
    private void initializeMenu() {
        try{ UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());}catch(Exception e){}

        menuPanel = new JPanel(new FlowLayout(FlowLayout.LEADING, 10, 10), true);
        this.setContentPane(menuPanel);

        JLabel pathLabel = new JLabel(" path : ");
        pathLabel.setSize(100, 40);
        pathLabel.setCursor(cursor);

        pathta = new JTextArea(1, 30);
        pathta.setText("c://");
        pathta.setSize(100, 40);
        pathta.setToolTipText(" enter path from where to start slide show ");

        JLabel speedLabel = new JLabel(" speed : ");
        speedLabel.setSize(100, 40);
        speedLabel.setCursor(cursor);

        speedta = new JTextArea(1, 5);
        speedta.setSize(100, 40);
        speedta.setText("1");
        speedta.setToolTipText("enter delay duration in seconds ");

        okay = new JButton("Okay");
        okay.setSize(100, 60);
        okay.addActionListener(listeners);
        okay.setCursor(new Cursor(Cursor.HAND_CURSOR));

         labelofPath = new JLabel();
        labelofPath.setSize(100, 20);

        menuPanel.add(pathLabel);
        menuPanel.add(pathta);
        menuPanel.add(speedLabel);
        menuPanel.add(speedta);
        menuPanel.add(okay);
        menuPanel.add(labelofPath);

        this.addKeyListener(listeners);
        speedta.addKeyListener(listeners);
        pathta.addKeyListener(listeners);
        this.setSize(200, 300);
        this.setLocationRelativeTo(null);
        this.addWindowListener(listeners);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

    }

}
