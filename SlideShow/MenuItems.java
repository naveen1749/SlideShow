package SlideShow;

import java.awt.*;

public class MenuItems {
   private int x,y;
   private String name;
   MenuItems(){

   }
   enum ID{
       play,
       stop,
   }
   ID id;
    MenuItems(String name,int x,int y, ID id){
        this.name =name;
        this.y=y;
        this.x=x;
        this.id=id;
    }
    int getX(){
        return this.x;
    }
    int getY(){
        return this.y;
    }
    String getName(){
        return this.name;
    }
    void setName(String name){
        this.name=name;
    }
    void setX(int x){
        this.x=x;
    }
    void setY(int y){
        this.y=y;
    }
    ID getID(){
        return this.id;
    }

    Rectangle getBounds(){
           return new Rectangle(x,y,200,50);
    }
}
