package msgsender.factory;

import enums.SenderType;
import msgsender.MsgSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/** Реалізація фабрики відправників повідомлень. */
public class MsgSenderFactoryImpl implements MsgSenderFactory {

    @Autowired
    @Qualifier(value = "viberMsgSender")
    /** Відправник viber. */
    private MsgSender viberMsgSender;

    @Autowired
    @Qualifier(value = "smsMsgSender")
    /** Відправник sms. */
    private MsgSender smsMsgSender;

    @Autowired
    @Qualifier(value = "emailMsgSender")
    /** Відправник email. */
    private MsgSender emailMsgSender;

    @Autowired
    @Qualifier(value = "facebookMsgSender")
    /** Відправник facebook. */
    private MsgSender facebookMsgSender;

    @Override
    public MsgSender getInstance(SenderType type) {
        MsgSender result;
        switch(type) {
            case VIBER:
                result = viberMsgSender;
                break;

            case SMS:
                result = smsMsgSender;
                break;

            case EMAIL:
                result = emailMsgSender;
                break;

            case FACEBOOK:
                result = facebookMsgSender;
                break;

            default: result = emailMsgSender;
        }
        return result;
    }
}
