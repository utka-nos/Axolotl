<#import "../parts/page_macro.ftlh" as page>

<@page.page>
    Hello, friend!
    <#if message??>
        ${message}
    </#if>
    <a href="/main">Main page</a>
</@page.page>
