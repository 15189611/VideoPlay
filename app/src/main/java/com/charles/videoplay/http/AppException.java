package com.charles.videoplay.http;

/**
 * Created by Charles on 2016/10/9.
 */

public class AppException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public static final String PARSE_ERROR = "数据解析异常";
    public static final String RESULT_ERROR = "获取数据失败";
    public static final String SERVER_ERROR = "服务器连接失败";
    public static final String IO_ERROR = "读取数据失败";
    public static final String FILE_ERROR = "未找到该文件,";
    public static final String NETWORK_ERROR = "网络连接失败,请检查网络连接";
    public static final String TIMEOUT_ERROR = "网络连接超时,请重试";

    public enum ExceptionStatus {
        IllegalStateException,
        FileNotFoundException,
        ParseException,
        IOException,
        ServerException,
        ParameterException,
        TimeoutException,
        ResultException,
        CancelException,
        NetWorkException
    }

    public ExceptionStatus status;
    public Object result;
    public String errorCode;
    public String errorMsg;
    public boolean showErrorToast = true;

    public AppException(String errorCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public AppException(ExceptionStatus status, String errorMsg) {
        this.status = status;
        this.errorMsg = errorMsg;
    }

    public AppException(ExceptionStatus status, String errorMsg, boolean isShowErrorToast) {
        this.status = status;
        this.errorMsg = errorMsg;
        this.showErrorToast = isShowErrorToast;
    }

    public <T> AppException(String errorCode, String errorMsg, T result) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
        this.result = result;
    }
}
