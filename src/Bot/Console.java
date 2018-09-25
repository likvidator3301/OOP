package Bot;

import static java.lang.System.*;

import java.io.*;
import java.util.Scanner;
import java.util.ArrayList;



public class Console {

    private String userName;
    private ArrayList<String> listOfNames;
    private ArrayList<String> tags;
    private Scanner scanner;
    private ArrayList<IDataSource> dataSources;
    private boolean needToAddUser;

    private static final String pathToFileWithNames = "info.txt";
    private static final String articleDelimiter = "-----------------------------------------------";


    public Console() throws FileNotFoundException {
        scanner = new Scanner(in);
        out.println("Привет, представься, пожалуйста!");
        userName = scanner.nextLine();
        listOfNames = getLinesFromFile(pathToFileWithNames);
        tags = getLinesFromFile("tags.txt");
        dataSources = new ArrayList<IDataSource>();
    }

    public void startDialog() {
        if(listOfNames.contains(userName))
            out.println("О, да я тебя помню!");
        else {
            out.println("А ты новенький, что тебе интересно?");
            needToAddUser = true;
        }
        out.println("Выбери интересующий тебя тег из предложенных:");

        while(true) {
            for (var tag : tags) {
                out.println(tag);
            }
            out.println("Введи /stop если хочешь выйти");
            var needTag = scanner.nextLine();

            if (needTag.equals("/stop"))
                break;

            var data = getInfoByTag(needTag);

            if (tags.contains(needTag))
                for (var article : data) {
                    out.println(articleDelimiter);
                    out.println(article);
                }
            else
                out.println("Извини, я не знаю такого тега");
        }
        stopDialog();
        out.println("Возвращайтесь, " + userName);
    }

    public void addDataSource(IDataSource dataSource) {
        dataSources.add(dataSource);
    }

    private ArrayList<String> getInfoByTag(String tag)
    {
        var data = new ArrayList<String>();
        for (var dataSource : dataSources)
            data.add(dataSource.getInfoByTag(tag));
        return data;
    }

    private void stopDialog() {
        if (needToAddUser)
            writeToFile(pathToFileWithNames, "\n" + userName);
    }

    private void writeToFile(String pathToFile, String info) {
        try {
            var fos = new FileOutputStream(pathToFile, true);
            var buffer = info.getBytes();

            fos.write(buffer, 0, buffer.length);
        } catch (IOException e) {
            out.println("Ошибка при записи в файл");
        }
    }

    private ArrayList<String> getLinesFromFile(String pathToFile) {
        var result = new ArrayList<String>();
        try {
            var fis = new FileInputStream(pathToFile);
            var br = new BufferedReader(new InputStreamReader(fis));
            String strLine;
            while ((strLine = br.readLine()) != null)
                result.add(strLine);

        } catch (IOException e) {
            out.println("Ошибка при чтении файла");
        }
        return result;
    }
}
