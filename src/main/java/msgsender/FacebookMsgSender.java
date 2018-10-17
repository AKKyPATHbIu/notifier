package msgsender;

import com.github.messenger4j.Messenger;
import com.github.messenger4j.send.MessagePayload;
import com.github.messenger4j.send.MessageTag;
import com.github.messenger4j.send.MessagingType;
import com.github.messenger4j.send.message.TextMessage;
import com.github.messenger4j.send.recipient.IdRecipient;
import enums.SenderType;
import org.springframework.beans.factory.annotation.Autowired;
import senderparams.FacebookParams;

import java.util.Optional;

/** Сповіщувач facebook. */
public class FacebookMsgSender extends BaseMsgSender {

    /** Допоміжний клас для роботи з сповіщеннями facebook. */
    private Messenger messenger;

    /** Параметри відправника повідомлень facebook. */
    private FacebookParams params;

    /** Конструктор за замовчанням. */
    public FacebookMsgSender() {
        super(SenderType.FACEBOOK);
    }

    /**
     * Втановити параметри відправника повідомлень facebook
     * @param params параметри відправника
     */
    @Autowired
    public void setParams(FacebookParams params) {
        this.params = params;
    }

    /**
     * Втановити допоміжний клас відправки повідомлень facebook
     * @param messenger допоміжний клас відправки повідомлень facebook
     */
    @Autowired
    public void setMessenger(Messenger messenger) {
        this.messenger = messenger;
    }

    @Override
    public void send(MsgDetail msgDetail) {
        final MessagePayload payload = MessagePayload.create(IdRecipient.create(msgDetail.getAddress()),
                MessagingType.MESSAGE_TAG, TextMessage.create(msgDetail.getBody()), Optional.empty(),
                    Optional.of(MessageTag.COMMUNITY_ALERT));
        try {
            messenger.send(payload);
        } catch(Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    @Override
    public boolean isEnabled() {
        return params.isEnabled();
    }
}