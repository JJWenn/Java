package com.iflytek.lfasr.demo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.iflytek.msp.lfasr.LfasrClient;
import com.iflytek.msp.lfasr.model.Message;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class MyThread extends Thread {
//    private static final String APP_ID = "2bc67103";
//    private static final String SECRET_KEY = "ff93ebec71eb313d25e2e7e39313adfd";
    private static final String APP_ID = "de5cee06";
    private static final String SECRET_KEY = "19ed34501c04a57a2424fe77ee54a124";
    private String FILE_DIR;
    private String FILE_NAME;
    private String AUDIO_FILE_PATH;
    public String data_result;

    public MyThread(String fileDir, String fileName) {
        this.FILE_DIR = fileDir;
        this.FILE_NAME = fileName;
        this.AUDIO_FILE_PATH = this.FILE_DIR + "/" + this.FILE_NAME;
    }

    public void run() {
        System.out.println(currentThread().getName() + "正在执行！");
        // System.out.println(currentThread().getName() + AUDIO_FILE_PATH);
        // 示例-4：使用性能调优参数
        try {
             performance();
            // standard();
            // System.out.println("结果获取成功！");
            String words = "";
            String[] result_split = data_result.split(",");
            for (String split : result_split) {
                if (split.startsWith("\"onebest\":")) {
                    words += split.substring(10);
                }
            }
            writeTXT(FILE_DIR, "result", words);

            System.out.println(currentThread().getName() + "执行完成！");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 性能调优参数，调用样例
     *
     * @throws InterruptedException e
     */
    private void performance() throws InterruptedException {
        //1、创建客户端实例, 设置性能参数
        LfasrClient lfasrClient =
                LfasrClient.getInstance(
                        APP_ID,
                        SECRET_KEY,
                        10, //线程池：核心线程数
                        50, //线程池：最大线程数
                        50, //网络：最大连接数
                        10000, //连接超时时间
                        30000, //响应超时时间
                        null);
        //LfasrClient lfasrClient = LfasrClient.getInstance(APP_ID, SECRET_KEY);

        //2、上传
        //2.1、设置业务参数
        Map<String, String> param = new HashMap<>(16);
        //语种： cn-中文（默认）;en-英文（英文不支持热词）
        param.put("language", "cn");
        //垂直领域个性化：法院-court；教育-edu；金融-finance；医疗-medical；科技-tech
        //param.put("pd","finance");

        Message task = lfasrClient.upload(
                AUDIO_FILE_PATH
                , param);
        String taskId = task.getData();
        System.out.println(currentThread().getName() + "转写任务 taskId：" + taskId);

        //3、查看转写进度
        int status = 0;
        while (status != 9) {
            Message message = lfasrClient.getProgress(taskId);
            JSONObject object = JSON.parseObject(message.getData());
            status = object.getInteger("status");
            System.out.println(message.getData());
            TimeUnit.SECONDS.sleep(2);
        }
        //4、获取结果
        Message result = lfasrClient.getResult(taskId);
        data_result = result.getData();
        System.out.println(currentThread().getName() + "转写结果: \n" + data_result);

        //退出程序，关闭线程资源，仅在测试main方法时使用。
        // System.exit(0);
    }

    /**
     * 简单 demo 样例
     *
     * @throws InterruptedException e
     */
//    private static void standard() throws InterruptedException {
//        //1、创建客户端实例
//        LfasrClient lfasrClient = LfasrClient.getInstance(APP_ID, SECRET_KEY);
//
//        //2、上传
//        Message task = lfasrClient.upload(AUDIO_FILE_PATH);
//        String taskId = task.getData();
//        System.out.println(currentThread().getName() + "转写任务 taskId：" + taskId);
//
//        //3、查看转写进度
//        int status = 0;
//        while (status != 9) {
//            Message message = lfasrClient.getProgress(taskId);
//            JSONObject object = JSON.parseObject(message.getData());
//            if (object ==null) throw new LfasrException(message.toString());
//            status = object.getInteger("status");
//            System.out.println(message.getData());
//            TimeUnit.SECONDS.sleep(2);
//        }
//        //4、获取结果
//        Message result = lfasrClient.getResult(taskId);
//        data_result = result.getData();
//        System.out.println(currentThread().getName() + "转写结果: \n" + data_result);
//
//        //退出程序，关闭线程资源，仅在测试main方法时使用。
//        // System.exit(0);
//    }

    public static void writeTXT(String path, String title, String content) {
        try {
            // 防止文件建立或读取失败，用catch捕捉错误并打印，也可以throw
            /* 写入Txt文件 */
            File writename = new File(path);// 相对路径，如果没有则要建立一个新的output。txt文件
            if (!writename.exists()) {
                writename.mkdirs();
            }
            writename = new File(path + "/" + title + ".txt");// 相对路径，如果没有则要建立一个新的output。txt文件
            writename.createNewFile(); // 创建新文件
            BufferedWriter out = new BufferedWriter(new FileWriter(writename));
            out.write(content); // \r\n即为换行
            out.flush(); // 把缓存区内容压入文件
            out.close(); // 最后记得关闭文件

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
