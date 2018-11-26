package ru.itmo.webmail.web.page;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class IndexPage extends Page {
    private void action(HttpServletRequest request, Map<String, Object> view) {
        // No operations.
    }

    private void registrationDone(HttpServletRequest request, Map<String, Object> view) {
        view.put("message", "You have been registered");
    }

    private void enterDone(HttpServletRequest request, Map<String, Object> view) {
        view.put("message", "You have successfully logged in");
    }

    private void logoutDone(HttpServletRequest request, Map<String, Object> view) {
        view.put("message", "You have logged out");
    }

    private void emailConfirmed(HttpServletRequest request, Map<String, Object> view) {
        view.put("message", "Email confirmed");
    }
    private void nonExistentSecret(HttpServletRequest request, Map<String, Object> view) {
        view.put("message", "This secret key doesn't exist");
    }
    private void talksNotAllowed(HttpServletRequest request, Map<String, Object> view) {
        view.put("message", "Enter to use talks");
    }
}
