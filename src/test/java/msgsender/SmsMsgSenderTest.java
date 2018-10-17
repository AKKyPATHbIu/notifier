package msgsender;

import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import senderparams.BaseSenderParams;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@TestPropertySource("classpath:notifier.properties")
@RunWith(MockitoJUnitRunner.class)
@ActiveProfiles("DEV")
public class SmsMsgSenderTest {

    @Mock
    BaseSenderParams smsParams;

    @InjectMocks
    SmsMsgSender smsSender = new SmsMsgSender();

    private String password = "sms_password";
    private String login = "sms_login";

    @Test
    public void test() {
        when(smsParams.isEnabled()).thenReturn(true);
        assertEquals(true, smsSender.isEnabled());

        when(smsParams.isEnabled()).thenReturn(false);
        assertEquals(false, smsSender.isEnabled());

        when(smsParams.getPassword()).thenReturn(password);
        when(smsParams.getLogin()).thenReturn(login);

        MsgDetail msgDetail = new MsgDetail(new JSONObject("{\"subject\":\"subj\", \"address\":\"addr\", \"body\":\"body\"}"));
        assertEquals("https://smsc.ua/sys/send.php?login=sms_login&psw=sms_password&phones=addr&charset=utf-8&mes=body", smsSender.buildUrl(msgDetail));
    }

}