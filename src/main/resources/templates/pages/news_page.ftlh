<#import "../parts/page_macro.ftlh" as page>
<#import "../parts/news_list.ftlh" as news_list>
<#import "../parts/add_post_form.ftlh" as add_post_form>

<@page.page>

    <#if message??>
        <div class="alert alert-danger">
            ${message!""}
        </div>
    </#if>



    <div class="container">
        <!-- button for adding new post -->
        <button class="btn btn-primary"
                type="button"
                id="collapseBtn" onclick="collapse()"
                data-bs-target="#addPostCollapse"
                data-bs-toggle="collapse">
            Add new post
        </button>

        <!-- btn for find post by tag -->
        <button type="button"
                class="btn btn-outline-secondary ms-1 d-inline-block h-100"
                data-bs-toggle="collapse"
                data-bs-target="#searchCollapseMenu"
                style="border: white">
            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" style="color: red" class="bi bi-search" viewBox="0 0 16 16">
                <path d="M11.742 10.344a6.5 6.5 0 1 0-1.397 1.398h-.001c.03.04.062.078.098.115l3.85 3.85a1 1 0 0 0 1.415-1.414l-3.85-3.85a1.007 1.007 0 0 0-.115-.1zM12 6.5a5.5 5.5 0 1 1-11 0 5.5 5.5 0 0 1 11 0z"></path>
            </svg>
        </button>
    </div>

    <!-- filter by tag -->
    <div class="collapse container ${(tagFilter??)?string('show', '')} my-2" id="searchCollapseMenu">
        <form class="d-flex" method="get" action="/filteredNews">
            <input class="form-control-sm form-control" type="search" placeholder="Search  by tag" aria-label="Search" name="tagFilter" value="${tagFilter!''}">
            <button class="btn btn-sm btn-outline-success" type="submit">Search</button>
            <a href="/news" class="btn btn-outline-danger ms-1">reset</a>
        </form>
    </div>

    <!-- form for adding new posts -->
    <@add_post_form.add_post_form />

    <!-- the list of posts -->
    <@news_list.news_list posts/>


    <!-- valid logic for collapse menu -->
    <script>
        function collapse(){
            if (document.getElementById("collapseBtn").innerText === "Hide") {
                document.getElementById("collapseBtn").innerText = "Add new post"
                makeValidAllFields();
            }
            else {
                document.getElementById("collapseBtn").innerText = "Hide"
            }
        }

        function makeValidAllFields() {
            const textArea = document.getElementById("postText");
            const tagArea = document.getElementById("postTag");
            const image = document.getElementById("inputFile");

            if (textArea.getAttribute("class").includes("is-invalid")) {
                let classAttrText = textArea.getAttribute("class");
                classAttrText = classAttrText.substr(0, classAttrText.length - 10);
                textArea.setAttribute("class", classAttrText);
            }

            if (tagArea.getAttribute("class").includes("is-invalid")) {
                let classAttrTag = tagArea.getAttribute("class");
                classAttrTag = classAttrTag.substr(0, classAttrTag.length - 10);
                tagArea.setAttribute("class", classAttrTag);
            }

            if (image.getAttribute("class").includes("is-invalid")) {
                let classAttrImage = image.getAttribute("class");
                classAttrImage = classAttrImage.substr(0, classAttrImage.length - 10);
                image.setAttribute("class", classAttrImage);
            }
        }
    </script>
</@page.page>
