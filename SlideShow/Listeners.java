package SlideShow;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Listeners extends KeyAdapter implements ActionListener, WindowListener, ChangeListener ,MouseListener,MouseMotionListener{

    Object obj;
    SlideWindow sw;
    String PATH;
    int SPEED;
    boolean resume=false;
    ArrayList temp;
    Listeners(Object t){
        this.obj=t;
        temp =new ArrayList();

    }


    @Override
    public void actionPerformed(ActionEvent e) {

        MenuWindow menu = ((MenuWindow) obj);
        if (e.getSource().equals((menu.okay))) {
            PATH = menu.pathta.getText();
            if (PATH.isEmpty() || !new File(PATH).exists() || menu.speedta.getText().isEmpty()) {
                menu.labelofPath.setForeground(Color.red);
                menu.labelofPath.setText("enter valid variables ");

            }else {
                SPEED = Integer.parseInt(menu.speedta.getText());
               //new Thread( () -> menu.start()
               //).start();

                sw = new SlideWindow(this);
                sw.state=State.show;


            }
          //  System.out.println(((MenuWindow) obj).okay);
        }
    }

    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {
        Toolkit.getDefaultToolkit().beep();
            JOptionPane optionPane = new JOptionPane();
            int option = optionPane.showConfirmDialog(sw, "Are you sure", "Exit", JOptionPane.YES_NO_OPTION);

            if (option == 0) {
                try {
                    if (e.getSource() instanceof MenuWindow) {
                        System.out.println("called");
                        System.exit(1);
                    } else {
                        closeShow();
                    }
                } catch (Exception err) {
                    err.printStackTrace();
                }
            }
    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }

    @Override
    public void stateChanged(ChangeEvent e) {
        JSlider slider = (JSlider) e.getSource();
        SPEED=slider.getValue();
        sw.thread.interrupt();
    }
    @Override
    public void keyPressed(KeyEvent e) {
        try {

            //SlideWindow win = ((SlideWindow) obj);

            if (e.getSource() instanceof  SlideWindow) {

                switch (e.getKeyCode()) {
                    case KeyEvent.VK_SPACE -> {
                        if (resume) {
                            resume = false;
                            sw.state=State.show;
                            if(sw.thread.isAlive()){
                                System.out.println("thread is alive");
                                try{
                                    Thread.currentThread().interrupt();}catch(Exception ignored){}
                            }else{
                                System.out.println("thread.is died");
                                sw.thread=new Thread(sw::draw);
                                sw.thread.start();
                            }

                        } else {
                            resume = true;
                            sw.state=State.menu;
                        }
                        break;
                    }

                    case KeyEvent.VK_EQUALS -> {
                        SPEED += 1;
                        System.out.println(SPEED);
                        sw.thread.interrupt();
                        System.out.println(SPEED);
                        break;
                    }
                    case KeyEvent.VK_MINUS -> {
                        SPEED -= 1;
                        System.out.println(SPEED);
                        sw.thread.interrupt();
                        System.out.println(SPEED);
                        break;
                    }
                    case KeyEvent.VK_ESCAPE -> {
                        closeShow();
                        break;
                    }

                }
            }

            if(e.isControlDown()){
                Toolkit.getDefaultToolkit().beep();
                if(e.getKeyCode() == KeyEvent.VK_C){
                    SlideWindow.paths.add(SlideWindow.imgPATH);
                    System.out.println(SlideWindow.paths);
                }
            }
        }catch(Exception err) {


        }
            try {
                MenuWindow menu = (MenuWindow) e.getSource();
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_TAB:
                        if (e.getSource().equals(menu.pathta)) {
                            menu.speedta.requestFocus();

                        }
                        if (e.getSource().equals(menu.speedta)) {
                            menu.okay.requestFocus();
                        }
//
                        break;


                }
            }catch(Exception err){}

    }

    private void closeShow(){
        sw.lists = temp;
        sw.dispose();
    }

    @Override
    public void mouseClicked(MouseEvent e) {

        Rectangle temp=new Rectangle(e.getX()+25,e.getY()+25,200,50);
        for(MenuItems item: sw.menuitems ) {
            if (temp.intersects(item.getBounds())) {
                if(item.getID().equals(MenuItems.ID.play)){
                    resume=false;
                    sw.state=State.show;
                    sw.thread= new Thread(sw::draw);
                    sw.thread.start();
                }else{
                    System.exit(1);
                }
            }

        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}
