package com.iflytek.lfasr.demo;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.iflytek.lfasr.demo.GetFileInfo.getFileInfo;

public class MyThreadPool {
    private static Map<String, List<String>> fileInfoMap = getFileInfo();
    private static List<String> fileDir = fileInfoMap.get("dir");
    private static List<String> fileName = fileInfoMap.get("name");

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(5);

        for (int i = 0; i < fileDir.size(); i++) {
            executorService.execute(new MyThread(fileDir.get(i), fileName.get(i)));
        }
        executorService.shutdown();
    }
}
