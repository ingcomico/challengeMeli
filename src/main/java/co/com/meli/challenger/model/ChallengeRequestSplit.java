package co.com.meli.challenger.model;

import javax.validation.constraints.NotNull;
import java.util.List;

public class ChallengeRequestSplit {

    @NotNull(message = "The variable distance cannot be null.")
    protected double distance;
    @NotNull(message = "The variable message cannot be null.")
    protected List<String> message;

    public ChallengeRequestSplit() {
        super();
    }

    public ChallengeRequestSplit(double distance, List<String> message) {
        this.distance = distance;
        this.message = message;
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
