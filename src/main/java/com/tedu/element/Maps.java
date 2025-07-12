package com.tedu.element;

import javax.swing.*;
import java.awt.*;

public class Maps extends ElementObj{
    @Override
    public void showElement(Graphics g){
        g.drawImage(this.getIcon().getImage(), this.getX(), this.getY(), this.getW(), this.getH(), null);
    }

    @Override
    public ElementObj createElement(String str){
        this.setX(0);
        this.setY(0);

        ImageIcon icon = new ImageIcon(str);// 地图元素图片
        this.setIcon(icon);
        this.setW(icon.getIconWidth());
        this.setH(icon.getIconHeight());

        return this;
    }
}
