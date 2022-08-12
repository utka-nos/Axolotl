<#import "post_item.ftlh" as pi/>

<#macro news_list posts>
    <#if !posts?has_content>
        <div class="container-lg">No posts</div>
    <#else>
        <#list posts as post>
            <div class="container align-content-md-center">
                <@pi.post_item post/>
            </div>
        </#list>

    </#if>
</#macro>