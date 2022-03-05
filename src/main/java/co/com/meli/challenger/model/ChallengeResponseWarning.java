package co.com.meli.challenger.model;

public class ChallengeResponseWarning extends ChallengeResponse{

    protected String warning;

    public ChallengeResponseWarning(String warning) {
        this.warning = warning;
    }

    public ChallengeResponseWarning(Position position, String message, String warning) {
        super(position, message);
        this.warning = warning;
    }

    @Override
    public Position getPosition() {
        return super.getPosition();
    }

    @Override
    public void setPosition(Position position) {
        super.setPosition(position);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }

    @Override
    public void setMessage(String message) {
        super.setMessage(message);
    }

    public String getWarning() {
        return warning;
    }

    public void setWarning(String warning) {
        this.warning = warning;
    }
}
