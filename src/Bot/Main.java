package Bot;

import Bot.Console;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class Main
{
    public static void main(String[] args) throws FileNotFoundException {
        var console = new Console(System.out, new StringInputStream());
        console.addDataSource(new ApiVK());
        console.startDialog();
    }
}
