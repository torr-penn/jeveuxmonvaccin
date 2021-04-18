package com.gtasoft.jeveuxmonvaccin.lwjgl3;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.gtasoft.jeveuxmonvaccin.resource.NativePlatform;

public class DesktopPlatform implements NativePlatform {
    private static final int iWaitMINUTE = 1;
    static private final int rWaitMINUTE_slow = 120;
    static private final int rWaitMINUTE_fast = 1;
    public static String FILENAMEUSER = "player.dat";
    static int adStatus = -2;
    private static int MINUTE = 1000 * 60;
    int iadStatus = -2;
    long t = 0;
    long it = 0;
    private int rWaitMINUTE = 2;
    private String version = "";
    private String package_name = "";
    private String name = "";
    private String email = "";
    private long localHighscore = 0;
    private long localRecord = 0;
    private String localPerfdate = "";


    /**
     *
     */
    public DesktopPlatform() {


        getRessources();


    }


    public static void writeFile(String fileName, String s) {
        FileHandle file = Gdx.files.local(fileName);
        file.writeString(s, false);

    }

    public static String readFile(String fileName) {
        //System.out.println(" read file " + fileName);
        try {
            FileHandle file = Gdx.files.local(fileName);
            if (file != null && file.exists()) {
                String s = file.readString();
                if (!s.isEmpty()) {
                    return s;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();

        }
        return null;
    }

    @Override
    public void resumeSession() {

    }

    public boolean getRessources() {

        version = "v0.1:1(prod)";//context.getString(R.string.version);
        package_name = "com.gtasoft.jeveuxmonvaccin";//context.getString(R.string.package_name);
        String min = "500";//context.getString(R.string.minute_time);


        int mymin = Integer.parseInt(min);
        MINUTE = mymin;
        return false;

    }


    @Override
    public String getVersion() {
        return version;
    }

    @Override
    public boolean isAndroid() {
        return true;
    }


    @Override
    public void initAlert(int centerid, int vaccineid, String salt, String centerName) {
        System.out.println(" I initialize alerts " + centerid + "/" + vaccineid + "/" + salt + "/");
        System.out.println("       -> " + centerName);
    }

    @Override
    public String infoAlert() {
        return ("" + true + ";ENQUED;" + 5 + ";" + true);
    }

    @Override
    public void stopAlert() {
        System.out.println(" I stop the alerts");


    }
}


