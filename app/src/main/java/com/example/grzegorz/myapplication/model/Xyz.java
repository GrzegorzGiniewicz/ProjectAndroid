package com.example.grzegorz.myapplication.model;

import io.realm.RealmObject;

/**
 * Created by Grzegorz on 2017-01-04.
 */

public class Xyz extends RealmObject {

   // int id;
    float x;
    float y;
    float z;

    //public int getID() {return id;}

    //public  void setID(int id) {this.id = id;}

    public float getX() {
        return x;
    }

    public void setX(float x) {this.x = x;}

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getZ() {
        return z;
    }

    public void setZ(float z) {this.z = z;}

    @Override
    public String toString() {
        return "Xyz{" +
                //"ID=" + id +
                ", x=" + x +
                ", y=" + y +
                ", z=" + z +
                '}';
    }
}
