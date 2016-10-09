package com.charles.videoplay.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BussinessUtil {

    /**
     * 检测网络是否可用
     */
    public static boolean isNetWorkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }

        return false;
    }

    /**
     * 验证姓名是否合法
     *
     * @param userName
     * @return public static String validateName(String name){ StringBuilder suggest = new StringBuilder();
     *
     *         if(name.matches("[\u4e00-\u9fa5]{2,5}")){ return ""; }else{ suggest.append(YppApplication.newInstance().getText(R.string.)); } return
     *         suggest.toString(); }
     */

    /**
     * 格式化身份证
     */
    public static String formatterResidentNo(String residentNo) {

        StringBuilder result = new StringBuilder();

        if (residentNo.length() >= 5) {
            for (int i = 0; i < residentNo.length(); i++) {
                if (i < 4) {
                    result.append(residentNo.charAt(i));
                } else if (i > residentNo.length() - 5) {
                    result.append(residentNo.charAt(i));
                } else {
                    result.append("*");
                }
            }
        }
        return result.toString();
    }

    private static final int[] weightNumber = new int[]{7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2, 1};

    private static final int[] checknumber = new int[]{1, 0, 'X', 9, 8, 7, 6, 5, 4, 3, 2};

    // 判断指定身份证格式是否正确
    public static boolean validateResidentNo(String residentNo) {
        String regx = "\\d{15}|\\d{17}[\\d,X]";
        if (!residentNo.matches(regx)) {
            return false;
        }
        if (residentNo.length() == 15) { // 如果要进行验证的身份证号码为15位
            residentNo = fiveteenToeighteen(residentNo); // 将其转换为18位
        }
        if (residentNo.length() != 18) {
            return false;
        }

        String checkDight = residentNo.substring(17, 18); // 获取要进行验证身份证号码的校验号
        if (checkDight.equals(getCheckDigit(residentNo))) { // 判断校验码是否正确
            return true;
        }
        return false;
    }

    // 将15位身份证号码转为18位身份证号码
    public static String fiveteenToeighteen(String fifteenNumber) {
        String eighteenNumberBefore = fifteenNumber.substring(0, 6); // 获取参数身份证号码中的地区码
        String eightNumberAfter = fifteenNumber.substring(6, 15); // 获取参数身份证号码中的出生日期码
        String eighteenNumber;
        eighteenNumber = eighteenNumberBefore + "19"; // 将地区码后面加"19"
        eighteenNumber = eighteenNumber + eightNumberAfter; // 获取地区码加出生日期码
        eighteenNumber = eighteenNumber + getCheckDigit(eighteenNumber);// 获取身份证的校验码
        return eighteenNumber; // 将转换后的身份证号码返回
    }

    // 获取身份证号码中的校验码
    private static String getCheckDigit(String cardNumber) {
        int verify = 0;
        cardNumber = cardNumber.substring(0, 17); // 获取身份证号码中的前17位
        int sum = 0;
        int[] wi = new int[17]; // 创建int型数组
        for (int i = 0; i < 17; i++) { // 循环向数组赋值
            String strid = cardNumber.substring(i, i + 1);
            wi[i] = Integer.parseInt(strid);
        }
        for (int i = 0; i < 17; i++) { // 循环遍历数组
            sum = sum + weightNumber[i] * wi[i]; // 对17位本利码加权求和
        }
        verify = sum % 11; // 取模
        if (verify == 2) { // 如果模为2，则返回"X"
            return "X";
        } else {
            return String.valueOf(checknumber[verify]); // 否则返回对应的校验码
        }
    }

    /**
     * 验证邮编是否合法 public static String validatePostCode(String postcode){ StringBuilder suggest = new StringBuilder(); if(postcode.length() < 1){
     * suggest.append(YppApplication.newInstance().getText(R.string.)); }else{ if(postcode.length() == 6){ suggest.append(""); }else{
     * suggest.append(YppApplication.newInstance().getText(R.string.)); } } return suggest.toString(); }
     */

    /**
     * 验证用户名是否合法
     *
     * @param internetID
     * @return public static String validateInternetID(String internetID){ StringBuilder suggest = new StringBuilder(); if(internetID.length() < 4 ||
     *         internetID.length() > 20){ suggest.append(YppApplication.newInstance().getText(R.string.)); }else{ boolean otherword = true; boolean allnumber =
     *         true;
     *
     *         for(int i=0; i<internetID.length(); i++){ if((internetID.charAt(i)<='Z'&& internetID.charAt(i)>='A') || (internetID.charAt(i)<='z'&&
     *         internetID.charAt(i)>='a')){ otherword = false; allnumber = false; }else if((internetID.charAt(i)<='9'&& internetID.charAt(i)>='0')){ otherword =
     *         false; }else{ otherword = true; } }
     *
     *         if(otherword || allnumber){ suggest.append("用户名不支持中文和全数字!"); } } return suggest.toString(); }
     */

    /**
     * 验证两次密码是否一致
     *
     * @param setPassword
     * @param checkPassword
     * @return
     */
    public static String validateCheckPassword(String setPassword, String checkPassword) {

        if (checkPassword.equals(setPassword)) {
            return "";
        } else {
            return "对不起,两次输入的密码不一致,请重新输入";
        }
    }

    /**
     * 验证邮箱地址是否合法
     *
     * @param email
     * @return public static String validateEmail(String email){ StringBuilder suggest = new StringBuilder(); if(email.matches(
     *         "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$" )){ return "";
     *         }else{ suggest.append(YppApplication.newInstance().getText(R.string.)); }
     *
     *         return suggest.toString(); }
     */

    /**
     * 验证输入的手机号码是否正确
     */
    public static boolean validateMobilePhone(String mobilePhone) {
        if (mobilePhone.matches(("1\\d{10}"))) {
            return true;
        }
        return false;
    }


    /**
     * 验证输入框是否为空
     */
    public static boolean isNull(EditText editText) {
        String text = editText.getText().toString().trim();
        if (text != null && text.length() > 0) {
            return false;
        }
        return true;
    }

    /**
     * 将对象转为json
     */
    public static boolean isValid(String str) {
        boolean isValid = false;
        if (str != null && !"".equals(str.trim())) {
            isValid = true;
        }
        return isValid;
    }



    /**
     * @param context
     * @param imageSize
     * @param type      布局类型:1,LinearLayout;2,RelativeLayout;
     * @return
     */
    public static ViewGroup.LayoutParams getDongtaiImageLayoutParams(Context context, String imageSize, int type) {
        ViewGroup.LayoutParams layoutParams = null;
        if (BussinessUtil.isValid(imageSize) && imageSize.length() >= 3 && imageSize.contains("x")) {
            String[] size = imageSize.split("x");
            if (size.length == 2) {
//                int padding = DensityUtil.dip2px(context, 20);
//                int width = context.getResources().getDisplayMetrics().widthPixels - padding;
                int width = context.getResources().getDisplayMetrics().widthPixels;
                float imageWidth = Float.parseFloat(size[0]);
                float imageHeight = Float.parseFloat(size[1]);
                float height;
                if ((imageHeight / imageWidth) > 1 / 1) {
                    height = width * 1 / 1;
                } else {
                    height = width * imageHeight / imageWidth;
                }

                if (type == 1) {
                    layoutParams = new LinearLayout.LayoutParams(width, (int) height);
                } else if (type == 2) {
                    layoutParams = new RelativeLayout.LayoutParams(width, (int) height);
                }
            }
        } else {
            int width = context.getResources().getDisplayMetrics().widthPixels;
            if (type == 1) {
                layoutParams = new LinearLayout.LayoutParams(width, (int) width);
            } else if (type == 2) {
                layoutParams = new RelativeLayout.LayoutParams(width, (int) width);
            }
        }

        return layoutParams;
    }

    /**
     * 从短信字符窜提取验证码
     *
     * @param body      短信内容
     * @param YZMLENGTH 验证码的长度 一般6位或者4位
     * @return 接取出来的验证码
     */
    public static String getyzm(String body, int YZMLENGTH) {
        // 首先([a-zA-Z0-9]{YZMLENGTH})是得到一个连续的六位数字字母组合
        // (?<![a-zA-Z0-9])负向断言([0-9]{YZMLENGTH})前面不能有数字
        // (?![a-zA-Z0-9])断言([0-9]{YZMLENGTH})后面不能有数字出现
        Pattern p = Pattern.compile("(?<![a-zA-Z0-9])([a-zA-Z0-9]{" + YZMLENGTH + "})(?![a-zA-Z0-9])");
        Matcher m = p.matcher(body);
        if (m.find()) {
            return m.group(0);
        }
        return null;
    }

    /**
     * 检查手机是否安装指定应用 指定应用的包名,
     *
     * @return
     */
    public static boolean isAvilible(Context context, String packageName) {
        final PackageManager packageManager = context.getPackageManager();
        // 获取所有已安装程序的包信息
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
        for (int i = 0; i < pinfo.size(); i++) {
            if (pinfo.get(i).packageName.equalsIgnoreCase(packageName))
                return true;
        }
        return false;
    }

    public static SpannableString getSpannableString(String allName, String spannable, int colorid) {
        SpannableString msp = new SpannableString(allName);
        int spannableIndex = allName.indexOf(spannable);
        msp.setSpan(new ForegroundColorSpan(colorid), spannableIndex, spannableIndex + spannable.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return msp;
    }


    private static int getInt(Context context, int resid) {
        int result = Integer.parseInt(context.getResources().getString(resid));
        return result;
    }

    private static long lastClickTime;

    public static boolean isClickable() {
        long time = System.currentTimeMillis();
        long timeDelta = time - lastClickTime;
        if (0 < timeDelta && timeDelta < 1000) {
            return false;
        }
        lastClickTime = time;
        return true;
    }

    public static boolean isValidate(ArrayList<?> list) {
        if (list != null && list.size() > 0) {
            return true;
        }
        return false;
    }


    public static String getUUID() {
        return UUID.randomUUID().toString();
    }


}
