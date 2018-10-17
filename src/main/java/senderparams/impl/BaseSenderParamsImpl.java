package senderparams.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import senderparams.BaseSenderParams;

@PropertySource("classpath:notifier.properties")
public class BaseSenderParamsImpl implements BaseSenderParams {

    @Autowired
    protected Environment env;

    /** Префікс імен параметрів. */
    private final String prefix;

    /**
     * Конструктор
     * @param prefix префікс імен параметрів
     */
    public BaseSenderParamsImpl(String prefix) {
        this.prefix = prefix;
    }

    @Override
    public boolean isEnabled() {
        return env.getProperty(buildParam("enabled"), "true").equalsIgnoreCase("true");
    }

    @Override
    public String getLogin() {
        return env.getProperty(buildParam("login"));
    }

    @Override
    public String getPassword() {
        return env.getProperty(buildParam("password"));
    }

    /**
     * Отримати ім'я параметра з префіксом
     * @param paramName ім'я параметра
     * @return ім'я параметра з префіксом
     */
    protected String buildParam(String paramName) {
        StringBuilder result = new StringBuilder();
        result.append(prefix).append(".").append(paramName);
        return result.toString();
    }
}
