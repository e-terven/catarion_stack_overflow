let sidebar = document.createElement("sidebar");

let bodyHTML = document.body.innerHTML
document.body.innerHTML = '';

sidebar.innerHTML =
    '<div class="container" style="height: 100vh">' +
    '<div class="row h-100">' +
    '<div class="d-flex flex-shrink-0  col-2 border-right">\n' +
    '    <hr>\n' +
    '    <ul class="nav flex-column ">\n' +
    '      <li class="nav-item ">\n' +
    '        <a href="#" class="nav-link text-secondary active" aria-current="page">\n' +
    '          <svg class="bi me-2" width="16" height="16"><use xlink:href="#home"></use></svg>\n' +
    '          Home\n' +
    '        </a>\n' +
    '      </li>\n' +
    '      <li>\n' +
    '        <a href="#" class="nav-link  text-secondary"  style="font-size: small">\n' +
    '          <svg class="bi me-2" width="16" height="16"><use xlink:href="#speedometer2"></use></svg>\n' +
    '          Questions\n' +
    '        </a>\n' +
    '      </li>\n' +
    '      <li>\n' +
    '        <a href="#" class="nav-link text-secondary " style="font-size: small">\n' +
    '          <svg class="bi me-2" width="16" height="16"><use xlink:href="#table"></use></svg>\n' +
    '          Tags\n' +
    '        </a>\n' +
    '      </li>\n' +
    '      <li>\n' +
    '        <a href="#" class="nav-link text-secondary " style="font-size: small">\n' +
    '          <svg class="bi me-2" width="16" height="16"><use xlink:href="#grid"></use></svg>\n' +
    '          Users\n' +
    '        </a>\n' +
    '      </li>\n' +
    '      <li>\n' +
    '        <a href="#" class="nav-link text-secondary " style="font-size: small">\n' +
    '          <svg class="bi me-2" width="16" height="16"><use xlink:href="#people-circle"></use></svg>\n' +
    '          Companies\n' +
    '        </a>\n' +
    '      </li>\n' +
    '    </ul>\n' +
    '    <hr>\n' +
    '  </div>' +
    '<content class="col">' +
    bodyHTML +
    '</content>' +
    '</div>' +
    '</div>';

document.body.insertAdjacentElement("afterbegin", sidebar);