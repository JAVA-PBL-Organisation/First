

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

const toggleInfobar = () => {

    if($(".info-bar").is(":visible")) {
        $(".info-bar").css("display","none");
    }
    else {
        $(".info-bar").css("display","block");
    }
}
const search = () => {
   let query= $("#search-input").val();

   if(query == ""){
       $(".search-result").hide();

   }else{
   console.log(query);
// sending request to server

let url =`http://localhost:8080/search/${query}`;
fetch(url).then((response) => {
	return response.json();
}).then((data) =>{
	//console.log(data);
	
	let text=`<div class='list-group'>`;
	
	data.forEach((favorite)=>{
		
		text +=`<a href='/user/${favorite.cId}/favorite' class='list-group-item list-group-item-action'> ${favorite.name}</a>`
	});
	
	text += `</div>`;
	
	$(".search-result").html(text);
	 $(".search-result").show();
});


  
   }

};