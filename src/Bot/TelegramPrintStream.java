package Bot;

import java.io.PrintStream;
import java.util.ArrayList;

public class TelegramPrintStream extends PrintStream {
    public ArrayList<String> output;

    public TelegramPrintStream(){
        super(System.out);
        output = new ArrayList<String>();
    }

    public void println(String x) {
        output.add(x);
    }
}
