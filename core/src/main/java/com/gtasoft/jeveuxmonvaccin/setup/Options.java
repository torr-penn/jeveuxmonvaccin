package com.gtasoft.jeveuxmonvaccin.setup;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import com.gtasoft.jeveuxmonvaccin.center.CenterTools;
import com.gtasoft.jeveuxmonvaccin.center.VaccinationCenter;

public class Options {

    //public final static int NONE = 5;
    public final static int UNDEFINED = 0;
    public final static int ASTRAZENECA = 1;
    public final static int PFIZER = 2;
    public final static int JANSSEN = 3;
    public final static int DIFF_HARD = 5;
    public final static int DIFF_MEDIUM = 3;
    public final static int DIFF_EASY = 1;
    public static String ANON = "Anonymous";
    private static String OP_FNAME = "preferences.dat";
    boolean offline = false;
    boolean isAndroid = true;

    @Expose
    private boolean alert = false;

    @Expose
    private String centerName = "";

    @Expose
    private int checkTotal = 0;

    @Expose
    private int successTotal = 0;


    @Expose
    private boolean phoneRegistered = false;

    @Expose
    private boolean centerRegistered = false;


    @Expose
    private int vaccineId = UNDEFINED;

    @Expose
    private int department = UNDEFINED;

    @Expose
    private int centerId = UNDEFINED;

    @Expose
    private boolean subscriptionPageSeen = false;

    @Expose
    private boolean controlPageSeen = false;


    private CenterTools ct = null;

    private VaccinationCenter centerSelected;


    public Options(boolean isAndroid) {
        this.isAndroid = isAndroid;
        loadPrefs();

    }


    public static String readFile(String fileName) {
        //System.out.println(" read file " + fileName);
        FileHandle file = Gdx.files.local(fileName);
        if (file != null && file.exists()) {
            String s = file.readString();
            if (!s.isEmpty()) {
                return s;
            }
        }
        return null;
    }

    public static void writeFile(String fileName, String s) {
//        System.out.println(" about to write on " + fileName);
        if (Gdx.files == null) {
            System.out.println("--error -   no GDX file handler  exist at this stage");
        }
        FileHandle file = Gdx.files.local(fileName);
        if (file != null && file.exists()) {
            //System.out.println("--- Writing to " + fileName + " exists");
            file.writeString(s, false);
        } else {
            System.out.println("--- NO " + fileName + " SO CREATION OF FILE");
            file.writeString(s, false);
        }

    }

    public void saveOptions() {
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        writeFile(OP_FNAME, gson.toJson(this));
        //writeFile(OP_FNAME, vaccineId + ";" + centerSelected.getGid());
//        if (centerSelected != null) {
        //   System.out.println("  SAVE options done : " + vaccineId + ";" + centerSelected.getCenterId() + " with registration :" + isPhoneRegistered());
//        } else {
        //System.out.println("  SAVE options  at least vaccine done : " + vaccineId);
        //      }

    }

    public void displayOptions() {
        System.out.println("******** -  options here : ");

    }

    public void loadPrefs() {

        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        Options opx = null;
        try {

            String fpref = readFile(OP_FNAME);
            if (fpref != null) {
                opx = gson.fromJson(fpref, Options.class);

            }
            //       System.out.println("file " + OP_FNAME + " contains : " + fpref);
            this.setPhoneRegistered(opx.isPhoneRegistered());
            this.setCenterId(opx.getCenterId());
            this.setVaccineId(opx.getVaccineId());
            this.setDepartment(opx.getDepartment());
            this.setCenterName(opx.getCenterName());
            this.setCheckTotal(opx.getCheckTotal());
            this.setSuccessTotal(opx.getSuccessTotal());
            this.setAlert(opx.isAlert());
            this.setSubscriptionPageSeen(opx.isSubscriptionPageSeen());
            this.setControlPageSeen(opx.isControlPageSeen());
        } catch (Exception e) {
            System.out.println("*** Jeveuxmonvaaccin : erreur file " + " - preferences.dat - " + "cannot be read");
            //e.printStackTrace();
        }
    }


    public boolean isOffline() {
        return offline;
    }

    public void setOffline(boolean offline) {
        this.offline = offline;
    }

    public int getVaccineId() {
        return vaccineId;
    }

    public void setVaccineId(int vaccineId) {
        this.vaccineId = vaccineId;
    }

    public VaccinationCenter getCenterSelected() {
        return centerSelected;
    }


    public void setCenterSelected(VaccinationCenter centerSelected) {

        this.centerSelected = centerSelected;
        if (centerId == UNDEFINED) {
            if (centerSelected != null && centerSelected.getCenterId() != UNDEFINED) {
                centerId = centerSelected.getCenterId();
            }
        }
        if (centerSelected != null) {
            centerName = centerSelected.getName();
        } else {
            centerName = "-";
        }

    }

    public boolean isSubscriptionPageSeen() {
        return subscriptionPageSeen;
    }

    public void setSubscriptionPageSeen(boolean subscriptionPageSeen) {
        this.subscriptionPageSeen = subscriptionPageSeen;
    }

    public int getCenterId() {
        if (centerId == UNDEFINED) {
            if (centerSelected != null && centerSelected.getCenterId() != UNDEFINED) {
                centerId = centerSelected.getCenterId();
            }
        }

        return centerId;
    }

    public void setCenterId(int centerId) {
        this.centerId = centerId;
    }

    public String toString() {

        return "v:" + vaccineId + " - c:" + centerId + " registered : " + isPhoneRegistered();

    }

    public boolean isPhoneRegistered() {
        return phoneRegistered;
    }

    public void setPhoneRegistered(boolean phoneRegistered) {
        this.phoneRegistered = phoneRegistered;
    }

    public int getDepartment() {
        return department;
    }

    public void setDepartment(int department) {
        if (this.getDepartment() != Options.UNDEFINED) {
            if (department != this.department) {
                centerId = Options.UNDEFINED;
                setControlPageSeen(false);
                setSubscriptionPageSeen(false);
            }
        }
        this.department = department;
    }

    public boolean isCenterRegistered() {
        return centerRegistered;
    }

    public void setCenterRegistered(boolean centerRegistered) {
        this.centerRegistered = centerRegistered;
    }

    public String getCenterName() {
        return centerName;
    }

    public void setCenterName(String centerName) {
        this.centerName = centerName;
    }

    public int getCheckTotal() {
        return checkTotal;
    }

    public void setCheckTotal(int checkTotal) {
        this.checkTotal = checkTotal;
    }

    public void incrementCheckTotal() {
        this.checkTotal = this.checkTotal + 1;
    }

    public int getSuccessTotal() {
        return successTotal;
    }

    public void setSuccessTotal(int successTotal) {
        this.successTotal = successTotal;
    }

    public void incrementSuccessTotal() {
        this.successTotal = this.successTotal + 1;
    }

    public boolean isControlPageSeen() {
        return controlPageSeen;
    }

    public void setControlPageSeen(boolean controlPageSeen) {
        this.controlPageSeen = controlPageSeen;
    }

    public boolean isAlert() {
        return alert;
    }

    public void setAlert(boolean alert) {
        this.alert = alert;
    }
}
