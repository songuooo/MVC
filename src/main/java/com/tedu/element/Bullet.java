package com.tedu.element;

import com.dd.plist.PropertyListFormatException;
import com.tedu.manager.GameElement;
import com.tedu.manager.GameLoader;
import com.tedu.show.GameJFrame;
import org.xml.sax.SAXException;

import javax.swing.*;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.text.ParseException;

/**
 * @说明 玩家子弹类 本类的实体对象由玩家对象调用和创建
 * @author songuooo
 */
public class Bullet extends ElementObj {
    private int level;// 子弹等级
    private int atk;// 子弹伤害
    private int speed;// 子弹速度
    private long oldTime = 0;
    private double angle = 0;

    public Bullet(){

    }

    // 当一个类的创建步骤过多时，可以对创建这个对象的过程进行封装，外界只需要传输必要的参数，返回值就是对象实体
    @Override
    public ElementObj createElement(String str){
        // Connon传参
        String[] split1 = str.split(",");
        for(String string:split1){
            String[] split2 = string.split(":");
            switch(split2[0]){
                case "centerX":this.setCenterX(Integer.parseInt(split2[1])); break;
                case "centerY":this.setCenterY(Integer.parseInt(split2[1])); break;
                case "level":this.level = Integer.parseInt(split2[1]); break;
                case "angle":this.angle = Double.parseDouble(split2[1]); break;
            }
        }

        // plist传参
        try {
            String url = "src/main/resources/texture/bullet/bulletandnet";
            String str2 = GameLoader.GetDataFromPlist(url, "bullet0"+String.valueOf(level)+".png");
            String[] split = str2.split(",");
            ImageIcon img = GameLoader.imgMap.get(split[0]);
            this.setIcon(img);
            this.setW(Integer.parseInt(split[1]));
            this.setH(Integer.parseInt(split[2]));
            this.setSx(Integer.parseInt(split[3]));
            this.setSy(Integer.parseInt(split[4]));
            this.atk = this.level;
            this.speed = 2;

        } catch (PropertyListFormatException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        } catch (SAXException e) {
            throw new RuntimeException(e);
        }

        System.out.println("createElement");

        return this;
    }

    @Override
    public void showElement(Graphics g){
        Graphics2D g2d = (Graphics2D) g;
        g2d.rotate(angle, this.getCenterX(), this.getCenterY());// 旋转坐标系

        g2d.drawImage(this.getIcon().getImage(),
                this.getCenterX() - this.getW() / 2, this.getCenterY() - this.getH() / 2,
                this.getCenterX() + this.getW() / 2, this.getCenterY() + this.getH() / 2,
                this.getSx(), this.getSy(),
                this.getSx() + this.getW(), this.getSy() + this.getH(),
                null);

        g2d.dispose();
    }

    @Override
    protected void move() {
        // 存活判定（边界）
        if(this.getX()<0 || GameJFrame.GameX<this.getX() ||
                this.getY()<0 || GameJFrame.GameY<this.getY()){
            this.setLife(false);
            return;
        }

        // 根据角度移动子弹
//        this.setCenterX((int)(this.getCenterX() + this.speed * Math.sin(angle)));
//        this.setCenterY((int)(this.getCenterY() + this.speed * -Math.cos(angle)));
    }
}
