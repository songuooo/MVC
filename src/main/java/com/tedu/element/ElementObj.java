package com.tedu.element;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseWheelEvent;

/**
 * @说明 所有元素的基类
 */
public abstract class ElementObj {
    private int x;
    private int y;
    private int centerX;
    private int centerY;
    private int w;
    private int h;
    private ImageIcon icon;// ImageIcon相比Image可以读取图片参数
    private boolean life;// 存活状态
    private int HP;

    private int sx;
    private int sy;

    public ElementObj() {
        this.life = true;
    }

    public ElementObj(int x, int y, int w, int h, ImageIcon icon) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.icon = icon;
        this.life = true;
    }

    // 封装创建对象方法
    public ElementObj createElement(String str){
        return null;
    }

    /**
     * @说明  抽象方法，显示元素
     * @param g 画笔，用于绘画
     */
    public abstract void showElement(Graphics g);

    public void KeyClick(boolean pressed, int key) {

    }

    public void MouseClick(boolean pressed, int key) {

    }

    public void MouseMove(int x, int y) {

    }

    public void WheelMove(int wheelValue) {

    }

    /**
     * @设计模式 模板模式：在模板模式中定义对象执行方法的先后顺序，子类选择性重写方法
     * 1.换装 2.移动 3.发射子弹
     */
    public final void model(long gameTime){
        updateImage(gameTime);
        move();
        shoot(gameTime);
    }

    protected void updateImage(long gameTime){

    }
    protected void move(){

    }
    protected void shoot(long gameTime){

    }



    public void die(){

    }

    // Setter与Getter
    public int getX() {
        return x;
    }
    public void setX(int x) {
        this.x = x;
    }
    public int getY() {
        return y;
    }
    public void setY(int y) {
        this.y = y;
    }
    public int getCenterX() {
        return centerX;
    }
    public void setCenterX(int centerX) {
        this.centerX = centerX;
    }
    public int getCenterY() {
        return centerY;
    }
    public void setCenterY(int centerY) {
        this.centerY = centerY;
    }
    public int getW() {
        return w;
    }
    public void setW(int w) {
        this.w = w;
    }
    public int getH() {
        return h;
    }
    public void setH(int h) {
        this.h = h;
    }
    public int getSx() {
        return sx;
    }
    public void setSx(int sx) {
        this.sx = sx;
    }
    public int getSy() {
        return sy;
    }
    public void setSy(int sy) {
        this.sy = sy;
    }
    public int getHP() {
        return HP;
    }
    public void setHP(int HP) {
        this.HP = HP;
    }
    public ImageIcon getIcon() {
        return icon;
    }
    public void setIcon(ImageIcon icon) {
        this.icon = icon;
    }
    public boolean isLife() {
        return life;
    }
    public void setLife(boolean life) {
        this.life = life;
    }

    /**
     * @说明 本方法实时返回元素的碰撞矩形对象
     * @return
     */
    public Rectangle getRectangle(){
        return new Rectangle(x, y, w, h);
    }

    /**
     * @说明 碰撞方法
     * @return boolean
     */
    public boolean pk(ElementObj obj){
        return this.getRectangle().intersects(obj.getRectangle());
    }
}
