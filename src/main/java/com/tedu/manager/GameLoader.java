package com.tedu.manager;

import com.dd.plist.*;
import com.tedu.element.Connon;
import com.tedu.element.ElementObj;
import com.tedu.element.BarObj;
import com.tedu.element.Maps;
import org.xml.sax.SAXException;

import javax.swing.*;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.*;

/**
 * @说明 游戏加载器（工具类：用于读取配置文件，多为 static 方法）
 *      加载其实就是把元素添加到元素管理器中
 */
public class GameLoader {

    // 获取元素管理器
    private static ElementManager em = ElementManager.getManager();
    // 用于读取配置文件的类
    private static Properties pro = new Properties();
    // 图片集合
    public static Map<String, ImageIcon> imgMap = new HashMap<>();
    public static Map<String, List<ImageIcon>> imgMaps;
    // 存储类的反射
    private static Map<String, Class<?>> objMap = new HashMap<>();

    public static void LoadMap(String url){
        ElementObj map = new Maps().createElement(url);
        em.addElement(GameElement.BAR, map);
    }

    public static void LoadBar(){
        String mapName = "text/bar.pro";
        ClassLoader classLoader = GameLoader.class/*GameLoader类的class对象*/.getClassLoader();// 获取当前类的类加载器
        InputStream maps = classLoader.getResourceAsStream(mapName);
        pro.clear();// 清除此哈希表，使其不包含任何键。

        try{
            pro.load(maps);// 从输入字节流中读取属性列表（键和元素对）。此方法返回后，指定的流仍保持打开状态。

            Enumeration<?> names = pro.propertyNames();
            while(names.hasMoreElements()){
                String key = names.nextElement().toString();
                String[] arrs = pro.getProperty(key).split(";");// 依据键获取值
                for(int i=0;i<arrs.length;i++){
                    ElementObj element = new BarObj().createElement(key + "," + arrs[i]);
                    em.addElement(GameElement.BAR, element);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void LoadConnon() throws PropertyListFormatException, IOException, ParseException, ParserConfigurationException, SAXException {
        String url = "src/main/resources/texture/cannon/cannon";
        String str = GetDataFromPlist(url, "net_11.png");
        String fileName = str.split(",")[0];

        imgMap.put(fileName, new ImageIcon(url+".png"));
        ElementObj element = new Connon().createElement(str);
        em.addElement(GameElement.CONNON, element);
    }

    public static void LoadBullet() throws PropertyListFormatException, IOException, ParseException, ParserConfigurationException, SAXException {
        imgMap.put("bulletandnet", new ImageIcon("src/main/resources/texture/bullet/bulletandnet.png"));
    }

    public static String GetDataFromPlist(String url, String str) throws PropertyListFormatException, IOException, ParseException, ParserConfigurationException, SAXException {// 注：不含后缀
        // 1. 加载 plist 文件和 png 图片
        File plistFile = new File(url+".plist");
        String fileName = plistFile.getName();
        String fileNameWithoutExtention = fileName.substring(0, fileName.lastIndexOf("."));
        // 2. 解析文件
        NSObject root = PropertyListParser.parse(plistFile);
        // 3. 获取根字典（plist 通常以字典开头）
        NSDictionary rootDict = (NSDictionary) root;

        // 解析嵌套字典
        NSDictionary frames = (NSDictionary) rootDict.get("frames");
        NSDictionary Data = (NSDictionary) frames.get(str);
        String sx = Data.get("x").toString();
        String sy = Data.get("y").toString();
        String w = Data.get("width").toString();
        String h = Data.get("height").toString();

        return fileNameWithoutExtention+","+w+","+h+","+sx+","+sy;
    }

//    public static void LoadImgFromPro(String textUrl){
//        ClassLoader classLoader = GameLoader.class.getClassLoader();
//        InputStream texts = classLoader.getResourceAsStream(textUrl);
//        pro.clear();
//
//        try{
//            pro.load(texts);
//            Set<Object> set = pro.keySet();// 类似字典的键
//            for(Object o:set){
//                String url = pro.getProperty(o.toString());// 通过键获取值
//                imgMap.put(o.toString(), new ImageIcon(url));
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

//    public static void LoadConnon(){
//        String connonStr = "500,500,up";// {X,Y,fx}
//
//        // 这一段相当于 new Play()
//        // 解耦，降低代码和代码之间的耦合度，可以直接通过接口或者是父类就可以获取到实体对象
//        // 让用户够脱离程序本身去修改相关的变量设置
//        LoadObj();
//        Class<?> class1 = objMap.get("Connon");
//        ElementObj obj = null;
//        try{
//            Object newInstance = class1.newInstance();
//            if(newInstance instanceof ElementObj){
//                obj = (ElementObj) newInstance;// 这个对象就和 new Play() 等价
//            }
//        } catch(InstantiationException e){
//            e.printStackTrace();
//        } catch(IllegalAccessException e){
//            e.printStackTrace();
//        }
//
//        em.addElement(GameElement.CONNON, obj.createElement(connonStr));
//    }

//    // 读取配置文件里的类载入objMap
//    public static void LoadObj(){
//        String textUrl = "com/tedu/text/Obj.pro";
//        ClassLoader classLoader = GameLoader.class.getClassLoader();
//        InputStream texts = classLoader.getResourceAsStream(textUrl);
//        pro.clear();
//
//        try{
//            pro.load(texts);
//            Set<Object> set = pro.keySet();// 类似字典的键
//            for(Object o:set){
//                String classUrl = pro.getProperty(o.toString());// 通过键获取值
//                // 使用反射的方式获取类
//                Class<?> forName = Class.forName(classUrl);
//                objMap.put(o.toString(), forName);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (ClassNotFoundException e){
//            e.printStackTrace();
//        }
//    }
}
