package com.iflytek.lfasr.demo;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GetFileInfo {
    public static String path = "src/main/resources/audio/recording"; // 路径
//    public static String path = "src/main/resources/audio/test"; // 路径


    public static Map<String, List<String>> getFileInfo() {
        Map<String, List<String>> map = new HashMap<>();
        List<String> list = new ArrayList<>();
        List<String> fileDir = new ArrayList<>();
        List<String> fileName = new ArrayList<>();

        File f = new File(path);
        if (!f.exists()) {
            System.out.println(path + " not exists");
            return map;
        }
        File fa[] = f.listFiles();
        for (int i = 0; i < fa.length; i++) {
            File fs = fa[i];
            list.add(fs.getName());
        }
        for (String s : list) {
            String filepath = path + "/" +s;
            f = new File(filepath);
            File fl[] = f.listFiles();
            fileName.add(fl[0].getName());
            fileDir.add(filepath);
        }
        map.put("dir", fileDir);
        map.put("name", fileName);
        return map;
    }

}
