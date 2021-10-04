package com.bird.main;

import com.bird.util.Constant;
import com.bird.util.GameUtil;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Barrier {

    //    ���β���
    private Rectangle rect;

    private boolean mob = true;

    //    �ϰ����ƶ��ٶ�
    private int speed = 3;

    //    �ϰ�����Ҫ������ͼƬ
    private static BufferedImage[] imgs;

    //    �ϰ�����״̬
    private boolean visible;

    static {
        final int COUNT = 3;
        //    ����ص�ʱ������ͼƬ��ʼ��
        imgs = new BufferedImage[COUNT];
        for (int i = 0; i < COUNT; i++) {
            imgs[i] = GameUtil.loadBufferedImage(Constant.BARRIER_IMG_PATH[i]);
        }
    }

    //    λ��
    private int x, y;
    //    ��Ⱥ͸߶�
    private int width, height;
    //    �ϰ�������
    private int type;
    public static final int TYPE_TOP_NORMAL = 0;
    public static final int TYPE_BOTTOM_NORMAL = 2;
    public static final int TYPE_HOVER_NORMAL = 4;
    public static final int TYPE_MOBILE = 6;

    //    ����ϰ���ĸ߶ȺͿ��
    public static final int BARRIRE_WIDTH = imgs[0].getWidth();
    public static final int BARRIRE_HEIGHT = imgs[0].getHeight();
    public static final int BARRIRE_HEAD_WIDTH = imgs[1].getWidth();
    public static final int BARRIRE_HEAD_HEIGHT = imgs[1].getHeight();

    public Barrier() {
        rect = new Rectangle();
    }

    public Barrier(int x, int y, int height, int type) {
        this.x = x;
        this.y = y;
        this.width = BARRIRE_WIDTH;
        this.height = height;
        this.type = type;
    }

    //    ���ݲ�ͬ�����ͻ����ϰ���
    public void draw(Graphics g) {
        switch (type) {
            case TYPE_TOP_NORMAL:
                drawTopNormal(g);
                break;
            case TYPE_BOTTOM_NORMAL:
                drawDownNormal(g);
                break;
            case TYPE_HOVER_NORMAL:
                drawHoverNormal(g);
                break;
            case TYPE_MOBILE:
                drawMobile(g);
                break;
        }
    }

    //    ���ƴ������µ��ϰ���
    private void drawTopNormal(Graphics g) {
        //    �������Ҫ�Ŀ���
        int count = (height - BARRIRE_HEAD_HEIGHT) / BARRIRE_HEIGHT + 1;
        for (int i = 0; i < count; i++) {
            g.drawImage(imgs[0], x, y + i * BARRIRE_HEIGHT, null);
        }

        //    ����ͷ
        int y = height - BARRIRE_HEAD_HEIGHT;
        g.drawImage(imgs[2], x - (BARRIRE_HEAD_WIDTH - BARRIRE_WIDTH) / 2, y, null);
        x -= speed;
        if (x < -50) {
            visible = false;
        }
        rect(g);
    }

    //    ���ƴ������ϵ��ϰ���
    private void drawDownNormal(Graphics g) {
        //    �������Ҫ�Ŀ���
        int count = height / BARRIRE_HEIGHT + 1;
        for (int i = 0; i < count; i++) {
            g.drawImage(imgs[0], x, Constant.FRAM_HEIGHT - i * BARRIRE_HEIGHT, null);
        }

        //    ����ͷ
        int y = Constant.FRAM_HEIGHT - height;
        g.drawImage(imgs[1], x - (BARRIRE_HEAD_WIDTH - BARRIRE_WIDTH) / 2, y, null);
        x -= speed;
        if (x < -50) {
            visible = false;
        }
        rect(g);
    }

    //    �����м���ϰ���
    private void drawHoverNormal(Graphics g) {
        //    �������Ҫ�Ŀ���
        int count = (height - BARRIRE_HEAD_HEIGHT) / BARRIRE_HEIGHT;
        //    ������ͷ
        g.drawImage(imgs[1], x, y, null);
        //    forѭ�������ϰ���
        for (int i = 0; i < count; i++) {
            g.drawImage(imgs[0], x, y + BARRIRE_HEAD_HEIGHT + i * BARRIRE_HEIGHT, null);
        }
        rect(g);
        //    ������ͷ
        int y11 = y + height - BARRIRE_HEAD_HEIGHT;
        g.drawImage(imgs[2], x, y11, null);
        x -= speed;
        if (x < -50) {
            visible = false;
        }
    }

    //    ���ƿ����ƶ����ϰ���
    private void drawMobile(Graphics g) {
        //    �������Ҫ�Ŀ���
        int count = (height - BARRIRE_HEAD_HEIGHT) / BARRIRE_HEIGHT;
        //    ������ͷ
        g.drawImage(imgs[1], x, y, null);
        //    forѭ�������ϰ���
        for (int i = 0; i < count; i++) {
            g.drawImage(imgs[0], x, y + BARRIRE_HEAD_HEIGHT + i * BARRIRE_HEIGHT, null);
        }
        rect(g);
        //    ������ͷ
        int y11 = y + height - BARRIRE_HEAD_HEIGHT;
        g.drawImage(imgs[2], x, y11, null);
        x -= speed;
        if (x < -50) {
            visible = false;
        }
        if (mob) {
            y += 5;
            if (y >= 250) {
                mob = false;
            }
        } else if (!mob) {
            y -= 5;
            if (y <= 100) {
                mob = true;
            }
        }
    }

    //    �����ϰ������ײ����
    public void rect(Graphics g) {
        int x1 = this.x;
        int y1 = this.y;
        int w1 = imgs[0].getWidth();
//        g.setColor(Color.blue);
//        g.drawRect(x1, y1, w1, height);
        setRectangle(x1, y1, w1, height);
    }

    //    �ϰ�����β���
    public void setRectangle(int x, int y, int width, int height) {
        rect.x = x;
        rect.y = y;
        rect.width = width;
        rect.height = height;
    }

    //    �ж�ʲôʱ�������һ���ϰ���
    public boolean isInFrame() {
        return 600 - x > 150;
    }

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

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public Rectangle getRect() {
        return rect;
    }
}