package com.nltu.traffic_rest_controller.entity;


public class TrafficData {

    private float q1;
    private float q2;
    private float s1;
    private float s2;

    public TrafficData() {}

    public TrafficData(float s2, float s1, float q2, float q1) {
        this.s2 = s2;
        this.s1 = s1;
        this.q2 = q2;
        this.q1 = q1;
    }

    public float getQ1() {return q1;}

    public void setQ1(float q1) {this.q1 = q1;}

    public float getQ2() {return q2;}

    public void setQ2(float q2) {this.q2 = q2;}

    public float getS1() {return s1;}

    public void setS1(float s1) {this.s1 = s1;}

    public float getS2() {return s2;}

    public void setS2(float s2) {this.s2 = s2;}
}
