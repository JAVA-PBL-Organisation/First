console.log("Hi there");

const toggleSidebar = () => {

    if($(".sidebar").is(":visible")) {
        $(".sidebar").css("display","none");
        $(".content").css("margin-left","0%");
        $("#dashboardToggle").css("dispaly","block")

    }
    else {
        $(".sidebar").css("display","block");
        $(".content").css("margin-left","30%");
        $("#dashboardToggle").css("dispaly","none")

    }
};