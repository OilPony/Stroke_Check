package com.example.test_php2;

public class Smiles {

    private int id;
    private String user;
    private double sm1;

    public Smiles() {
    }

    public Smiles(int id, double sm1,String user, double sm2) {
        this.id = id;
        this.user = user;
        this.sm1 = sm1;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public String getUser() {
        return user;
    }

    public void setId(String user) {
        this.user = user;
    }

    public double getSm1() {
        return sm1;
    }

    public void setSm1(double sm1) {
        this.sm1 = sm1;
    }


    @Override
    public String toString() {
        return id + "ค่าความปากมุมปากซ้าย-ขวา" + sm1;
    }

}