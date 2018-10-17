package msgsender;

import enums.SenderType;

/** Інтерфейс сповіщувача. */
public interface MsgSender {

    /**
     * Отримати тип сповіщувача
     * @return тип сповіщувача
     */
    SenderType getType();

    /**
     * Отримати ідентифікатор сповіщувача
     * @return ідентифікатр сповіщувача
     */
    Long getTypeId();

    /**
     * Надіслати сповіщення
     * @param msgDetail деталі сповіщення
     * @throws Exception
     */
    void send(MsgDetail msgDetail) throws Exception;

    /**
     * Отримати ознаку включеності сповіщувача
     * @return ознака включеності сповіщювача
     */
    boolean isEnabled();
}
