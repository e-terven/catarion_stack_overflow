const footerHTML = `
<div class="container">
    <div class="row">
      <div class="col-12 text-center">
        <p class="m-0">&copy; 2023 Stack Overflow Kata</p>
      </div>
    </div>
</div>
`;

const footer = document.createElement("footer");
footer.className = "bg-dark text-white py-3 mt-auto";
footer.innerHTML = footerHTML;

const body = document.querySelector("body");
body.insertAdjacentElement("beforeend", footer);
body.classList.add("d-flex", "flex-column");
body.style.height = "100vh";