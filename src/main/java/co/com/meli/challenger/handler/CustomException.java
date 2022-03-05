package co.com.meli.challenger.handler;

import org.springframework.http.HttpStatus;

public class CustomException extends Exception{

    private HttpStatus statusError;
    private String  strMessage;


    public CustomException(HttpStatus statusError, String message) {
        super(message);
        this.statusError = statusError;
    }

    public HttpStatus getStatusError() {
        return statusError;
    }

    public void setStatusError(HttpStatus statusError) {
        this.statusError = statusError;
    }

    public String getStrMessage() {
        return strMessage;
    }

    public void setStrMessage(String strMessage) {
        this.strMessage = strMessage;
    }
}
