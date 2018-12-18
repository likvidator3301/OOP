package Tests;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public class StringPrintStream extends PrintStream {

    public List<String> output;

    public StringPrintStream(){
        super(System.out);
        output = new ArrayList<String>();
    }

    public void println(String x) {
        output.add(x);
    }
}
