package senderparams.impl;

import senderparams.FacebookParams;

/** Реалізація завантажування параметрів сповіщення facebook. */
public class FacebookParamsImpl extends BaseSenderParamsImpl implements FacebookParams {

    /** Конструктор за замовчанням. */
    public FacebookParamsImpl() {
        super("facebook");
    }

    @Override
    public String getAppSecret() {
        return env.getProperty(buildParam("appSecret"));
    }

    @Override
    public String getPageAccessToken() {
        return env.getProperty(buildParam("pageAccessToken"));
    }

    @Override
    public String getVerifyToken() {
        return env.getProperty(buildParam("verifyToken"));
    }
}
