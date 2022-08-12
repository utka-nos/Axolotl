<#macro login path>

    <#assign loginException = Session.SPRING_SECURITY_LAST_EXCEPTION??/>

    <form class="container-lg needs-validation" method="post" action="${path}">
        <div class="h2">Log in</div>
        <div class="mb-3 ">
            <label for="exampleInputEmail1" class="form-label">Login</label>
            <input
                    type="text"
                    name="username"
                    class="form-control ${(loginException)?string('is-invalid', '')}"
                    id="exampleInputEmail1"
                    required>
            <div id="emailHelp" class="form-text">We'll never share your email with anyone else.</div>
            <div class="invalid-feedback">Invalid credentials</div>
        </div>
        <div class="mb-3">
            <label for="exampleInputPassword1" class="form-label">Password</label>
            <input
                    type="password"
                    name="password"
                    class="form-control ${(loginException)?string('is-invalid', '')}"
                    id="exampleInputPassword1"
                    required>
            <div class="invalid-feedback">Invalid credentials</div>
        </div>

        <button class="btn btn-secondary" type="submit">Sign in</button>

        <input type="hidden" name="_csrf" value="${_csrf.token}">
    </form>
</#macro>