package co.com.meli.challenger.model;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

public class ChallengeRequest {

    @Valid
    @NotNull(message = "The variable satellites cannot be  null.")
    @Size(min = 3, max = 3, message = "The satellite list not contain enough information.")
    private List<Satellite> satellites;

    public ChallengeRequest() {
        super();
    }

    public ChallengeRequest(List<Satellite> satellites) {
        this.satellites = satellites;
    }

    public List<Satellite> getSatellites() {
        return satellites;
    }

    public void setSatellites(List<Satellite> satellites) {
        this.satellites = satellites;
    }
}
