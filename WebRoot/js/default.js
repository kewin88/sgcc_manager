var tab = null;
var tree = null;
var accordion = null;
$(function () {
    $("#layout1").ligerLayout({ leftWidth: 190, height: '100%', heightDiff: -34, space: 4, onHeightChanged: f_heightChanged });
    var height = $(".l-layout-center").height();
    $("#accordion1").ligerAccordion({ height: height - 24, speed: null });
    $(".l-link").hover(function () {
        $(this).addClass("l-link-over");
    }, function () {
        $(this).removeClass("l-link-over");
    });
    $("#framecenter").ligerTab({ height: height });
    $("#tree1").ligerTree({
        data: indexdata,
        checkbox: false,
        slide: false,
        nodeWidth: 120,
        attribute: ['nodename', 'url'],
        onSelect: function (node) {
            if (!node.data.url) return;
            var tabid = $(node.target).attr("tabid");
            if (!tabid) {
                tabid = new Date().getTime();
                $(node.target).attr("tabid", tabid)
            }
            f_addTab(tabid, node.data.text, node.data.url);
        }
    });

    tab = $("#framecenter").ligerGetTabManager();
    accordion = $("#accordion1").ligerGetAccordionManager();
    tree = $("#tree1").ligerGetTreeManager();
    $("#pageloading").hide();

});
function f_heightChanged(options) {
    if (tab)
        tab.addHeight(options.diff);
    if (accordion && options.middleHeight - 24 > 0)
        accordion.setHeight(options.middleHeight - 24);
}
function f_addTab(tabid, text, url) {
    tab.addTabItem({ tabid: tabid, text: text, url: url });
}



function AutoScroll(obj) {
    $(obj).find("ul:first").animate({
        marginTop: "-25px"
    }, 500, function () {
        $(this).css({ marginTop: "0px" }).find("li:first").appendTo(this);
    });
}
$(document).ready(function () {
    setInterval('AutoScroll("#scrollDiv")', 1000)
});  