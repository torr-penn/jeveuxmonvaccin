package com.gtasoft.jeveuxmonvaccin.worker;


import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.work.Worker;
import androidx.work.WorkerParameters;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.gtasoft.jeveuxmonvaccin.AndroidLauncher;
import com.gtasoft.jeveuxmonvaccin.R;
import com.gtasoft.jeveuxmonvaccin.setup.Options;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class CenterAlerter extends Worker {
    private static String CHECK_CENTER = "http://www.torr-penn.bzh/jeveuxmonvaccin/center/centerCheck.php";
    private static String COUNTERFILENAME = "alertcounter.dat";
    WorkerParameters workerParams;
    private Context mycontext;
    private long lastPhoneControl = 0;
    private String displayDate = "";


    public CenterAlerter(@NonNull Context context, @NonNull WorkerParameters params) {


        super(context, params);
        this.mycontext = context;
        this.workerParams = params;


    }

    public void writeFile(String fileName, String s) {
        if (Gdx.files == null) {
            try {
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(mycontext.openFileOutput(fileName, Context.MODE_PRIVATE));
                outputStreamWriter.write(s);
                outputStreamWriter.close();
            } catch (IOException e) {
                System.out.println("*** File write failed: " + e.toString());
            }
            return;
        }
        FileHandle file = Gdx.files.local(fileName);
        if (file != null && file.exists()) {
            //System.out.println("--- Writing to " + fileName + " exists");
            file.writeString(s, false);
        } else {
            System.out.println("*** NO " + fileName + " SO CREATION OF FILE");
            file.writeString(s, false);
        }

    }


    public String readFile(String fileName) {

        if (Gdx.files != null) {
            FileHandle file = Gdx.files.local(fileName);
            if (file != null && file.exists()) {
                String s = file.readString();
                if (s != null && !"".equals(s)) {
                    return s;
                }
            }
        } else {
            if (mycontext != null) {
                String ret = "";

                try {
                    InputStream inputStream = mycontext.openFileInput(fileName);
                    if (inputStream != null) {
                        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                        String receiveString = "";
                        StringBuilder stringBuilder = new StringBuilder();
                        while ((receiveString = bufferedReader.readLine()) != null) {
                            stringBuilder.append(receiveString);
                        }
                        inputStream.close();
                        ret = stringBuilder.toString();
                    }
                } catch (FileNotFoundException e) {
                    System.out.println("*** File not found: " + e.toString());
                } catch (IOException e) {
                    System.out.println("*** Can not read file: " + e.toString());
                }
                return ret;
            }
        }
        return null;
    }

    @NonNull
    @Override
    public Result doWork() {
        System.out.println("**************JEVEUXMONVACCIN--- Remote control");
        int centerID = 0;
        int vaccineID = 0;
        String salt = null;
        String centerName = null;


        if (workerParams != null) {
            centerID = Integer.parseInt(workerParams.getInputData().getString("centerID"));
            vaccineID = Integer.parseInt(workerParams.getInputData().getString("vaccineID"));
            salt = workerParams.getInputData().getString("salt");
            centerName = workerParams.getInputData().getString("centerName");
            if (centerID != Options.UNDEFINED && vaccineID != Options.UNDEFINED && salt != null) {

                if (controlCenter(centerID, vaccineID, salt)) {
                    Intent intent = new Intent(mycontext, AndroidLauncher.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    PendingIntent pendingIntent = PendingIntent.getActivity(mycontext, 0, intent, 0);

                    NotificationCompat.Builder builder = new NotificationCompat.Builder(mycontext, AlertManager.CHANNEL_ID)
                            //.setSmallIcon(R.drawable.notification_icon)
                            .setSmallIcon(R.drawable.ic_launcher)
                            .setContentTitle("RDV : " + getDisplayDate() + " !")
                            .setContentText("Faites vite... [ " + centerName + "]")
                            .setPriority(NotificationCompat.PRIORITY_HIGH)
                            .setContentIntent(pendingIntent)
                            .setAutoCancel(true);

                    NotificationManagerCompat notificationManager = NotificationManagerCompat.from(mycontext);
                    notificationManager.notify(1, builder.build());
                }
            }
        }

        return Result.success();
    }

    public Context getMycontext() {
        return mycontext;
    }

    public void setMycontext(Context mycontext) {
        this.mycontext = mycontext;
    }

    public boolean controlCenter(int centerid, int vaccineid, String salt) {
        if (salt == null) {
            System.out.println("houston pb no salt");
            return false;
        }
        if (centerid == Options.UNDEFINED || vaccineid == Options.UNDEFINED) {
            System.out.println("houston pb no center");
            return false;
        }

        try {
            String urlParameters;
            urlParameters = "phonesalt=" + URLEncoder.encode(salt, "UTF-8") +
                    "&cid=" + centerid + "&vid=" + vaccineid;

            System.out.println(" calling  center : " + CHECK_CENTER + "?" + urlParameters);
            String res = executePost(CHECK_CENTER, urlParameters);
            System.out.println("*** check center  Result : " + res);
            incrementCounter();
            return processCheck(res);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


        return false;
    }

    public boolean processCheck(String res) {

        if (res != null) {
            if (res.startsWith("-")) {
                return false;
            } else {
                String sd = part(res, 4);
                if (sd == null || "".equals(sd.trim())) {
                    return false;
                } else {
                    setDisplayDate(sd);
                    return true;
                }
            }
        }
        return false;

    }

    private String part(String str, int x) {
        if (x < 1) {
            return null;
        }
        int idxSemicolon = str.indexOf(";");
        if (idxSemicolon != -1) {
            String ret[] = str.split(";");
            if (ret.length >= x) {
                return ret[x - 1].trim();
            } else {
                return null;
            }
        } else {
            return null;
        }

    }

    private String partThree(String str) {

        int idxSemicolon = str.indexOf(";");
        if (idxSemicolon != -1) {
            String ret[] = str.split(";");
            if (ret.length > 2) {
                return ret[2].trim();
            } else {
                return null;
            }


        } else {
            return null;
        }

    }

    public String executePost(String targetURL, String urlParameters) {
        HttpURLConnection connection = null;
        try {
            // Create connection
            URL url = new URL(targetURL);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            connection.setRequestProperty("Content-Length", Integer.toString(urlParameters.getBytes().length));
//            connection.setRequestProperty("Content-Language", "en-US");

            connection.setUseCaches(false);
            connection.setDoOutput(true);

            // Send request
            DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
            wr.writeBytes(urlParameters);
            wr.close();

            // Get Response
            InputStream is = connection.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            StringBuilder response = new StringBuilder(); // or StringBuffer if
            // not Java 5+
            String line;
            while ((line = rd.readLine()) != null) {
                response.append(line + "\n");
            }
            rd.close();
            return response.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }

    }

    private void incrementCounter() {
        String value = readFile(COUNTERFILENAME);
        try {
            int l = 0;
            if (value != null) {
                l = Integer.parseInt(value);
            } else {
                l = 0;
            }
            l = l + 1;
            writeFile(COUNTERFILENAME, l + "");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public String getDisplayDate() {
        return displayDate;
    }

    public void setDisplayDate(String displayDate) {
        this.displayDate = displayDate;
    }
}

