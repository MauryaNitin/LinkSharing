$(document).ready(function () {

    $("#search").on("keyup", function () {
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
                $("#search-dropdown").replaceWith("<a class='dropdown-item' href='#'>" + response.topics[i].name + "</a>");
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

});


function unsubscribeTopic(topic){
    var topicId = topic.getAttribute("data-topicId");
    var url  = "/topic/" + topicId + "/unsubscribe";
    $("html").load(url);
}

function subscribeTopic(topic){
    var topicId = topic.getAttribute("data-topicId");
    var url  = "/topic/" + topicId + "/subscribe";
    $("html").load(url);
}

$(document).on("click", ".edit-btn", function (event) {
    event.preventDefault();
    $(".topicName").hide();
    $(".editTopicName").show();
    $(".topicVisibility").hide();
    $(".editTopicVisibility").show();
    $(".edit-topic-close").show();
    $(".edit-topic-save").show();
    $(this).hide();
})

$(document).on("click", ".edit-topic-save", function (event) {
    event.preventDefault();
    $(".topicName").toggle();
    $(".editTopicName").toggle();
    $(".topicVisibility").toggle();
    $(".editTopicVisibility").toggle();
    $(".edit-topic-close").toggle();
    $(".edit-topic-save").toggle();
    $(".edit-btn").show();
})

$(document).on("click", ".edit-topic-close", function (event) {
    event.preventDefault();
    $(".topicName").toggle();
    $(".editTopicName").toggle();
    $(".topicVisibility").toggle();
    $(".editTopicVisibility").toggle();
    $(".edit-topic-save").toggle();
    $(".edit-btn").show();
    $(".edit-topic-close").toggle();
})

$(document).on("click", "[data-target*=Modal]", function () {
    var element = $(this).attr('data-target');
    console.log(element + this);
    // $().modal('show');
})

