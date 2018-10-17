package msgsender;

import enums.SenderType;
import senderparams.BaseSenderParams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Logger;

/** Базовий клас відправки повідомлення viber та sms. */
public abstract class ViberSmsMsgSender extends BaseMsgSender {

    private static Logger logger = Logger.getLogger(ViberSmsMsgSender.class.getName());

    /** Параметри відправника повідомлення. */
    private BaseSenderParams params;

    /**
     * Встановити параметри відправника повідомлення
     * @param params параметри відправника повідомлення
     */
    public void setParams(BaseSenderParams params) {
        this.params = params;
    }

    /**
     * Конструктор
     * @param type тип повідомлення
     */
    public ViberSmsMsgSender(SenderType type) {
        super(type);
    }

    @Override
    public void send(MsgDetail msgDetail) {
        String url = buildUrl(msgDetail);
        try {
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("GET");

            int responseCode = con.getResponseCode();
            logger.info("Response Code : " + responseCode);

            try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                String responseStr = response.toString();
                logger.info(responseStr);
                if (responseStr != null && responseStr.contains("ERROR")) {
                    throw new RuntimeException(responseStr);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * Отримати url для відправки повідомлення
     * @param msgDetail параметри повідомлення
     * @return url для відправки повідомлення
     */
    protected String buildUrl(MsgDetail msgDetail) {
        StringBuilder url = new StringBuilder();
        url.append("https://smsc.ua/sys/send.php?login=").append(params.getLogin());
        url.append("&psw=").append(params.getPassword()).append("&phones=").append(msgDetail.getAddress());
        url.append("&charset=utf-8").append("&mes=").append(msgDetail.getBody().replaceAll("\\s+","%20"));
        return url.toString();
    }

    @Override
    public boolean isEnabled() {
        return params.isEnabled();
    }
}
