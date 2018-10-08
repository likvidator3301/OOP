package Bot;

import java.io.InputStream;

public class StringInputStream extends InputStream {

    private String data = "Анатолий";
    private byte[] bData = data.getBytes();
    private int pointer = 0;

    public int read() {
        /*if (pointer >= data.length()) {
            pointer = 0;
            data = "/stop";
            bData = data.getBytes();
            return -1;
        }*/
        return bData[pointer++];
    }

}
