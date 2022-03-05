package co.com.meli.challenger.model;

import javax.validation.constraints.*;
import java.util.List;

public class Satellite {

    @NotNull(message = "The variable name cannot be  null.")
    @NotEmpty(message = "The variable name cannot be empty.")
    protected String name;
    @NotNull(message = "The variable distance cannot be null.")
    protected double distance;
    @NotNull(message = "The variable message cannot be null.")
    protected List<String> message;

    public Satellite() {
        super();
    }

    public Satellite(String name, double distance, List<String> message) {
        this.name = name;
        this.distance = distance;
        this.message = message;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getDistance() {
        return distance;
   }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public List<String> getMessage() {
        return message;
    }

    public void setMessage(List<String> message) {
        this.message = message;
    }
}
