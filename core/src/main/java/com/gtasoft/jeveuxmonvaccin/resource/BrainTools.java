package com.gtasoft.jeveuxmonvaccin.resource;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

public class BrainTools {


    public static String readFile(String fileName) {
        //System.out.println(" read file " + fileName);
        FileHandle file = Gdx.files.local(fileName);
        if (file != null && file.exists()) {
            String s = file.readString();
            if (s != null && !"".equals(s)) {
                return s.trim();
            }
        }
        return null;
    }


}
