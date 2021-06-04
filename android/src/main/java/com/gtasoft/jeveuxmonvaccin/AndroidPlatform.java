package com.gtasoft.jeveuxmonvaccin;

import androidx.annotation.NonNull;
import androidx.work.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.google.common.util.concurrent.ListenableFuture;
import com.gtasoft.jeveuxmonvaccin.resource.NativePlatform;
import com.gtasoft.jeveuxmonvaccin.resource.WebClient;
import com.gtasoft.jeveuxmonvaccin.worker.AlertManager;
import com.gtasoft.jeveuxmonvaccin.worker.CenterAlerter;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import static androidx.work.WorkManager.getInstance;

public class AndroidPlatform implements NativePlatform {
    @NonNull
    public static final String TAGID = "LookingForVaccine";
    private static final int iWaitMINUTE = 1;
    static private final int rWaitMINUTE_slow = 120;
    static private final int rWaitMINUTE_fast = 1;
    public static String FILENAMEUSER = "player.dat";
    static int adStatus = -2;
    private static int MINUTE = 1000 * 60;
    WebClient wc = null;
    int iadStatus = -2;
    long t = 0;
    long it = 0;
    private AndroidLauncher context;
    private int rWaitMINUTE = 2;
    private String version = "";
    private String package_name = "";
    private String name = "";
    private String email = "";
    private long localHighscore = 0;
    private long localRecord = 0;
    private String localPerfdate = "";
    private AlertManager manager;

    /**
     *
     */
    public AndroidPlatform(AndroidLauncher context) {


        if (context == null) {
            return;
        }
        this.context = context;
        getRessources();


    }

    public static void initFilePlayer() {
        String player = AndroidPlatform.readFile(FILENAMEUSER);
        if (player == null) {
            writeFile(FILENAMEUSER, "anonymous\nanonymous@torr-penn.bzh");
        }
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
                if (s != null && !"".equals(s)) {
                    return s.trim();
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();

        }
        return null;
    }


    public boolean getRessources() {
        if (context == null) {
            return false;
        }

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

    private boolean loadUserLocal() {
        String s = readFile(FILENAMEUSER);
        if (s != null) {
            try {
                String[] values = s.split("\\s*\n\\s*");
                if (values.length >= 2) {
                    if (email != null && email.contains("@")) {
                        name = values[0];
                        email = values[1];

                        return true;
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                name = "anonymous";
                email = "anonymous@torr-penn.bzh";
            }
        }
        initFilePlayer();
        return false;
    }

    @Override
    public void stopAlert() {
        WorkManager.getInstance(context).cancelAllWorkByTag(TAGID);
        System.out.println("******** jeveuxmonvaccin : background Alert check stopped");


    }

    @Override
    public String infoAlert() {

        ListenableFuture<List<WorkInfo>> lwinfo = WorkManager.getInstance(context).getWorkInfosByTag(TAGID); //


        WorkManager wm = WorkManager.getInstance(context);
        WorkInfo.State state = null;
        long tentative = 0;
        boolean running = false;
        boolean infoValid = true;
        try {
            List<WorkInfo> lwi = wm.getWorkInfosByTag(TAGID).get();
            if (!lwi.isEmpty()) {
                WorkInfo wi = lwi.get(0);
                state = wi.getState();
                tentative = wi.getRunAttemptCount();
                running = isWorkScheduled(lwi);
            }
        } catch (ExecutionException | InterruptedException ignored) {
            System.out.println(" pb getting app alert info ");
            ignored.printStackTrace();
            infoValid = false;
        }

        return (infoValid + ";" + state + ";" + tentative + ";" + running);
    }


    private boolean isWorkScheduled(List<WorkInfo> workInfos) {

        boolean running = false;

        if (workInfos == null || workInfos.size() == 0) {
            return false;
        }

        for (WorkInfo workStatus : workInfos) {
            running = workStatus.getState() == WorkInfo.State.RUNNING | workStatus.getState() == WorkInfo.State.ENQUEUED;
        }

        return running;
    }


    @Override
    public void initAlert(int centerid, int vaccineid, String salt, String centerName) {
        PeriodicWorkRequest myWorkRequest;
        if (context == null) {
            System.out.println("alert setup ABORTED lack of context");
            return;
        }
        if (manager == null) {
            manager = new AlertManager(context);
        }

        manager.createNotificationChannel();
        System.out.println("******** Launching background check notifiaction Jeveuxmonvaccin");

        Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.UNMETERED)
                //.setRequiresBatteryNotLow(true)
                .build();

        myWorkRequest =
                new PeriodicWorkRequest.Builder(CenterAlerter.class, 15, TimeUnit.MINUTES)
                        .setConstraints(constraints)
                        .addTag(TAGID)
                        .setInitialDelay(30, TimeUnit.SECONDS)
                        .setBackoffCriteria(
                                BackoffPolicy.LINEAR,
                                OneTimeWorkRequest.MIN_BACKOFF_MILLIS,
                                TimeUnit.MILLISECONDS)
                        .setInputData(
                                new Data.Builder()
                                        .putString("centerID", centerid + "")
                                        .putString("vaccineID", vaccineid + "")
                                        .putString("salt", salt)
                                        .putString("centerName", centerName)
                                        .build()
                        )

                        .build();
        getInstance(context).enqueueUniquePeriodicWork(TAGID, ExistingPeriodicWorkPolicy.REPLACE, myWorkRequest);

    }

}


