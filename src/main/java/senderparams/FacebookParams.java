package senderparams;

/** Параметри сповіщувача facebook. */
public interface FacebookParams extends BaseSenderParams {

    /**
     * Отримати секрет додатку
     * @return секрет додатку
     */
    String getAppSecret();

    /**
     * Отримати маркер доступу сторінки
     * @return маркер доступу сторінки
     */
    String getPageAccessToken();

    /**
     * Отримати маркер webhook
     * @return маркер webhook
     */
    String getVerifyToken();
}
