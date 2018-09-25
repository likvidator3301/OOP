package Bot;

import java.io.*;
import java.util.Dictionary;
import java.util.Scanner;
import java.util.ArrayList;

public class Console {

    String userName;
    ArrayList<String> listOfNames;
    ArrayList<String> tags;
    Scanner scanner;


    public Console() throws FileNotFoundException {
        scanner = new Scanner(System.in);
        System.out.println("Привет, представься, пожалуйста!");
        userName = scanner.nextLine();
        listOfNames = getLinesFromFile("info.txt");
        tags = getLinesFromFile("tags.txt");
        vk = new ApiVK();
    }

    public void startDialog() {
        if(listOfNames.contains(userName))
            System.out.println("О, да я тебя помню!");
        else
            System.out.println("А ты новенький, что тебе интересно?");

        System.out.println("Выбери интересующий тебя тег из предложенных:");
        while(true) {
            for (var tag : tags) {
                System.out.println(tag);
            }
            System.out.println("Введи /stop если хочешь выйти");
            var needTag = scanner.nextLine();

            if (needTag == "/stop")
                break;

            if (tags.contains(needTag))
                System.out.println(vk.getInfoByTag(needTag));
            else
                System.out.println("Извини, я не знаю такого тега");
        }
        System.out.println("Возвращайтесь, " + userName);
    }

    private ArrayList<String> getLinesFromFile(String pathToFile) {
        var result = new ArrayList<String>();
        try {
            FileInputStream fstream = new FileInputStream(pathToFile);
            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
            String strLine;
            while ((strLine = br.readLine()) != null)
                result.add(strLine);

        } catch (IOException e) {
            System.out.println("Ошибка при чтении файла");
        }
        return result;
    }
}
