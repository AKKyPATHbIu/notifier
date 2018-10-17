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

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@TestPropertySource("classpath:notifier.properties")
@RunWith(MockitoJUnitRunner.class)
@ActiveProfiles("DEV")
public class ViberSmsMsgSenderTest {

    @Mock
    BaseSenderParams viberParams;

    @InjectMocks
    ViberMsgSender viberSender = new ViberMsgSender();

    private String password = "password";
    private String login = "login";

    @Test
    public void test() {
        when(viberParams.isEnabled()).thenReturn(true);
        assertEquals(true, viberSender.isEnabled());

        when(viberParams.isEnabled()).thenReturn(false);
        assertEquals(false, viberSender.isEnabled());

        when(viberParams.getPassword()).thenReturn(password);
        when(viberParams.getLogin()).thenReturn(login);

        MsgDetail msgDetail = new MsgDetail(new JSONObject("{\"subject\":\"subj\", \"address\":\"addr\", \"body\":\"body\"}"));
        assertEquals("https://smsc.ua/sys/send.php?login=login&psw=password&phones=addr&charset=utf-8&mes=body&viber=1", viberSender.buildUrl(msgDetail));
    }
}