console.log("Hi there");

const toggleSidebar = () => {

    if($(".sidebar").is(":visible")) {
        $(".sidebar").css("display","none");
        $(".content").css("margin-left","0%");
        $(".bars").css("dispaly","block")

    }
    else {
        $(".sidebar").css("display","block");
        $(".content").css("margin-left","30%");
        $(".bars").css("dispaly","none")

    }
};

const toggleInfobar = () => {

    if($(".info-bar").is(":visible")) {
        $(".info-bar").css("display","none");
    }
    else {
        $(".info-bar").css("display","block");
    }
}