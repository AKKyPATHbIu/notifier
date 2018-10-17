package scheduler;

import db.DBUtil;
import db.Task;
import db.TaskUtil;
import enums.MsgStatus;
import enums.SenderType;
import msgsender.MsgDetail;
import msgsender.MsgSender;
import msgsender.factory.MsgSenderFactory;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

@TestPropertySource("classpath:notifier.properties")
@RunWith(MockitoJUnitRunner.class)
@ActiveProfiles("PROD")
public class NotifierProdImplTest {

    @Mock
    DBUtil dbUtil;

    @Mock
    TaskUtil taskUtil;

    @Mock
    MsgSenderFactory msgSenderFactory;

    @Mock
    PlatformTransactionManager txManager;

    @InjectMocks
    Notifier notifier = new NotifierProdImpl();

    @Test
    public void test() throws Exception {
        Task task1 = mock(Task.class);
        Task task2 = mock(Task.class);

        when(task1.getId()).thenReturn(1L);
        when(task2.getId()).thenReturn(2L);

        List<Task> tasks = new ArrayList<>();
        tasks.add(task1);
        tasks.add(task2);

        when(taskUtil.loadTasks()).thenReturn(tasks);

        MsgSender telegramSender = mock(MsgSender.class);
        MsgSender emailSender = mock(MsgSender.class);
        MsgSender smsSender = mock(MsgSender.class);
        MsgSender viberSender = mock(MsgSender.class);
        MsgSender facebookSender = mock(MsgSender.class);

        when(msgSenderFactory.getInstance(SenderType.TELEGRAM)).thenReturn(telegramSender);
        when(msgSenderFactory.getInstance(SenderType.EMAIL)).thenReturn(emailSender);
        when(msgSenderFactory.getInstance(SenderType.SMS)).thenReturn(smsSender);
        when(msgSenderFactory.getInstance(SenderType.VIBER)).thenReturn(viberSender);
        when(msgSenderFactory.getInstance(SenderType.FACEBOOK)).thenReturn(facebookSender);


        when(telegramSender.isEnabled()).thenReturn(false);
        when(emailSender.isEnabled()).thenReturn(false);
        when(smsSender.isEnabled()).thenReturn(false);
        when(viberSender.isEnabled()).thenReturn(false);
        when(facebookSender.isEnabled()).thenReturn(false);

        notifier.execute();

        verify(telegramSender, times(0)).send(any(MsgDetail.class));
        verify(emailSender, times(0)).send(any(MsgDetail.class));
        verify(smsSender, times(0)).send(any(MsgDetail.class));
        verify(viberSender, times(0)).send(any(MsgDetail.class));
        verify(facebookSender, times(0)).send(any(MsgDetail.class));

        when(smsSender.isEnabled()).thenReturn(true);
        when(smsSender.getTypeId()).thenReturn(SenderType.SMS.getTypeId());

        List<MsgDetail> emptyList = new ArrayList<> ();

        when(task1.getNotificationsFor(SenderType.TELEGRAM)).thenReturn(emptyList);
        when(task1.getNotificationsFor(SenderType.VIBER)).thenReturn(emptyList);
        when(task1.getNotificationsFor(SenderType.EMAIL)).thenReturn(emptyList);
        when(task1.getNotificationsFor(SenderType.FACEBOOK)).thenReturn(emptyList);

        List<MsgDetail> smsMessages = new ArrayList<> ();
        MsgDetail msg1 = new MsgDetail(new JSONObject("{\"address\":\"zzz\", \"subject\":\"123\", \"body\":\"text\"}"));
        MsgDetail msg2 = new MsgDetail(new JSONObject("{\"address\":\"ddd\", \"subject\":\"111\", \"body\":\"text1\"}"));
        smsMessages.add(msg1);
        smsMessages.add(msg2);

        when(task1.getNotificationsFor(SenderType.SMS)).thenReturn(smsMessages);
        when(task2.getNotificationsFor(SenderType.SMS)).thenReturn(emptyList);

        notifier.execute();
        verify(smsSender, times(2)).send(any(MsgDetail.class));

        verify(dbUtil, times(2)).insertLog(task1.getId(), smsSender.getTypeId());
        verify(dbUtil, times(0)).insertLog(task2.getId(), smsSender.getTypeId());

        verify(dbUtil, times(0)).changeTaskStatus(task1.getId(), MsgStatus.PARTIAL, null);
        verify(dbUtil, times(0)).changeTaskStatus(task2.getId(), MsgStatus.PARTIAL, null);

        verify(dbUtil, times(2)).changeTaskStatus(task1.getId(), MsgStatus.OK, null);
        verify(dbUtil, times(2)).changeTaskStatus(task2.getId(), MsgStatus.OK, null);

        verify(dbUtil, times(0)).changeTaskStatus(any(Long.class), MsgStatus.ERROR, eq(anyString()));
        verify(dbUtil, times(0)).changeTaskStatus(any(Long.class), MsgStatus.PARTIAL, eq(anyString()));

        when(viberSender.isEnabled()).thenReturn(true);

        List<MsgDetail> viberMsgs = new ArrayList<> ();
        MsgDetail viberMsg = new MsgDetail(new JSONObject("{\"address\":\"vvv\", \"subject\":\"22\", \"body\":\"321\"}"));
        viberMsgs.add(viberMsg);

        when(task1.getNotificationsFor(SenderType.VIBER)).thenReturn(viberMsgs);
        when(viberSender.getTypeId()).thenReturn(SenderType.VIBER.getTypeId());
        doThrow(new Exception()).when(viberSender).send(viberMsg);
        when(dbUtil.insertLog(task1.getId(), SenderType.VIBER.getTypeId())).thenReturn(5L);
        notifier.execute();

        verify(dbUtil, times(1)).changeLogStatus(eq(5L), eq(MsgStatus.ERROR),  anyString());
        verify(dbUtil, times(1)).changeTaskStatus(task1.getId(), MsgStatus.PARTIAL, null);
    }
}