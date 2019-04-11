$(document).ready(function () {

    $("#search").on("keyup", function () {
        console.log($("#search").val());
        $.ajax({
                method: "POST",
                url : "/search",
                data: {
                    query: $("#search").val()
                }
            }
        ).done(function (response) {
            var topicsList = response.topics;
            console.log(response.topics)
            console.log(response.topics[0].name)
            var topicResults = [];
            for(var i = 0 ; i < topicsList.length; i++){
                topicResults.push(response.topics[i].name)
                $("#search-dropdown").append("<a class='dropdown-item' href='#'>" + response.topics[i].name + "</a>");
            }
        });
    });

    $("#forgotPassword").on("click", function () {
        var username = $("#username-login").val();
        if(!username){
            alert("Please Enter a username");
            return;
        }
        $.ajax({
                method: "POST",
                url : "/forgotPassword",
                data: {
                    username: username
                }
            }
        ).done(function (response) {
            if(response){
                $("#popup").append(response);
            }
        })
    })

    function unsubscribeTopic(){
        event.preventDefault();
        console.log(element);
        var topicId = $("a").data("topicid").val();
        console.log(topicId);
        // $.ajax({
        //         method: "GET",
        //         url : "/topic/" + topicId + "/unsubscribe",
        //     }
        // ).done(function (response) {
        //     if(response){
        //         $("#alerts").append(response);
        //     }
        // })
    }

    function subscribeTopic(){
        element.preventDefault();
        console.log(element);
        var topicId = $("a").data("topicid").val();
        console.log(topicId);
        // var topicId = $(element).attr("data-topicId");
        // $.ajax({
        //         method: "GET",
        //         url : "/topic/" + topicId + "/subscribe",
        //     }
        // ).done(function (response) {
        //     if(response){
        //         $("#alerts").append(response);
        //     }
        // })
    }
});