package com.abc.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "provinces")
public class Province {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idProvince")
    private int idProvince;

    @Column(name = "nameProvince")
    private String nameProvince;

    @Column(name = "note")
    private String note;

    public Province() {}

    public Province(int idProvince, String nameProvince, String note) {
        this.idProvince = idProvince;
        this.nameProvince = nameProvince;
        this.note = note;
    }

    public int getIdProvince() {
        return idProvince;
    }

    public void setIdProvince(int idProvince) {
        this.idProvince = idProvince;
    }

    public String getNameProvince() {
        return nameProvince;
    }

    public void setNameProvince(String nameProvince) {
        this.nameProvince = nameProvince;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
