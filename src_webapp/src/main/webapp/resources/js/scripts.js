$(document).ready(function(){

    $("#sidebar_btn").click(function(){
        if($("#check").prop("checked") != true) {
            $("#content").addClass("bigger");
        } else {
            $("#content").removeClass("bigger");
        }
    });

})

