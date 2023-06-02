function isFooter(currentPath) {
    let sidebarPages = [
        "/",
        "/main",
        "/questions",
        "/tag_page",
        "/question",
        "/profile"
    ];
    return sidebarPages.includes(currentPath);
}

let footer =`
<footer>
    <div class="container-fluid bg-dark py-5 footer">
        <div class="container">
            <div class="row text-light">
                <div class="col-3  d-flex flex-column">
                    <div><h6 class="text-uppercase">заголовок 1</h6></div>
                    <div><a href="#">Ссылка 1</a></div>
                    <div><a href="#">Ссылка 2</a></div>
                    <div><a href="#">Ссылка 3</a></div>
                    <div><a href="#">Ссылка 4</a></div>
                    <div><a href="#">Ссылка 5</a></div>
                    <div><a href="#">Ссылка 6</a></div>

                </div>
                <div class="col-3  d-flex flex-column">
                    <div><h6 class="text-uppercase">заголовок 2</h6></div>
                    <div><a href="#">Ссылка 1</a></div>
                    <div><a href="#">Ссылка 2</a></div>
                    <div><a href="#">Ссылка 3</a></div>
                    <div><a href="#">Ссылка 4</a></div>
                    <div><a href="#">Ссылка 5</a></div>
                    <div><a href="#">Ссылка 6</a></div>

                </div>
                <div class="col-3  d-flex flex-column">
                    <div><h6 class="text-uppercase">заголовок 3</h6></div>
                    <div><a href="#">Ссылка 1</a></div>
                    <div><a href="#">Ссылка 2</a></div>
                    <div><a href="#">Ссылка 3</a></div>
                    <div><a href="#">Ссылка 4</a></div>
                    <div><a href="#">Ссылка 5</a></div>
                    <div><a href="#">Ссылка 6</a></div>

                </div>
                <div class="col-3 d-flex flex-column justify-content-between ">
                    <div class="social d-flex">
                        <div><a href="#" class="me-1">Ссылка 1</a></div>
                        <div><a href="#" class="me-1">Ссылка 2</a></div>
                        <div><a href="#" class="me-1">Ссылка 3</a></div>
                        <div><a href="#" class="me-1">Ссылка 4</a></div>
                        <div><a href="#">Ссылка 5</a></div>
                    </div>
                    <div class="copyrights">
                        <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit. Aliquam asperiores at
                            dicta distinctio ducimus eligendi ex excepturi facere hic nobis nulla, optio praesentium
                            quod sequi temporibus totam vero voluptas! Magni.</p>
                    </div>
                </div>
            </div>
        </div>
    </div>
</footer>
`

if (isFooter(currentPath)) {
    const containerAfterSidebar = document.querySelector('header + .container')
    containerAfterSidebar.insertAdjacentHTML('afterend', footer);
}



