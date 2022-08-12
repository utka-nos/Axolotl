<#assign
    context = Session.SPRING_SECURITY_CONTEXT??
/>

<#if context>
    <#assign
        authUser = Session.SPRING_SECURITY_CONTEXT.authentication.principal
        authName = authUser.getUsername()
        isAdmin = authUser.isAdmin()
    />
<#else>
    <#assign
        authName = "unknown"
        isAdmin = false
    />
</#if>