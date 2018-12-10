package Bot;

import DataSources.ApiVK;
import Tests.StringInputStream;
import Tests.StringPrintStream;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.FileNotFoundException;
import java.io.IOException;

public class Telegram extends TelegramLongPollingBot {

    @Override
    public String getBotUsername() {
        return "NewITNewsBot";
    }

    @Override
    public void onUpdateReceived(Update e) {
        var msg = e.getMessage(); // Это нам понадобится
        var chatId = msg.getChatId();
        var txt = msg.getText();
        if (!Main.telegramConsoleDictionary.containsKey(chatId.toString())) {
            var myThready = new Thread(new Runnable()
            {
                public void run()
                {
                    try {
                    var console = new Console(new TelegramPrintStream(chatId), new TelegramInputStream(chatId), chatId);
                    var vkApi = new ApiVK();
                    console.addDataSource(vkApi);
                    Main.telegramConsoleDictionary.put(chatId.toString(), console);

                    console.startDialog();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            });
            myThready.start();
        }
        else {
            var in = Main.telegramConsoleDictionary.get(chatId.toString()).in;
            ((TelegramInputStream)in).addData(txt);
        }
    }

    @Override
    public String getBotToken() {
        return "728945111:AAGk9iNDHmiYKpD7gupKPkqtHsJuTs4beWo";
    }

    private void sendMsg(Message msg, String text) {
        var s = new SendMessage();
        s.setChatId(msg.getChatId());
        s.setText(text);
        try {
            execute(s);
        } catch (TelegramApiException e){
            e.printStackTrace();
        }
    }
}
