<#macro post_item post>
    <div class="card mt-2 mx-auto bg-light">
        <#if post.filename??>
            <img src="/img/${post.filename}" class="card-img-top" alt="Your image...">
        </#if>
        <div class="card-body">
            <h5 class="card-title">Post title</h5>
            <p class="card-text">${post.getText()}</p>
        </div>
        <div class="card-body">
            <a href="/profile/${post.getAuthor().getId()}" class="card-link">${post.getAuthor().getUsername()}</a>
            <a href="#" class="card-link">${post.getTag()}</a>
        </div>
    </div>
</#macro>

