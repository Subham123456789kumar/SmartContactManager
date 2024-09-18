console.log("hello java script")
;

//sidebar
const toggleSidebar = () => {
    if($(".sidebar").is(":visible")) {
        //true
        //band karna hai
        $(".sidebar").css("display", "none");
        $(".content").css("margin-left", "0%");
		console.log("side bar is visiable")

		
    }else{
        //false
        //show karna hai
        $(".sidebar").css("display", "block");
        $(".content").css("margin-left", "20%");
		console.log("sidebar is block")

    }
};