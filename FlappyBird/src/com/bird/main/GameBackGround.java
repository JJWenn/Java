package com.bird.main;

//游戏背景类

import com.bird.util.Constant;
import com.bird.util.GameUtil;

import java.awt.*;
import java.awt.image.BufferedImage;

public class GameBackGround {
    //    背景需要的资源图片
    private BufferedImage bkimg;

    //    构造器(初始化资源)
    public GameBackGround() {
        bkimg = GameUtil.loadBufferedImage(Constant.BK_IMG_PATH);
    }

    //    绘制图片
    public void draw(Graphics g) {
        //    得到图片的高度和宽度
        int height = bkimg.getHeight();
        int width = bkimg.getWidth();
        //    所需要的图片的资源
        int count = Constant.FRAM_WIDTH / width + 1;
        for (int i = 0; i < count; i++) {
            g.drawImage(bkimg, width * i, Constant.FRAM_HEIGHT - height, null);
        }
    }
}
