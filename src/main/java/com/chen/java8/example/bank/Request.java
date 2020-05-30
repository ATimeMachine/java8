package com.chen.java8.example.bank;

import java.util.Scanner;

/**
 * Program:     com.chen.java8.example.bank <br/>
 * ClassName:   Request <br/>
 * Description: 请求向量 <br/>
 * Author:      陈建 <br/>
 * Create:      2020-05-30 10:19 <br/>
 */
public class Request{
    //初始化每个请求最大的需求矩阵,减低实验结果复杂性，不是使用随机数，统一值为25
    private Resources max = new  Resources(25,25,25,25,25);
    //已分配矩阵 默认都是0
    private Resources allocation = new Resources();
    //需求量，每次获取都计算需求量
    public Resources getNeed() {
        return  Resources.getNeed(max, allocation);
    }
    //线程代号
    private int thread;
    //请求量
    private Resources request;
    //是否安全序列
    private boolean finish = true;

    public Request(int thread) {
        this.thread = thread;
    }

    //获取一个请求向量，0是随机获取
    public static Request getOneRequest(Banker banker) {
        try {
            Scanner scanner=new Scanner(System.in);
            System.out.println("请输入线程号：");
            int i = scanner.nextInt();
            System.out.println("线程号是：" + i);
            Request request = new Request(i);
            //随机资源
            Resources rsourcesByRandom;
            if (i == 0) {
                rsourcesByRandom = Resources.getRsourcesByRandom();
            }else {
                System.out.println("请A资源：");
                int a = scanner.nextInt();
                System.out.println("请B资源：");
                int b = scanner.nextInt();
                System.out.println("请C资源：");
                int c = scanner.nextInt();
                System.out.println("请D资源：");
                int d = scanner.nextInt();
                System.out.println("请E资源：");
                int e = scanner.nextInt();
                rsourcesByRandom = new Resources(a, b, c, d, e);
            }
            request.setRequest(rsourcesByRandom);
            banker.getThreadMap().put(i, request);
            return request;
        } catch (Exception e) {
            System.out.println("输入错误，请重新输入");
            return getOneRequest(banker);
        }
    }

    //默认请求
    public static Request defaultRequst(Banker banker) {
        Request request = new Request(0);
        Resources rsourcesByRandom = Resources.getRsourcesByRandom();
        request.setRequest(rsourcesByRandom);
        banker.getThreadMap().put(request.getThread(), request);
        return request;
    }

    public Resources getAllocation() {
        return allocation;
    }

    public void setAllocation(Resources allocation) {
        this.allocation = allocation;
    }

    public Resources getRequest() {
        return request;
    }

    public void setRequest(Resources request) {
        this.request = request;
    }

    public int getThread() {
        return thread;
    }

    public boolean isFinish() {
        return finish;
    }

    public void setFinish(boolean finish) {
        this.finish = finish;
    }

    @Override
    public String toString() {
        return thread + "线程的资源"  + "->" + request.toString();
    }
}
