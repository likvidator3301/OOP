package Tests;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class StringInputStream extends InputStream {

    private List<String> data;
    private byte[] bData;
    private int pointer = 0;
    private int dataPointer = 0;

    public void setData(List<String> data) {
        this.data = data;
        bData = this.data.get(dataPointer).getBytes();
    }

    public int read() {
    if (pointer >= bData.length) {
            pointer = 0;
            if (++dataPointer < data.size())
                bData = data.get(dataPointer).getBytes();
            return '\n';
        }
        return bData[pointer++];
    }
}
