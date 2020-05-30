package com.chen.java8.example.bank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Program:     com.chen.java8.example.bank <br/>
 * ClassName:   Resources <br/>
 * Description: 资源 <br/>
 * Author:      陈建 <br/>
 * Create:      2020-05-30 10:08 <br/>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Resources {
    private int A;
    private int B;
    private int C;
    private int D;
    private int E;

    //计算需要需求矩阵
    public static Resources getNeed(Resources max, Resources allocation) {
        int a = max.getA() - allocation.getA();
        int b = max.getB() - allocation.getB();
        int c = max.getC() - allocation.getC();
        int d = max.getD() - allocation.getD();
        int e = max.getE() - allocation.getE();
        return new Resources(a, b, c, d, e);
    }

    //随机获取资源
    public static Resources getRsourcesByRandom() {
        int a = (int) (Math.random()*10);
        int b = (int) (Math.random()*10);
        int c = (int) (Math.random()*10);
        int d = (int) (Math.random()*10);
        int e = (int) (Math.random()*10);
        return new Resources(a, b, c, d, e);
    }

    //比较
    public Boolean compare(Resources request) {
       return this.getA() >= request.getA() && (this.getB() >= request.getB() && (this.getC() >= request.getC() && (this.getD() >= request.getD() && (this.getE() >= request.getE()))));
    }
    //减法
    public Resources subtract(Resources request){
        int a = this.getA() - request.getA();
        int b = this.getB() - request.getB();
        int c = this.getC() - request.getC();
        int d = this.getD() - request.getD();
        int e = this.getE() - request.getE();
        return new Resources(a, b, c, d, e);
    }

    //减法
    public Resources plus(Resources request){
        int a = this.getA() + request.getA();
        int b = this.getB() + request.getB();
        int c = this.getC() + request.getC();
        int d = this.getD() + request.getD();
        int e = this.getE() + request.getE();
        return new Resources(a, b, c, d, e);
    }


    @Override
    public String toString() {
        return "{" +
                "A资源：" + A +
                ", B资源：" + B +
                ", C资源：" + C +
                ", D资源：" + D +
                ", E资源：" + E +
                '}';
    }
}
