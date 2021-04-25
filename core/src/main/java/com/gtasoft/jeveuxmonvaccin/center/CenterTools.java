package com.gtasoft.jeveuxmonvaccin.center;

import com.gtasoft.jeveuxmonvaccin.JeVeuxMonVaccin;
import com.gtasoft.jeveuxmonvaccin.setup.Machine;
import com.gtasoft.jeveuxmonvaccin.setup.Options;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.*;

public class CenterTools {
    public static int NO_LOAD = 0;
    public static int LOADING = 1;
    public static int LOADED = 2;
    private static String URL_DEPARTMENT = "http://www.torr-penn.bzh/jeveuxmonvaccin/department_list.csv";
    private static String CENTER_LIST = "http://www.torr-penn.bzh/jeveuxmonvaccin/";
    private static String CHECK_CENTER = "http://www.torr-penn.com/jeveuxmonvaccin/centerCheck";
    private static String REGISTER_CENTER = "http://www.torr-penn.com/jeveuxmonvaccin/changeCenter";
    private static String INFO_CENTER = "http://www.torr-penn.com/jeveuxmonvaccin/infoCenter";
    ArrayList<Department> listDepartment;
    ArrayList<VaccinationCenter> listCenter;
    Machine machine;
    private int centerStatus;
    private int checkStatus;

    private int lastCheckCode = -1;
    private String checkMsg;
    private String timeMsg;


    private int registerStatus;
    private String registerMsg;

    private int infoStatus;
    private int infoReturnCode;
    private String infoMsg;
    private String infoLastok;
    private String infoClosing;
    private float infoPct;


    // private boolean loadingCenter = false;
    private JeVeuxMonVaccin app;

    public CenterTools() {
        System.out.println("Dummy constructor do not use");
    }

    public CenterTools(Machine machine, JeVeuxMonVaccin app) {
        this.app = app;
        setCenterStatus(NO_LOAD);
        setRegisterStatus(NO_LOAD);
        listDepartment = new ArrayList<>();

        listCenter = null;
        this.machine = machine;
        loadDepartment();
    }

    public static void main(String[] args) {
        CenterTools ct = new CenterTools();
        String s = "{-2;aaa;134;23;--:--:00;877}";
        for (int i = 0; i < 8; i++) {

            System.out.println(" " + ct.extractStrAtIndex(s, i));
            System.out.println("--------------");
        }
    }

    public void process_dept(String s) {
        listDepartment = new ArrayList<>();
        Department ddebut = new Department(0, " -- Choix du département -- ");
        listDepartment.add(ddebut);
        if (s == null) {
            return;
        }
        Scanner scanner = new Scanner(s);
        scanner.useDelimiter("\n|\r\n|\\s+");

        while (scanner.hasNextLine()) {

            String line = scanner.nextLine();
            String[] values = line.split(";");
            if (values != null && values.length == 2) {
                int did = Integer.parseInt(values[0]);
                String dname = values[1];
                Department d = new Department(did, dname);
                // System.out.println(" ---------- Department " + did + " ////// " + dname + "//////// ADDED");
                listDepartment.add(d);
            }
        }
        scanner.close();
    }

    public int getIdFromProvider(String prov) {
        if ("keldoc".equals(prov)) {
            return VaccinationCenter.KELDOC_ID;
        }
        if ("maiia".equals(prov)) {
            return VaccinationCenter.MAIIA_ID;
        }
        if ("doctolib".equals(prov)) {
            return VaccinationCenter.DOCTOLIB_ID;
        }
        return VaccinationCenter.UNKNOWN_ID;
    }

    public void process_center(String s) {
        listCenter = new ArrayList<>();
        Scanner scanner = new Scanner(s);
        //scanner.useDelimiter("\n");
        scanner.useDelimiter("\n|\r\n|\\s+");
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] values = line.split(";");

            if (values != null && (values.length == 12 || values.length == 10)) {
                String cname = values[0];
                int gid = Integer.parseInt(values[7]);
                VaccinationCenter vc = new VaccinationCenter(gid, cname);
                vc.setAddress(values[1]);
                vc.setCity(values[2]);
                vc.setPostalcode(values[3]);
                vc.setClose_date(values[5]);
                vc.setLink(values[6]);
                vc.setProviderId(getIdFromProvider(values[8]));
                vc.setVaccineId(Integer.parseInt(values[9]));

                int vtype = app.getOptions().getVaccineId();
                //  System.out.println(" center ids: " + vc.getCenterId() + " -- " + vc.getVaccineId() + " -- " + gid);
                if (vtype == Options.ASTRAZENECA && vc.getVaccineId() == VaccinationCenter.astrazenecaID) {
                    listCenter.add(vc);
                } else if (vtype == Options.PFIZER && vc.getVaccineId() == VaccinationCenter.pfizerID) {
                    listCenter.add(vc);
                } else if (vtype == Options.JANSSEN && vc.getVaccineId() == VaccinationCenter.janssenID) {
                    listCenter.add(vc);
                }
            }
        }
        scanner.close();
        Collections.sort(listCenter, VaccinationCenter.CenterComparator);

    }

    public Department getDepartment(String sid) {
        if (sid != null) {
            try {
                int id = Integer.parseInt(sid);
                if (id != 0 && listDepartment != null) {


                    Iterator it = listDepartment.iterator();
                    while (it.hasNext()) {
                        Department dep = (Department) it.next();
                        if (dep.getId() == id) {
                            return dep;
                        }
                    }
                }
            } catch (NumberFormatException nfe) {
                System.out.println(" bad dept choice");
                nfe.printStackTrace();
            }
        }
        return null;
    }

    public void loadDepartment() {

        Timer validTimer = new Timer();

        validTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    String urlParameters;
                    urlParameters = "" + URLEncoder.encode(machine.getSalt(), "UTF-8");
                    String res = executePost(URL_DEPARTMENT, urlParameters);
                    //System.out.println("calling " + URL_DEPARTMENT + "?"                            + urlParameters);
                    //System.out.println(" result is " + res + " must be 1 to be connected");
                    process_dept(res);

                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

            }
        }, 5);

    }

    public void controlCenter() {
        if (app.getMachine() == null) {
            System.out.println("houston pb no salt");
            return;
        }
        if (app.getOptions().getCenterId() == Options.UNDEFINED || app.getOptions().getVaccineId() == Options.UNDEFINED) {
            System.out.println("houston pb no center");
            return;
        }

        if (app.getOptions().isPhoneRegistered()) {
            Timer validTimer = new Timer();
            validTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    try {
                        String urlParameters;
                        urlParameters = "phonesalt=" + URLEncoder.encode(app.getMachine().getSalt(), "UTF-8") +
                                "&cid=" + app.getOptions().getCenterId() + "&vid=" + app.getOptions().getVaccineId();

                        //System.out.println(" calling  center : " + CHECK_CENTER + "?" + urlParameters);
                        String res = executePost(CHECK_CENTER, urlParameters);
                        // System.out.println("*** check center  Result : " + res);
                        processCheck(res);
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }

                }
            }, 5);
        } else {
            System.out.println("phone is a not already registered ");
            processCheck(null);
        }


    }

    public void registerCenter() {
        if (app.getMachine() == null) {
            System.out.println("houston pb no salt");
            return;
        }
        if (app.getOptions().getCenterId() == Options.UNDEFINED || app.getOptions().getVaccineId() == Options.UNDEFINED) {
            System.out.println("houston pb no center");
            return;
        }

        if (app.getOptions().isPhoneRegistered()) {
            if (registerStatus != LOADING) {
                registerStatus = LOADING;
                Timer validTimer = new Timer();
                validTimer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        try {
                            String urlParameters;
                            urlParameters = "phonesalt=" + URLEncoder.encode(app.getMachine().getSalt(), "UTF-8") +
                                    "&cid=" + app.getOptions().getCenterId() + "&vid=" + app.getOptions().getVaccineId();

                            //System.out.println(" calling  center : " + REGISTER_CENTER + "?" + urlParameters);
                            String res = executePost(REGISTER_CENTER, urlParameters);
                            //     System.out.println("*** register center  Result : " + res);
                            processRegister(res);

                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }

                    }
                }, 100);
            } else {
                System.out.println("register phone and center in progress");
                processRegister(null);

            }

        } else {
            System.out.println("register phone not performed");
            processRegister(null);
        }


    }

    public void processRegister(String res) {

        if (res != null) {
            if (res.startsWith("{-")) {
                setRegisterStatus(NO_LOAD);
                setRegisterMsg("Préparation de la recherche : Erreur\n recommencez les étapes précédentes");
            } else {
                setRegisterStatus(LOADED);
                setRegisterMsg("ok");
            }
        } else {
            setRegisterStatus(NO_LOAD);
            setRegisterMsg("Préparation de la recherche : Erreur\n recommencez les étapes précédentes SVP");

        }

    }

    private String partTwo(String str) {
        String strx = null;
        int idxSemicolon = str.indexOf(";");
        if (idxSemicolon != -1) {
            strx = str.substring(idxSemicolon + 1, str.length() - 2);
            idxSemicolon = strx.indexOf(";");
            if (idxSemicolon != -1) {
                strx = strx.substring(0, idxSemicolon);
                return strx;
            } else {
                return strx;
            }
        } else {
            return null;
        }

    }

    private String partThree(String str) {
        String strx;
        int idxSemicolon = str.indexOf(";");
        if (idxSemicolon != -1) {
            strx = str.substring(idxSemicolon + 1, str.length() - 2);
            idxSemicolon = strx.indexOf(";");
            if (idxSemicolon != -1) {
                strx = strx.substring(idxSemicolon + 1, strx.length());
                return strx;
            } else {
                return null;
            }
        } else {
            return null;
        }

    }

    private int codeOne(String str) {
        String strx;
        int idxSemicolon = str.indexOf(";");
        if (idxSemicolon != -1) {
            strx = str.substring(1, idxSemicolon);

            idxSemicolon = strx.indexOf(";");
            try {
                int res = Integer.parseInt(strx);
                return res;
            } catch (Exception e) {
                return -1;
            }
        } else {
            return -1;
        }

    }

    public void processCheck(String res) {

        if (res != null) {
            app.getOptions().incrementCheckTotal();
            if (res.startsWith("{-")) {
                setTimeMsg("");
                setCheckStatus(-2);
                setLastCheckCode(codeOne(res));
                setCheckMsg(partTwo(res));
                if (getCheckMsg() == null) {
                    setCheckMsg("Erreur");
                }
                setTimeMsg(partThree(res));

            } else {
                setCheckMsg("");
                setTimeMsg(partThree(res));
                setLastCheckCode(codeOne(res));
                if (getTimeMsg() == null) {
                    setCheckStatus(-2);
                    setCheckMsg("Erreur");
                } else {
                    app.getOptions().incrementCheckTotal();
                    app.getOptions().incrementSuccessTotal();
                    setCheckStatus(1);
                    setCheckMsg(partTwo(res));
                    setTimeMsg(partThree(res));
                }
            }
        } else {
            setLastCheckCode(-1);
            setCheckStatus(-1);
            setCheckMsg("Erreur");
            setTimeMsg("-");
        }

    }

    public void loadCenter(Department d) {
        if (d != null && d.getId() != Options.UNDEFINED) {
            loadCenter(d.getId());
        }
    }

    public void selectCenter(int centerId, int vaccineId) {
        //System.out.println(" slect center called " + centerId + " / " + vaccineId);
        if (centerId != Options.UNDEFINED && vaccineId != Options.UNDEFINED) {

            if (app.getOptions().getCenterSelected() == null) {
                Iterator<VaccinationCenter> it = listCenter.iterator();
                while (it.hasNext()) {
                    VaccinationCenter vc = it.next();
                    if (vc.getCenterId() == centerId && vc.getVaccineId() == vaccineId) {
                        app.getOptions().setCenterSelected(vc);
                        return;
                    }
                }
            }
        }
    }

    public void loadCenter(int depId) {
        //System.out.println(" load center from :" + depId);
        if (getCenterStatus() != LOADING && getCenterStatus() != LOADED) {
            setCenterStatus(LOADING);

            Timer validTimer = new Timer();

            validTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    try {
                        String urlParameters;
                        urlParameters = "" + URLEncoder.encode(machine.getSalt(), "UTF-8");
                        String res = executePost(CENTER_LIST + "c" + depId + ".csv", urlParameters);
                        //System.out.println(" calling  center : " + CENTER_LIST + "c" + depId + ".csv?" + urlParameters);
                        process_center(res);
                        //System.out.println(" result is " + res);
                        setCenterStatus(LOADED);

                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }

                }
            }, 5);
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

    public void infoCenter() {
        if (app.getMachine() == null) {
            System.out.println("houston pb no salt");
            return;
        }
        if (app.getOptions().getCenterId() == Options.UNDEFINED || app.getOptions().getVaccineId() == Options.UNDEFINED) {
            System.out.println("houston pb no center");
            return;
        }

        if (getInfoStatus() != LOADING) {
            setInfoStatus(LOADING);
            Timer validTimer = new Timer();
            validTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    try {
                        String urlParameters;
                        urlParameters = "phonesalt=" + URLEncoder.encode(app.getMachine().getSalt(), "UTF-8") +
                                "&cid=" + app.getOptions().getCenterId() + "&vid=" + app.getOptions().getVaccineId();

                        //                      System.out.println(" calling  center : " + INFO_CENTER + "?" + urlParameters);
                        String res = executePost(INFO_CENTER, urlParameters);
//                        System.out.println("*** info center  Result : " + res);
                        processInfo(res);
                        setInfoStatus(LOADED);
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }

                }
            }, 5);
        } else {
            System.out.println(" gathering info");
            processInfo(null);

        }


    }

    private int extractCodeAtIndex(String s, int fieldx) {
        String strx = s;
        int iw = 0;
        if (s == null || "".equals(s)) {
            return -1;
        }
        int idx = s.indexOf(";");
        String found = "";
        if (fieldx == 0 && idx > 0) {
            found = strx.substring(1, idx);
        }
        while (iw != fieldx) {
            iw = iw + 1;
            idx = strx.indexOf(";");
            if (idx > 0) {
                strx = strx.substring(idx + 1, strx.length());
                if (strx == null || strx.length() == 0) {
                    return -1;
                }
            } else {
                return -1;
            }
        }
        idx = strx.indexOf(";");
        if (idx > 0) {
            found = strx.substring(0, idx);
        } else {
            found = strx.substring(0, strx.length() - 1);


        }
        if (found == null || found.length() == 0) {
            return -1;
        }
        if (found.indexOf("{") == 0) {
            found = found.substring(1);
        }
        if (found.indexOf("}") == found.length()) {

            found = found.substring(0, found.length() - 1);
        }
        int res = -1;
        try {
            res = Integer.parseInt(found);
        } catch (NumberFormatException nfe) {
            res = -3;
        }
        return res;
    }

    private String extractStrAtIndex(String s, int fieldx) {
        String strx = s;
        int iw = 0;
        if (s == null || "".equals(s)) {
            return "";
        }
        int idx = s.indexOf(";");
        String found = "";
        if (fieldx == 0 && idx > 0) {
            found = strx.substring(1, idx);
        }
        while (iw != fieldx) {
            iw = iw + 1;
            idx = strx.indexOf(";");
            if (idx > 0) {
                strx = strx.substring(idx + 1, strx.length());
                if (strx == null || strx.length() == 0) {
                    return "";
                }
            } else {
                return "";
            }
        }
        idx = strx.indexOf(";");
        if (idx > 0) {
            found = strx.substring(0, idx);
        } else {
            found = strx.substring(0, strx.length() - 1);


        }
        if (found == null || found.length() == 0) {
            return "";
        }
        if (found.indexOf("{") == 0) {
            found = found.substring(1);
        }
        if (found.indexOf("}") == found.length()) {

            found = found.substring(0, found.length() - 1);
        }
        return found;
    }

//    private String partTwo(String str) {
//        String strx = null;
//        int idxSemicolon = str.indexOf(";");
//        if (idxSemicolon != -1) {
//            strx = str.substring(idxSemicolon + 1, str.length() - 2);
//            idxSemicolon = strx.indexOf(";");
//            if (idxSemicolon != -1) {
//                strx = strx.substring(0, idxSemicolon);
//                return strx;
//            } else {
//                return strx;
//            }
//        } else {
//            return null;
//        }
//
//    }


    // resp.getWriter().print(
// "{-1;nouveau centre;" + vc.getTotal() + ";" + vc.getNbsuccess() + ";[--:--:--];" + vc.getClose_date() + "}");
    public void processInfo(String res) {

        if (res != null) {
            if (res.startsWith("{-")) {


                setInfoMsg(extractStrAtIndex(res, 1));
                setInfoClosing("");
                setInfoLastok("--");
                setInfoPct(-1);
                setInfoReturnCode(codeOne(res));
                if (getInfoMsg() == null) {
                    setInfoMsg("Erreur");
                }
            } else {
                setInfoMsg(extractStrAtIndex(res, 1));
                setInfoReturnCode(extractCodeAtIndex(res, 0));
                int div = extractCodeAtIndex(res, 2);
                if (div > 0) {
                    setInfoPct((float) (extractCodeAtIndex(res, 3) * 1.0f / div));
                } else {
                    setInfoPct(0);
                }
                setInfoLastok(extractStrAtIndex(res, 4));
                setInfoClosing(extractStrAtIndex(res, 5));
            }
        } else {
            setInfoMsg("Erreur");
            setInfoClosing("");
            setInfoLastok("--");
            setInfoPct(-1);
            setInfoReturnCode(codeOne(res));
        }

    }


    public ArrayList<Department> getListDepartment() {
        return listDepartment;
    }

    public ArrayList<VaccinationCenter> getListCenter() {
        return listCenter;
    }


    public int getCenterStatus() {
        return centerStatus;
    }

    public void setCenterStatus(int centerStatus) {

//        System.out.println(" CENTER STATUS CHANGE to :" + centerStatus);
        this.centerStatus = centerStatus;
    }

    public int getRegisterStatus() {
        return registerStatus;
    }

    public void setRegisterStatus(int registerStatus) {
        this.registerStatus = registerStatus;
    }

    public String getRegisterMsg() {
        return registerMsg;
    }

    public void setRegisterMsg(String registerMsg) {
        this.registerMsg = registerMsg;
    }

    public int getCheckStatus() {
        return checkStatus;
    }

    public void setCheckStatus(int checkStatus) {
        this.checkStatus = checkStatus;
    }

    public String getCheckMsg() {
        return checkMsg;
    }

    public void setCheckMsg(String checkMsg) {
        this.checkMsg = checkMsg;
    }

    public String getTimeMsg() {
        return timeMsg;
    }

    public void setTimeMsg(String timeMsg) {
        this.timeMsg = timeMsg;
    }

    public int getLastCheckCode() {
        return lastCheckCode;
    }

    public void setLastCheckCode(int lastCheckCode) {
        this.lastCheckCode = lastCheckCode;
    }

    public int getInfoStatus() {
        return infoStatus;
    }

    public void setInfoStatus(int infoStatus) {
        this.infoStatus = infoStatus;
    }

    public int getInfoReturnCode() {
        return infoReturnCode;
    }

    public void setInfoReturnCode(int infoReturnCode) {
        this.infoReturnCode = infoReturnCode;
    }

    public String getInfoMsg() {
        return infoMsg;
    }

    public void setInfoMsg(String infoMsg) {
        this.infoMsg = infoMsg;
    }

    public String getInfoLastok() {
        return infoLastok;
    }

    public void setInfoLastok(String infoLastok) {
        this.infoLastok = infoLastok;
    }

    public String getInfoClosing() {
        return infoClosing;
    }

    public void setInfoClosing(String infoClosing) {
        this.infoClosing = infoClosing;
    }

    public float getInfoPct() {
        return infoPct;
    }

    public void setInfoPct(float infoPct) {
        this.infoPct = infoPct;
    }
}
