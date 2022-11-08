const headerText = `<header class="s-topbar ps-fixed t0 l0 js-top-bar">
    <div class="s-topbar--container">
        <a href="#" class="s-topbar--menu-btn js-left-sidebar-toggle" role="menuitem" aria-haspopup="true" aria-controls="left-sidebar" aria-expanded="false"><span></span></a>
        <div class="topbar-dialog leftnav-dialog js-leftnav-dialog dno">
            <div class="left-sidebar js-unpinned-left-sidebar" data-can-be="left-sidebar" data-is-here-when="sm"></div>
        </div>
        <a href="#" class="s-topbar--logo js-gps-track"
           data-gps-track="top_nav.click({is_current:true, location:1, destination:8})">
            <span class="-img _glyph">Stack Overflow &#x43D;&#x430; &#x440;&#x443;&#x441;&#x441;&#x43A;&#x43E;&#x43C;</span>
        </a>

        <form id="search" role="search" action=/search class="s-topbar--searchbar js-searchbar " autocomplete="off">
            <div class="s-topbar--searchbar--input-group">
                <input name="q"
                       type="text"
                       placeholder="&#x41F;&#x43E;&#x438;&#x441;&#x43A;..."
                       value=""
                       autocomplete="off"
                       maxlength="240"
                       class="s-input s-input__search js-search-field "
                       aria-label="&#x41F;&#x43E;&#x438;&#x441;&#x43A;"
                       aria-controls="top-search"
                       data-controller="s-popover"
                       data-action="focus->s-popover#show"
                       data-s-popover-placement="bottom-start" />
                <svg aria-hidden="true" class="s-input-icon s-input-icon__search svg-icon iconSearch" width="18" height="18" viewBox="0 0 18 18"><path d="m18 16.5-5.14-5.18h-.35a7 7 0 1 0-1.19 1.19v.35L16.5 18l1.5-1.5ZM12 7A5 5 0 1 1 2 7a5 5 0 0 1 10 0Z"/></svg>
                <div class="s-popover p0 wmx100 wmn4 sm:wmn-initial js-top-search-popover" id="top-search" role="menu">
                    <div class="s-popover--arrow"></div>
                    <div class="js-spinner p24 d-flex ai-center jc-center d-none">
                        <div class="s-spinner s-spinner__sm fc-orange-400">
                            <div class="v-visible-sr">Loading&#x2026;</div>
                        </div>
                    </div>

                    <span class="v-visible-sr js-screen-reader-info"></span>
                    <div class="js-ac-results overflow-y-auto hmx3 d-none"></div>

                    <div class="js-search-hints" aria-describedby="Tips for searching"></div>
                </div>
            </div>
        </form>

        <nav class="h100 ml-auto overflow-x-auto pr12">
            <ol class="s-topbar--content" role="menubar">

                <li role="none">
                    <a href="/help" class="s-topbar--item js-help-button" role="menuitem" title="&#x421;&#x43F;&#x440;&#x430;&#x432;&#x43A;&#x430; &#x438; &#x43F;&#x440;&#x43E;&#x447;&#x438;&#x435; &#x440;&#x435;&#x441;&#x443;&#x440;&#x441;&#x44B;" aria-haspopup="true" aria-controls="topbar-help-dialog"
                       data-ga="[&quot;top navigation&quot;,&quot;help menu click&quot;,null,null,null]"><svg aria-hidden="true" class="svg-icon iconHelp" width="18" height="18" viewBox="0 0 18 18"><path d="M9 1C4.64 1 1 4.64 1 9c0 4.36 3.64 8 8 8 4.36 0 8-3.64 8-8 0-4.36-3.64-8-8-8Zm.81 12.13c-.02.71-.55 1.15-1.24 1.13-.66-.02-1.17-.49-1.15-1.2.02-.72.56-1.18 1.22-1.16.7.03 1.2.51 1.17 1.23ZM11.77 8c-.59.66-1.78 1.09-2.05 1.97a4 4 0 0 0-.09.75c0 .05-.03.16-.18.16H7.88c-.16 0-.18-.1-.18-.15.06-1.35.66-2.2 1.83-2.88.39-.29.7-.75.7-1.24.01-1.24-1.64-1.82-2.35-.72-.21.33-.18.73-.18 1.1H5.75c0-1.97 1.03-3.26 3.03-3.26 1.75 0 3.47.87 3.47 2.83 0 .57-.2 1.05-.48 1.44Z"/></svg></a>
                </li>
                <div class="topbar-dialog help-dialog js-help-dialog dno" id="topbar-help-dialog" role="menu">
                    <div class="modal-content">
                        <ul>
                            <li>
                                <a href="/tour" class="js-gps-track" data-gps-track="help_popup.click({ item_type:1 })"
                                   data-ga="[&quot;top navigation&quot;,&quot;tour submenu click&quot;,null,null,null]">
                                    &#x422;&#x443;&#x440;
                                    <span class="item-summary">
                                    &#x41D;&#x430;&#x447;&#x43D;&#x438;&#x442;&#x435; &#x441; &#x44D;&#x442;&#x43E;&#x439; &#x441;&#x442;&#x440;&#x430;&#x43D;&#x438;&#x446;&#x44B;, &#x447;&#x442;&#x43E;&#x431;&#x44B; &#x431;&#x44B;&#x441;&#x442;&#x440;&#x43E; &#x43E;&#x437;&#x43D;&#x430;&#x43A;&#x43E;&#x43C;&#x438;&#x442;&#x44C;&#x441;&#x44F; &#x441; &#x441;&#x430;&#x439;&#x442;&#x43E;&#x43C;
                                </span>
                                </a>
                            </li>
                            <li>
                                <a href="/help" class="js-gps-track"
                                   data-gps-track="help_popup.click({ item_type:4 })"
                                   data-ga="[&quot;top navigation&quot;,&quot;help center&quot;,null,null,null]">
                                    &#x421;&#x43F;&#x440;&#x430;&#x432;&#x43A;&#x430;
                                    <span class="item-summary">
                                &#x41F;&#x43E;&#x434;&#x440;&#x43E;&#x431;&#x43D;&#x44B;&#x435; &#x43E;&#x442;&#x432;&#x435;&#x442;&#x44B; &#x43D;&#x430; &#x43B;&#x44E;&#x431;&#x44B;&#x435; &#x432;&#x43E;&#x437;&#x43C;&#x43E;&#x436;&#x43D;&#x44B;&#x435; &#x432;&#x43E;&#x43F;&#x440;&#x43E;&#x441;&#x44B;
                            </span>
                                </a>
                            </li>
                            <li>
                                <a href="#" class="js-gps-track" data-gps-track="help_popup.click({ item_type:2 })"
                                   data-ga="[&quot;top navigation&quot;,&quot;meta submenu click&quot;,null,null,null]">
                                    &#x41C;&#x435;&#x442;&#x430;
                                    <span class="item-summary">
                                            &#x41E;&#x431;&#x441;&#x443;&#x434;&#x438;&#x442;&#x44C; &#x43F;&#x440;&#x438;&#x43D;&#x446;&#x438;&#x43F;&#x44B; &#x440;&#x430;&#x431;&#x43E;&#x442;&#x44B; &#x438; &#x43F;&#x43E;&#x43B;&#x438;&#x442;&#x438;&#x43A;&#x443; &#x441;&#x430;&#x439;&#x442;&#x430;
                                        </span>
                                </a>
                            </li>
                            <li>
                                <a href="#" class="js-gps-track" data-gps-track="help_popup.click({ item_type:6 })"
                                   data-ga="[&quot;top navigation&quot;,&quot;about us submenu click&quot;,null,null,null]">
                                    &#x41E; &#x43D;&#x430;&#x441;
                                    <span class="item-summary">
                                        &#x423;&#x437;&#x43D;&#x430;&#x442;&#x44C; &#x431;&#x43E;&#x43B;&#x44C;&#x448;&#x435; &#x43E; &#x43A;&#x43E;&#x43C;&#x43F;&#x430;&#x43D;&#x438;&#x438; Stack Overflow
                                    </span>
                                </a>
                            </li>
                            <li>
                                <a href="#" class="js-gps-track" data-gps-track="help_popup.click({ item_type:7 })"
                                   data-ga="[&quot;top navigation&quot;,&quot;business submenu click&quot;,null,null,null]">
                                    &#x411;&#x438;&#x437;&#x43D;&#x435;&#x441;
                                    <span class="item-summary">
                                        Learn more about our products
                                    </span>
                                </a>
                            </li>
                        </ul>
                    </div>
                </div>
                <li role="none">
                    <a href="#" class="s-topbar--item js-site-switcher-button js-gps-track" data-gps-track="site_switcher.show"
                       aria-label="Site switcher"
                       role="menuitem"
                       title="&#x421;&#x43F;&#x438;&#x441;&#x43E;&#x43A; &#x432;&#x441;&#x435;&#x445; &#x441;&#x430;&#x439;&#x442;&#x43E;&#x432; Stack Exchange (182 &#x441;&#x430;&#x439;&#x442;&#x430;)"
                       aria-haspopup="true" aria-expanded="false"
                       data-ga="[&quot;top navigation&quot;,&quot;stack exchange click&quot;,null,null,null]">
                        <svg aria-hidden="true" class="svg-icon iconStackExchange" width="18" height="18" viewBox="0 0 18 18"><path d="M15 1H3a2 2 0 0 0-2 2v2h16V3a2 2 0 0 0-2-2ZM1 13c0 1.1.9 2 2 2h8v3l3-3h1a2 2 0 0 0 2-2v-2H1v2Zm16-7H1v4h16V6Z"/></svg>
                    </a>
                </li>


                <li class="js-topbar-dialog-corral" role="presentation">


                    <div class="topbar-dialog siteSwitcher-dialog dno" role="menu">
                        <div class="header fw-wrap">
                            <h3 class="flex--item">
                                <a href="#">&#x442;&#x435;&#x43A;&#x443;&#x449;&#x435;&#x435; &#x441;&#x43E;&#x43E;&#x431;&#x449;&#x435;&#x441;&#x442;&#x432;&#x43E;</a>
                            </h3>
                            <div class="flex--item fl1">
                                <div class="ai-center d-flex jc-end">
                                    <button
                                            class="js-close-button s-btn s-btn__muted p0 ml8 d-none sm:d-block"
                                            type="button"
                                            aria-label="Close"
                                    >
                                        <svg aria-hidden="true" class="svg-icon iconClear" width="18" height="18" viewBox="0 0 18 18"><path d="M15 4.41 13.59 3 9 7.59 4.41 3 3 4.41 7.59 9 3 13.59 4.41 15 9 10.41 13.59 15 15 13.59 10.41 9 15 4.41Z"/></svg>
                                    </button>
                                </div>
                            </div>
                        </div>
                        <div class="modal-content bg-powder-050 current-site-container">
                            <ul class="current-site ">
                                <li class="d-flex">
                                    <div class="fl1">
                                        <a href="#"
                                           class="current-site-link site-link js-gps-track d-flex gs8 gsx"
                                           data-id="609"
                                           data-gps-track="site_switcher.click({ item_type:3 })">
                                            <div class="favicon favicon-ru site-icon flex--item" title="Stack Overflow на русском"></div>
                                            <span class="flex--item fl1">
            Stack Overflow &#x43D;&#x430; &#x440;&#x443;&#x441;&#x441;&#x43A;&#x43E;&#x43C;
        </span>
                                        </a>

                                    </div>
                                    <div class="related-links">
                                        <a href="#" class="js-gps-track" data-gps-track="site_switcher.click({ item_type:14 })">&#x441;&#x43F;&#x440;&#x430;&#x432;&#x43A;&#x430;</a>
                                        <a href="#" class="js-gps-track" data-gps-track="site_switcher.click({ item_type:6 })">&#x447;&#x430;&#x442;</a>
                                    </div>

                                </li>
                                <li class="related-site d-flex">
                                    <div class="L-shaped-icon-container">
                                        <span class="L-shaped-icon"></span>
                                    </div>

                                    <a href="#"
                                       class=" site-link js-gps-track d-flex gs8 gsx"
                                       data-id="610"
                                       data-gps-track="site.switch({ target_site:610, item_type:3 }),site_switcher.click({ item_type:4 })">
                                        <div class="favicon favicon-rumeta site-icon flex--item" title="Stack Overflow на русском Meta"></div>
                                        <span class="flex--item fl1">
            Stack Overflow &#x43D;&#x430; &#x440;&#x443;&#x441;&#x441;&#x43A;&#x43E;&#x43C; Meta
        </span>
                                    </a>

                                </li>
                            </ul>
                        </div>

                        <div class="header" id="your-communities-header">
                            <h3>
                                &#x412;&#x430;&#x448;&#x438; &#x441;&#x43E;&#x43E;&#x431;&#x449;&#x435;&#x441;&#x442;&#x432;&#x430;            </h3>

                        </div>
                        <div class="modal-content" id="your-communities-section">

                            <div class="call-to-login">
                                Чтобы изменить список, <a href="#" class="login-link js-gps-track" data-gps-track="site_switcher.click({ item_type:10 })">зарегистрируйтесь</a> или <a href="#" class="login-link js-gps-track" data-gps-track="site_switcher.click({ item_type:11 })">войдите</a>.                </div>
                        </div>

                        <div class="header">
                            <h3><a href="#">&#x434;&#x440;&#x443;&#x433;&#x438;&#x435; &#x441;&#x43E;&#x43E;&#x431;&#x449;&#x435;&#x441;&#x442;&#x432;&#x430; stack exchange</a>
                            </h3>
                            <a href="#" class="float-right">&#x431;&#x43B;&#x43E;&#x433; &#x43A;&#x43E;&#x43C;&#x43F;&#x430;&#x43D;&#x438;&#x438;</a>
                        </div>
                        <div class="modal-content">
                            <div class="child-content"></div>
                        </div>
                    </div>

                </li>

                <li role="none"><button class="s-topbar--item s-btn s-btn__icon s-btn__muted d-none sm:d-inline-flex js-searchbar-trigger" role="menuitem" aria-label="&#x41F;&#x43E;&#x438;&#x441;&#x43A;" aria-haspopup="true" aria-controls="search" title="Click to show search"><svg aria-hidden="true" class="svg-icon iconSearch" width="18" height="18" viewBox="0 0 18 18"><path d="m18 16.5-5.14-5.18h-.35a7 7 0 1 0-1.19 1.19v.35L16.5 18l1.5-1.5ZM12 7A5 5 0 1 1 2 7a5 5 0 0 1 10 0Z"/></svg></button></li>
                <li role="none">
                    <a href="#" class="s-topbar--item s-topbar--item__unset s-btn s-btn__filled ws-nowrap js-gps-track" role="menuitem" rel="nofollow"
                       data-gps-track="login.click" data-ga="[&quot;top navigation&quot;,&quot;login button click&quot;,null,null,null]">&#x412;&#x43E;&#x439;&#x442;&#x438;</a>
                </li>
                <li role="none"><a href="#" class="s-topbar--item s-topbar--item__unset ml4 s-btn s-btn__primary ws-nowrap" role="menuitem" rel="nofollow" data-ga="[&quot;sign up&quot;,&quot;Sign Up Navigation&quot;,&quot;Header&quot;,null,null]">&#x420;&#x435;&#x433;&#x438;&#x441;&#x442;&#x440;&#x430;&#x446;&#x438;&#x44F;</a></li>
            </ol>
        </nav>


    </div>
</header>`;

const headText = `
 <title>Stack OverKata на русском</title>
    <link rel="shortcut icon" href="https://cdn.sstatic.net/Sites/ru/Img/favicon.ico?v=92117f9cb35c">
    <link rel="apple-touch-icon" href="https://cdn.sstatic.net/Sites/ru/Img/apple-touch-icon.png?v=0dd80f442adc">
    <link rel="image_src" href="https://cdn.sstatic.net/Sites/ru/Img/apple-touch-icon.png?v=0dd80f442adc">
    <link rel="search" type="application/opensearchdescription+xml" title="Stack Overflow &#x43D;&#x430; &#x440;&#x443;&#x441;&#x441;&#x43A;&#x43E;&#x43C;" href="/opensearch.xml">
    <meta name="description" content="&#x412;&#x43E;&#x43F;&#x440;&#x43E;&#x441;&#x44B; &#x438; &#x43E;&#x442;&#x432;&#x435;&#x442;&#x44B; &#x434;&#x43B;&#x44F; &#x43F;&#x440;&#x43E;&#x433;&#x440;&#x430;&#x43C;&#x43C;&#x438;&#x441;&#x442;&#x43E;&#x432;"/>
    <meta name="viewport" content="width=device-width, height=device-height, initial-scale=1.0, minimum-scale=1.0">
    <meta property="og:type" content= "website" />
    <meta property="og:url" content="https://ru.stackoverflow.com/"/>
    <meta property="og:site_name" content="Stack Overflow &#x43D;&#x430; &#x440;&#x443;&#x441;&#x441;&#x43A;&#x43E;&#x43C;" />
    <meta property="og:image" itemprop="image primaryImageOfPage" content="https://cdn.sstatic.net/Sites/ru/Img/apple-touch-icon@2.png?v=387ab71bdafd" />
    <meta name="twitter:card" content="summary"/>
    <meta name="twitter:domain" content="ru.stackoverflow.com"/>
    <meta name="twitter:title" property="og:title" itemprop="name" content="Stack Overflow &#x43D;&#x430; &#x440;&#x443;&#x441;&#x441;&#x43A;&#x43E;&#x43C;" />
    <meta name="twitter:description" property="og:description" itemprop="description" content="&#x412;&#x43E;&#x43F;&#x440;&#x43E;&#x441;&#x44B; &#x438; &#x43E;&#x442;&#x432;&#x435;&#x442;&#x44B; &#x434;&#x43B;&#x44F; &#x43F;&#x440;&#x43E;&#x433;&#x440;&#x430;&#x43C;&#x43C;&#x438;&#x441;&#x442;&#x43E;&#x432;" />


    <link rel="stylesheet" type="text/css" href="https://cdn.sstatic.net/Shared/stacks.css?v=5ad0f45f4799">
    <link rel="stylesheet" type="text/css" href="https://cdn.sstatic.net/Sites/ru/primary.css?v=14a33b4ac9f9">

    <link rel="alternate" type="application/atom+xml" title="&#x43B;&#x435;&#x43D;&#x442;&#x430; &#x43D;&#x43E;&#x432;&#x44B;&#x445; &#x432;&#x43E;&#x43F;&#x440;&#x43E;&#x441;&#x43E;&#x432;" href="/feeds">

    <link rel="stylesheet" type="text/css" href="https://cdn.sstatic.net/Shared/Channels/channels.css?v=d098999fc478">

`

function setHeader() {
    const header = document.createElement("div");
    header.innerHTML = headerText ;
    document.body.setAttribute('class', 'home-page unified-theme')
    document.body.insertAdjacentElement('afterbegin', header );
}

function setHead() {
    const head = document.createElement("div");
    head.innerHTML = headText ;
    document.head.append(head);
}

setHead();
setHeader();
