package enums;

import org.junit.Test;

import static org.junit.Assert.*;

public class MsgStatusTest {

    @Test
    public void test() {
        assertEquals("NEW", MsgStatus.NEW.getDbValue());
        assertEquals("INPROGRESS", MsgStatus.INPROGRESS.getDbValue());
        assertEquals("OK", MsgStatus.OK.getDbValue());
        assertEquals("ERR", MsgStatus.ERROR.getDbValue());
        assertEquals("PARTIAL", MsgStatus.PARTIAL.getDbValue());
    }
}