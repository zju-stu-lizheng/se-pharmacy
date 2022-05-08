package com.example;
import java.util.ArrayList;
import java.util.*;
<<<<<<< HEAD
//订单表项类
=======

>>>>>>> ae3372efeee750c0ceb463870462698ce72c105c
class MedicineBillEntry{
    //订单表项含有药品id,数量,品牌,有效期,药房号等数量
    String medicine_id;
    int num;
    String brand;
    String storehouse_id;
    String effective_date;
<<<<<<< HEAD
    public MedicineBillEntry(String medicine_id, int num, String brand, String storehouse_id){
=======
    public MedicineBillEntry(String medicine_id, int num, String brand, String storehouse_id,String effective_date){
>>>>>>> ae3372efeee750c0ceb463870462698ce72c105c
        this.medicine_id=medicine_id;
        this.num=num;
        this.brand=brand;
        this.storehouse_id=storehouse_id;
        this.effective_date = effective_date;
    }
}
<<<<<<< HEAD
//订单类
=======

>>>>>>> ae3372efeee750c0ceb463870462698ce72c105c
class MedicineBill{
    //患者id
    String user_id;
    //账单号
    int sequence_num;
    //具体的药品
    ArrayList<MedicineBillEntry> bill;
    MedicineBill(String user_id, int sequence_num, ArrayList<MedicineBillEntry> bill){
        this.user_id=user_id;
        this.sequence_num=sequence_num;
        this.bill=bill;
    }
}
<<<<<<< HEAD
//药房窗口类
=======

>>>>>>> ae3372efeee750c0ceb463870462698ce72c105c
public class MyWindows{
    //记录估计的等待时间，线程安全 <0 表示窗口关闭
    Vector <Double>windows=new Vector <Double>();
    //窗口等待队列，线程安全
    Vector <Vector <Integer>>queue=new Vector <Vector<Integer>>();
    //订单号，类变量
    static int sequence_num;
    final double time_base=2;
    final double take_time=0.1;
    //窗口初始化
    public MyWindows(int n){
        for(int i=0;i<n;i++){
            windows.add(0.0);
            queue.add(new Vector<Integer>());
        }
        sequence_num=0;
    }

    /**
	 * 关闭窗口
	 * 
	 * @param i            : 关闭窗口i
	 */
    boolean colseWindow(int i){
        if(windows.get(i)==0) {
            windows.set(i,-1.0);
            return true;
        }else  
            return false;           
    }
    /**
	 * 开启窗口
	 * 
	 * @param i            : 开启窗口i
	 */
    boolean openWindow(int i){
        if(i>=windows.size()){
            for(int j=windows.size();j<i;j++){
                windows.add(-1.0);
                queue.add(new Vector<Integer>());
            }           
        }
        if(windows.get(i)==-1.0){
            windows.set(i,0.0);
            return true;
        }else
            return false;
    }
    /**
	 * 选取估计时间最短的窗口
	 */
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
    /**
	 * 队列加人
     * 
     * @param medicine_bill         ：药单
     * @param window_no             ：加入的窗口号
	 */
    void addPerson(MedicineBill medicine_bill,int window_no){
        double time=time_base;
        for(int i=0;i<medicine_bill.bill.size();i++){
            time+=take_time*medicine_bill.bill.get(i).num;
        }
        time+=windows.get(window_no);
        windows.set(window_no,time);
        queue.get(window_no).add(medicine_bill.sequence_num);
    }
    /**
	 * 队列踢人
     * 
     * @param medicine_bill         ：药单
     * @param window_no             ：踢出的窗口号
	 */
    void deletePerson(MedicineBill medicine_bill,int window_no){
        double time=time_base;
        for(int i=0;i<medicine_bill.bill.size();i++){
            time+=take_time*medicine_bill.bill.get(i).num;
        }
        time-=windows.get(window_no);
        windows.set(window_no,time);
        queue.get(window_no).remove(medicine_bill.sequence_num);
    }
    /**
	 * 获取药单号
	 */
    synchronized static int getSequenceNum(){
        sequence_num++;
        return sequence_num-1;
    }
    /**
	 * 获取该窗口排队的订单
     * 
     * @param window_no             ：踢出的窗口号
	 */
    Vector<Integer> getWindowQueue(int window_no){
        return queue.get(window_no);
    }
}