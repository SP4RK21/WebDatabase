package ru.itmo.webmail.web.page;

import ru.itmo.webmail.model.domain.User;
import ru.itmo.webmail.model.exception.ValidationException;
import ru.itmo.webmail.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class ConfirmPage extends Page {
    private void action(HttpServletRequest request, Map<String, Object> view) {
        String secret = request.getParameter("secret");
        if (getEmailConfirmationService().confirm(secret)) {
            throw new RedirectException("/index", "emailConfirmed");
        } else {
            throw new RedirectException("/index", "nonExistentSecret");
        }

    }
}
