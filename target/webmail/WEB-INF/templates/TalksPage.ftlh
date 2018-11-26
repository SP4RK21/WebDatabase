<#-- @ftlvariable name="users" type="ru.itmo.webmail.model.domain.User[]" -->
<#import "common.ftl" as c>

<@c.page>
<div class="datatable">
    <div class="caption">Messages</div>
    <div>
        <table>
            <thead>
            <tr>
                <th>From</th>
                <th>To</th>
                <th>Message</th>
            </tr>
            </thead>
            <tbody>
                <#list messages as message>
                <tr>
                    <td>
                    ${message.fromUser}
                    </td>
                    <td>
                    ${message.toUser}
                    </td>
                    <td>
                    ${message.text}
                    </td>
                </tr>
                </#list>
        </table>
    </div>
    <form method="post" action="/talks">
        <input type="hidden" name="action" value="sendMessage">
        <div class="field">
            <div class="name">
                <label for="To">To:</label>
            </div>
            <div class="value">
                <input id="to" name="to"/>
            </div>
            <#if error??>
                <div class="error">
                ${error}
                </div>
            </#if>
        </div>
        <div class="field">
            <div class="name">
                <label for="Message">Message:</label>
            </div>
            <div class="value">
                <textarea id="text" name="text" value="${text!}"></textarea>
            </div>
        </div>
        <div class="button-field">
            <input type="submit" value="Send">
        </div>
</div>
</form>
</div>
</@c.page>
