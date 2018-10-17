package config;

import com.github.messenger4j.Messenger;
import msgsender.*;
import msgsender.factory.MsgSenderFactory;
import msgsender.factory.MsgSenderFactoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import scheduler.Notifier;
import scheduler.NotifierDevImpl;
import scheduler.NotifierProdImpl;
import senderparams.FacebookParams;

@Configuration
public class SendConfig {

    @Bean
    public Messenger messenger(@Autowired FacebookParams params) {
        return Messenger.create(params.getPageAccessToken(), params.getAppSecret(), params.getVerifyToken());
    }

    @Bean(name = "emailMsgSender")
    public MsgSender emailMsgSender() {
        return new EmailMsgSender();
    }

    @Bean(name = "smsMsgSender")
    public MsgSender smsMsgSender() {
        return new SmsMsgSender();
    }

    @Bean(name = "viberMsgSender")
    public MsgSender viberMsgSender() {
        return new ViberMsgSender();
    }

    @Bean(name = "facebookMsgSender")
    public MsgSender facebookMsgSender() {
        return new FacebookMsgSender();
    }

    @Bean
    public MsgSenderFactory msgSenderFactory() {
        return new MsgSenderFactoryImpl();
    }

    @Bean
    @Profile("PROD")
    Notifier notifierProd() {
        return new NotifierProdImpl();
    }

    @Bean
    @Profile("DEV")
    Notifier notifierDev() {
        return new NotifierDevImpl();
    }
}
