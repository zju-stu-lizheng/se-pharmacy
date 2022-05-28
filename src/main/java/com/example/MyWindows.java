package com.example;

import java.util.ArrayList;
import java.util.*;

class MedicineBillEntry {
    // 订单表项含有药品id,数量,品牌,有效期,药房号等数量
    String medicine_id;
    int num;
    String brand;
    String storehouse_id;
    String effective_date;

    public MedicineBillEntry(String medicine_id, int num, String brand, String storehouse_id, String effective_date) {
        this.medicine_id = medicine_id;
        this.num = num;
        this.brand = brand;
        this.storehouse_id = storehouse_id;
        this.effective_date = effective_date;
    }
}

class MedicineBill {
    // 患者id
    String user_id;
    // 账单号
    int sequence_num;
    // 具体的药品
    ArrayList<MedicineBillEntry> bill;

    MedicineBill(String user_id, int sequence_num, ArrayList<MedicineBillEntry> bill) {
        this.user_id = user_id;
        this.sequence_num = sequence_num;
        this.bill = bill;
    }
}

public class MyWindows {
    // 记录估计的等待时间，线程安全 <0 表示窗口关闭
    static Vector<Boolean> windows = new Vector<Boolean>();
    static final double time_base = 2;
    static final double take_time = 0.1;
    static String house_id;
    // 窗口初始化
    public MyWindows(int n,String house_id) {
        this.house_id=house_id;
        for (int i = 0; i < n; i++)
            windows.add(true);
    }

    /**
     * 关闭窗口
     * 
     * @param i : 关闭窗口i
     */
    boolean colseWindow(int i) {
        if (MyJDBC.searchWindowPeople(house_id,i) == 0) {
            windows.set(i, false);
            return true;
        } else
            return false;
    }

    /**
     * 开启窗口
     * 
     * @param i : 开启窗口i
     */
    boolean openWindow(int i) {
        if (i >= windows.size()) {
            for (int j = windows.size(); j < i; j++) {
                windows.add(false);
            }
        }
        if (!windows.get(i)) {
            windows.set(i,true);
            return true;
        } else
            return false;
    }

    /**
     * 选取估计时间最短的窗口
     */
    static int windowSchedule() {
        double min = 999999999;
        int window_no = -1;
        double time=0;
        for (int i = 0; i < windows.size(); i++) {
            if(!windows.get(i)) continue;
            time=time_base*MyJDBC.searchWindowPeople(house_id,i)
                +take_time*MyJDBC.searchWindowMedicine(house_id,i);
            if (min > time) {
                min = time;
                window_no = i;
            }
        }
        return window_no;
    }

    /**
     * 队列加人
     * 
     * @param medicine_bill ：药单
     * @param window_no     ：加入的窗口号
     */
    static int  addPerson(String bill_id,String house_id) {
        if(MyJDBC.addQueue(bill_id,house_id)){
            int window_no=windowSchedule();
            MyJDBC.addWindow(bill_id,house_id,window_no);
            return window_no; 
        }
        return -1;
    }

    /**
     * 队列踢人
     * 
     * @param medicine_bill ：药单
     * @param window_no     ：踢出的窗口号
     */
    void deletePerson(String bill) {
        MyJDBC.deleteQueue(bill);
        MyJDBC.deleteWindow(bill);
    }



    /**
     * 获取该窗口排队的订单
     * 
     * @param window_no ：踢出的窗口号
     */
    Vector<Integer> getWindowQueue(int window_no) {
        return queue.get(window_no);
    }
}