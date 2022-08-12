<#import "../parts/page_macro.ftlh" as page/>
<#import "../parts/news_list.ftlh" as news_list/>

<@page.page>
    <div>
        ${user.username}
    </div>
    <div class="d-inline-flex">
        <#list user.roles as role>
            <div class="badge bg-success my-2 mx-2">${role} </div>
        </#list>
    </div>
    <div>
        <@news_list.news_list posts />
    </div>
</@page.page>