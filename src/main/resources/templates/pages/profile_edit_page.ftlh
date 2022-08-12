<#import "../parts/page_macro.ftlh" as page>

<@page.page>

    <div class="container">

        <!-- Message about status of saving changes-->
        <#if message??>
            <div class="alert alert-${alertStyle!''}">
                ${message!''}
            </div>
        </#if>

        <!-- avatar -->
        <#if user.getAvatarUrl()??>
            <img src="${user.getAvatarUrl()}" style="max-width: 500px" alt="img here..."/>
        </#if>

        <!-- change avatar -->
        <form class="container-lg m-2" method="post" action="/profile/editAvatar" enctype="multipart/form-data">
            <div class="input-group input-group-sm mb-3 ">
                <input  name="avatar" type="file" class="form-control" aria-label="Upload">
                <button class="btn btn-success" type="submit">upload image</button>
                <input type="hidden" name="_csrf" value="${_csrf.token}">
            </div>
        </form>

        <!-- change general info -->
        <div class="h3 m-2">General information</div>
        <form class="container-lg needs-validation m-2" method="post" action="/profile/editGeneralInfo">
            <div class="mb-3 ">
                <label for="inputUsername" class="form-label">Login</label>
                <input type="text"
                       name="username"
                       value="${user.getUsername()}"
                       class="form-control <#if errors??>${(errors.usernameError??)?string("is-invalid", "")}</#if>"
                       id="inputUsername"
                       required>
                <div class="invalid-feedback"><#if errors??>${errors.usernameError!""}</#if></div>
            </div>
            <!-- submit button -->
            <button class="btn btn-secondary" type="submit">Save changes</button>
            <!-- basic fields which wasn't changed-->
            <input type="hidden" name="id" value="${user.getId()}">
            <input type="hidden" name="password" value="${user.getPassword()}">
            <input type="hidden" name="email" value="${user.getEmail()}">
            <input type="hidden" name="_csrf" value="${_csrf.token}">
        </form>

        <!-- change email-->
        <div class="h3 m-2">Email</div>
        <form class="container-lg needs-validation m-2" method="post" action="/profile/editEmail">
            <div class="mb-3 ">
                <label for="inputEmail" class="form-label visually-hidden"></label>
                <input type="email"
                       name="email"
                       value="${user.getEmail()}"
                       class="form-control <#if errors??>${(errors.emailError??)?string("is-invalid", "")}</#if>"
                       id="inputEmail"
                       required
                       readonly>
                <div class="invalid-feedback"><#if errors??>${errors.emailError!""}</#if></div>
            </div>
            <!-- confirm changes button -->
            <button class="btn btn-primary"
                    id="btnClickEmailChange"
                    onclick="changeEmail()"
                    type="button">
                Change email
            </button>
            <!-- cancel changes button -->
            <button class="btn btn-outline-warning"
                    type="button"
                    onclick="resetEmail()"
                    id="btnClickResetEmail"
                    hidden>
                Cancel
            </button>

            <button type="submit" id="btnSubmitChangeEmail" hidden></button>

            <input type="hidden" name="id" value="${user.getId()}">
            <input type="hidden" name="password" value="${user.getPassword()}">
            <input type="hidden" name="username" value="${user.getUsername()}">
            <input type="hidden" name="_csrf" value="${_csrf.token}">
        </form>

    </div>

    <script>
        let btnSubmitEmail  = document.getElementById("btnSubmitChangeEmail");
        let btnClickEmail   = document.getElementById("btnClickEmailChange");
        let inputEmail      = document.getElementById("inputEmail");
        let btnClickResetEmail = document.getElementById("btnClickResetEmail");


        function changeEmail() {
            if (inputEmail.getAttributeNames().includes("readonly")){
                inputEmail.removeAttribute("readonly");
                btnClickResetEmail.removeAttribute("hidden");
                btnClickEmail.innerText = "Confirm changes"
            } else {
                btnSubmitEmail.click();
            }
        }

        function resetEmail() {
            inputEmail.value = "${user.getEmail()}";
            inputEmail.setAttribute("readonly", "true");
            btnClickResetEmail.setAttribute("hidden", "true");
            btnClickEmail.innerText = "Change email"
        }
    </script>

</@page.page>