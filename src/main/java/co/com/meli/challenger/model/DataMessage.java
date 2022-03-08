package co.com.meli.challenger.model;

import javax.persistence.*;
@Entity
@Table(name = "Message")
public class DataMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(name = "distanceKenobi")
    private String distanceKenobi;
    @Column(name = "distanceSato")
    private String distanceSato;
    @Column(name = "distanceSkywalker")
    private String distanceSkywalker;
    @Column(name = "message")
    private String message;
    @Column(name = "type")
    private String type;
    @Column(name = "X")
    private String positionX;
    @Column(name = "Y")
    private String positionY;

    public DataMessage() {
        super();
    }

    public DataMessage(long id, String distanceKenobi, String distanceSato, String distanceSkywalker, String message, String type, String positionX, String positionY) {
        this.id = id;
        this.distanceKenobi = distanceKenobi;
        this.distanceSato = distanceSato;
        this.distanceSkywalker = distanceSkywalker;
        this.message = message;
        this.type = type;
        this.positionX = positionX;
        this.positionY = positionY;
    }

    public String getPositionX() {
        return positionX;
    }

    public void setPositionX(String positionX) {
        this.positionX = positionX;
    }

    public String getPositionY() {
        return positionY;
    }

    public void setPositionY(String positionY) {
        this.positionY = positionY;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDistanceKenobi() {
        return distanceKenobi;
    }

    public void setDistanceKenobi(String distanceKenobi) {
        this.distanceKenobi = distanceKenobi;
    }

    public String getDistanceSato() {
        return distanceSato;
    }

    public void setDistanceSato(String distanceSato) {
        this.distanceSato = distanceSato;
    }

    public String getDistanceSkywalker() {
        return distanceSkywalker;
    }

    public void setDistanceSkywalker(String distanceSkywalker) {
        this.distanceSkywalker = distanceSkywalker;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
