package com.tedu.element;

import com.dd.plist.PropertyListFormatException;
import com.tedu.manager.ElementManager;
import com.tedu.manager.GameElement;
import com.tedu.manager.GameLoader;
import com.tedu.show.GameJFrame;
import org.xml.sax.SAXException;

import javax.swing.*;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.*;
import java.io.IOException;
import java.text.ParseException;

import static com.tedu.manager.GameLoader.GetDataFromPlist;

// 玩家元素类
public class Connon extends ElementObj {

    private int atkType = 1;// 1不攻击 2攻击
    private int level = 1;// 炮筒等级

    private double angle = 0;
    private long oldTime = 0;

    private int mouseX = 0;// 鼠标实时坐标
    private int mouseY = 0;// 鼠标实时坐标

    public Connon() {

    }

    @Override
    public ElementObj createElement(String str){// fileName,w,h,sx,sy
        String[] split = str.split(",");
        ImageIcon img = GameLoader.imgMap.get(split[0]);
        this.setIcon(img);
        this.setX(375);
        this.setY(390);
        this.setW(Integer.parseInt(split[1]));
        this.setH(Integer.parseInt(split[2]));
        this.setCenterX(this.getX() + this.getW() / 2);
        this.setCenterY(this.getY() + this.getH() / 2);
        this.setSx(Integer.parseInt(split[3]));
        this.setSy(Integer.parseInt(split[4]));
        return this;
    }

    /**
     * 面向对象第一思想：对象自己的事情自己做，专门的事件用专门的方法做
     * @param g 画笔，用于绘画
     */
    @Override
    public void showElement(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        this.angle = Math.atan2(mouseX - this.getCenterX(), this.getCenterY() - mouseY);
        g2d.rotate(angle, this.getCenterX(), this.getCenterY());// 旋转坐标系

        g2d.drawImage(this.getIcon().getImage(),
                this.getCenterX() - this.getW() / 2, this.getCenterY() - this.getH() / 2,
                this.getCenterX() + this.getW() / 2, this.getCenterY() + this.getH() / 2,
                this.getSx(), this.getSy(),
                this.getSx()+this.getW(), this.getSy()+this.getH(),
                null);

        g2d.dispose();
    }

    @Override
    public void KeyClick(boolean pressed, int key) {

    }

    @Override
    public void MouseClick(boolean pressed, int key) {
        if(key == 1){
            if(pressed){
                this.atkType = 2;
            }else{
                this.atkType = 1;
            }
        }
    }

    @Override
    public void MouseMove(int x, int y) {
        mouseX = x;
        mouseY = y;
    }

    @Override
    public void WheelMove(int WheelValue) {
        if(WheelValue == -1){// 往上滚
            level++;
            level %= 13;
            if(level == 0){
                level = 1;
            }
        } else if(WheelValue == 1){// 往下滚
            level--;
            level %= 13;
            if(level == 0){
                level = 12;
            }
        }
    }

    @Override
    protected void updateImage(long gameTime){
        try {
            // 读取并更新炮弹
            String[] split = GetDataFromPlist("src/main/resources/texture/cannon/cannon", "net_"+String.valueOf(this.level)+String.valueOf(this.atkType)+".png")
                    .split(",");
            this.setSx(Integer.parseInt(split[3]));
            this.setSy(Integer.parseInt(split[4]));

            if(this.atkType == 1){// 不攻击
                this.setY(390);
            }else{
                this.setY(400);
            }

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
    }

    /**
     * 子弹间隔实现方法：
     * 1.使用本地时间与全局时间做比较，小于1s，将本地时间赋值为全局时间并发射子弹；
     * 2.shoot（）后将atkType改为false，按一次发射一次，手速越快发射越快
     */
    @Override
    public void shoot(long gameTime){
        if(this.atkType == 1 || gameTime - oldTime < 50){// 子弹间隔控制
            return;
        }
        oldTime = gameTime;

        ElementObj bullet = new Bullet().createElement(this.toString());// 使用子弹类的封装方法创建对象
        ElementManager.getManager().addElement(GameElement.BULLET, bullet);
    }

    // 用于传参数给子弹
    @Override
    public String toString(){
        // 计算子弹起始位置 (炮口位置)
        int cannonCenterX = this.getX() + this.getW() / 2;
        int cannonCenterY = this.getY();
        double barrelLength = this.getH() * 0.6; // 炮管长度取炮身高度百分比
        int bulletCenterX = (int) (cannonCenterX + barrelLength * Math.sin(this.angle));
        int bulletCenterY = (int) (cannonCenterY - barrelLength * Math.cos(this.angle));

        return "centerX:"+bulletCenterX+",centerY:"+bulletCenterY+",level:"+this.level+",angle:"+this.angle;
    }
}