<#import "../parts/page_macro.ftlh" as page>


<@page.page>
    <form class="row g-3" method="get" action="/admin/users">
        <div class="col-auto">
            <label for="searchInput" class="visually-hidden"></label>
            <input
                    class="form-control"
                    value="${searchText!""}"
                    placeholder="Find by username..."
                    name="searchText"
                    id="searchInput"/>
            <div class="invalid-feedback">It doesn't look like id...</div>
        </div>
        <div class="col-auto">
            <select class="form-select" onchange="makeValidSearchInput()" id="selectOption" name="searchField" aria-label="Default select example">
                <option value="id"
                        <#if searchField??>${(searchField == "id")?string("selected", "")}</#if>>
                    Id
                </option>
                <option value="username"
                        <#if searchField??>${(searchField == "username")?string("selected", "")}</#if>>
                    Username
                </option>
            </select>
        </div>
        <div class="col-auto">
            <button type="button" onclick="onClickBtnSubmitSearch()" class="btn btn-outline-success">Search</button>
            <button type="submit" hidden id="btnSubmitSearch">Search</button>
        </div>
        <div class="col-auto">
            <a href="/admin/users" class="btn btn-danger">Reset</a>
        </div>
    </form>

    <table class="table table-striped mt-2">
        <thead>
            <tr>
                <th scope="col">Id</th>
                <th scope="col">Avatar</th>
                <th scope="col">Login</th>
                <th scope="col">Email</th>
                <th scope="col">Roles</th>
                <th scope="col">Actions</th>
            </tr>
        </thead>
        <tbody>
            <#list users as user>
                <tr>
                    <th scope="row">${user.getId()}</th>
                    <td></td>
                    <td>
                        <a class="btn-link" href="/profile/${user.getUsername()}">${user.getUsername()}</a>
                    </td>
                    <td>${user.getEmail()}</td>
                    <td>
                        <#list user.getRoles() as role>
                            <strong class="" style="color: blueviolet">${role}</strong>
                        </#list>
                    </td>
                    <td>
                        <a class="btn btn-outline-primary" href="/admin/users/edit/${user.getId()}">Edit</a>
                    </td>
                </tr>
            </#list>
        </tbody>
    </table>

    <script>
        let searchInput = document.getElementById("searchInput");
        let btnSubmit = document.getElementById("btnSubmitSearch");
        let selectedItem = document.getElementById("selectOption");

        function onClickBtnSubmitSearch() {
            if (selectedItem.value === "id" && searchInput.value.search(/[a-zA-Z]/) !== -1) {
                let classAttr = searchInput.getAttribute("class");
                classAttr = classAttr + " is-invalid";
                searchInput.setAttribute("class", classAttr);
            }
            else btnSubmit.click();
        }

        function makeValidSearchInput() {
            let classAttr = searchInput.getAttribute("class");
            if (classAttr.search(/is-invalid/) !== -1) {
                classAttr = classAttr.substr(0, classAttr.length - 10);
                searchInput.setAttribute("class", classAttr);
            }
        }
    </script>


</@page.page>

