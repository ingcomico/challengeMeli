package co.com.meli.challenger.model;

import java.util.List;

public class ChallegeResponseQueryResponse {

    protected List<Satellite> listSatellite;

    public ChallegeResponseQueryResponse() {
        super();
    }

    public ChallegeResponseQueryResponse(List<Satellite> listSatellite) {
        this.listSatellite = listSatellite;
    }

    public List<Satellite> getListSatellite() {
        return listSatellite;
    }

    public void setListSatellite(List<Satellite> listSatellite) {
        this.listSatellite = listSatellite;
    }
}
