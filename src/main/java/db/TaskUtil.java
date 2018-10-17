package db;

import java.util.List;

/** Інтерфейс допоміжної утиліти завантаження тасків. */
public interface TaskUtil {

    /**
     * Завантажити таски із сповіщеннями
     * @return таски із сповіщеннями
     */
    List<Task> loadTasks();
}
