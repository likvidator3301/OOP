package Bot;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class ApiVK implements IDataSource {

    public void Autorize() throws IOException {
        String url = "https://api.vk.com/method/users.get?user_ids=210700286&fields=bdate&access_token=2304416f2304416f2304416f5823530eb8223042304416f79101fab155e4b6420c44a98&v=5.85";

        var obj = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) obj.openConnection();

        connection.setRequestMethod("GET");

        var in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        var inputLine = "";
        var response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        writeToFile("response.txt", response.toString());
    }

    public String getInfoByTag(String tag) {
        return "Здесь будет интересная информация по тегу " + tag;
    }

    private void writeToFile(String pathToFile, String info) {
        try {
            var fos = new FileOutputStream(pathToFile);
            var buffer = info.getBytes();

            fos.write(buffer, 0, buffer.length);
        } catch (IOException e) {
            System.out.println("Ошибка при записи в файл");
        }
    }
}
