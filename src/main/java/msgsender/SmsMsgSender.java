package msgsender;

import enums.SenderType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import senderparams.BaseSenderParams;

/** Сповіщувач sms. */
public class SmsMsgSender extends ViberSmsMsgSender {

    /** Конструктор за замовчанням. */
    public SmsMsgSender() {
        super(SenderType.SMS);
    }

    @Autowired
    public void setParams(@Qualifier(value = "smsParams") BaseSenderParams params) {
        super.setParams(params);
    }
}
