package senderparams;

/**
 * Базовий інтерфейс налаштувань сповіщувача.<br>
 * Налаштування зберігаються в файлі <i>notifier.properties</i>
 */
public interface BaseSenderParams {

    /**
     * Отримати ознаку включеності сповіщувача
     * @return ознака включеності сповіщувача
     */
    boolean isEnabled();

    /**
     * Отримати ім'я користувача
     * @return ім'я користувача
     */
    String getLogin();

    /**
     * Отримати пароль
     * @return пароль
     */
    String getPassword();
}
