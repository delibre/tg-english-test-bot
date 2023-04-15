package by.ciao.EnglishSchoolBot.states;

import by.ciao.EnglishSchoolBot.bot.ServiceCallback;
import by.ciao.EnglishSchoolBot.enums.StateEnum;
import by.ciao.EnglishSchoolBot.states.statesservice.AbstractState;
import by.ciao.EnglishSchoolBot.states.statesservice.UserHandlerState;
import by.ciao.EnglishSchoolBot.states.statesservice.UserMessageHandlerState;
import by.ciao.EnglishSchoolBot.user.User;
import by.ciao.EnglishSchoolBot.utils.AppConfig;
import by.ciao.EnglishSchoolBot.utils.BotResponses;
import by.ciao.EnglishSchoolBot.utils.LoggerMessages;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalTime;
import java.util.Objects;
import java.util.TimerTask;

public class StartTestState extends AbstractState implements UserMessageHandlerState {
    private static final Logger log = LoggerFactory.getLogger(StartTestState.class);

    public StartTestState(ServiceCallback serviceCallback) {
        super(serviceCallback);
    }

    @Override
    public void apply(final String textMsg, final User user) throws Exception {
        if (Objects.equals(textMsg, BotResponses.startTestButton())) {
            user.setState(StateEnum.SEND_QUESTION);
            user.clearTest();
            setTimer(user);
            user.getTestState().setStartTime(LocalTime.now());

            UserHandlerState state = new SendQuestionState(getServiceCallback());
            state.apply(user);
        }
        else {
            sendStartButton(user);
        }
    }

    private void setTimer(User user) {
        TimerTask task = new TimerTask() {
            public void run() {
                try {
                    sendText(user.getChatId().toString(), "Время вышло. Результаты вашего теста ниже");
                    changeStateToTestFinished(user);
                } catch (Exception e) {
                    log.error(LoggerMessages.setTimerException(), e);
                }
                user.getTestState().getTimer().cancel();
            }
        };
        user.getTestState().getTimer().schedule(task, Long.parseLong(AppConfig.getProperty("test_duration")) * 1000);
    }
}
