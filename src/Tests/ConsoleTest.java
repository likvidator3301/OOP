package Tests;

import Bot.Console;
import DataSources.SimpleDataSource;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class ConsoleTest {
    private StringPrintStream out;
    private StringInputStream in;
    private List<String> output;

    @org.junit.Before
    public void setUp() throws Exception {
        var data = new ArrayList<String>();
        data.add("Anatolii");
        data.add("blablabla");
        data.add("python");
        data.add("/stop");
        out = new StringPrintStream();
        in = new StringInputStream();
        in.setData(data);
        var console = new Console(out, in);
        console.addDataSource(new SimpleDataSource());
        console.startDialog();
        output = out.output;
    }

    @Test
    public void TestHelloUser() {
        Assert.assertEquals(output.get(0), "Привет, представься, пожалуйста!");
        Assert.assertEquals(output.get(1), "О, да я тебя помню!");
        Assert.assertEquals(output.get(2), "Ты ничего не искал(");
    }

    @Test
    public void TestPrintTags() {
        Assert.assertEquals(output.get(3), "Выбери интересующий тебя тег из предложенных:");
        Assert.assertEquals(output.get(4), "javascript");
        Assert.assertEquals(output.get(12), "cryptography");
        Assert.assertEquals(output.get(13), "Введи /stop если хочешь выйти");
    }

    @Test
    public void TestInfoByRightTag() {
        Assert.assertEquals(output.get(15), "Здесь будет информация по тегу python");
        Assert.assertEquals(output.get(16), "А здесь ссылка на статью, если такая есть");
    }

    @Test
    public void TestEndDialoge() {
        Assert.assertEquals(output.get(27), "Возвращайтесь, Anatolii");
    }
}
