package com.gtasoft.jeveuxmonvaccin.setup;

import com.google.gson.annotations.Expose;

public class Pref {


    @Expose
    private int vaccineId = 0;

    @Expose
    private int centerId = 0;

    @Override
    public boolean equals(Object o) {
        if (o instanceof Pref) {
            Pref up = (Pref) o;
            if (((Pref) o).getVaccineId() == (this.getVaccineId())) {
                if (((Pref) o).getCenterId() == (this.getCenterId())) {
                    return true;
                }
            }
        }
        return false;
    }

    public int getVaccineId() {
        return vaccineId;
    }

    public void setVaccineId(int vaccineId) {
        this.vaccineId = vaccineId;
    }

    public int getCenterId() {
        return centerId;
    }

    public void setCenterId(int centerId) {
        this.centerId = centerId;
    }
}
