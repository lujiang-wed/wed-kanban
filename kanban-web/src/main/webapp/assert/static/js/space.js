/**
 * Created by wyyangyang1 on 2014/12/30.
 */

function addNewSpace(){
    $("#alertText").html('');
    $("#alertText").attr("style","display: none");
    //这个是获取input数据，$("#inputUser")表示获取id为inputUser的input
    var inputSpaceName=$("#inputSpaceName").val();
    var inputSpacePin=$("#inputSpacePin").val();
    var inputParentSpacePin=$("#inputParentSpacePin").val();
    var inputSpaceDes=$("#inputSpaceDes").val();
    if(inputSpaceName=='' || inputSpacePin==''|| inputSpaceDes==''){
        $("#alertText").html('"空间名称"或"唯一标识"或"空间描述"不能为空');
        $("#alertText").attr("style","");
        return;
    }

    var url="/space/addNewSpace";


    $.ajax({
            url:url,
            type:"GET",
            data:{spaceName:inputSpaceName,spacePin:inputSpacePin,parentSpacePin:inputParentSpacePin,spaceDes:inputSpaceDes},
            success:function(data){
                //这里是执行程序之后的操作
                alert(data) ;
            }
        }
    );

}
function alterSpace(){

    $("#alertTextModify").html('');
    $("#alertTextModify").attr("style","display: none");
    //这个是获取input数据，$("#inputUser")表示获取id为inputUser的input
    var spaceId=$("#spaceId").val();
    var inputSpaceName=$("#spaceName").val();
    var inputSpacePin=$("#spacePin").val();
    var inputParentSpacePin=$("#parentSpacePin").val();
    var inputSpaceDes=$("#spaceDes").val();
    if(inputSpaceName=='' || inputSpacePin==''|| inputSpaceDes==''){
        $("#alertText").html('"空间名称"或"唯一标识"或"空间描述"不能为空');
        $("#alertText").attr("style","");
        return;
    }

    var url="/space/alterSpace";


    $.ajax({
            url:url,
            type:"GET",
            data:{spaceId:spaceId,spaceName:inputSpaceName,spacePin:inputSpacePin,parentSpacePin:inputParentSpacePin,spaceDes:inputSpaceDes},
            success:function(data){
                //这里是执行程序之后的操作
                alert(data) ;
            }
        }
    );

}