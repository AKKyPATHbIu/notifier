package msgsender;

import enums.SenderType;

/** Базовий клас сповіщувача. */
public abstract class BaseMsgSender implements MsgSender {

    /** Тип повідомлення. */
    private final SenderType type;

    /**
     * Конструктор
     * @param type тип сповіщувача
     */
    public BaseMsgSender(SenderType type) {
        this.type = type;
    }

    /**
     * Отримати ознаку включеності сповіщувача
     * @return ознака включенності сповіщувача
     */
    public abstract boolean isEnabled();

    @Override
    public String toString() {
        return "MsgSender {" +
                "type='" + type.getDescr() + '\'' +
                ", typeId=" + type.getTypeId() +
                '}';
    }

    /**
     * Отримати тип сповіщувача
     * @return тип сповіщувача
     */
    public SenderType getType() {
        return type;
    }

    /**
     * Отримати ідентифікатор сповіщувача
     * @return ідентифікатр сповіщувача
     */
    public Long getTypeId() {
        return type.getTypeId();
    }
}
