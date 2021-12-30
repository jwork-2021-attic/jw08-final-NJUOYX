package nju.java.logic.system.engine.utils;

import org.apache.commons.io.IOUtils;

import java.io.*;
import java.net.Socket;
import java.util.Properties;

public class WebIO {
    public static void write(Properties properties, Socket socket)throws IOException {
        OutputStream outputStream = socket.getOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(outputStream);

        CharArrayWriter writer = new CharArrayWriter();
        properties.store(writer,null);
        dataOutputStream.writeUTF(writer.toString());
        dataOutputStream.flush();
    }

    public static Properties read(Socket socket)throws IOException{
        InputStream inputStream = socket.getInputStream();
        DataInputStream dataInputStream = new DataInputStream(inputStream);
        String string = dataInputStream.readUTF();
        Properties properties = new Properties();
        properties.load(IOUtils.toInputStream(string));
        return properties;
    }
}
