package co.com.meli.challenger.model;

import java.util.List;

public class RepositoryResponse {

    List<DataMessage> listDataMessage;

    public RepositoryResponse() {
        super();
    }

    public RepositoryResponse(List<DataMessage> listDataMessage) {
        this.listDataMessage = listDataMessage;
    }

    public List<DataMessage> getListDataMessage() {
        return listDataMessage;
    }

    public void setListDataMessage(List<DataMessage> listDataMessage) {
        this.listDataMessage = listDataMessage;
    }
}
