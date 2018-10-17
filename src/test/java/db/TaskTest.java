package db;

import enums.SenderType;
import msgsender.MsgDetail;
import org.json.JSONObject;
import org.junit.Test;

import java.util.List;
import java.util.Objects;

import static org.junit.Assert.*;

public class TaskTest {

    @Test
    public void test() {
        Task task = new Task(10L, new JSONObject(getJsonObj()));
        assertEquals(new Long(10L), task.getId());
        List<MsgDetail> messages = task.getNotificationsFor(SenderType.EMAIL);
        assertEquals(1, messages.size());
        MsgDetail msgDetail = messages.get(0);
        testData(msgDetail, "email");

        messages = task.getNotificationsFor(SenderType.SMS);
        assertEquals(1, messages.size());
        msgDetail = messages.get(0);
        testData(msgDetail, "sms");

        messages = task.getNotificationsFor(SenderType.VIBER);
        assertEquals(1, messages.size());
        msgDetail = messages.get(0);
        testData(msgDetail, "viber");

        messages = task.getNotificationsFor(SenderType.FACEBOOK);
        assertEquals(1, messages.size());
        msgDetail = messages.get(0);
        testData(msgDetail, "facebook");
   }

    private String getJsonObj() {
        StringBuilder result = new StringBuilder();

        result.append("{");
        result.append("\"data\": {");
        result.append("\"messages\": [");
        result.append("{");
        result.append("\"subject\": \"email_subj\",");
        result.append("\"body\": \"email_body\",");
        result.append("\"type\": \"MailMsg\",");
        result.append("\"address\": \"email_addr\",");
        result.append("\"typeId\": 2");
        result.append("},");
        result.append("{");
        result.append("\"subject\": \"sms_subj\",");
        result.append("\"body\": \"sms_body\",");
        result.append("\"type\": \"SmsMsg\",");
        result.append("\"address\": \"sms_addr\",");
        result.append("\"typeId\": 5");
        result.append("},");
        result.append("{");
        result.append("\"subject\": \"viber_subj\",");
        result.append("\"body\": \"viber_body\",");
        result.append("\"type\": \"ViberMsg\",");
        result.append("\"address\": \"viber_addr\",");
        result.append("\"typeId\": 3");
        result.append("},");
        result.append("{");
        result.append("\"subject\": \"facebook_subj\",");
        result.append("\"body\": \"facebook_body\",");
        result.append("\"type\": \"FacebookMsg\",");
        result.append("\"address\": \"facebook_addr\",");
        result.append("\"typeId\": 3");
        result.append("}");
        result.append("]");
        result.append("}");
        result.append("}");

        return result.toString();
    }

    private void testData(MsgDetail msgDetail, String type) {
        assertEquals(type + "_addr", msgDetail.getAddress());
        assertEquals(type + "_subj", msgDetail.getSubject());
        assertEquals(type + "_body", msgDetail.getBody());
    }

    @Test
    public void testEquals() {
        Task task1 = new Task(10L, new JSONObject(getJsonObj()));
        assertEquals(task1, task1);
        assertNotEquals(task1, new Object());

        Task task2 = new Task(2L, new JSONObject(getJsonObj()));
        assertNotEquals(task1, task2);

        Task task3 = new Task(10L, new JSONObject(getJsonObj()));
        assertEquals(task1, task3);
    }

    @Test
    public void testHashCode() {
        Task task1 = new Task(null, new JSONObject(getJsonObj()));
        assertEquals(Objects.hash((Long) null), task1.hashCode());
        Task task2 = new Task(100L, new JSONObject(getJsonObj()));
        assertEquals(Objects.hash(task2.getId()), task2.hashCode());
    }
}