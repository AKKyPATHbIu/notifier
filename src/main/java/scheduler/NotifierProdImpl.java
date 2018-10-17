package scheduler;

import db.DBUtil;
import db.Task;
import db.TaskUtil;
import enums.MsgStatus;
import enums.SenderType;
import msgsender.MsgDetail;
import msgsender.MsgSender;
import msgsender.factory.MsgSenderFactory;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.sql.SQLException;
import java.util.List;

/** Реалізація сповіщувача профіля продакшн. */
public class NotifierProdImpl implements Notifier {

    @Autowired
    DBUtil dbUtil;

    @Autowired
    TaskUtil taskUtil;

    @Autowired
    MsgSenderFactory msgSenderFactory;

    @Autowired
    @Qualifier(value = "transactionManager")
    private PlatformTransactionManager txManager;

    /** Логер. */
    private static Logger logger = Logger.getLogger(NotifierProdImpl.class);

    public void execute() {
        logger.info("Запуск сповіщувача...");
        List<Task> tasks = taskUtil.loadTasks();

        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus txStatus = txManager.getTransaction(def);

        try {
            for (Task task : tasks) {
                try {
                    MsgStatus taskStatus = MsgStatus.OK;
                    dbUtil.changeTaskStatus(task.getId(), MsgStatus.INPROGRESS, null);
                    for (SenderType senderType : SenderType.values()) {
                        MsgSender sender = msgSenderFactory.getInstance(senderType);
                        if (sender.isEnabled()) {
                            for (MsgDetail msgDetail : task.getNotificationsFor(senderType)) {
                                Long logId = dbUtil.insertLog(task.getId(), sender.getTypeId());
                                try {
                                    sender.send(msgDetail);
                                } catch (Exception ex) {
                                    taskStatus = MsgStatus.PARTIAL;
                                    dbUtil.changeLogStatus(logId, MsgStatus.ERROR, getStackTrace(ex));
                                }
                            }
                        }
                    }
                    dbUtil.changeTaskStatus(task.getId(), taskStatus, null);
                } catch (Exception ex) {
                    try {
                        dbUtil.changeTaskStatus(task.getId(), MsgStatus.PARTIAL, null);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        } finally {
            txManager.commit(txStatus);
        }
        logger.info("Сповіщення розіслано!");
    }

    /**
     * Отримати трейс у вигляді строки
     * @param e Exception що має бути виведений у вигляді строки
     * @return текст трейса
     */
    private String getStackTrace(Exception e) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
        e.printStackTrace(ps);
        ps.close();
        return baos.toString();
    }
}
