package db.impl;

import config.RootConfig;
import db.Task;
import db.TaskUtil;
import enums.MsgStatus;
import enums.SenderType;
import msgsender.MsgDetail;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
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

import java.util.*;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@TestPropertySource("classpath:notifier.properties")
@WebAppConfiguration
@ContextConfiguration(loader = AnnotationConfigWebContextLoader.class,
        classes = { RootConfig.class })
@ActiveProfiles("DEV")
public class TaskUtilImplTest {

    @Autowired
    private TaskUtil taskUtil;

    @Autowired
    @Qualifier(value = "transactionManager")
    private PlatformTransactionManager txManager;

    private JdbcTemplate jdbcTemplate;

    private SimpleJdbcInsert jdbcInsert;

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
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus txStatus = txManager.getTransaction(def);

        try {
            Long clinicId = jdbcTemplate.queryForObject("SELECT id FROM clinics LIMIT 1", Long.class);
            assertNotNull(clinicId);

            StringBuilder json = new StringBuilder();
            json.append("{ \"data\": {");
            json.append("\"messages\": [");
            json.append("{");
            json.append("\"subject\": \"test viber\",");
            json.append("\"body\": \"test viber body\",");
            json.append("\"type\": \"ViberMsg\",");
            json.append("\"address\": \"+380504144695\"");
            json.append("},");
            json.append("{");
            json.append("\"subject\": \"test mail\",");
            json.append("\"body\": \"test mail body\",");
            json.append("\"type\": \"MailMsg\",");
            json.append("\"address\": \"alextov@ukr.net\"");
            json.append("},");
            json.append("{");
            json.append("\"subject\": \"test fb\",");
            json.append("\"body\": \"test fb body\",");
            json.append("\"type\": \"FacebookMsg\",");
            json.append("\"address\": \"2007548539311631\"");
            json.append("},");
            json.append("{");
            json.append("\"subject\": \"test sms\",");
            json.append("\"body\": \"test sms body\",");
            json.append("\"type\": \"SmsMsg\",");
            json.append("\"address\": \"+380504144695\"");
            json.append("}");
            json.append("]");
            json.append("}}");

            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) + 1000);

            Map<String, Object> params = new HashMap<>();
            params.put("dt", calendar.getTime());
            params.put("clinica_id", clinicId);
            params.put("event_dt", new Date());
            params.put("event_name", "some event");
            params.put("user_login", "appsecembas");
            params.put("status", MsgStatus.NEW);
            params.put("package", json.toString());

            Long taskId1 = jdbcInsert.executeAndReturnKey(params).longValue();
            assertNotNull(taskId1);


            json = new StringBuilder();
            json.append("{ \"data\": {");
            json.append("\"messages\": [");
            json.append("{");
            json.append("\"subject\": \"viber_subj\",");
            json.append("\"body\": \"viber_body\",");
            json.append("\"type\": \"ViberMsg\",");
            json.append("\"address\": \"+11111\"");
            json.append("},");
            json.append("{");
            json.append("\"subject\": \"viber_subj1\",");
            json.append("\"body\": \"viber_body1\",");
            json.append("\"type\": \"ViberMsg\",");
            json.append("\"address\": \"+22222\"");
            json.append("},");
            json.append("{");
            json.append("\"subject\": \"mail_subj\",");
            json.append("\"body\": \"mail_body\",");
            json.append("\"type\": \"MailMsg\",");
            json.append("\"address\": \"gmail@gmail.com\"");
            json.append("}");
            json.append("]");
            json.append("}}");

            calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) + 1001);

            params = new HashMap<>();
            params.put("dt", calendar.getTime());
            params.put("clinica_id", clinicId);
            params.put("event_dt", new Date());
            params.put("event_name", "some event");
            params.put("user_login", "appsecembas");
            params.put("status", MsgStatus.NEW);
            params.put("package", json.toString());

            Long taskId2 = jdbcInsert.executeAndReturnKey(params).longValue();
            assertNotNull(taskId2);

            List<Task> tasks = taskUtil.loadTasks();
            assertTrue(tasks.size() >= 2);

            Task task1 = tasks.get(0);
            assertEquals(task1.getId(), taskId1);
            assertTrue(task1.getNotificationsFor(SenderType.TELEGRAM).isEmpty());
            assertEquals(task1.getNotificationsFor(SenderType.EMAIL).size(), 1);
            assertEquals(task1.getNotificationsFor(SenderType.SMS).size(), 1);
            assertEquals(task1.getNotificationsFor(SenderType.VIBER).size(), 1);
            assertEquals(task1.getNotificationsFor(SenderType.FACEBOOK).size(), 1);

            List<MsgDetail> msgDetails = task1.getNotificationsFor(SenderType.EMAIL);
            MsgDetail details = msgDetails.get(0);
            assertEquals("test mail", details.getSubject());
            assertEquals("test mail body", details.getBody());
            assertEquals("alextov@ukr.net", details.getAddress());

            msgDetails = task1.getNotificationsFor(SenderType.VIBER);
            details = msgDetails.get(0);
            assertEquals("test viber", details.getSubject());
            assertEquals("test viber body", details.getBody());
            assertEquals("+380504144695", details.getAddress());

            msgDetails = task1.getNotificationsFor(SenderType.FACEBOOK);
            details = msgDetails.get(0);
            assertEquals("test fb", details.getSubject());
            assertEquals("test fb body", details.getBody());
            assertEquals("2007548539311631", details.getAddress());

            msgDetails = task1.getNotificationsFor(SenderType.SMS);
            details = msgDetails.get(0);
            assertEquals("test sms", details.getSubject());
            assertEquals("test sms body", details.getBody());
            assertEquals("+380504144695", details.getAddress());

            Task task2 = tasks.get(1);
            assertTrue(task2.getNotificationsFor(SenderType.TELEGRAM).isEmpty());
            assertEquals(task2.getNotificationsFor(SenderType.EMAIL).size(), 1);
            assertTrue(task2.getNotificationsFor(SenderType.SMS).isEmpty());
            assertEquals(task2.getNotificationsFor(SenderType.VIBER).size(), 2);
            assertTrue(task2.getNotificationsFor(SenderType.FACEBOOK).isEmpty());

            msgDetails = task2.getNotificationsFor(SenderType.EMAIL);
            details = msgDetails.get(0);
            assertEquals("mail_subj", details.getSubject());
            assertEquals("mail_body", details.getBody());
            assertEquals("gmail@gmail.com", details.getAddress());

            msgDetails = task2.getNotificationsFor(SenderType.VIBER);
            details = msgDetails.get(0);

            assertEquals("viber_subj", details.getSubject());
            assertEquals("viber_body", details.getBody());
            assertEquals("+11111", details.getAddress());

            details = msgDetails.get(1);

            assertEquals("viber_subj1", details.getSubject());
            assertEquals("viber_body1", details.getBody());
            assertEquals("+22222", details.getAddress());
        } finally {
            txManager.rollback(txStatus);
        }
    }
}