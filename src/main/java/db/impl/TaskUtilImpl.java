package db.impl;

import db.Task;
import db.TaskUtil;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

@PropertySource("classpath:notifier.properties")
/** Реалізація інтерфейсу допоміжної утиліти завантаження тасків. */
public class TaskUtilImpl implements TaskUtil {

    /** Шаблон jdbc. */
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /** Кількість тасків для завантаження за замовчанням. */
    private final String DEFAULT_RECORD_COUNT = "10";

    /** Кількість тасків для завантаження. */
    private Integer recordCount;

    /** Запит вибірки списку тасків з бд. */
    private final String SELECT_TASKS_SQL = "SELECT id, package FROM notifier_tasks WHERE status = 'NEW' and dt > now() - interval '7 days' ORDER BY dt LIMIT ? ";

    @Autowired
    public void setEnv(Environment env) {
        recordCount = new Integer(env.getProperty("recordCount", DEFAULT_RECORD_COUNT));
    }

    @Override
    public List<Task> loadTasks() {
        SqlRowSet srs = jdbcTemplate.queryForRowSet(SELECT_TASKS_SQL, new Object[] { recordCount }, new int[] { Types.BIGINT });
        return mapAll(srs);
    }

    /**
     * Перетворити набір даних на список сутностей
     * @param srs набір даних з бд
     * @return список відповідних сутностей
     */
    public List<Task> mapAll(SqlRowSet srs) {
        List<Task> result = new ArrayList<>();
        while (srs.next()) {
            Long id = srs.getLong("id");
            JSONObject obj = new JSONObject(srs.getString("package"));
            result.add(new Task(id, obj));
        }
        return result;
    }
}
