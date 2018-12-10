/**
 * Created by wyliyue on 2014/12/22.
 */
$(document).ready(function () {
    $("#customedBox div").bind("click", function () {
        var id = this.id;
        var title = $(this).attr("name");
        console.log("id:" + id);
        var tr = document.createElement("tr");
        var name = document.createElement("td");
        var type = document.createElement("td");
        var defaultValue = document.createElement("td");
        var selectValues = document.createElement("td");
        var operate = document.createElement("td");
        name.appendChild(getInput("name", "input-small"));
        name.appendChild(getInput("id", "input-small"));
        type.appendChild(document.createTextNode(title));
        defaultValue.appendChild(getInput("defaultValue", "input-small"));
        selectValues.appendChild(getInput("selectValues", "input-small"));
        // operate.appendChild(document.createElement("button")) ;
        tr.appendChild(name);
        tr.appendChild(type);
        tr.appendChild(defaultValue);
        tr.appendChild(selectValues);
        tr.appendChild(setOperator(operate));
        console.log(tr);


            $("#templeteTable  tr:last").after(tr);


    })

    function getInput( name, classValue){
        var input= document.createElement("input");
        input.setAttribute("name",name);
        input.setAttribute("class",classValue);
        return input;
    }

    function getHiddenInput( name, classValue){
        var input= document.createElement("input");
        input.setAttribute("name",name);
        input.setAttribute("class",classValue);
        input.setAttribute("type","hidden");
        return input;
    }



    function setOperator(td){
       // td.innerHTML="[<a href=\"#editModel\" data-toggle=\"modal\">编辑</a>][<a href=\"#delete\">删除</a>]";
        var saveButton=document.createElement("input")
        saveButton.setAttribute("class","btn");
        saveButton.setAttribute("value","保存");
        saveButton.setAttribute("type","button");
        $(saveButton).bind("click",function(){
            saveTem(this) ;
        })

        var deleteButton=document.createElement("input");
        deleteButton.setAttribute("class","btn");
        deleteButton.setAttribute("value","删除");
        deleteButton.setAttribute("type","button");
        $(deleteButton).bind("click",function(){
            deleteTem(this)  ;
        })

        td.appendChild(saveButton);
        td.appendChild(deleteButton);
        return td;
    }


})

function saveTem(btn){
    alert("save");
    var tr=$(btn).parent("td").parent("tr");
    var label=tr.children("td:eq(0)").children("input").val();
    var type=tr.children("td:eq(1)").text();
    var defaultValue=tr.children("td:eq(2)").children("input").val();
    var inputOption=tr.children("td:eq(3)").children("input").val();
    var templateId=$("#templateId").val();
    alert("label="+label+"\ttype="+type+"\tdefaultValue="+defaultValue+"\tselectValues="+selectValues) ;
    var url="/template/attr/add";
    $.ajax({
            url:url,
            type:"POST",
            data:{templateId:templateId,attrLabel:label,inputOption:inputOption,defaultValue:defaultValue,inputType:type},
            success:function(data){
                //这里是执行程序之后的操作
                alert(data) ;
            }
        }
    );



}

function deleteTem(btn){
    alert("delete");

}