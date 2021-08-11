package sun.net.www.protocol.system;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;
import java.util.HashMap;
import java.util.Map;

public class Handler extends URLStreamHandler {
    @Override
    protected URLConnection openConnection(URL url) throws IOException {
        return new URLConnection(url) {

            String resourcePath = url.getPath();

            Map map = new HashMap();

            @Override
            public void connect() throws IOException {

                //TODO 貌似没什么作用，没有走到
//                while (resourcePath.startsWith("/")) {
//                    resourcePath = resourcePath.substring(1);
//                }
//                if (!resourcePath.equals("systemproperties")){
//                    throw new IOException("can not found system properties");
//                }

            }

            @Override
            public InputStream getInputStream() throws IOException {
                map.putAll(System.getProperties());
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(baos);


                oos.writeObject(map);

                oos.flush();
                oos.close();

                InputStream is = new ByteArrayInputStream(baos.toByteArray());

                return is;
            }

//            public byte[] mapToBytes(Map map) throws IOException {
//                byte[] bytes = null;
//                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//                ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
//                objectOutputStream.writeObject(map);
//                bytes = outputStream.toByteArray();
//
//
//                ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
//
//
//                return bytes;
//            }
        };
    }
}
