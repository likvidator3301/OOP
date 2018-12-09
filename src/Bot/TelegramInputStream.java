package Bot;

import java.io.InputStream;
import java.util.ArrayList;

public class TelegramInputStream extends InputStream {
    private ArrayList<String> data = new ArrayList<String>();
    private byte[] bData;
    private int pointer = 0;
    private int dataPointer = 0;

    public void addData(String data)
    {
        this.data.add(data);
        bData = this.data.get(dataPointer).getBytes();
    }

    public int read() {
        if (pointer >= bData.length)
        {
            pointer = 0;
            dataPointer++;
            return '\n';
        }
        return bData[pointer++];
    }
}
