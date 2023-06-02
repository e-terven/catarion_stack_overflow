function isSidebar(currentPath) {
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

let currentPath = window.location.pathname;

const before = `
<div class="container " style="margin-top: 63px;">
    <div class="row">
        <div class="col-2 d-none d-sm-block text-center">
            <div class="d-flex flex-column mt-3">
                <div class="row">
                    <div class="col nav-div text-start pe-0"><a href="/" >Главная</a></div>
                </div>
                <div class="row">
                    <div class="col text-start pe-0"><span id="public">ПУБЛИЧНЫЕ</span></div>
                </div>
                <div class="row">
                    <div class="col-3 "></div>
                    <div class="col-9 text-start nav-div pe-0"><a href="/questions">Вопросы</a></div>
                </div>
                <div class="row">
                    <div class="col-3"></div>
                    <div class="col-9  text-start nav-div pe-0"><a href="/tag_page">Метки</a></div>
                </div>
                <div class="row">
                    <div class="col-3"></div>
                    <div class="col-9  text-start nav-div pe-0" ><a href="#">Участники</a></div>
                </div>
                <div class="row">
                    <div class="col-3"></div>
                    <div class="col-9  text-start nav-div pe-0"><a href="#">Неотвеченные</a></div>
                </div>
            </div>
        </div>
        <div class="col-md-10 ps-0 border-start">
            <div class="container">
`

const after = `</div>
        </div>
    </div>
</div>
`


if (isSidebar(currentPath)) {
    const container = document.querySelector('.container')
    document.body.innerHTML = before + container.innerHTML + after
}

let navLinks = document.querySelectorAll('.nav-div a')
navLinks.forEach(function (link) {
    if (link.getAttribute('href') === currentPath) {
        link.parentNode.classList.add('active-page')
    }
})