package enums;

/** Статус повідомлення. */
public enum MsgStatus { NEW("NEW"), INPROGRESS("INPROGRESS"), OK("OK"), ERROR("ERR"), PARTIAL("PARTIAL");

    /** Відповідне значення в базі. */
    private final String dbValue;

    /**
     * Конструктор
     * @param dbValue значення статуса в базі
     */
    MsgStatus(String dbValue) {
        this.dbValue = dbValue;
    }

    /**
     * Отримати значення статуса в базі
     * @return значення статуса в базі
     */
    public String getDbValue() {
        return dbValue;
    }
}
