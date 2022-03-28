package com.bird.main;

//���ⷴ���ش��������ٶ���ʹ�ö��������ǰ������һЩ����
//ʹ�õ�ʱ��ӳ��л�ã�ʹ����Ϻ󣬹黹

import java.util.ArrayList;
import java.util.List;

public class Barrierpool {
    //    ���ڹ���������ж��������
    private static List<Barrier> pool = new ArrayList<>();
    //    ���г�ʼ�Ķ������
    public static final int initCount = 16;
    //    �������������
    public static final int maxCount = 20;

    static {
        //    ��ʼ�����еĶ���
        for (int i = 0; i < initCount; i++) {
            pool.add(new Barrier());
        }
    }

    //    �ӳ��л�ȡһ������
    public static Barrier getPool() {
        int size = pool.size();
        //    ��������ж���ſ�����
        if (size > 0) {
            //    �Ƴ������ض���
            System.out.println("����һ��");
            return pool.remove(size - 1);
        } else {
            //    ����û�ж����ˣ�ֻ��new
            System.out.println("�µĶ���");
            return new Barrier();
        }
    }

    //    ������黹������
    public static void setPool(Barrier barrier) {
        if (pool.size() < maxCount) {
            pool.add(barrier);
            System.out.println("�����黹��");
        }

    }

}
