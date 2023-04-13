let header = document.createElement("header");

header.classList.add('sticky-top')

header.innerHTML =
    '<nav class="navbar navbar-expand-md navbar-light bg-light border-bottom shadow-sm bg-white">' +
    '   <div class="container w-100">' +
    '       <div class="row w-100"' +
    '       <a class="navbar-brand col-3" href="#">' +
    '       <img src="/static/images/1280px-Stack_Overflow_logo.svg.png" height="30" class="d-inline-block align-top" alt="">' +
    '       </a>' +
    '       <div class="input-group col-8 ">\n' +
    '            <input class="form-control form-control-dark" type="text" placeholder="Search here..." aria-label="Search">\n' +
    '            <div class="input-group-append">\n' +
    '                <button class="btn btn-outline-success" type="submit">Search</button>\n' +
    '            </div>\n' +
    '        </div>' +
    '       <div class=" col">' +
    '           <button class="btn btn-outline-primary">Log in</button>' +
    '           <button class="btn btn-primary">Sign up</button>' +
    '       </div>' +
    '   </div>' +
    '   </div>' +
    '</nav>'
document.body.insertAdjacentElement("afterbegin", header);
