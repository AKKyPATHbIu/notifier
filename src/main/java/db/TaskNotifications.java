package db;

import enums.SenderType;
import msgsender.MsgDetail;

import java.util.*;

/** Спощівення таска. */
public class TaskNotifications {

    /** Список сповіщень таска. */
    private final Map<SenderType, List<MsgDetail>> notifications = new HashMap<> ();

    /**
     * Додати повідомлення
     * @param senderType тип відправника
     * @param msgDetail параметри повідомлення
     */
    public void addFor(SenderType senderType, MsgDetail msgDetail) {
        List<MsgDetail> messages = notifications.containsKey(senderType) ? notifications.get(senderType) : new ArrayList<>();
        messages.add(msgDetail);
        notifications.put(senderType, messages);
    }

    /**
     * Отримати список повідомлень для заданого відправника
     * @param senderType тип відправника повідомлень
     * @return список повідомлень для заданого відправника
     */
    public List<MsgDetail> getFor(SenderType senderType) {
        return notifications.containsKey(senderType) ? notifications.get(senderType) : Collections.emptyList();
    }
}
