package booking.alore.booking.rest.model;


import jdk.nashorn.api.scripting.JSObject;

public class ErrorResponseModel
{
    private String errorMsg;
    private int  errorCode;

    public ErrorResponseModel(String message, int code) {
        this.errorMsg = message;
        this.errorCode = code;
    }
}
