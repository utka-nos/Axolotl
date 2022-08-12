<#import "../parts/page_macro.ftlh" as page>

<@page.page>
    <h2>Registration</h2>

    <#if message??>
        <div class="alert alert-${textStyle!"primary"}" role="alert">
            ${message!''}
            </div>
    </#if>


    <form class="needs-validation" method="post" action="/registration">
        <!-- login -->
        <div class="form-floating small mb-3">
            <input type="text"
                   class="form-control form-control-sm <#if errors??>${(errors.usernameError??)?string("is-invalid", "")}</#if>"
                   value="<#if user??>${user.getUsername()}<#else></#if>"
                   name="username"
                   id="usernameInput">
            <label for="usernameInput">Login</label>
            <div class="invalid-feedback"><#if errors?? && errors.usernameError??>${errors.usernameError}</#if></div>
        </div>
        <!--email-->
        <div class="form-floating small mb-3">
            <input type="email"
                   class="form-control form-control-sm <#if errors??>${(errors.emailError??)?string("is-invalid", "")}</#if>"
                   value="<#if user??>${user.getEmail()}<#else></#if>"
                   name="email"
                   id="emailInput">
            <label for="emailInput">Email</label>
            <div class="invalid-feedback"><#if errors?? && errors.emailError??>${errors.emailError}</#if></div>
        </div>
        <!-- password-->
        <div class="form-floating small mb-3">
            <input type="password"
                   name="password"
                   class="form-control form-control-sm <#if errors??>${(errors.passwordError??)?string("is-invalid", "")}</#if>"
                   id="passwordInput">
            <label for="passwordInput">Password</label>
            <div class="invalid-feedback"><#if errors?? && errors.passwordError??>${errors.passwordError}</#if></div>
        </div>
        <!-- Confirm password -->
        <div class="form-floating small mb-3">
            <input type="password"
                   name="passwordConfirm"
                   class="form-control form-control-sm <#if errors??>${(errors.passwordConfirmError??)?string("is-invalid", "")}</#if>"
                   id="passwordConfirmInput">
            <label for="passwordConfirmInput">Password confirm</label>
            <div class="invalid-feedback"><#if errors?? && errors.passwordConfirmError??>${errors.passwordConfirmError}</#if></div>
        </div>
        <!-- reCaptcha -->
        <div class="form-floating small mb-3">
            <div class="g-recaptcha mb-3" data-sitekey="6LdYusQgAAAAAKKzM81Q6ScyDOG0ljcpFRSgbiG-"></div>
            <#if errors?? && errors.recaptchaError??>
                <div class="alert alert-danger">${errors.recaptchaError}</div>
            </#if>
        </div>
        <!-- confirm btn + csrf token -->
        <input type="hidden" name="_csrf" value="${_csrf.token}">
        <button class="btn btn-outline-primary mb-3" type="submit">Sign up</button>
    </form>


</@page.page>