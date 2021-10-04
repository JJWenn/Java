package com.bird.main;

//避免反复地创建和销毁对象，使用对象池来提前创建好一些对象
//使用的时候从池中获得，使用完毕后，归还

import java.util.ArrayList;
import java.util.List;

public class Barrierpool {
    //    用于管理池中所有对象的容器
    private static List<Barrier> pool = new ArrayList<>();
    //    池中初始的对象个数
    public static final int initCount = 16;
    //    对象池中最大个数
    public static final int maxCount = 20;

    static {
        //    初始化池中的对象
        for (int i = 0; i < initCount; i++) {
            pool.add(new Barrier());
        }
    }

    //    从池中获取一个对象
    public static Barrier getPool() {
        int size = pool.size();
        //    如果池中有对象才可以拿
        if (size > 0) {
            //    移除并返回对象
            System.out.println("拿走一个");
            return pool.remove(size - 1);
        } else {
            //    池中没有对象了，只能new
            System.out.println("新的对象");
            return new Barrier();
        }
    }

    //    将对象归还容器中
    public static void setPool(Barrier barrier) {
        if (pool.size() < maxCount) {
            pool.add(barrier);
            System.out.println("容器归还了");
        }

    }

}
