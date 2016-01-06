package web.Utils;

import java.io.*;
import java.math.BigDecimal;
import java.net.*;
import java.nio.charset.Charset;
import java.util.Map;

public class MoneyUtils {

    public BigDecimal Minus(BigDecimal num1, BigDecimal num2) {
        // num1-num2
        return num1.subtract(num2);
    }

    public static void main(String[] args) {
        getCurrency("EUR", "CNY");
    }

    public static BigDecimal getCurrency(String from, String to) {
        String result = "";
        String strUrl = "http://query.yahooapis.com/v1/public/yql?q=select%20rate%2Cname%20from%20csv%20where%20url%3D%27http%3A%2F%2Fdownload.finance.yahoo.com%2Fd%2Fquotes%3Fs%3D" + from + to + "%253DX%26f%3Dl1n%27%20and%20columns%3D%27rate%2Cname%27&format=xml";
        StringBuffer sb = new StringBuffer();

        try {
            URL url = new URL(strUrl);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setInstanceFollowRedirects(false);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setRequestProperty("charset", "utf-8");
            connection.setUseCaches(false);

            DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
            wr.flush();
            wr.close();

            InputStream stream = connection.getInputStream();
            InputStreamReader isReader = new InputStreamReader(stream);

            BufferedReader br = new BufferedReader(isReader);

            String line;
            while ((line = br.readLine()) != null) {
                if (line.contains("<rate>")) {
                    line = line.substring(line.indexOf("<rate>") + 6);
                    result = line.substring(0, line.indexOf("</rate>"));
                    break;
                }
            }
            br.close();
            connection.disconnect();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new BigDecimal(result);
    }

}
