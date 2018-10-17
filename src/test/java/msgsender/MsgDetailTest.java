package msgsender;

import org.json.JSONObject;
import org.junit.Test;

import static org.junit.Assert.*;

public class MsgDetailTest {

    private String ADDRESS = "addr";
    private String SUBJECT = "subj";
    private String BODY = "body";

    private String jsonStr = String.format("{\"address\":\"%s\",\"subject\":\"%s\", \"body\":\"%s\"}", ADDRESS, SUBJECT, BODY);
    private final JSONObject jsonObj = new JSONObject(jsonStr);

    @Test
    public void test() {
        MsgDetail param = new MsgDetail(jsonObj);
        assertEquals(ADDRESS, param.getAddress());
        assertEquals(SUBJECT, param.getSubject());
        assertEquals(BODY, param.getBody());
    }
}