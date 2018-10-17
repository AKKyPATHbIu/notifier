package senderparams;

/** Параметри сповіщувача email. */
public interface EmailParams extends BaseSenderParams {

    /**
     * Отримати сервер smtp
     * @return сервер smtp
     */
    String getSmtpServer();

    /**
     * Отримати порт smtp
     * @return порт smtp
     */
    Integer getSmptPort();

    /**
     * Отримати відправника
     * @return відправник
     */
    String getFrom();
}
