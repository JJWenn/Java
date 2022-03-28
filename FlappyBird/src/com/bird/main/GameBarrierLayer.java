package com.bird.main;

//��Ϸ��ǰ����

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

    //    �����ϰ���
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
            g.setFont(new Font("΢���ź�", 1, 20));
            g.drawString("����ˣ�" + differ + "��", 30, 80);

            txt = getTxt();
            if (differ <= txt) {
                g.drawString("��߳ɼ���" + txt, 200, 80);
            }else{
                setTxt(String.valueOf(differ));
                g.drawString("��߳ɼ���" + getTxt(), 200, 80);
            }
            //    �ж����һ���ϰ����Ƿ���ȫ������Ļ��
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

    //    ���ڵõ��ļ��е�����
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

    //    ���ڴ��沼��
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

    //    ���ڴӳ��л�ȡ���󣬲��Ѳ�����װ��barrier����barriers������
    public void insert(int x, int y, int num, int type) {
        Barrier top = Barrierpool.getPool();
        top.setX(x);
        top.setY(y);
        top.setHeight(num);
        top.setType(type);
        top.setVisible(true);
        barriers.add(top);
    }

    //    �Ϸ��ϰ��߶�
    private int numberTop;
    //    �·��ϰ��߶�
    private int numberDown;

    private int number;

    //    ��������100-500֮�������߶�
    public void ran() {
        numberTop = random.nextInt(400) + 100;
        numberDown = random.nextInt(400) + 100;
        number = random.nextInt(500);
        //    ����ܵ��غϣ����������
        if (numberTop + numberDown > 450) {
            ran();
        }
    }

    //    �ж�С�����ϰ��﷢����ײ
    public boolean collideBird(Bird bird) {
        for (int i = 0; i < barriers.size(); i++) {
            Barrier barrier = barriers.get(i);
            //    �жϾ����Ƿ��ཻ
            if (barrier.getRect().intersects(bird.getRect())) {
                System.out.println("ײ������");
                bird.life = false;
            }
        }
        return false;
    }

    //    ��������ϰ���ĳ���
    public void restant() {
        barriers.clear();
    }

}
