package msgsender;

import org.json.JSONObject;

/** Параметри повідомлення. */
public class MsgDetail {

    /** Адреса повідомлення. */
    private final String address;

    /** Тема повідомлення. */
    private final String subject;

    /** Текст повідомлення. */
    private final String body;

    /**
     * Конструктор
     * @param json параметри повідомлення у вигляді JSON
     */
    public MsgDetail(JSONObject json) {
        this.address = json.getString("address");
        this.subject = json.getString("subject");
        this.body = json.getString("body");
    }

    /**
     * Отримати адресу повідомлення
     * @return адреса повідомлення
     */
    public String getAddress() {
        return address;
    }

    /**
     * Отримати тему повідомлення
     * @return тема повідомлення
     */
    public String getSubject() {
        return subject;
    }

    /**
     * Отримати текст повідомлення
     * @return текст повідомлення
     */
    public String getBody() {
        return body;
    }
}
