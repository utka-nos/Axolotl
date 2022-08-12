<#macro logout>
    <form action="/logout" method="post">
        <button type="submit">Logout</button>
        <input type="hidden" name="_csrf" value="${_csrf.token}">
    </form>
</#macro>