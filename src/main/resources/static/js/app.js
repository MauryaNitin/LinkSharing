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



    $(document).on("click", "[id^=deleteTopic]", function (event) {
        event.preventDefault();
        var topicId = $(this).attr("id").replace("deleteTopic-", "");
        var url = '/topic/' + topicId + '/delete'
        $("html").load(url);
        showAlert();
    })

    $(document).on("change", "[id^=topicSeriousness]", function (event) {
        event.preventDefault();
        var topicId = $(this).attr("id").replace("topicSeriousness-", "");
        var url = '/topic/' + topicId + '/seriousness'
        $("html").load(url,{
            seriousness : $("#topicSeriousness-" + topicId).val()
        });
        showAlert();
    })
});



function unsubscribeTopic(topic){
    var topicId = topic.getAttribute("data-topicId");
    var url  = "/topic/" + topicId + "/unsubscribe";
    $("html").load(url);
    showAlert();
}

function subscribeTopic(topic){
    var topicId = topic.getAttribute("data-topicId");
    var url  = "/topic/" + topicId + "/subscribe";
    $("html").load(url);
    showAlert();
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

    var data = {
        name: $("#editTopicName-" + topicId).val(),
        visibility: $("#editTopicVisibility-" + topicId + " > select").val()
    };

    var url  = "/topic/" + topicId + "/edit";
    $("html").load(url,data);
    showAlert();
})

$(document).on("click", "[id^=activateUser]", function (event) {
    event.preventDefault();
    var userId = $(this).attr("id").replace("activateUser-","")
    console.log($(this));
    var url = "/users/activate"
    $("html").load(url, {
        userId : userId
    });
    showAlert();
})

$(document).on("click", "[id^=deactivateUser]", function (event) {
    event.preventDefault();
    var userId = $(this).attr("id").replace("deactivateUser-", "")
    var url = "/users/deactivate"
    $("html").load(url, {
        userId : userId
    });
    showAlert();
})


// POST EDITING

$(document).on("click", "[id^=saveEditPost]", function (event) {
    event.preventDefault();
    postId = $(this).attr("id").replace("saveEditPost-", "");
    $("#showPostDescription-" + postId).toggle();
    $("#editPostDescription-" + postId).toggle();
    $("#closeEditPost-" + postId).toggle();
    $("[data-editPost=editPost-" + postId + "]").toggle();
    $(this).toggle();

    var data = {
        description: $("#editPostDescription-" + postId).val(),
    };

    var url =  "/resource/" + postId + "/edit"
    $("html").load(url,data);
    showAlert();
})


$(document).on("click", "[data-editPost^=editPost]", function (event) {
    event.preventDefault();
    postId = $(this).attr("data-editPost").replace("editPost-", "");
    $("#showPostDescription-" + postId).toggle();
    $("#editPostDescription-" + postId).toggle();
    $("#closeEditPost-" + postId).toggle();
    $("#saveEditPost-" + postId).toggle();
    $(this).toggle();
});

$(document).on("click", "[id^=closeEditPost]", function (event) {
    event.preventDefault();
    postId = $(this).attr("id").replace("closeEditPost-", "");
    $("#showPostDescription-" + postId).toggle();
    $("#editPostDescription-" + postId).toggle();
    $("#saveEditPost-" + postId).toggle();
    $("[data-editPost^=editPost-" + postId + "]").toggle();
    $(this).toggle();
})

$(document).on("click", "[data-deletePost^=deletePost]", function (event) {
    event.preventDefault();
    var postId = $(this).attr("data-deletePost").replace("deletePost-", "");
    var url = '/resource/' + postId + '/delete'
    $("html").load(url);
    showAlert();
})

$(document).on("click", "[id^=read]", function (event) {
    event.preventDefault();
    var postId = $(this).attr("id").replace("read-", "");
    var url = '/resource/markAsRead'
    $("html").load(url, {
        resourceId : postId
    });
    showAlert();
})

function showAlert() {
    setTimeout(function () {
        $("[data-dismiss^='alert']").click();
    }, 2000)
}


