package Bot;

import Bot.Console;

import java.io.FileNotFoundException;

public class Main
{
    public static void main(String[] args) throws FileNotFoundException {
        var console = new Console();
        console.startDialog();
    }
}
