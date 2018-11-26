package ru.itmo.webmail.web.page;

import ru.itmo.webmail.model.domain.Event;
import ru.itmo.webmail.model.domain.User;
import ru.itmo.webmail.model.exception.ValidationException;
import ru.itmo.webmail.model.service.UserService;
import ru.itmo.webmail.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class EnterPage extends Page {
    private void enter(HttpServletRequest request, Map<String, Object> view) {
        String authData = request.getParameter("authData");
        String password = request.getParameter("password");

        try {
            getUserService().validateEnter(authData, password);
        } catch (ValidationException e) {
            view.put("authData", authData);
            view.put("error", e.getMessage());
            return;
        }

        User user = getUserService().authorize(authData, password);
        request.getSession(true).setAttribute(USER_ID_SESSION_KEY, user.getId());
        getEventService().saveEvent(user.getId(), "ENTER");
        throw new RedirectException("/index", "enterDone");
    }

    private void action(HttpServletRequest request, Map<String, Object> view) {
        // No operations.
    }
}
