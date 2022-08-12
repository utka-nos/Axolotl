<#import "parts/page_macro.ftlh" as page>
<#import "parts/login_macro.ftlh" as form_login>

<@page.page>

    <@form_login.login "/login"/>

    <a type="button" href="/registration" class="btn btn-link">No account yet? Sign up now!</a>

</@page.page>




