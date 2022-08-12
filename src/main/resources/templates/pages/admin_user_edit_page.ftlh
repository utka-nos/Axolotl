<#import "../parts/page_macro.ftlh" as page>

<@page.page>

    <!-- Message about errors -->
    <#if message??>
        <div class="alert alert-${(messageStyle??)?string(messageStyle, 'danger')}" role="alert">
            <strong>${message!''}</strong>
        </div>
    </#if>

    <!-- User info edit form -->
    <form class="container-fluid" method="post" action="/admin/users/edit/${user.getId()}">

        <!-- UserId info -->
        <div class="row m-2">
            <strong class="col col-lg-2 my-auto">Id :</strong>
            <div class="col col-lg-2">
                ${user.getId()}
            </div>
        </div>

        <!-- username -->
        <div class="row m-2">
            <label class="form-label col col-lg-2 my-auto" for="inputUsername"><strong>Username:</strong></label>
            <div class="col-lg-2">
                <input class="form-control form-control-sm col-lg-2 <#if errors??>${(errors.usernameError??)?string("is-invalid", "")}</#if>"
                       name="username"
                       value="${user.getUsername()}"
                       type="text"
                       id="inputUsername"/>
                <div class="invalid-feedback"><#if errors??>${errors.usernameError!""}</#if></div>
            </div>
        </div>

        <!-- user roles edit checkbox -->
        <div class="row m-2">
            <strong class="col col-lg-2">Roles:</strong>
            <div class="col col-lg-2 ps-0">
                <#list roles as role>
                    <div class="form-check">
                        <input type="checkbox"
                               name="${role}"
                               <#if user.getRoles()?seq_contains(role)>checked</#if>
                               id="check_${role}"/>
                        <label class="form-check-label" for="check_${role}">${role}</label>
                    </div>
                </#list>
            </div>
        </div>

        <!-- is account activated -->
        <div class="row m-2">
            <strong class="col col-lg-2">Active status:</strong>
            <div class="form-check col col-lg-2">
                <input type="checkbox"
                       name="isActive"
                       <#if user.isActive()>checked</#if>
                       id="isActive"/>
                <label class="form-check-label" for="isActive">Is active</label>
            </div>
        </div>

        <!-- button save changes-->
        <div class="row m-2">
            <div class="col col-lg-2">
                <button class="btn btn-success" type="submit">Save</button>
            </div>
        </div>

        <input type="hidden" name="password" value="${user.getPassword()}"/>
        <input type="hidden" name="email" value="${user.getEmail()}">
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>

    </form>

    <!-- delete user button -->
    <form class="container-fluid" method="post" action="/admin/users/delete/${user.getId()}">
        <div class="row m-2">
            <div class="col col-lg-2">
                <button class="btn btn-danger" type="submit">Delete user</button>
            </div>
        </div>
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
    </form>

</@page.page>

