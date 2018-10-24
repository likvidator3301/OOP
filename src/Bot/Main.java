package Bot;

import DataSources.ApiVK;

import java.io.IOException;

public class Main
{
    public static void main(String[] args) throws IOException {
        var console = new Console(System.out, System.in);
        console.addDataSource(new ApiVK());
        console.startDialog();
    }
}
