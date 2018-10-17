package config;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;
import senderparams.BaseSenderParams;
import senderparams.EmailParams;
import senderparams.FacebookParams;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@TestPropertySource("classpath:notifier.properties")
@WebAppConfiguration
@ContextConfiguration(loader = AnnotationConfigWebContextLoader.class,
        classes = { RootConfig.class })
@ActiveProfiles("DEV")
public class ParamsConfigTest {

    @Autowired
    @Qualifier(value = "smsParams")
    BaseSenderParams smsParams;

    @Autowired
    @Qualifier(value = "viberParams")
    BaseSenderParams viberParams;

    @Autowired
    EmailParams emailParams;

    @Autowired
    FacebookParams facebookParams;

    @Test
    public void smsParams() {
        assertNotNull(smsParams);
        assertEquals("skydigitallab_sms", smsParams.getLogin());
        assertEquals("07061979_sms", smsParams.getPassword());
        assertEquals(false, smsParams.isEnabled());
    }

    @Test
    public void viberParams() {
        assertNotNull(viberParams);
        assertEquals("skydigitallab_viber", viberParams.getLogin());
        assertEquals("07061979_viber", viberParams.getPassword());
        assertEquals(true, viberParams.isEnabled());
    }

    @Test
    public void emailParams() {
        assertNotNull(emailParams);
        assertEquals("a.tovstyuk@gmail.com", emailParams.getLogin());
        assertEquals("inferno32", emailParams.getPassword());
        assertEquals("smtp.gmail.com" ,emailParams.getSmtpServer());
        assertEquals(new Integer(465), emailParams.getSmptPort());
        assertEquals("a.tovstyuk@gmail.com", emailParams.getFrom());
        assertEquals(true, emailParams.isEnabled());
    }

    @Test
    public void facebookParams() {
        assertNotNull(facebookParams);
        assertEquals("app_secret", facebookParams.getAppSecret());
        assertEquals("access_token", facebookParams.getPageAccessToken());
        assertEquals("verify_token", facebookParams.getVerifyToken());
        assertEquals(true, facebookParams.isEnabled());
    }
}