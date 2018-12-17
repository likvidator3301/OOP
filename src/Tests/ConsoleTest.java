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

    //перед каждый запуском убедитесь что у Anatolii нет тега
    @org.junit.Before
    public void setUp() throws Exception {
        var data = new ArrayList<String>();
        data.add("python");
        data.add("wrong tag");
        data.add("/stop");
        out = new StringPrintStream();
        in = new StringInputStream();
        in.setData(data);
        var console = new Console(out, in, 0);
        console.addDataSource(new SimpleDataSource());
        console.startDialog();
        output = out.output;
    }

    @Test
    public void TestHelloUser() {
        Assert.assertEquals(output.get(0), "Привет, Anatolii!");
        Assert.assertEquals(output.get(1), "Ты ничего не искал(");
    }

    @Test
    public void TestPrintTags() {
        Assert.assertEquals(output.get(2), "Выбери интересующий тебя тег из предложенных:");
        Assert.assertEquals(output.get(3), "javascript\n" +
                "python\n" +
                "programming\n" +
                "вакансия\n" +
                "sorting\n" +
                "gamedev\n" +
                "java\n" +
                "design\n" +
                "cryptography\n");
        Assert.assertEquals(output.get(4), "Введи /stop если хочешь выйти");
    }

    @Test
    public void TestInfoByRightTag() {
        Assert.assertEquals(output.get(5), "Здесь будет информация по тегу python\n\nА здесь ссылка на статью, если такая есть");
    }

    @Test
    public void TestWrongTag(){
        Assert.assertEquals(output.get(8), "Извини, я не знаю такого тега");
    }

    @Test
    public void TestEndDialoge() {
        Assert.assertEquals(output.get(11), "Возвращайтесь, Anatolii");
    }
}
