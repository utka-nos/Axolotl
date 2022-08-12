<#macro add_post_form>
    <div class="collapse container" id="addPostCollapse">
        <form class="needs-validation" method="post" enctype="multipart/form-data" action="/news">
            <!-- post text -->
            <div class="mb-3">
                <label class="form-label col-form-label-sm" for="postText">Text: </label>
                <textarea class="form-control form-control-sm"
                          id="postText"
                          type="text"
                          placeholder="text"
                          name="text"></textarea>
            </div>
            <!-- post TAG -->
            <div class="mb-3">
                <label class="form-label col-form-label-sm" for="postTag">Tag: </label>
                <input class="form-control form-control-sm" id="postTag" type="text" placeholder="tag" name="tag"/>
            </div>
            <!-- image -->
            <label class="form-label col-form-label-sm" for="inputFile">Upload image:</label>
            <div class="input-group input-group-sm mb-3 ">
                <input type="file" id="inputFile" name="image" class="form-control" aria-label="Upload">
            </div>
            <button onclick="addNewPostCheck()" class="btn btn-outline-primary" type="button">Add</button>
            <button type="submit" id="submitBtn" hidden></button>
            <input type="hidden" name="_csrf" value="${_csrf.token}">
        </form>
    </div>

    <!-- Toast with errors -->

    <div class="toast-containers position-fixed bottom-0 end-0 p-3" style="z-index : 999999">
        <div id="errorsToast"
             class="toast border-danger text-danger"
             data-bs-delay="10000"
             role="alert"
             aria-live="assertive"
             aria-atomic="true">
            <div class="toast-header">
                <strong class="me-auto">Problems with sending new post!</strong>
                <button type="button" class="btn-close" data-bs-dismiss="toast" aria-label="Close"></button>
            </div>
            <div class="toast-body">
                <#if errors??>
                    <#list errors as errorKey, errorValue>
                        ${errorValue}
                    </#list>
                </#if>
            </div>
        </div>

    </div>


    <script>


        window.onload = function () {
            let err =
                [
                    <#if errors??><#list errors as errorKey, errorValue>
                    {
                        "errorType" : "${errorKey}",
                        "errorText" : "${errorValue}",
                    },
                    </#list></#if>
                ]
            if (err.length > 0) {
                let errorsToast = document.getElementById("errorsToast")
                const toast = new bootstrap.Toast(errorsToast)
                toast.show()
            }
        }


        function addNewPostCheck() {
            const textArea = document.getElementById("postText");
            const tagArea = document.getElementById("postTag");
            const image = document.getElementById("inputFile");
            if (textArea.value === "" && tagArea.value === "" && image.value === "") {
                let classAttrText = textArea.getAttribute("class");
                if (!classAttrText.includes("is-invalid")) {
                    classAttrText = classAttrText + " is-invalid";
                    textArea.setAttribute("class", classAttrText);
                }

                let classAttrTag = tagArea.getAttribute("class");
                if (!classAttrTag.includes("is-invalid")) {
                    classAttrTag = classAttrTag + " is-invalid"
                    tagArea.setAttribute("class", classAttrTag);
                }

                let classAttrImage = image.getAttribute("class");
                if (!classAttrImage.includes("is-invalid")) {
                    classAttrImage = classAttrImage + " is-invalid";
                    image.setAttribute("class", classAttrImage);
                }

            }
            else document.getElementById("submitBtn").click();
        }
    </script>
</#macro>