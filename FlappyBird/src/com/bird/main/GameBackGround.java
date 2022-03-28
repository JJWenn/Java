package com.bird.main;

//��Ϸ������

import com.bird.util.Constant;
import com.bird.util.GameUtil;

import java.awt.*;
import java.awt.image.BufferedImage;

public class GameBackGround {
    //    ������Ҫ����ԴͼƬ
    private BufferedImage bkimg;

    //    ������(��ʼ����Դ)
    public GameBackGround() {
        bkimg = GameUtil.loadBufferedImage(Constant.BK_IMG_PATH);
    }

    //    ����ͼƬ
    public void draw(Graphics g) {
        //    �õ�ͼƬ�ĸ߶ȺͿ��
        int height = bkimg.getHeight();
        int width = bkimg.getWidth();
        //    ����Ҫ��ͼƬ����Դ
        int count = Constant.FRAM_WIDTH / width + 1;
        for (int i = 0; i < count; i++) {
            g.drawImage(bkimg, width * i, Constant.FRAM_HEIGHT - height, null);
        }
    }
}
