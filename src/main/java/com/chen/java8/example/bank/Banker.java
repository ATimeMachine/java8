package com.chen.java8.example.bank;

import lombok.Data;

import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Program:     com.chen.java8.example.bank <br/>
 * ClassName:   Banker <br/>
 * Description: 银行家拥有的资源 <br/>
 * Author:      陈建 <br/>
 * Create:      2020-05-30 10:05 <br/>
 */
@Data
public class Banker{
    //初始化可利用资源向量
    private Resources available = new Resources(50, 50, 50, 50, 50);
    //记录每个线程的的资源
    private Map<Integer, Request> threadMap = new ConcurrentHashMap<>();

    public static void main(String[] args) {
        Banker banker = new Banker(); //开银行
        System.out.println("请输入请求量个数");
        Scanner scanner=new Scanner(System.in);
        int nextInt = scanner.nextInt();
        for (int i = 0; i < nextInt; i++) {
            //获取一个请求向量，0是随机获取,其他数字需要输入A-E资源的数量
            Request one = Request.getOneRequest(); //来了一个请求
            banker.algorithm(one,true);//银行家算法
        }
        Map<Integer, Request> threadMap = banker.getThreadMap();
        boolean finish = true;
        for (Request request : threadMap.values()) {
            finish = request.isFinish();
            if (!finish){
                System.out.println("系统是不安全的");
                break;
            }
        }
        if (finish){
            System.out.println("系统是安全的");
        }
    }



    //算法
    public synchronized void algorithm(Request request,Boolean action) {
        //打印情况
        this.printData(request);
        //
        Request oldRequest = threadMap.get(request.getThread());
        if (null == oldRequest){
            oldRequest = request;
        }
        threadMap.put(request.getThread(), request);
        //初始化数据
        Resources available = this.getAvailable(); //银行家拥有的资源
        Resources allocation = oldRequest.getAllocation();//线程已分配在资源
        boolean finish = oldRequest.isFinish();
        if (!finish) {
            System.out.println("线程false;系统没有足够资源分配给线程：" + request.getThread());
            return;
        }
        //判断是否超过最大需求量
        Resources need = oldRequest.getNeed(); //需要的资源
        Resources requestResource = request.getRequest(); //请求的资源
        Boolean isLessNeed = need.compare(requestResource); //判断需求矩阵
        if (!isLessNeed) {
            //回收资源重新分配
            callback(request, action, available, allocation);
            return;
        }
        //判断是否有足够的资源
        Boolean isLessAvailable = available.compare(requestResource);
        if (!isLessAvailable) {
            System.out.println("尚无足够的资源：" + request.getThread());
            return;
        }
        available = available.subtract(requestResource);
        this.setAvailable(available);
        //分配量
        allocation = allocation.plus(requestResource);
        request.setAllocation(allocation);
        //请求量为0
        request.setRequest(Resources.zoro);
    }

    private void callback(Request request, Boolean action, Resources available, Resources allocation) {
        System.out.println("已超出它宣布的最大值：" + request.getThread());
        request.setFinish(false);
        //回收资源
        available = available.plus(allocation);
        this.setAvailable(available);
        request.setAllocation(Resources.zoro);
        request.setRequest(Resources.zoro);
        if (action) {
            System.out.println("****************************回收资源从新分配");
            Map<Integer, Request> threadMap = this.getThreadMap();
            Resources one = new Resources(1, 1, 1, 1, 1);
            for (Request mapRequest : threadMap.values()) {
                Resources mapRequestRequest = mapRequest.getRequest();
                if (mapRequestRequest.compare(one)){
                    this.algorithm(mapRequest, false);
                }
            }
        }
    }

    private void printData(Request request) {
        System.out.println(request.getThread() + "线程新的资源请求：" + request.getRequest());
        System.out.println("============当前系统资源情况=====================");
        System.out.println("当前系统资源：" +  this.getAvailable().toString());
        Map<Integer, Request> threadMap = this.getThreadMap();
        for (Request value : threadMap.values()) {
            System.out.println(value.getThread() + "线程已分配资源：" + value.getAllocation().toString() + ",是否计算："+ value.isFinish());
        }
        System.out.println("--------------------------结束---------------------------");
    }
}
