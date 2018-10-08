package Bot;

import Bot.Console;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class Main
{
    public static void main(String[] args) throws IOException {
        var vk = new ApiVK();
        vk.Autorize();
        //var console = new Console(System.out, System.in);
        //console.addDataSource(new ApiVK());
        //console.startDialog();
    }
}
