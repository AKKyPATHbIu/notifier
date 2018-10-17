package db;

import enums.MsgStatus;

import java.sql.SQLException;

/** Інтерфейс допоміжної утиліти для роботи з базою. */
public interface DBUtil {

    /**
     * Змінити статуc завдання (notifier_tasks)
     * @param taskId  ідентифікатор завдання
     * @param status  статус завдання
     * @param errMsg  текст помилки, якщо статус ERR
     * @throws SQLException
     */
    void changeTaskStatus(Long taskId, MsgStatus status, String errMsg) throws SQLException;

    /**
     * Вставити запис про спробу відправки повідомлення до журналу відправлених повідомлень (notifier_log)
     * @param taskId  ідентифікатор завдання
     * @param notifierTp  ідентифікатор типу повідомлення (мейлб смсб вайбер)
     * @return  ідентифікатор створеного запису в журналі відправок
     * @throws SQLException
     */
    Long insertLog(Long taskId, Long notifierTp) throws SQLException;


    /**
     * Змінити статус відправки повідомлення в журналі відправлених повідомлень
     * @param logId  ідентифікатор запису в журналі повідомлень
     * @param status cтатус запису в журналі повідомлень
     * @param errMsg nекст помилки, якщо статус ERR
     * @throws SQLException
     */
    void changeLogStatus(Long logId, MsgStatus status, String errMsg) throws SQLException;
}
