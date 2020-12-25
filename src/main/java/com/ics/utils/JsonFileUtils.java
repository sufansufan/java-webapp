package com.ics.utils;

import com.alibaba.fastjson.JSONObject;
import org.springframework.core.io.Resource;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class JsonFileUtils {


    public static JSONObject jsonRead(Resource data){
        File file = null;
        try {
            file = data.getFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scanner scanner = null;
        StringBuilder buffer = new StringBuilder();
        try {
            scanner = new Scanner(file, "utf-8");
            while (scanner.hasNextLine()) {
                buffer.append(scanner.nextLine());
            }
        } catch (Exception e) {

        } finally {
            if (scanner != null) {
                scanner.close();
            }
        }

        return getJsonObject(buffer);
    }

    private static JSONObject getJsonObject(StringBuilder buffer) {
        return JSONObject.parseObject(buffer.toString());
    }
}
