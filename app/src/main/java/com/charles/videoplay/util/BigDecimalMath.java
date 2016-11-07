package com.charles.videoplay.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class BigDecimalMath {
    public static double add(double d1, double d2) { // 进行加法运算
        BigDecimal b1 = new BigDecimal(d1);
        BigDecimal b2 = new BigDecimal(d2);
        return b1.add(b2).doubleValue();
    }

    public static double sub(double d1, double d2) { // 进行减法运算
        BigDecimal b1 = new BigDecimal(d1);
        BigDecimal b2 = new BigDecimal(d2);
        return b1.subtract(b2).doubleValue();
    }

    public static double mul(double d1, double d2) { // 进行乘法运算
        BigDecimal b1 = new BigDecimal(d1);
        BigDecimal b2 = new BigDecimal(d2);
        return b1.multiply(b2).doubleValue();
    }

    public static double div(double d1, double d2, int len) {// 进行除法运算
        BigDecimal b1 = new BigDecimal(d1);
        BigDecimal b2 = new BigDecimal(d2);
        return b1.divide(b2, len, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    public static double round(double d, int len) { // 进行四舍五入操作
        BigDecimal b1 = new BigDecimal(Double.toString(d));
        BigDecimal b2 = new BigDecimal("1.00");
        // 任何一个数字除以1都是原数字
        // ROUND_HALF_UP是BigDecimal的一个常量，表示进行四舍五入的操作
        return b1.divide(b2, len, BigDecimal.ROUND_HALF_UP).doubleValue();
    }
    
    public static double roundWithLen(double d, int len) { // 进行四舍五入操作
        DecimalFormat df = new DecimalFormat("0.00");
        String str = df.format(d);
        BigDecimal bd = new BigDecimal(str);
        return bd.doubleValue();
    }
    
    public static String formaterTwoPoint(double d){
    	DecimalFormat df = new DecimalFormat("0.00");
        return df.format(d);
    }

    public static String formaterNoPoint(double d){
        DecimalFormat df = new DecimalFormat("0");
        return df.format(d);
    }

    public static String formaterMoney(String money){
        if (BussinessUtil.isValid(money)) {
            double d = Double.parseDouble(money);
            DecimalFormat df = new DecimalFormat("0.00");
            return df.format(d);
        }
        return money;
    }

    public static String formaterMoneyNoPoint(String money){
        if (BussinessUtil.isValid(money)) {
            double d = Double.parseDouble(money);
            DecimalFormat df = new DecimalFormat("0");
            return df.format(d);
        }
        return money;
    }

    public static String formaterMoneyWhetherPoint(String money){
        if (BussinessUtil.isValid(money)) {
            double d = Double.parseDouble(money);
            if(d % 1.0 == 0){
                return String.valueOf((long)d);
            }

            DecimalFormat df = new DecimalFormat("0.00");
            return df.format(d);
        }
        return money;
    }

    public static String formaterMoneyInOrderInfo(String money){
        if (BussinessUtil.isValid(money)) {
            double d = Double.parseDouble(money);
            DecimalFormat df = new DecimalFormat("0.00");
            return df.format(d);
        }
        return money;
    }

    public static String formaterSeparate(String pattern,String money){
        if (BussinessUtil.isValid(money)) {
            BigDecimal bd = new BigDecimal(money);
            DecimalFormat df = new DecimalFormat(pattern);
            return df.format(bd);
        }
        return money;
    }

}
