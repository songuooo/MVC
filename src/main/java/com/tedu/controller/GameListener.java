package com.tedu.controller;

import com.tedu.element.ElementObj;
import com.tedu.manager.ElementManager;
import com.tedu.manager.GameElement;

import java.awt.event.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @说明 监听类，用于监听用户的操作 KeyListener
 * @author songuooo
 */
public class GameListener implements KeyListener, MouseListener, MouseMotionListener, MouseWheelListener {
    private ElementManager em = ElementManager.getManager();

    private Set<Integer> setKey = new HashSet<Integer>();// 存储按键Code值，使按键作为位移开关
    private Set<Integer> setMouse = new HashSet<Integer>();

    public GameListener() {
        super();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        // 使一次按键只能触发一次位移，即让按键改变移动属性的状态，只作为移动的开关
        int key = e.getKeyCode();
        if(setKey.contains(key)){
            return;
        }
        setKey.add(key);

        List<ElementObj> connon = em.getElementByKey(GameElement.CONNON);// 通过元素管理器获取玩家元素
        for(ElementObj obj:connon){
            obj.KeyClick(true, e.getKeyCode());
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        if(!setKey.contains(key)){
            return;
        }
        setKey.remove(key);

        List<ElementObj> connon = em.getElementByKey(GameElement.CONNON);// 通过元素管理器获取玩家元素
        for(ElementObj obj:connon){
            obj.KeyClick(false, e.getKeyCode());
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        int key = e.getButton();
        if(setMouse.contains(key)){
            return;
        }
        setMouse.add(key);

        List<ElementObj> connon = em.getElementByKey(GameElement.CONNON);// 通过元素管理器获取玩家元素
        for(ElementObj obj:connon){
            obj.MouseClick(true, e.getButton());
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        int key = e.getButton();
        if(!setMouse.contains(key)){
            return;
        }
        setMouse.remove(key);

        List<ElementObj> connon = em.getElementByKey(GameElement.CONNON);// 通过元素管理器获取玩家元素
        for(ElementObj obj:connon){
            obj.MouseClick(false, e.getButton());
        }
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
        List<ElementObj> connon = em.getElementByKey(GameElement.CONNON);// 通过元素管理器获取玩家元素
        for(ElementObj obj:connon){
            obj.MouseMove(e.getX(), e.getY());
        }
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        List<ElementObj> connon = em.getElementByKey(GameElement.CONNON);// 通过元素管理器获取玩家元素
        for(ElementObj obj:connon){
            obj.WheelMove(e.getWheelRotation());
        }
    }
}
