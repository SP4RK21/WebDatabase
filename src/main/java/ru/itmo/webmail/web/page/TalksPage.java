package ru.itmo.webmail.web.page;

import ru.itmo.webmail.model.domain.Talk;
import ru.itmo.webmail.model.domain.User;
import ru.itmo.webmail.model.exception.ValidationException;
import ru.itmo.webmail.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TalksPage extends Page {

    @Override
    public void before(HttpServletRequest request, Map<String, Object> view) {
        if (request.getSession(true).getAttribute(USER_ID_SESSION_KEY) != null) {
            List<Talk> talks = getTalkService().findAll((Long)request.getSession(true).getAttribute(USER_ID_SESSION_KEY));
            for (Talk t: talks) {
                t.setToUser(getUserService().find(t.getTargetUserId()).getLogin());
                t.setFromUser(getUserService().find(t.getSourceUserId()).getLogin());
            }
            view.put("messages", talks);
        } else {
            throw new RedirectException("/index", "talksNotAllowed");
        }
    }

    private void sendMessage(HttpServletRequest request, Map<String, Object> view) {
        Talk talk = new Talk();
        talk.setSourceUserId((Long)request.getSession(true).getAttribute(USER_ID_SESSION_KEY));
        talk.setText(request.getParameter("text"));
        talk.setFromUser(getUserService().find((Long)request.getSession(true).getAttribute(USER_ID_SESSION_KEY)).getLogin());
        try {
            getUserService().validateReciever(request.getParameter("to"));
            talk.setTargetUserId(getUserService().find(request.getParameter("to")).getId());
            talk.setToUser(request.getParameter("to"));
        } catch (ValidationException e) {
            view.put("text", talk.getText());
            view.put("error", e.getMessage());
            return;
        }
        getTalkService().saveTalk(talk);
        throw new RedirectException("/talks");
    }

    private void action(HttpServletRequest request, Map<String, Object> view) {
        //No operations
    }
}
