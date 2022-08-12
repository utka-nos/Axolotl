<#include "security.ftlh"/>

<nav class="navbar navbar-expand-lg navbar-light bg-light" style="padding-left: 10px">
    <div class="container-fluid">
        <a class="navbar-brand" href="/">Axolotl</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarScroll" aria-controls="navbarScroll" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse " id="navbarScroll">
            <div class="container-fluid">
                <ul class="navbar-nav navbar-nav-scroll float-lg-start">
                    <li class="nav-item" >
                        <a class="nav-link" href="/news" id="news">News</a>
                    </li>
                    <#if authUser??>
                        <li class="nav-item">
                            <a class="nav-link" href="/profile" id="profile">Profile</a>
                        </li>
                    </#if>
                    <#if authUser??>
                        <li class="nav-item">
                            <a class="nav-link" href="/profile/edit" id="profile_edit">Edit profile</a>
                        </li>
                    </#if>
                    <#if isAdmin>
                        <li class="nav-item">
                            <a class="nav-link" href="/admin/users" id="admin_users">Admin panel</a>
                        </li>
                    </#if>

                </ul>
                <div class="float-lg-end">
                    <div class="navbar-text me-2" style="display: inline-block" >${authName}</div>
                    <#if authUser??>
                        <form method="post" action="/logout" style="display: inline-block">
                            <button class="btn btn-outline-success" type="submit">Logout</button>
                            <input type="hidden" name="_csrf" value="${_csrf.token}">
                        </form>
                    <#else>
                        <a class="btn btn-secondary" href="/login">Log in</a>
                    </#if>
                </div>
            </div>
        </div>
    </div>

    <script>
        switch (document.location.pathname) {
            case "/news" : {
                const newsBar = document.getElementById("news");
                let classAttr = newsBar.getAttribute("class");
                classAttr = classAttr + " active"
                newsBar.setAttribute("class", classAttr);
                break;
            }
            case "/profile" : {
                const profileBar = document.getElementById("profile");
                let classAttr = profileBar.getAttribute("class");
                classAttr = classAttr + " active"
                profileBar.setAttribute("class", classAttr);
                break;
            }
            case "/profile/edit" : {
                const profileEditBar = document.getElementById("profile_edit");
                let classAttr = profileEditBar.getAttribute("class");
                classAttr = classAttr + " active"
                profileEditBar.setAttribute("class", classAttr);
                break;
            }
            case "/admin/users" : {
                const adminUsersBar = document.getElementById("admin_users");
                let classAttr = adminUsersBar.getAttribute("class");
                classAttr = classAttr + " active"
                adminUsersBar.setAttribute("class", classAttr);
                break;
            }
            default : {
                break;
            }
        }
    </script>


</nav>
