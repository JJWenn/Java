package com.bird.main;

//��Ϸ��ʱ��

public class GameTime {
    //    ��ʼ
    private long beginTime;
    //    ����
    private long endTime;
    //    ʱ���
    private long differ;

    public GameTime() {
    }

    public void begin() {
        beginTime = System.currentTimeMillis();
    }

    public long differ() {
        endTime = System.currentTimeMillis();
        return differ = (endTime - beginTime) / 1000;
    }


}

