package co.com.meli.challenger.model;

import java.util.ArrayList;
import java.util.List;

public class SingletonSatellite {

    protected List<Satellite> listSatellite = new ArrayList();
    private static SingletonSatellite singletonSatellite;

    public  static SingletonSatellite getInfoSatellites() {
        if (singletonSatellite==null) {
            singletonSatellite = new SingletonSatellite();
        }
        return singletonSatellite;
    }

    private SingletonSatellite(){
        super();
    }

    public List<Satellite> getListSatellite() {
        return listSatellite;
    }

    public void setListSatellite(List<Satellite> listSatellite) {
        this.listSatellite = listSatellite;
    }
}

