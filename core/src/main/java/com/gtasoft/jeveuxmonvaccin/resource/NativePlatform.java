package com.gtasoft.jeveuxmonvaccin.resource;


public interface NativePlatform {


    String getVersion();

    boolean isAndroid();

    public void initAlert(int centerid, int vaccineid, String salt, String centerName);

    public void stopAlert();

    public String infoAlert();
}
