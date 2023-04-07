package by.ciao.EnglishSchoolBot.states.statesservice;

import by.ciao.EnglishSchoolBot.bot.ServiceCallback;
import by.ciao.EnglishSchoolBot.user.User;
import by.ciao.EnglishSchoolBot.utils.BotResponses;
import by.ciao.EnglishSchoolBot.utils.ExceptionLogger;
import by.ciao.EnglishSchoolBot.utils.KeyboardCreator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.logging.Level;

@AllArgsConstructor
@Getter
public abstract class AbstractState {
    private final ServiceCallback serviceCallback;

    protected void sendText(final Long id, final String textMsg) throws TelegramApiException {
        serviceCallback.execute(createMessage(id, textMsg));
    }

    protected void sendStartButton(final User user) throws TelegramApiException {
        var sm = createMessage(user.getChatId(), BotResponses.startTest());
        sm.setReplyMarkup(KeyboardCreator.createReplyKeyboard(BotResponses.startTestButton(), false));

        serviceCallback.execute(sm);
    }

    protected SendMessage createMessage(Long id, String textMsg) {
        var sm = SendMessage.builder()
                .chatId(id.toString())
                .text(textMsg).build();
        sm.setParseMode(ParseMode.HTML);
        return sm;
    }

    // Made to humanise bot's responses, so it is not sending lots of messages in one second.
    protected void setDelay(int millis) {
        try {
            Thread.sleep(millis); // Delay for 0.5 seconds (500 milliseconds)
        } catch (InterruptedException e) {
            ExceptionLogger.logException(Level.SEVERE, e.toString(), e);
        }
    }
}
