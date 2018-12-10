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



})


function leftPad(strToPad, length, padChar) {
    var result = strToPad;

    if (strToPad.length < length) {
        return leftPad(padChar + strToPad, length, padChar);
    } else {
        return result;
    }
}