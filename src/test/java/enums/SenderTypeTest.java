package enums;

import org.junit.Test;

import static org.junit.Assert.*;

public class SenderTypeTest {

    @Test
    public void getTypeId() {
        assertEquals(new Long(2L), SenderType.EMAIL.getTypeId());
        assertEquals(new Long(3L), SenderType.VIBER.getTypeId());
        assertEquals(new Long(4L), SenderType.TELEGRAM.getTypeId());
        assertEquals(new Long(5L), SenderType.SMS.getTypeId());
        assertEquals(new Long(6L), SenderType.FACEBOOK.getTypeId());
    }

    @Test
    public void getDescr() {
        assertEquals("Email", SenderType.EMAIL.getDescr());
        assertEquals("Viber", SenderType.VIBER.getDescr());
        assertEquals("Telegram", SenderType.TELEGRAM.getDescr());
        assertEquals("Sms", SenderType.SMS.getDescr());
        assertEquals("Facebook", SenderType.FACEBOOK.getDescr());
    }

    @Test
    public void getTypeBy() {
        assertEquals(SenderType.VIBER, SenderType.getTypeBy("ViberMsg"));
        assertEquals(SenderType.VIBER, SenderType.getTypeBy("VIBERMsg"));
        assertEquals(SenderType.VIBER, SenderType.getTypeBy("vibermsg"));
        assertEquals(SenderType.VIBER, SenderType.getTypeBy("VIBERMSG"));

        assertEquals(SenderType.SMS, SenderType.getTypeBy("SmsMsg"));
        assertEquals(SenderType.SMS, SenderType.getTypeBy("SMSMsg"));
        assertEquals(SenderType.SMS, SenderType.getTypeBy("smsmsg"));
        assertEquals(SenderType.SMS, SenderType.getTypeBy("SMSMSG"));

        assertEquals(SenderType.EMAIL, SenderType.getTypeBy("EmailMsg"));
        assertEquals(SenderType.EMAIL, SenderType.getTypeBy("EMAILMsg"));
        assertEquals(SenderType.EMAIL, SenderType.getTypeBy("emailmsg"));
        assertEquals(SenderType.EMAIL, SenderType.getTypeBy("EMAILMSG"));

        assertEquals(SenderType.FACEBOOK, SenderType.getTypeBy("FacebookMsg"));
        assertEquals(SenderType.FACEBOOK, SenderType.getTypeBy("FACEBOOKMsg"));
        assertEquals(SenderType.FACEBOOK, SenderType.getTypeBy("facebookmsg"));
        assertEquals(SenderType.FACEBOOK, SenderType.getTypeBy("FACEBOOKMSG"));

        assertEquals(SenderType.EMAIL, SenderType.getTypeBy(null));
        assertEquals(SenderType.EMAIL, SenderType.getTypeBy(""));
        assertEquals(SenderType.EMAIL, SenderType.getTypeBy("UNKNOWN"));
    }
}