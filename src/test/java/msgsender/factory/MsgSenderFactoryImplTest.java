package msgsender.factory;

import config.RootConfig;
import enums.SenderType;
import msgsender.MsgSender;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@TestPropertySource("classpath:notifier.properties")
@WebAppConfiguration
@ContextConfiguration(loader = AnnotationConfigWebContextLoader.class,
        classes = { RootConfig.class })
@ActiveProfiles("DEV")
public class MsgSenderFactoryImplTest {

    @Autowired
    MsgSenderFactory senderFactory;

    @Test
    public void getInstance() {
        assertNotNull(senderFactory);
        SenderType[] availSenders = new SenderType[] { SenderType.EMAIL, SenderType.SMS, SenderType.VIBER, SenderType.FACEBOOK };
        for (SenderType senderType : availSenders) {
            MsgSender sender = senderFactory.getInstance(senderType);
            assertNotNull(sender);
            assertEquals(senderType, sender.getType());
        }

        //При спробі отримати нереалізованого відправника повідомлень повертає email
        MsgSender sender = senderFactory.getInstance(SenderType.TELEGRAM);
        assertNotNull(sender);
        assertEquals(SenderType.EMAIL, sender.getType());
    }
}