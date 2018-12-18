package Tests;

import Bot.TelegramInputStream;
import org.junit.Assert;
import org.junit.Test;

import java.util.Scanner;

public class TelegramInputStreamTest {
    private TelegramInputStream in;
    private Scanner scanner;
    @org.junit.Before
    public void setUp()
    {
        in = new TelegramInputStream(0);
        scanner = new Scanner(in);
        in.addData("Data");
        in.addData("Another data");
    }

    @Test
    public void testRead()
    {
        var data = scanner.nextLine();
        Assert.assertEquals(data, "Data");
        data = scanner.nextLine();
        Assert.assertEquals(data, "Another data");
    }

    @Test()
    public void testEmptyRead()
    {
        var thread = new Thread(new Runnable() {
            @Override
            public void run() {
                var data = scanner.nextLine();
                data = scanner.nextLine();
                data = scanner.nextLine();
            }
        });
        thread.start();
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (!thread.isAlive()) {
            Assert.fail();
        } else {
            thread.interrupt();
        }
    }
}
