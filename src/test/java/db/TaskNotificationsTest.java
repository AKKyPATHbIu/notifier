package db;

import enums.SenderType;
import msgsender.MsgDetail;
import org.json.JSONObject;
import org.junit.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class TaskNotificationsTest {

    private final String jsonTemplate = "{ \"address\":\"%s\", \"subject\":\"%s\", \"body\":\"%s\"}";

    @Test
    public void test() {
        TaskNotifications taskNotifications = new TaskNotifications();
        for (SenderType senderType : SenderType.values()) {
            assertEquals(Collections.emptyList(), taskNotifications.getFor(senderType));
        }

        String email = "gmail@gmail.com";
        MsgDetail emailMsg1 = new MsgDetail(new JSONObject(String.format(jsonTemplate, email, "subj1", "body1")));
        MsgDetail emailMsg2 = new MsgDetail(new JSONObject(String.format(jsonTemplate, email, "subj2", "body2")));
        MsgDetail emailMsg3 = new MsgDetail(new JSONObject(String.format(jsonTemplate, email, "subj3", "body3")));
        taskNotifications.addFor(SenderType.EMAIL, emailMsg1);
        taskNotifications.addFor(SenderType.EMAIL, emailMsg2);
        taskNotifications.addFor(SenderType.EMAIL, emailMsg3);
        assertEquals(3, taskNotifications.getFor(SenderType.EMAIL).size());

        for (SenderType senderType : new SenderType[] { SenderType.SMS, SenderType.VIBER, SenderType.FACEBOOK, SenderType.TELEGRAM }) {
            assertEquals(Collections.emptyList(), taskNotifications.getFor(senderType));
        }

        String phoneNumber = "+30501366187";
        MsgDetail smsMsg1 = new MsgDetail(new JSONObject(String.format(jsonTemplate, phoneNumber, "subj1", "body1")));
        MsgDetail smsMsg2 = new MsgDetail(new JSONObject(String.format(jsonTemplate, phoneNumber, "subj2", "body2")));
        taskNotifications.addFor(SenderType.SMS, smsMsg1);
        taskNotifications.addFor(SenderType.SMS, smsMsg2);

        assertEquals(3, taskNotifications.getFor(SenderType.EMAIL).size());
        assertEquals(2, taskNotifications.getFor(SenderType.SMS).size());

        for (SenderType senderType : new SenderType[] { SenderType.VIBER, SenderType.FACEBOOK, SenderType.TELEGRAM }) {
            assertEquals(Collections.emptyList(), taskNotifications.getFor(senderType));
        }

        String psid = "1234567891011";
        MsgDetail fbMsg = new MsgDetail(new JSONObject(String.format(jsonTemplate, psid, "subj1", "body1")));
        taskNotifications.addFor(SenderType.FACEBOOK, fbMsg);

        assertEquals(3, taskNotifications.getFor(SenderType.EMAIL).size());
        assertEquals(2, taskNotifications.getFor(SenderType.SMS).size());
        assertEquals(1, taskNotifications.getFor(SenderType.FACEBOOK).size());

        List<MsgDetail> emailMessages = taskNotifications.getFor(SenderType.EMAIL);
        assertEquals(emailMsg1, emailMessages.get(0));
        assertEquals(emailMsg2, emailMessages.get(1));
        assertEquals(emailMsg3, emailMessages.get(2));

        List<MsgDetail> smsMessages = taskNotifications.getFor(SenderType.SMS);
        assertEquals(smsMsg1, smsMessages.get(0));
        assertEquals(smsMsg2, smsMessages.get(1));

        List<MsgDetail> fbMessages = taskNotifications.getFor(SenderType.FACEBOOK);
        assertEquals(fbMsg, fbMessages.get(0));
    }
}