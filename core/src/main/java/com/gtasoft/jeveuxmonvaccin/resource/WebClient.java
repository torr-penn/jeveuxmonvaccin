package com.gtasoft.jeveuxmonvaccin.resource;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.gtasoft.jeveuxmonvaccin.JeVeuxMonVaccin;
import com.gtasoft.jeveuxmonvaccin.setup.Machine;

import java.io.*;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class WebClient {
    public static String serverAddress = "http://www.torr-penn.com";


    private Machine machine = null;
    private JeVeuxMonVaccin app = null;

    public WebClient(JeVeuxMonVaccin app) {
        this.app = app;
        loadMachine();

    }

    public void loadMachine() {

        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

        try {
            FileHandle file;
            file = Gdx.files.local("machine.dat");
            if (file != null && file.exists()) {
                String s = file.readString();
                machine = gson.fromJson(s, Machine.class);
                String mname = machine.getName();
                java.net.InetAddress localMachine = java.net.InetAddress.getLocalHost();
                String mname2 = localMachine.getHostName();
                if (!mname.equals(mname2)) {
                    machine = null;
                }
            }

        } catch (UnknownHostException e) {

            e.printStackTrace();
        } catch (NullPointerException npe) {
        }
        try {
            String salt = null;
            if (machine == null) {
                salt = generateSalt();
                java.net.InetAddress localMachine = java.net.InetAddress.getLocalHost();
                String mname = localMachine.getHostName();
                machine = new Machine(mname);
                machine.setSalt(salt);
                FileHandle file;
                file = Gdx.files.local("machine.dat");
                file.writeString(gson.toJson(machine), false);
            }
        } catch (IOException e) {
            e.printStackTrace();
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
                response.append(line);
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


    public String generateSalt() {
        String name = System.getProperty("user.name");
        String os = System.getProperty("os.name");
        String dig = null;
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("MD5");
            md.update(String.valueOf(os).getBytes());
            md.update(String.valueOf(name).getBytes());
            md.update(String.valueOf(System.currentTimeMillis() + "").getBytes());
            dig = new BigInteger(1, md.digest()).toString(16);

        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            return System.currentTimeMillis() + "";

        }
        return dig;

    }

    public Machine getMachine() {
        return machine;
    }

    public void setMachine(Machine machine) {
        this.machine = machine;
    }

}
