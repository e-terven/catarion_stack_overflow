const headerHTML = `
<div class="container d-flex justify-content-between align-items-center py-2">
  <a href="#" class="d-flex text-dark text-decoration-none">
    <img src="/static/images/kata-logo.png" alt="logo" class="rounded-lg mr-2" width="25" height="25">
    <h5 class="m-0"><span class="font-weight-normal">stack</span> overflow</h4>
  </a>
  <form class="mx-4 col-6">
    <div class="d-flex position-relative">
      <svg aria-hidden="true" style="top: 50%; transform: translateY(-50%)" class="px-2 position-absolute text-secondary" fill="currentColor" width="30" height="30" viewBox="0 0 18 18"><path d="m18 16.5-5.14-5.18h-.35a7 7 0 1 0-1.19 1.19v.35L16.5 18l1.5-1.5ZM12 7A5 5 0 1 1 2 7a5 5 0 0 1 10 0Z"></path></svg>
      <input class="p-2 pl-5 form-control" placeholder="Поиск...">
    </div>
    
  </form>
  <nav>
    <ul class="d-flex p-0 m-0" style="list-style: none">
      <li>
        <a href="#" class="btn alert-primary text-decoration-none mr-1">Войти</a> 
      </li> 
      <li>
        <a class="btn btn-primary" href="#">Регистрация</a> 
      </li> 
    </ul>
  </nav>
</div>
`;

const header = document.createElement("header");
header.classList.add("bg-light", "shadow-sm", "fixed-top");
header.style.borderTop = "3px solid orange";
document.body.style.paddingTop = "54px";
header.innerHTML = headerHTML;

document.body.insertAdjacentElement("afterbegin", header);
