package by.ciao.EnglishSchoolBot.states;

import by.ciao.EnglishSchoolBot.bot.ServiceCallback;
import by.ciao.EnglishSchoolBot.dto.UserInfoDTO;
import by.ciao.EnglishSchoolBot.enums.States;
import by.ciao.EnglishSchoolBot.states.statesservice.State;
import by.ciao.EnglishSchoolBot.states.statesservice.AbstractState;

import java.util.Objects;

public class TestToDoState extends AbstractState {
    public TestToDoState(ServiceCallback serviceCallback) {
        super(serviceCallback, States.TEST_TODO);
    }

    @Override
    public void apply(final String textMsg, final UserInfoDTO user) {
        if (Objects.equals(textMsg, "Начать тестирование\uD83C\uDFC1")) {
            user.setState(States.QUESTION_TO_SEND);
            user.clearTest();

            State state = new QuestionToSendState(getServiceCallback());
            state.apply(textMsg, user);
        }
        else sendStartButton(user);
    }
}
