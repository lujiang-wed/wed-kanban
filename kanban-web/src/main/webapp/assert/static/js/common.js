$(document).ready(function(){
    $("#create1,#create2").click(function(){
        $("#inputName,#inputSign,#inputDes").val("");
        $(".btn-submit").removeClass("hide");
    })

    /*添加空间权限 保存*/
    $("button[name='addUserFormSub']").click(function(){
        var user = $("#user").val();
        var auth = $("#auth").val();
        alert("user="+user+";auth="+auth);
        $("#addUser").modal('hide');
    })

    $("a[href='#delete'],#deleteBtn").click(function(){
        confirm("确定删除？")
    })

/*    $("#spaceList").click(function(){
        $(".second-list").toggle("slow");
    })
    $("#list1").click(function(){
        $("#th-list1").toggle("slow");

    })*/





})
