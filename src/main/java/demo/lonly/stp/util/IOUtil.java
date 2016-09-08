package demo.lonly.stp.util;

import java.io.*;
import java.nio.charset.Charset;

/**
 * IO处理类
 * Created by Lonly on 2016/9/8.
 */
public class IOUtil {
    public static final String UTF8 = "utf-8";
    public static final String GBK = "gbk";
    public static final String TABLE = "\t";
    public static final String LINE = "\n";
    public static final byte[] TABBYTE = "\t".getBytes(Charset.forName("UTF-8"));
    public static final byte[] LINEBYTE = "\n".getBytes(Charset.forName("UTF-8"));

    public IOUtil() {
    }

    public static InputStream getInputStream(String path) {
        try {
            return new FileInputStream(path);
        } catch (FileNotFoundException var2) {
            var2.printStackTrace();
            return null;
        }
    }

    public static BufferedReader getReader(String path, String charEncoding)
        throws FileNotFoundException,
        UnsupportedEncodingException {
        return getReader(new File(path), charEncoding);
    }

    public static BufferedReader getReader(File file, String charEncoding)
        throws FileNotFoundException, UnsupportedEncodingException {
        FileInputStream is = new FileInputStream(file);
        return new BufferedReader(new InputStreamReader(is, charEncoding));
    }

    public static RandomAccessFile getRandomAccessFile(String path, String charEncoding)
        throws FileNotFoundException {
        InputStream is = getInputStream(path);
        return is != null ? new RandomAccessFile(new File(path), "r") : null;
    }

    public static void writer(String path, String charEncoding, String content) {
        FileOutputStream fos = null;

        try {
            fos = new FileOutputStream(new File(path));
            fos.write(content.getBytes(charEncoding));
            fos.flush();
        } catch (FileNotFoundException var9) {
            var9.printStackTrace();
        } catch (IOException var10) {
            var10.printStackTrace();
        } finally {
            close(fos);
        }

    }

    public static BufferedReader getReader(InputStream inputStream, String charEncoding)
        throws UnsupportedEncodingException {
        return new BufferedReader(new InputStreamReader(inputStream, charEncoding));
    }

    public static String getContent(String path, String charEncoding) {
        return getContent(new File(path), charEncoding);
    }

    public static String getContent(InputStream is, String charEncoding) {
        BufferedReader reader = null;

        try {
            reader = getReader(is, charEncoding);
            String e = getContent(reader);
            return e;
        } catch (Exception var13) {
            var13.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException var12) {
                    var12.printStackTrace();
                }
            }

        }

        return "";
    }

    public static String getContent(File file, String charEncoding) {
        FileInputStream is = null;

        try {
            is = new FileInputStream(file);
            String e = getContent(is, charEncoding);
            return e;
        } catch (FileNotFoundException var7) {
            var7.printStackTrace();
        } finally {
            close(is);
        }

        return "";
    }

    public static String getContent(BufferedReader reader) throws IOException {
        StringBuilder sb = new StringBuilder();

        try {
            String temp = null;

            while ((temp = reader.readLine()) != null) {
                sb.append(temp);
                sb.append("\n");
            }
        } finally {
            close(reader);
        }

        return sb.toString();
    }

    public static void writerObj(String path, Serializable hm) throws IOException {
        ObjectOutputStream objectOutputStream = null;

        try {
            objectOutputStream = new ObjectOutputStream(new FileOutputStream(path));
            objectOutputStream.writeObject(hm);
        } finally {
            if (objectOutputStream != null) {
                objectOutputStream.close();
            }

        }

    }

    public static void close(Reader reader) {
        try {
            if (reader != null) {
                reader.close();
            }
        } catch (IOException var2) {
            var2.printStackTrace();
        }

    }

    public static void close(InputStream is) {
        try {
            if (is != null) {
                is.close();
            }
        } catch (IOException var2) {
            var2.printStackTrace();
        }

    }

    public static void close(OutputStream os) {
        try {
            if (os != null) {
                os.flush();
                os.close();
            }
        } catch (IOException var2) {
            var2.printStackTrace();
        }

    }
}

