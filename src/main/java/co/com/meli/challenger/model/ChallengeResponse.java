package co.com.meli.challenger.model;

public class ChallengeResponse {

    protected Position position;
    protected String message;

    public ChallengeResponse() {
        super();
    }

    public ChallengeResponse(Position position, String message) {
        this.position = position;
        this.message = message;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
