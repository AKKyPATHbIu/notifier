package config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import senderparams.BaseSenderParams;
import senderparams.EmailParams;
import senderparams.FacebookParams;
import senderparams.impl.BaseSenderParamsImpl;
import senderparams.impl.EmailParamsImpl;
import senderparams.impl.FacebookParamsImpl;

@Configuration
public class ParamsConfig {

    @Bean(name = "smsParams")
    public BaseSenderParams smsParams() {
        return new BaseSenderParamsImpl("sms");
    }

    @Bean(name = "viberParams")
    public BaseSenderParams viberParams() {
        return new BaseSenderParamsImpl("viber");
    }

    @Bean
    public EmailParams emailParams() {
        return new EmailParamsImpl();
    }

    @Bean
    public FacebookParams facebookParams() {
        return new FacebookParamsImpl();
    }
}
