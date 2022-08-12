<#import "../parts/page_macro.ftlh" as page>

<@page.page>

    <#if message??>
        <div class="alert alert-info">
            ${message!""}
        </div>
    <#else>
        <div class="alert alert-info">
            ***404***
        </div>
    </#if>


</@page.page>
