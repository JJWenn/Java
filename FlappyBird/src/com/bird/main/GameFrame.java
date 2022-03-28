package com.bird.main;

import static com.bird.util.Constant.*;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;

//    ��Ϸ���������ࡢ���еĹ�����Ϸ�л��Ƶ����ݶ��ڴ��������

public class GameFrame extends Frame {

    //    ʵ����GameBackGround��
    private GameBackGround gameBackGround;

    //    ʵ����Bird��
    private Bird bird;

    //    ʵ����GameBarrierLayer��
    private GameBarrierLayer gameBarrierLayer;

    //    ���ͼƬ��ͼƬ
    private BufferedImage buffimg = new BufferedImage(FRAM_WIDTH, FRAM_HEIGHT, BufferedImage.TYPE_4BYTE_ABGR);

    //    ���췽���г�ʼ��һЩ����
    public GameFrame() {
        //    �����Ƿ�ɼ�
        setVisible(true);
        //    ���ڵĴ�С
        setSize(FRAM_WIDTH, FRAM_HEIGHT);
        //    ���ڵı���
        setTitle(FRAM_TITLE);
        //    ���ڵĳ�ʼ��λ��
        setLocation(FRAM_X, FRAM_Y);
        //    ���ڵĴ�С���ɸı�
        setResizable(false);

        //    ���ڵĹر��¼�
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);  // ��������
            }
        });

        //    ��ʼ����Ϸ����
        initGame();

        new run().start();

        //    ��Ӱ���������
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

    //    ����������Ҫ���Ƶ����ݶ��ڴ˷����н��л���
    @Override
    public void update(Graphics g) {
        if (bird.life) {
            //    �õ�ͼƬ�Ļ���
            Graphics graphics = buffimg.getGraphics();

            gameBackGround.draw(graphics);
            bird.draw(graphics);
            gameBarrierLayer.draw(graphics, bird);

            //    һ���Եؽ�ͼƬ���Ƶ���Ļ��
            g.drawImage(buffimg, 0, 0, null);
        } else {
            String over = "��Ϸ����";
            g.setColor(Color.red);
            g.setFont(new Font("΢���ź�", 1, 60));
            g.drawString(over, 190, 250);

            String reset = "Space Reset Game";
            g.drawString(reset, 25, 350);
        }
    }

    //    ����
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

    //    ̧��
    public void minu(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                bird.fly(5);
                break;
        }
    }

    //    ������Ϸ
    public void restart() {
        gameBarrierLayer.restant();
        bird.restartDraw();
    }
}
