package com.tedu.element;

import javax.swing.*;
import java.awt.*;

public class BarObj extends ElementObj {

    @Override
    public void showElement(Graphics g){
        g.drawImage(this.getIcon().getImage(), this.getX(), this.getY(), this.getW(), this.getH(), null);
    }

    @Override
    public ElementObj createElement(String str){
        String[] arr = str.split(",");// 地图元素,X,Y
        ImageIcon icon = null;// 地图元素图片
        switch (arr[0]){
            case "bottom":icon = new ImageIcon("src/main/resources/texture/bottombar/bottom.png"); break;
            case "left":icon = new ImageIcon("src/main/resources/texture/bottombar/left.png"); break;
            case "right":icon = new ImageIcon("src/main/resources/texture/bottombar/right.png"); break;
            case "gold":icon = new ImageIcon("src/main/resources/texture/bottombar/gold.png"); break;
            case "time":icon = new ImageIcon("src/main/resources/texture/bottombar/time.png"); break;
        }
        this.setX(Integer.parseInt(arr[1]));
        this.setY(Integer.parseInt(arr[2]));
        this.setW(icon.getIconWidth());
        this.setH(icon.getIconHeight());
        this.setIcon(icon);
        return this;
    }
}
