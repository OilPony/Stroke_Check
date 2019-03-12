package com.example.test_php2;

public class Stroke {

    private int id;
    private int sm1;
    private int sm2;

    public Stroke() {
    }

    public Stroke(int id, int sm1, int sm2) {
        this.id = id;
        this.sm1 = sm1;
        this.sm2 = sm2;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSm1() {
        return sm1;
    }

    public void setSm1(int sm1) {
        this.sm1 = sm1;
    }

    public int getSm2() {
        return sm2;
    }

    public void setSm2(int sm2) {
        this.sm2 = sm2;
    }



    @Override
    public String toString() {
        return id + " - " + sm1 + " - " + sm2;
    }

}