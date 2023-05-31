let link = document.createElement('link')
link.href = '/css/style.css'
link.rel = 'stylesheet'
const head = document.querySelector('head')
head.appendChild(link)

let header = `
<header>
    <div class="container-fluid border-bottom fixed-top  bg-white">
        <div class="container">
            <div class="row align-items-center">
                <div class="col-2 text-center p-0 ">
                    <div class="row m-0">
                        <div class="col-3 ">
                            <div class="d-none d-sm-none d-md-block">
                                <!-- место для сайдабра на экранах моб телефонов-->
                            </div>
                        </div>
                        <div class="col-9 pb-2 bg-hover-grey min-w-150">
                            <a href="/"><img src="images/icons/logo.svg"></a>
                        </div>
                    </div>
                </div>
                <div class="col-10 text-center">
                    <div class="container">
                        <div class="row align-items-center">
                            <div class="col-7">
                                <form>
                                    <input class="form-control me-2" type="search" placeholder="Поиск"
                                           aria-label="Search">
                                </form>
                            </div>
                            <div class="col-5">
                                <div class="row align-items-center text-center">
                                    <div class="col flex-grow-0 bg-hover-grey">
                                        <a href="/profile">1</a>
                                    </div>
                                    <div class="col flex-grow-0 bg-hover-grey">
                                        2
                                    </div>
                                    <div class="col flex-grow-0 bg-hover-grey">
                                        3
                                    </div>
                                    <div class="col flex-grow-0 bg-hover-grey">
                                        4
                                    </div>
                                    <div class="col flex-grow-0 bg-hover-grey">
                                        5
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</header>
`

document.body.insertAdjacentHTML('afterbegin', header);


