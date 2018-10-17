package db.impl;

import config.RootConfig;
import db.DBUtil;
import enums.MsgStatus;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@TestPropertySource("classpath:notifier.properties")
@WebAppConfiguration
@ContextConfiguration(loader = AnnotationConfigWebContextLoader.class,
        classes = { RootConfig.class })
@ActiveProfiles("DEV")
public class DBUtilImplTest {

    @Autowired
    private DBUtil dbUtil;

    @Autowired
    @Qualifier(value = "transactionManager")
    private PlatformTransactionManager txManager;

    JdbcTemplate jdbcTemplate;

    SimpleJdbcInsert jdbcInsert;

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withSchemaName("embas")
                .withTableName("notifier_tasks")
                .usingGeneratedKeyColumns("id");
    }

    @Test
    public void test() {
        assertNotNull(dbUtil);
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus txStatus = txManager.getTransaction(def);

        try {
            Long clinicId = jdbcTemplate.queryForObject("SELECT id FROM clinics LIMIT 1", Long.class);
            assertNotNull(clinicId);

            String errorMsg = "huge error";

            Map<String, Object> params = new HashMap<>();
            params.put("dt", new Date());
            params.put("clinica_id", clinicId);
            params.put("event_dt", new Date());
            params.put("event_name", "some event");
            params.put("user_login", "appsecembas");
            params.put("status", MsgStatus.OK);
            params.put("err_msg", errorMsg);

            Long taskId = jdbcInsert.executeAndReturnKey(params).longValue();
            assertNotNull(taskId);

            NotifierTask task = findTaskBy(taskId);
            assertEquals(MsgStatus.OK.getDbValue(), task.status);
            assertEquals(errorMsg, task.errorMsg);

            errorMsg = "some random error";

            dbUtil.changeTaskStatus(taskId, MsgStatus.INPROGRESS, errorMsg);

            task = findTaskBy(taskId);
            assertEquals(MsgStatus.INPROGRESS.getDbValue(), task.status);
            assertEquals(errorMsg, task.errorMsg);

            Long notifTypeId = jdbcTemplate.queryForObject("SELECT id FROM notifier_tp LIMIT 1", Long.class);
            Long logId = dbUtil.insertLog(taskId, notifTypeId);
            assertNotNull(logId);

            NotifierLog log = findLogBy(logId);
            assertEquals(MsgStatus.OK.getDbValue(), log.status);
            assertNull(log.errorMsg);

            errorMsg = "message";
            dbUtil.changeLogStatus(logId, MsgStatus.INPROGRESS, errorMsg);
            log = findLogBy(logId);
            assertEquals(MsgStatus.INPROGRESS.getDbValue(), log.status);
            assertEquals(errorMsg, log.errorMsg);
        } catch (Exception ex) {
            ex.printStackTrace();
            fail();
        } finally {
            txManager.rollback(txStatus);
       }
    }


    private NotifierTask findTaskBy(Long id) {
        NotifierTask task = jdbcTemplate.queryForObject("SELECT status, err_msg FROM notifier_tasks WHERE id = ?", new Object[] { id } , new RowMapper<NotifierTask>() {
            @Override
            public NotifierTask mapRow(ResultSet resultSet, int i) throws SQLException {
                NotifierTask task = new NotifierTask();
                task.status = resultSet.getString("status");
                task.errorMsg = resultSet.getString("err_msg");
                return task;
            }
        });
        return task;
    }

    private NotifierLog findLogBy(Long id) {
        NotifierLog log = jdbcTemplate.queryForObject("SELECT status, err_msg FROM notifier_log WHERE id = ?", new Object[] { id } , new RowMapper<NotifierLog>() {
            @Override
            public NotifierLog mapRow(ResultSet resultSet, int i) throws SQLException {
                NotifierLog log = new NotifierLog();
                log.status = resultSet.getString("status");
                log.errorMsg = resultSet.getString("err_msg");
                return log;
            }
        });
        return log;
    }

    private class NotifierTask {
        private String status;
        private String errorMsg;
    }

    private class NotifierLog {
        private String status;
        private String errorMsg;
    }
}