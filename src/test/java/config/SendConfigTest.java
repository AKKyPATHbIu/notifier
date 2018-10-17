package config;

import com.github.messenger4j.Messenger;
import msgsender.MsgSender;
import msgsender.factory.MsgSenderFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;
import scheduler.Notifier;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@TestPropertySource("classpath:notifier.properties")
@WebAppConfiguration
@ContextConfiguration(loader = AnnotationConfigWebContextLoader.class,
        classes = { RootConfig.class })
@ActiveProfiles("DEV")
public class SendConfigTest {

    @Autowired
    Messenger messenger;

    @Autowired
    @Qualifier(value = "emailMsgSender")
    MsgSender emailMsgSender;

    @Autowired
    @Qualifier(value = "smsMsgSender")
    MsgSender smsMsgSender;

    @Autowired
    @Qualifier(value = "viberMsgSender")
    MsgSender viberMsgSender;

    @Autowired
    @Qualifier(value = "facebookMsgSender")
    MsgSender facebookMsgSender;

    @Autowired
    MsgSenderFactory msgSenderFactory;

    @Autowired
    Notifier notifier;

    @Test
    public void test() {
        assertNotNull(messenger);
        assertNotNull(emailMsgSender);
        assertNotNull(smsMsgSender);
        assertNotNull(viberMsgSender);
        assertNotNull(facebookMsgSender);
        assertNotNull(msgSenderFactory);
        assertNotNull(notifier);
    }
}