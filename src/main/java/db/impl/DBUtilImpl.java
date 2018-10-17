package db.impl;

import db.DBUtil;
import enums.MsgStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;

@Service
@Transactional("transactionManager")
/** Реалізація допоміжної утиліти для роботи з базою. */
public class DBUtilImpl implements DBUtil {

    /** Наіменування схеми бд. */
    private final String SCHEMA_NAME = "embas";

    /** Об'єкт зміни статусу таска. */
    private SimpleJdbcCall changeTaskStatus;

    /** Об'єкт додавання запису в журнал подій. */
    private SimpleJdbcCall insertLog;

    /** Об'єкт зміни статусу запису журналу подій.  */
    private SimpleJdbcCall changeLogStatus;

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        changeTaskStatus = new SimpleJdbcCall(jdbcTemplate).withSchemaName(SCHEMA_NAME).withFunctionName("notif_task_change_status");
        insertLog = new SimpleJdbcCall(jdbcTemplate).withSchemaName(SCHEMA_NAME).withFunctionName("notif_log_add");
        changeLogStatus = new SimpleJdbcCall(jdbcTemplate).withSchemaName(SCHEMA_NAME).withFunctionName("notif_log_change_status");
    }

    @Override
    public void changeTaskStatus(Long taskId, MsgStatus status, String errMsg) throws SQLException {
        SqlParameterSource inParams = new MapSqlParameterSource()
                .addValue("p_task_id", taskId)
                .addValue("p_status", status.getDbValue())
                .addValue("p_err_msg", errMsg);
        changeTaskStatus.execute(inParams);
    }

    @Override
    public Long insertLog(Long taskId, Long notifierTp) throws SQLException {
        SqlParameterSource inPparams = new MapSqlParameterSource()
                .addValue("p_task_id", taskId)
                .addValue("p_notif_tp", notifierTp);
        return insertLog.executeFunction(Long.class, inPparams).longValue();
    }

    @Override
    public void changeLogStatus(Long logId, MsgStatus status, String errMsg) throws SQLException {
        SqlParameterSource inPparams = new MapSqlParameterSource()
                .addValue("p_log_id", logId)
                .addValue("p_status", status.getDbValue())
                .addValue("p_err_msg", errMsg);
        changeLogStatus.execute(inPparams);
    }
}
