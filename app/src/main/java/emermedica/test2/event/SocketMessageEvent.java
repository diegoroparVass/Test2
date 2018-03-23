package emermedica.test2.event;

/**
 * Created by Diego Roman on 22/03/2018.
 */
public class SocketMessageEvent {
    private String mMessage;

    public SocketMessageEvent(String message) {
        mMessage = message;
    }

    public String getMessage() {
        return mMessage;
    }
}
