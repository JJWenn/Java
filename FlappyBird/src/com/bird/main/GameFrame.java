package com.bird.main;

import static com.bird.util.Constant.*;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;

//    游戏的主窗口类、所有的关于游戏中绘制的内容都在此类中完成

public class GameFrame extends Frame {

    //    实例化GameBackGround类
    private GameBackGround gameBackGround;

    //    实例化Bird类
    private Bird bird;

    //    实例化GameBarrierLayer类
    private GameBarrierLayer gameBarrierLayer;

    //    存放图片的图片
    private BufferedImage buffimg = new BufferedImage(FRAM_WIDTH, FRAM_HEIGHT, BufferedImage.TYPE_4BYTE_ABGR);

    //    构造方法中初始化一些参数
    public GameFrame() {
        //    窗口是否可见
        setVisible(true);
        //    窗口的大小
        setSize(FRAM_WIDTH, FRAM_HEIGHT);
        //    窗口的标题
        setTitle(FRAM_TITLE);
        //    窗口的初始化位置
        setLocation(FRAM_X, FRAM_Y);
        //    窗口的大小不可改变
        setResizable(false);

        //    窗口的关闭事件
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);  // 结束程序
            }
        });

        //    初始化游戏对象
        initGame();

        new run().start();

        //    添加按键监听器
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                add(e);
            }

            @Override
            public void keyReleased(KeyEvent e) {
                minu(e);
            }
        });

    }

    public void initGame() {
        gameBackGround = new GameBackGround();
        bird = new Bird();
        gameBarrierLayer = new GameBarrierLayer();
    }

    class run extends Thread {
        @Override
        public void run() {
            while (true) {
                repaint();
                try {
                    Thread.sleep(33);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //    所有我们需要绘制的内容都在此方法中进行绘制
    @Override
    public void update(Graphics g) {
        if (bird.life) {
            //    得到图片的画笔
            Graphics graphics = buffimg.getGraphics();

            gameBackGround.draw(graphics);
            bird.draw(graphics);
            gameBarrierLayer.draw(graphics, bird);

            //    一次性地将图片绘制到屏幕中
            g.drawImage(buffimg, 0, 0, null);
        } else {
            String over = "游戏结束";
            g.setColor(Color.red);
            g.setFont(new Font("微软雅黑", 1, 60));
            g.drawString(over, 190, 250);

            String reset = "Space Reset Game";
            g.drawString(reset, 25, 350);
        }
    }

    //    按键
    public void add(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                bird.fly(1);
                break;
            case KeyEvent.VK_SPACE:
                if (bird.life == false) {
                    restart();
                }
                break;
        }
    }

    //    抬键
    public void minu(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                bird.fly(5);
                break;
        }
    }

    //    重置游戏
    public void restart() {
        gameBarrierLayer.restant();
        bird.restartDraw();
    }
}
