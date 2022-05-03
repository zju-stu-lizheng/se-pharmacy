package com.example;
import java.util.ArrayList;
import java.util.*;
class MedicineBillEntry{
    String medicine_id;
    int num;
    String brand;
    String storehouse_id;
    public MedicineBillEntry(String medicine_id, int num, String brand, String storehouse_id){
        this.medicine_id=medicine_id;
        this.num=num;
        this.brand=brand;
        this.storehouse_id=storehouse_id;
    }
}
public class MyWindows{
    Vector <Double>windows=new Vector <Double>();
    final double time_base=2;
    final double take_time=0.1;
    public MyWindows(int n){
        for(int i=0;i<n;i++){
            windows.add(0.0);
        }
    }
    boolean colseWindow(int i){
        if(windows.get(i)==0) {
            windows.set(i,-1.0);
            return true;
        }else  
            return false;           
    }
    boolean openWindow(int i){
        if(i>=windows.size()){
            for(int j=windows.size();j<i;j++)
                windows.add(-1.0);
        }
        if(windows.get(i)==-1.0){
            windows.set(i,0.0);
            return true;
        }else
            return false;
    }
    int windowSchedule(){
        double min=999999999;
        int window_no=-1;
        for(int i=0;i<windows.size();i++){
            if(windows.get(i)>=0 && min>windows.get(i)){
                min=windows.get(i);
                window_no=i;
            }
        }
        return window_no;
    }
    void addPerson(ArrayList<MedicineBillEntry>bill,int window_no){
        double time=time_base;
        for(int i=0;i<bill.size();i++){
            time+=take_time*bill.get(i).num;
        }
        time+=windows.get(window_no);
        windows.set(window_no,time);
    }
    void deletePerson(ArrayList<MedicineBillEntry>bill,int window_no){
        double time=time_base;
        for(int i=0;i<bill.size();i++){
            time+=take_time*bill.get(i).num;
        }
        time-=windows.get(window_no);
        windows.set(window_no,time);  
    }
}