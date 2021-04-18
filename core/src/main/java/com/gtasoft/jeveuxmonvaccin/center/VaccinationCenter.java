package com.gtasoft.jeveuxmonvaccin.center;

import java.util.Comparator;

public class VaccinationCenter implements Comparable {
    public static int MAIIA_ID = 1;
    public static int DOCTOLIB_ID = 2;
    public static int KELDOC_ID = 3;
    public static int UNKNOWN_ID = 4;

    public static int astrazenecaID = 1;
    public static int pfizerID = 2;
    public static int janssenID = 3;
    public static int noneID = 5;
    public static Comparator<VaccinationCenter> CenterComparator = new Comparator<VaccinationCenter>() {
        public int compare(VaccinationCenter s1, VaccinationCenter s2) {
            String c1name = s1.getName().toUpperCase();
            String c2name = s2.getName().toUpperCase();
            return c1name.compareTo(c2name);
        }
    };
    private static VaccinationCenter firstInstance = null;
    private int centerId;
    private String name;
    private String postalcode;
    private String city;
    private String address;
    private String link;
    private String close_date;
    private int providerId;
    private int vaccineId;

    public VaccinationCenter(int centerId, String name) {
        this.setCenterId(centerId);
        this.setName(name);
    }

    public static VaccinationCenter getFirstInstance() {
        if (firstInstance != null) {
            return firstInstance;
        }
        firstInstance = new VaccinationCenter(0, "- Choix du Centre -");

        return firstInstance;

    }

    public int getCenterId() {
        return centerId;
    }

    public void setCenterId(int centerId) {
        this.centerId = centerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPostalcode() {
        return postalcode;
    }

    public void setPostalcode(String postalcode) {
        this.postalcode = postalcode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public int getProviderId() {
        return providerId;
    }

    public void setProviderId(int providerId) {
        this.providerId = providerId;
    }


    @Override
    public int compareTo(Object o) {
        VaccinationCenter vc = (VaccinationCenter) o;
        if (vc == null || vc.getName() == null) {
            return 1;
        }
        if (getName() == null) {
            return -1;
        }
        if (vc.getVaccineId() == getVaccineId()) {
            return getName().compareTo(vc.getName());
        } else {
            return getVaccineId() - vc.getVaccineId();
        }


    }

    public String toString() {
        if (centerId == 0) {
            return name;

        }

        if (name == null) {
            return city;
        }

        if (name.length() > 30) {
            return name.substring(0, 29) + " - " + postalcode;
        }
        return name + " - " + postalcode;

    }

    public int getVaccineId() {
        return vaccineId;
    }

    public void setVaccineId(int vaccineId) {
        this.vaccineId = vaccineId;
    }
}
