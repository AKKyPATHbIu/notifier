package msgsender.factory;

import enums.SenderType;
import msgsender.MsgSender;

/** Фабрика відправників повідомлень. */
public interface MsgSenderFactory {

    /**
     * Отримати відповідного відправника повідомлення
     * @param type тип відпраника повідомлення
     * @return віповідний відправник повідомлення
     */
    MsgSender getInstance(SenderType type);
}
