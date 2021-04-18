package com.gtasoft.jeveuxmonvaccin.setup;

import com.google.gson.annotations.Expose;

public class Machine {
    @Expose
    private String name = "Anonymous";
    @Expose
    private String salt = "";

    public Machine(String name) {
        this.name = name;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Machine) {
            if (((Machine) o).getName().equals(this.getName())) {
                return true;
            }
        }
        return false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
