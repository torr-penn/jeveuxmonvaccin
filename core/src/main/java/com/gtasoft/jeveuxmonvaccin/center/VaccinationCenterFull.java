package com.gtasoft.jeveuxmonvaccin.center;

import com.google.gson.annotations.Expose;

import java.sql.Timestamp;
import java.util.Comparator;

public class VaccinationCenterFull implements Comparable<VaccinationCenterFull> {
    public static int ASTRAZENECA = 1;
    public static int PFIZER = 2;
    public static int JANNSEN = 3;

    public static int DOCTOLIB = 2;
    public static int MAIIA = 1;
    public static int KELDOC = 3;

    public static int STATUS_OK = 1;
    public static int STATUS_KO = 2;
    public static int STATUS_OFF = 3;
    public static int STATUS_UNCHECKABLE = 4;
    public static Comparator<VaccinationCenterFull> nameComparator = new Comparator<VaccinationCenterFull>() {
        @Override
        public int compare(VaccinationCenterFull jc1, VaccinationCenterFull jc2) {
            return (jc1.getName().compareTo(jc2.getName()));
        }
    };
    public static Comparator<VaccinationCenterFull> timeComparator = new Comparator<VaccinationCenterFull>() {
        @Override
        public int compare(VaccinationCenterFull jc1, VaccinationCenterFull jc2) {
            if (jc2.getStatus() == 1 && jc1.getStatus() != 1) {
                return 1;
            }
            if (jc2.getStatus() == 2 && jc1.getStatus() == 1) {
                return -1;
            }
            if (jc2.getStatus() == jc1.getStatus()) {

                if (jc1.getReadableRdv() != null && jc2.getReadableRdv() != null) {
                    String rdv1 = jc1.getReadableRdv().trim();
                    String rdv2 = jc2.getReadableRdv().trim();
                    if (!"".equals(rdv1) && !"".equals(rdv2)) {

                        if (!isNumeric(rdv1.substring(0, 1))) {
                            if (!isNumeric(rdv2.substring(0, 1))) {
                                return rdv1.compareTo(rdv2);
                            } else {
                                return -1;
                            }
                        } else {
                            if (!isNumeric(rdv2.substring(0, 1))) {
                                return 1;
                            } else {
                                return rdv1.compareTo(rdv2);
                            }
                        }
                    } else {
                        if (!"".equals(rdv1)) {
                            return -1;
                        }
                        if (!"".equals(rdv2)) {
                            return 1;
                        }

                        return nameComparator.compare(jc1, jc2);

                    }
                }


            }
            return nameComparator.compare(jc1, jc2);


        }
    };
    public static Comparator<VaccinationCenterFull> dispoComparator = new Comparator<VaccinationCenterFull>() {
        @Override
        public int compare(VaccinationCenterFull jc1, VaccinationCenterFull jc2) {

            if (jc2.getTotal() == 0 && jc1.getTotal() == 0) {
                return 0;
            }
            if (jc2.getTotal() == 0) {
                return -1;
            }
            if (jc1.getStatus() == 0) {
                return 1;
            }
            if ((float) jc2.getNbsuccess() / (float) jc2.getTotal() == (float) jc1.getNbsuccess() / (float) jc1.getTotal()) {
                return 0;
            } else if ((float) jc2.getNbsuccess() / (float) jc2.getTotal() > (float) jc1.getNbsuccess() / (float) jc1.getTotal()) {

                return 1;
            } else {
                return -1;
            }


        }
    };
    @Expose
    private String link;
    @Expose
    private int status;
    private int msinterval;
    @Expose
    private int nbsuccess;
    @Expose
    private int total;
    @Expose
    private String close_date;
    @Expose
    private String open_date;
    @Expose
    private int centerId;
    @Expose
    private int vaccineId;
    @Expose
    private int platformId;
    @Expose
    private String name;
    @Expose
    private String address;
    @Expose
    private String city;
    @Expose
    private int zipcode;
    @Expose
    private String readableTime;
    @Expose
    private String readableRdv;
    @Expose
    private Timestamp lastphonecheck;
    private Timestamp nextRdv;
    @Expose
    private String params;
    @Expose
    private String checkLink;
    @Expose
    private Timestamp lastcheck;
    @Expose
    private String readableLastcheck;
    @Expose
    private Timestamp lastok;
    @Expose
    private int nbsuccess18;
    @Expose
    private int total18;
    @Expose
    private int status18;
    @Expose
    private String readableTime18;
    @Expose
    private String readableRdv18;
    @Expose
    private String readableLastcheck18;
    private Timestamp lastok18;
    private Timestamp lastcheck18;
    private Timestamp nextRdv18;
    private String params18;

    public VaccinationCenterFull() {

    }

    public VaccinationCenterFull(int centerId, int vaccine_type) {
        this.setCenterId(centerId);
        this.setVaccineId(vaccine_type);

    }

    public static boolean isNumeric(String str) {
        return str.matches("-?\\d+(\\.\\d+)?");  //match a number with optional '-' and decimal.
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getClose_date() {
        return close_date;
    }

    public void setClose_date(String close_date) {
        this.close_date = close_date;
    }

    public int getCenterId() {
        return centerId;
    }

    public void setCenterId(int centerId) {
        this.centerId = centerId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getMsinterval() {
        return msinterval;
    }

    public void setMsinterval(int msinterval) {
        this.msinterval = msinterval;
    }

    public int getNbsuccess() {
        return nbsuccess;
    }

    public void setNbsuccess(int nbsuccess) {
        this.nbsuccess = nbsuccess;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getVaccineId() {
        return vaccineId;
    }

    public void setVaccineId(int vaccineId) {
        this.vaccineId = vaccineId;
    }

    public Timestamp getLastphonecheck() {
        return lastphonecheck;
    }

    public void setLastphonecheck(Timestamp lastphonecheck) {
        this.lastphonecheck = lastphonecheck;
    }

    public Timestamp getLastcheck() {
        return lastcheck;
    }

    public void setLastcheck(Timestamp lastcheck) {
        this.lastcheck = lastcheck;
    }

    public Timestamp getLastok() {
        return lastok;
    }

    public void setLastok(Timestamp lastok) {
        this.lastok = lastok;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public String getOpen_date() {
        return open_date;
    }

    public void setOpen_date(String open_date) {
        this.open_date = open_date;
    }

    public int getPlatformId() {
        return platformId;
    }

    public void setPlatformId(int platformId) {
        this.platformId = platformId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getZipcode() {
        return zipcode;
    }

    public void setZipcode(int zipcode) {
        this.zipcode = zipcode;
    }

    public String getReadableTime() {
        return readableTime;
    }

    public void setReadableTime(String readableTime) {
        this.readableTime = readableTime;
    }

    public String getCheckLink() {
        return checkLink;
    }

    public void setCheckLink(String checkLink) {
        this.checkLink = checkLink;
    }

    public Timestamp getNextRdv() {
        return nextRdv;
    }

    public void setNextRdv(Timestamp nextRdv) {
        this.nextRdv = nextRdv;
    }

    public String getReadableRdv() {
        return readableRdv;
    }

    public void setReadableRdv(String readableRdv) {
        this.readableRdv = readableRdv;
    }

    public String getReadableLastcheck() {
        return readableLastcheck;
    }

    public void setReadableLastcheck(String readableLastcheck) {
        this.readableLastcheck = readableLastcheck;
    }

    public int getNbsuccess18() {
        return nbsuccess18;
    }

    public void setNbsuccess18(int nbsuccess18) {
        this.nbsuccess18 = nbsuccess18;
    }

    public int getTotal18() {
        return total18;
    }

    public void setTotal18(int total18) {
        this.total18 = total18;
    }

    public int getStatus18() {
        return status18;
    }

    public void setStatus18(int status18) {
        this.status18 = status18;
    }

    public String getReadableTime18() {
        return readableTime18;
    }

    public void setReadableTime18(String readableTime18) {
        this.readableTime18 = readableTime18;
    }

    public String getReadableRdv18() {
        return readableRdv18;
    }

    public void setReadableRdv18(String readableRdv18) {
        this.readableRdv18 = readableRdv18;
    }

    public String getReadableLastcheck18() {
        return readableLastcheck18;
    }

    public void setReadableLastcheck18(String readableLastcheck18) {
        this.readableLastcheck18 = readableLastcheck18;
    }

    public Timestamp getLastcheck18() {
        return lastcheck18;
    }

    public void setLastcheck18(Timestamp lastcheck18) {
        this.lastcheck18 = lastcheck18;
    }

    public Timestamp getNextRdv18() {
        return nextRdv18;
    }

    public void setNextRdv18(Timestamp nextRdv18) {
        this.nextRdv18 = nextRdv18;
    }

    public String getParams18() {
        return params18;
    }

    public void setParams18(String params18) {
        this.params18 = params18;
    }

    public Timestamp getLastok18() {
        return lastok18;
    }

    public void setLastok18(Timestamp lastok18) {
        this.lastok18 = lastok18;
    }


    @Override
    public int compareTo(VaccinationCenterFull o) {
        if (o != null) {
            int oid = o.getCenterId();
            return centerId - oid;

        }
        return 0;

    }
}
