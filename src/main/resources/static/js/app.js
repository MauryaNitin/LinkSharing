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

    $(document).on("click", "[data-target$=Modal]", function () {
        var el = $(this).attr("data-target");
        $(el).modal('toggle');
    })

    $(document).on("click", "[id^=saveEditTopic]", function (event) {
        event.preventDefault();
        topicId = $(this).attr("id").replace("saveEditTopic-", "");
        $("#showTopicName-" + topicId).toggle();
        $("#editTopicName-" + topicId).toggle();
        $("#showTopicVisibility-" + topicId).toggle();
        $("#editTopicVisibility-" + topicId).toggle();
        $("#closeEditTopic-" + topicId).toggle();
        $("[data-editTopic=editTopic-" + topicId + "]").toggle();
        $(this).toggle();

        var req = {
            name: $("#editTopicName-" + topicId).val(),
            visibility: $("#editTopicVisibility-" + topicId + " > select").val()
        };

        $.ajax({
            method: "POST",
            contentType: "application/json",
            url : "/topic/" + topicId + "/edit",
            dataType : 'json',
            data: JSON.stringify(req)
        }).done(function (response) {
            location.reload();
        });
    })

    $(document).on("click", "[id^=deleteTopic]", function (event) {
        event.preventDefault();
        var topicId = $(this).attr("id").replace("deleteTopic-", "");
        var url = '/topic/' + topicId + '/delete'
        $("html").load(url);
    })

    $(document).on("change", "[id^=topicSeriousness]", function (event) {
        event.preventDefault();
        var topicId = $(this).attr("id").replace("topicSeriousness-", "");
        var url = '/topic/' + topicId + '/seriousness'
        $("html").load(url,{
            seriousness : $("#topicSeriousness-" + topicId).val()
        });
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

$(document).on("click", "[data-editTopic^=editTopic]", function (event) {
    event.preventDefault();
    topicId = $(this).attr("data-editTopic").replace("editTopic-", "");
    $("#showTopicName-" + topicId).toggle();
    $("#editTopicName-" + topicId).toggle();
    $("#showTopicVisibility-" + topicId).toggle();
    $("#editTopicVisibility-" + topicId).toggle();
    $("#closeEditTopic-" + topicId).toggle();
    $("#saveEditTopic-" + topicId).toggle();
    $(this).toggle();
});

$(document).on("click", "[id^=closeEditTopic]", function (event) {
    event.preventDefault();
    topicId = $(this).attr("id").replace("closeEditTopic-", "");
    $("#showTopicName-" + topicId).toggle();
    $("#editTopicName-" + topicId).toggle();
    $("#showTopicVisibility-" + topicId).toggle();
    $("#editTopicVisibility-" + topicId).toggle();
    $("#saveEditTopic-" + topicId).toggle();
    $("[data-editTopic=editTopic-" + topicId + "]").toggle();
    $(this).toggle();
})





