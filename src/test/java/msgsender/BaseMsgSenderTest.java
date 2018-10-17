package msgsender;

import enums.SenderType;
import org.junit.Test;

import static org.junit.Assert.*;

public class BaseMsgSenderTest {

    @Test
    public void test() {

        BaseMsgSender sender = new BaseMsgSender(SenderType.SMS) {

            @Override
            public boolean isEnabled() {
                return false;
            }

            @Override
            public void send(MsgDetail msgDetail) throws Exception {

            }
        };

        assertEquals(SenderType.SMS, sender.getType());
        assertEquals(SenderType.SMS.getTypeId(), sender.getTypeId());
        assertEquals("MsgSender {type='Sms', typeId=5}", sender.toString());
    }
}