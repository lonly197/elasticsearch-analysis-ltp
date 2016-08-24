package demo.lonly.stp.util;

import org.apache.commons.io.IOUtils;
import org.elasticsearch.common.logging.ESLogger;
import org.elasticsearch.common.logging.ESLoggerFactory;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * HTTP Request请求工具类（用于请求Python Flask封装的Restful API）
 * Created by Lonly on 2016/7/19.
 */
public class RequestUtil {

    private static ESLogger logger = ESLoggerFactory.getLogger("ltp plugin");

    /**
     * 模拟请求WEBSERVICE方法
     *
     * @param url 请求的Webservice地址
     * @return
     */
    public static String doRequest(URL url) {
        HttpURLConnection connection = null;
        String rspMsg = "";
       /* StringBuilder logStr = new StringBuilder();*/
        try {
            // 初始化连接信息
            connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestMethod("GET");
            connection.setUseCaches(false);
            connection.setRequestProperty("Content-Type", "text/plain");
            connection.connect();

            if (connection.getResponseCode() == 200 || connection.getResponseCode() == 401) {
                InputStream in = connection.getInputStream();
                byte[] rspBuf = IOUtils.toByteArray(in);
                rspMsg = new String(rspBuf, "utf-8");
            }

            // 状态记录
           /* logStr.append(" requestUrl:" + connection.getURL());
            logStr.append(" responseCode:" + connection.getResponseCode());
            logStr.append(" responseMessage:" + connection.getResponseMessage());
            logStr.append(" content:" + rspMsg);*/

        } catch (Exception e) {
            // 错误信息记录
            logger.error("RequestUtil", e, e);

        } finally {
            // 连接释放
            if(connection != null){
                connection.disconnect();
            }
        }
        return rspMsg;
    }
}
