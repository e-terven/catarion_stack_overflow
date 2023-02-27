const sidebarHTML = `
  <nav class="position-fixed">
    <ul class="p-0" style="list-style: none">
      <li class="p-1 ${window.location.pathname === "/"? "bg-light border-right border-warning": ""}">
      <a href="/" class="text-dark">Главная</a>
      </li>
      <li class="p-1">
        <ul class="p-0" style="list-style: none">
          <li class="p-1">Публичные</li> 
          <li class="p-1">
            <a href="#" class="text-dark"> 
              <svg aria-hidden="true" class="svg-icon iconGlobe" width="18" height="18" viewBox="0 0 18 18"><path d="M9 1C4.64 1 1 4.64 1 9c0 4.36 3.64 8 8 8 4.36 0 8-3.64 8-8 0-4.36-3.64-8-8-8ZM8 15.32a6.46 6.46 0 0 1-4.3-2.74 6.46 6.46 0 0 1-.93-5.01L7 11.68v.8c0 .88.12 1.32 1 1.32v1.52Zm5.72-2c-.2-.66-1-1.32-1.72-1.32h-1v-2c0-.44-.56-1-1-1H6V7h1c.44 0 1-.56 1-1V5h2c.88 0 1.4-.72 1.4-1.6v-.33a6.45 6.45 0 0 1 3.83 4.51 6.45 6.45 0 0 1-1.51 5.73v.01Z"></path></svg>
              <span>Вопросы</span>
             </a>
            </li> 
          <li class="p-1 pl-3"><a class="text-dark" href="#">Метки</a></li> 
          <li class="p-1 pl-3"><a class="text-dark" href="#">Участники</a></li> 
          <li class="p-1 pl-3"><a class="text-dark" href="#">Неотвеченные</a></li> 
        </ul>
      </li>
    </ul>
  </nav>
`;

const sidebar = document.createElement("div");
sidebar.className="p-3 h-100 border-right"
sidebar.style.width="250px"
sidebar.innerHTML = sidebarHTML;

const container = document.createElement("div")
container.className = "container d-flex"

// Get first div with container class
const contentContainer = document.querySelector("body > div.container")
contentContainer.insertAdjacentElement("beforebegin", sidebar)

// Wrap contentContainer in sidebar container and add sidebar
const contentParent = contentContainer.parentNode;
contentParent.replaceChild(container, contentContainer)
container.appendChild( sidebar)
container.appendChild(contentContainer)
