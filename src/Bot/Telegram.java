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

    private static Console console;
    private static TelegramInputStream input;
    private static TelegramPrintStream print;

    public static void main(String[] args) throws FileNotFoundException, IOException {
        ApiContextInitializer.init();
        TelegramBotsApi botsApi = new TelegramBotsApi();

        try {
            botsApi.registerBot(new Telegram());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getBotUsername() {
        return "NewITNewsBot";
    }

    @Override
    public void onUpdateReceived(Update e) {
        Message msg = e.getMessage(); // Это нам понадобится
        String txt = msg.getText();
    }

    @Override
    public String getBotToken() {
        return "728945111:AAGk9iNDHmiYKpD7gupKPkqtHsJuTs4beWo";
    }

    private void sendMsg(Message msg, String text) {
        SendMessage s = new SendMessage();
        s.setChatId(msg.getChatId());
        s.setText(text);
        try {
            execute(s);
        } catch (TelegramApiException e){
            e.printStackTrace();
        }
    }
}
