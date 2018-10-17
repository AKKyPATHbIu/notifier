package msgsender;

import enums.SenderType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import senderparams.BaseSenderParams;

/** Сповіщувач viber. */
public class ViberMsgSender extends ViberSmsMsgSender {

    /** Конструктор за замовчаннчям. */
    public ViberMsgSender() {
        super(SenderType.VIBER);
    }

    @Autowired
    public void setParams(@Qualifier(value = "viberParams") BaseSenderParams params) {
        super.setParams(params);
    }

    @Override
    protected String buildUrl(MsgDetail msgDetail) {
        return super.buildUrl(msgDetail) + "&viber=1";
    }
}
