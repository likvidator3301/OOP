package Bot;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


public class ApiVK implements IDataSource {

    public ArrayList<Pair<String, String>> getInfoByTag(String tag) throws IOException {
        var data = getResponseByTag(tag);
        var parsed_data = parseJson(tag, data);
        return parsed_data;
    }

    private String getResponseByTag(String tag) throws IOException {
        var url = "https://api.vk.com/method/wall.search?query=" + tag + "&owner_id=-30666517&owners_only=1&domain=tproger&count=50&access_token=2304416f2304416f2304416f5823530eb8223042304416f79101fab155e4b6420c44a98&v=5.85";
        var obj = new URL(url);
        var connection = (HttpsURLConnection) obj.openConnection();
        connection.setDoOutput(true);

        var in = new BufferedReader(
                new InputStreamReader(connection.getInputStream()));
        String inputLine;
        var response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        return response.toString();
    }

    private ArrayList<Pair<String, String>> parseJson (String tag, String data) {
        var result = new ArrayList<Pair<String, String>>();
        var finish = 0;
        for (var i = 0; i < 50; i++) {
            var begin_text = data.indexOf("\"text\":\"", finish);
            var finish_text = data.indexOf("\"", begin_text + 8);
            var text = data.substring(begin_text + 8, finish_text);
            finish = finish_text;
            if (!text.contains("#" + tag))
                continue;
            var begin_link = data.indexOf("\"url\":\"", finish);
            var finihs_link = data.indexOf("\"", begin_link + 7);
            var link = data.substring(begin_link + 7, finihs_link);
            finish = finihs_link;
            result.add(new Pair<String, String>(text, link));
            if (result.size() == 3)
                return result;
        }
        return result;
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
