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


});