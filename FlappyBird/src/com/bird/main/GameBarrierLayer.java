package com.bird.main;

//游戏的前景类

import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameBarrierLayer {

    private GameTime gameTime;

    private int txt;

    private Random random = new Random();

    private List<Barrier> barriers;

    public GameBarrierLayer() {
        barriers = new ArrayList<>();
        gameTime = new GameTime();
    }

    //    绘制障碍物
    public void draw(Graphics g, Bird bird) {
        for (int i = 0; i < barriers.size(); i++) {
            Barrier barrier = barriers.get(i);
            if (barrier.isVisible()) {
                barrier.draw(g);
            } else {
                Barrier remove = barriers.remove(i);
                Barrierpool.setPool(remove);
                i--;
            }
        }
        collideBird(bird);
        logic(g);
    }

    public void logic(Graphics g) {
        if (barriers.size() == 0) {
            ran();
            gameTime.begin();
            insert(600, 0, numberTop, 0);
            insert(600, 500 - numberDown, numberDown, 2);

        } else {
            long differ = gameTime.differ();
            g.setColor(Color.white);
            g.setFont(new Font("微软雅黑", 1, 20));
            g.drawString("坚持了：" + differ + "秒", 30, 80);

            txt = getTxt();
            if (differ <= txt) {
                g.drawString("最高成绩：" + txt, 200, 80);
            }else{
                setTxt(String.valueOf(differ));
                g.drawString("最高成绩：" + getTxt(), 200, 80);
            }
            //    判断最后一个障碍物是否完全进入屏幕内
            Barrier last = barriers.get(barriers.size() - 1);
            if (last.isInFrame()) {
                ran();
                if (number < 50) {
                    insert(600, 102, 340, 4);
                } else if (number > 450) {
                    insert(600, 125, 200, 6);
                } else {
                    insert(600, 0, numberTop, 0);
                    insert(600, 500 - numberDown, numberDown, 2);
                }
            }
        }
    }

    File file = new File("record/game.txt");

    //    用于得到文件中的数据
    public int getTxt() {
        BufferedReader in = null;
        try {
            in = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        int read = 0;
        try {
            read = Integer.parseInt(in.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return read;
    }

    //    用于储存布局
    public void setTxt(String str) {
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            fileWriter.write(str);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //    用于从池中获取对象，并把参数封装成barrier存入barriers数组中
    public void insert(int x, int y, int num, int type) {
        Barrier top = Barrierpool.getPool();
        top.setX(x);
        top.setY(y);
        top.setHeight(num);
        top.setType(type);
        top.setVisible(true);
        barriers.add(top);
    }

    //    上方障碍高度
    private int numberTop;
    //    下方障碍高度
    private int numberDown;

    private int number;

    //    产生两个100-500之间的随机高度
    public void ran() {
        numberTop = random.nextInt(400) + 100;
        numberDown = random.nextInt(400) + 100;
        number = random.nextInt(500);
        //    如果管道重合，则重新随机
        if (numberTop + numberDown > 450) {
            ran();
        }
    }

    //    判断小鸟与障碍物发生碰撞
    public boolean collideBird(Bird bird) {
        for (int i = 0; i < barriers.size(); i++) {
            Barrier barrier = barriers.get(i);
            //    判断矩形是否相交
            if (barrier.getRect().intersects(bird.getRect())) {
                System.out.println("撞上啦！");
                bird.life = false;
            }
        }
        return false;
    }

    //    用于清空障碍物的池子
    public void restant() {
        barriers.clear();
    }

}
