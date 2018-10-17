package senderparams.impl;

import senderparams.EmailParams;

/** Реалізація завантажування параметрів сповіщення email. */
public class EmailParamsImpl extends BaseSenderParamsImpl implements EmailParams {

    /** Конструктор за замовчанням. */
    public EmailParamsImpl() {
        super("email");
    }

    @Override
    public String getSmtpServer() {
        return env.getProperty(buildParam("smtpServer"));
    }

    @Override
    public Integer getSmptPort() {
        return new Integer(env.getProperty(buildParam("smtpPort")));
    }

    @Override
    public String getFrom() {
        return env.getProperty(buildParam("from"));
    }
}
