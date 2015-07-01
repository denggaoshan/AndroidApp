package Test;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by admin on 2015/6/30.
 */
public class FileDeal {

    public static String uploadFile(String fileUrl, String serverUrl)
    {
        String end = "\r\n";
        String twoHyphens = "--";
        String boundary = "******";
        try
        {
            URL url = new URL(serverUrl);
            HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
            // 设置每次传输的流大小，可以有效防止手机因为内存不足崩溃
            // 此方法用于在预先不知道内容长度时启用没有进行内部缓冲的 HTTP 请求正文的流。
            httpURLConnection.setChunkedStreamingMode(128 * 1024);// 128K
            // 允许输入输出流
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setUseCaches(false);
            // 使用POST方法
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Connection", "Keep-Alive");
            httpURLConnection.setRequestProperty("Charset", "UTF-8");
            httpURLConnection.setRequestProperty("Content-Type",
                    "multipart/form-data;boundary=" + boundary);

            DataOutputStream dos = new DataOutputStream(
                    httpURLConnection.getOutputStream());
            dos.writeBytes(twoHyphens + boundary + end);
            dos.writeBytes("Content-Disposition: form-data; name=\"uploadedfile\"; filename=\""
                    + fileUrl.substring(fileUrl.lastIndexOf("/") + 1)
                    + "\""
                    + end);
            dos.writeBytes(end);

            FileInputStream fis = new FileInputStream(fileUrl);
            byte[] buffer = new byte[8192]; // 8k
            int count = 0;
            // 读取文件
            while ((count = fis.read(buffer)) != -1)
            {
                dos.write(buffer, 0, count);
            }
            fis.close();

            dos.writeBytes(end);
            dos.writeBytes(twoHyphens + boundary + twoHyphens + end);
            dos.flush();

            InputStream is = httpURLConnection.getInputStream();
            InputStreamReader isr = new InputStreamReader(is, "utf-8");
            BufferedReader br = new BufferedReader(isr);
            String result = br.readLine();

            dos.close();
            is.close();

            return result;
        }
        catch (Exception w)
        {

        }

        return null;
    }

    public static Bitmap downBitmap(String imgUrl)
    {
        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(imgUrl);
        Bitmap result = null;
        try
        {
            HttpResponse resp = httpClient.execute(httpGet);
            if (HttpStatus.SC_OK == resp.getStatusLine().getStatusCode())
            {
                HttpEntity entity = resp.getEntity();
                InputStream in = entity.getContent();
                result = BitmapFactory.decodeStream(in);
            }
        }
        catch(Exception e)
        {

        }
        finally
        {
            httpClient.getConnectionManager().shutdown();
        }

        return result;
    }

}
