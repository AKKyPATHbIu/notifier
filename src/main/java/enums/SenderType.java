package enums;

/** Типи сповіщувачів. */
public enum SenderType { EMAIL(2L, "Email"), VIBER(3L, "Viber"), TELEGRAM(4L, "Telegram"),
        SMS(5L, "Sms"), FACEBOOK(6L, "Facebook");

    /** Ідентифікатор типу сповіщувача. */
    private final Long typeId;

    /** Опис типу сповіщувача. */
    private final String descr;

    /**
     * Конструктор
     * @param typeId ідентифікатор типу сповіщувача
     * @param descr опис типу сповіщувача
     */
    SenderType(Long typeId, String descr) {
        this.typeId = typeId;
        this.descr = descr;
    }

    /**
     * Отримати ідентифікатор типу сповіщувача
     * @return ідентифікатор типу сповіщувача
     */
    public Long getTypeId() {
        return typeId;
    }

    /**
     * Отримати опис сповіщувача
     * @return опис сповіщувача
     */
    public String getDescr() {
        return descr;
    }

    /**
     * Отримати тип повідомлення за ім'ям
     * @param senderName ім'я сповіщувача
     * @return тип повідомлення за ім'ям
     */
    public static SenderType getTypeBy(String senderName) {
        return "ViberMsg".equalsIgnoreCase(senderName) ? SenderType.VIBER :
                "SmsMsg".equalsIgnoreCase(senderName) ? SenderType.SMS :
                "MailMsg".equalsIgnoreCase(senderName) ? SenderType.EMAIL :
                "FacebookMsg".equalsIgnoreCase(senderName) ? SenderType.FACEBOOK : SenderType.EMAIL;
    }
}
