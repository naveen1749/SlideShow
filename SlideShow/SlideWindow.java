package SlideShow;

import org.xml.sax.SAXParseException;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SlideWindow extends JFrame{

    Dimension size;

    Listeners listeners;
    Thread thread;

    BufferedImage image;
    File loadingImage;

    static File imgPATH;
    int width,height,imgcount,MINSPEED=500,count,scrnwidth,scrnheight;
    int loading;

    List<File> lists;

    Font font;

    State state;

     static List<File> paths;
    int x=0;
    int y=70;
    boolean fav;
    JProgressBar progress;
    List<MenuItems> menuitems;
    SlideWindow(Listeners listeners){
        menuitems= Arrays.asList(new MenuItems("replay",200,100, MenuItems.ID.play),new MenuItems("stop",200,160,MenuItems.ID.stop));
        state=State.menu;
        Toolkit t=Toolkit.getDefaultToolkit();
        scrnheight=t.getScreenSize().height;
        scrnwidth=t.getScreenSize().width;

        loadingImage=new File("C:\\Users\\naveen\\OneDrive\\Desktop\\Loading_icon.gif");
        this.listeners = listeners;
        lists = getArray();

        paths=getArray();

        progress = new JProgressBar();

        thread=new Thread(this::draw);

        font=new Font("times new Roman",Font.BOLD,20);
        initializeWindow();
        this.setVisible(true);

        thread.start();

    }
private List<File> getArray(){
        return new ArrayList<>();
}


    private void initializeWindow() {
        try{ UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());}catch(Exception e){}
        size = Toolkit.getDefaultToolkit().getScreenSize();

        JPanel panel = new JPanel();
        panel.setLayout(null);
        this.setContentPane(panel);

        JSlider slider = new JSlider();
        slider.setBounds(1098, 12, 150, 20);
        slider.setForeground(Color.red);
        slider.setFocusable(false);
        slider.setMaximum(20);
        slider.setMinimum(0);
        slider.setMinorTickSpacing(JSlider.TOP);
        slider.setValue(listeners.SPEED);
        slider.addChangeListener(listeners);

        progress.setBounds(100,100,200,200);
        progress.setString("loading "+ loading);
        progress.setValue(0);

        panel.add(slider);
        panel.add(progress);
        this.setSize(size);
        this.addKeyListener(listeners);
        this.addWindowListener(listeners);
        this.addWindowListener(listeners);
        this.addMouseListener(listeners);
        this.addMouseMotionListener(listeners);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    }

    synchronized  void draw(){
        if(state.equals(State.show)) {
            File file = new File(listeners.PATH);
            if (isDirectory(file)) {
                for (int i = 0; (lists != null?lists.size():0) > i; i++) {
                    //check any directory's are there in
                    File temp = lists.get(i);

                    if (!isDirectory(temp)) {
                        if (listeners.resume) {
                            lists = lists.subList(lists.indexOf(temp), lists.size());
                            repaint();
                            break;
                        }
                        assignImage(temp);
                    } else {
                        try {
                            image = ImageIO.read(loadingImage);
                            repaint();
                        } catch (Exception ignored) {
                        }
                    }

                }
            } else {
                try {
                    image = ImageIO.read(file);
                    repaint();
                } catch (Exception ignored) {
                }
            }
        }
    }

    private void assignImage(File fil) {
        try {
            imgPATH = fil;
           String temp=fil.getAbsolutePath();

            if ( Pattern.compile(".JPG",Pattern.CASE_INSENSITIVE).matcher(temp).find() ||
                    Pattern.compile(".Png",Pattern.CASE_INSENSITIVE).matcher(temp).find()
                     ) {
                image = ImageIO.read(fil);
                width = image.getWidth();
                height = image.getHeight();
                repaint();
                imgcount++;
                sleep(listeners.SPEED < 0 ? MINSPEED : listeners.SPEED * 1000);

            }

        } catch (Exception ignored) {
        }
    }

    private boolean isDirectory(File path){
        if(path.isDirectory()){
            repaint();
            File[] temp=path.listFiles();
                if(temp != null) {
                    for(File file: temp){
                        lists.add(file);
                        count += lists.size();
                    }

                }

        }
        return path.isDirectory();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if( state.equals(State.show)){
            if (image != null && !fav ) {

                g.drawImage(image, x, y, size.width, size.height, null);
                g.setFont(font);
                g.setColor(Color.blue);
                g.drawString(" count : " + imgcount + " \\ " + count + " folders : " + lists.size() + " Speed : " + listeners.SPEED + " Path : " +
                        imgPATH, 10, 55);
            }else{
                g.setFont(new Font("times new roman",Font.BOLD,25));
                g.setColor(Color.black);
                g.drawString("loading ..." ,100,100);
            }
        }else {
            g.setColor(Color.black);
            g.fillRect(0,0,scrnwidth,scrnheight);
            g.setColor(Color.white);
            g.setFont(new Font("times new roman", Font.BOLD, 25));

            for (MenuItems menu : menuitems) {
                g.drawRect(menu.getX(), menu.getY(), 200, 50);
                g.drawString(menu.getName(), menu.getX() + 20, menu.getY() + 20);
            }
        }


    }


    private void sleep(int time) {
        try {
            Thread.sleep(time);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


}


