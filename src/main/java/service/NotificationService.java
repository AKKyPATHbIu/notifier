package service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import scheduler.Notifier;

@Service
@EnableScheduling
@PropertySource("classpath:notifier.properties")
public class NotificationService {

    @Autowired
    Notifier notifier;

    @Scheduled(fixedDelayString = "${fixedDelay.in.milliseconds}")
    public void execute() {
        notifier.execute();
    }
}
