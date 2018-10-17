package db;

import enums.SenderType;
import msgsender.MsgDetail;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.*;

/** Таск із сповіщеннями. */
public class Task {

    /** Ідентифікатор таска. */
    private Long id;

    /** Сповіщення таска. */
    private final TaskNotifications notifications = new TaskNotifications();

    /**
     * Конструктор
     * @param id ідентифікатор таска
     * @param obj параметри сповіщень у вигляді JSON
     * @throws JSONException
     */
    public Task(Long id, JSONObject obj) throws JSONException {
        this.id = id;
        JSONArray notifArray = obj.getJSONObject("data").getJSONArray ("messages");
        for (int i = 0; i < notifArray.length(); i++) {
            JSONObject msgJSON = notifArray.getJSONObject(i);
            String senderTypeName = msgJSON.getString("type");
            SenderType senderType = SenderType.getTypeBy(senderTypeName);
            MsgDetail msgDetail = new MsgDetail(msgJSON);
            notifications.addFor(senderType, msgDetail);
        }
    }

    /**
     * Отримати ідентифікатор таска
     * @return ідентифікатор таска
     */
    public Long getId() {
        return id;
    }

    /**
     * Отримати список сповіщень для відправника заданого типу
     * @param senderType тип відправника сповіщень
     * @return перелік сповіщень для відправника заданого типу
     */
    public List<MsgDetail> getNotificationsFor(SenderType senderType) {
        return notifications.getFor(senderType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof Task)) {
            return false;
        }

        return this.id == null ? false : this.id.equals(((Task) o).id);
    }
}
