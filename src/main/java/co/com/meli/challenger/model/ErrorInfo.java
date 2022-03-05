package co.com.meli.challenger.model;

import java.util.ArrayList;
import java.util.List;


public class ErrorInfo {

    protected List<String> listMessage = new ArrayList<String>();

    protected String message;

    protected String uri;

    protected long timeStamp = System.currentTimeMillis();

    public ErrorInfo() {
        super();
    }

    public ErrorInfo(String message, String uri, long timeStamp) {
        this.message = message;
        this.uri = uri;
        this.timeStamp = timeStamp;
    }

    public ErrorInfo(List<String> listMessage, String message, String uri, long timeStamp) {
        this.listMessage = listMessage;
        this.message = message;
        this.uri = uri;
        this.timeStamp = timeStamp;
    }

    public List<String> getListMessage() {
        return listMessage;
    }

    public void setListMessage(List<String> listMessage) {
        this.listMessage = listMessage;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }
}
