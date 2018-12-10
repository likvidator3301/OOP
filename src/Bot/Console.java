package Bot;

import DataSources.IDataSource;
import DataStructure.Pair;
import Utils.FileWorker;

import java.io.*;
import java.util.*;



public class Console {

    public PrintStream out;
    public InputStream in;
    public long chatId;

    private String userName;
    private ArrayList<String> tags;
    private HashMap<String, String> dictOfNameAndTags;
    private Scanner scanner;
    private List<IDataSource> dataSources;
    private boolean needToAddUser;

    private static final String pathToFileWithNames = "info.txt";
    private static final String articleDelimiter = "-----------------------------------------------";


    public Console(PrintStream out, InputStream in, long chatId) throws FileNotFoundException {
        this.out = out;
        this.in = in;
        this.chatId = chatId;
        scanner = new Scanner(in);
        var data = FileWorker.getLinesFromFile(pathToFileWithNames);
        dictOfNameAndTags = new HashMap<String, String>();
        for (var item : data) {
            var nameAndTags = item.split("-");
            if (nameAndTags.length == 1)
                dictOfNameAndTags.put(nameAndTags[0], "");
            else
                dictOfNameAndTags.put(nameAndTags[0], nameAndTags[1]);
        }
        tags = FileWorker.getLinesFromFile("tags.txt");
        dataSources = new ArrayList<IDataSource>();
    }

    public void startDialog() throws IOException {
        out.println("Привет, представься, пожалуйста!");
        userName = scanner.nextLine();
        if (dictOfNameAndTags.containsKey(userName)) {
            out.println("О, да я тебя помню!");
            if (dictOfNameAndTags.get(userName).equals(""))
                out.println("Ты ничего не искал(");
            else
                out.println("Вот что тебе было интересно: " + dictOfNameAndTags.get(userName));
            out.println();
        } else {
            dictOfNameAndTags.put(userName, "");
            out.println("А ты новенький, что тебе интересно?");
            needToAddUser = true;
        }
        out.println("Выбери интересующий тебя тег из предложенных:");

        while (true) {
            for (var tag : tags) {
                out.println(tag);
            }
            out.println();
            out.println("Введи /stop если хочешь выйти");
            var needTag = scanner.nextLine();

            if (needTag.equals("/stop"))
                break;

            if (tags.contains(needTag)) {
                if (!dictOfNameAndTags.get(userName).equals("") && !dictOfNameAndTags.get(userName).contains(needTag))
                    dictOfNameAndTags.put(userName, dictOfNameAndTags.get(userName) + " " + needTag);
                else if (dictOfNameAndTags.get(userName).equals(""))
                    dictOfNameAndTags.put(userName, needTag);
                var data = getInfoByTag(needTag);
                for (var article : data) {
                    out.println(articleDelimiter);
                    out.println(article.first);
                    out.println(article.second);
                }
            } else
                out.println("Извини, я не знаю такого тега");
            out.println();
        }
        stopDialog();
        out.println("Возвращайтесь, " + userName);
    }

    public void addDataSource(IDataSource dataSource) {
        dataSources.add(dataSource);
    }

    private ArrayList<Pair<String, String>> getInfoByTag(String tag) throws IOException {
        var data = new ArrayList<Pair<String, String>>();
        for (var dataSource : dataSources) {
            for (var pair : dataSource.getInfoByTag(tag)) {
                data.add(pair);
            }
        }
        return data;
    }

    private void stopDialog() {
        var result = new StringBuilder();
        for (var key : dictOfNameAndTags.keySet()) {
            result.append(key);
            if (!dictOfNameAndTags.get(key).equals("")) {
                result.append("-");
                result.append(dictOfNameAndTags.get(key));
            }
            result.append('\n');
        }
        FileWorker.writeToFile(pathToFileWithNames, result.toString());
    }

}
