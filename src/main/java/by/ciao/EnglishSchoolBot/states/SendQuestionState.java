package by.ciao.EnglishSchoolBot.states;

import by.ciao.EnglishSchoolBot.bot.ServiceCallback;
import by.ciao.EnglishSchoolBot.enums.StateEnum;
import by.ciao.EnglishSchoolBot.states.statesservice.AbstractState;
import by.ciao.EnglishSchoolBot.states.statesservice.UserHandlerState;
import by.ciao.EnglishSchoolBot.user.User;
import by.ciao.EnglishSchoolBot.utils.BotResponses;
import by.ciao.EnglishSchoolBot.utils.KeyboardCreator;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class SendQuestionState extends AbstractState implements UserHandlerState {
    public SendQuestionState(final ServiceCallback serviceCallback) {
        super(serviceCallback);
    }

    @Override
    public void apply(final User user) throws Exception {
        if (testFinished(user)) { return; }
        sendQuestion(user);
        user.setState(StateEnum.CHECK_ANSWER);
    }

    private void sendQuestion(final User user) throws TelegramApiException {
        var sm = createMessage(user.getChatId().toString(), BotResponses.getQuestion(user));

        var markup = KeyboardCreator.createInlineKeyboard(BotResponses.optionsForAnswers(user));
        sm.setReplyMarkup(markup);

        if (user.getLastMessage() != null) {
            getServiceCallback().execute(editMessage(user, markup, BotResponses.getQuestion(user)));
        } else {
            getServiceCallback().execute(sm).ifPresent(user::setLastMessage);
        }
    }
}
