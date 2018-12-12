$(document).ready(function () {
    //var templateTabel = document.getElementById("templeteTable")//返回控件 templeteTable
    //var trList = templateTabel.getElementsByTagName("tr");
    //for (var i = 0; i < trList.length; i++)
    //{
    //    console.log(trList[i].cells[0].innerHTML);
    //    console.log("------------");
    //    //console.log()
    //}
    var attrSelects =  document.getElementsByName("attrType");//返回控件

    var parentAttrList = document.getElementsByName("parentTypeLabel");
    var attrList = document.getElementsByName("typeLabel");
    //  console.log(data[0].attrLabel);
    //$item in $template.attrList
    //for(var item in parentAttrList)
    //{
    //    console.log("type:"+item.inputType);
    //    if( item.inputType =="number")
    //    {
    //        var parentAttrOption = document.createElement("option");//创建一个控件
    //        parentAttrOption.value =  item.attrLabel;
    //        parentAttrOption.text = item.attrLabel;
    //        for(var j = 0;j<attrSelects.length ; j++)
    //        {
    //            attrSelects[j].appendChild(parentAttrOption);
    //        }
    //    }
    //
    //}


    initAttrSelect(attrSelects);
    for(var j = 0;j<attrSelects.length ; j++)
    {
        var attrSelections =attrSelects[j].getElementsByTagName("option");//返回一个Option列
        for(var i = 0; i < attrSelections.length; i++) {
            if(attrSelections[i].value == attrSelects[j].id) {
                attrSelections[i].setAttribute("selected","selected");//被选中
            } else {
                attrSelections[i].removeAttribute("selected");
            }
        }

    }

    $("#customedBox div").bind("click", function () {
        var id = this.id;
        var title = $(this).attr("name");
        console.log("id11:" + id +"sprint:" );


        var tr = document.createElement("tr");
        var attrLabelIndex = document.createElement("td");
        var name = document.createElement("td");
        var type = document.createElement("td");
        var defaultValue = document.createElement("td");
        var selectValues = document.createElement("td");
        var operate = document.createElement("td");
        var isShow = document.createElement("td");
        attrLabelIndex.appendChild(getInput("attrLabelIndex", "input-small", false));
        name.appendChild(getInput("name", "input-small", false));
        name.appendChild(getInput("id", "input-small" ,true));
        type.appendChild(document.createTextNode(title));
        defaultValue.appendChild(getInput("defaultValue", "input-small", false));
        selectValues.appendChild(getInput("selectValues", "input-small", false, id));
        isShow.appendChild(getInput("isShow", "input-small", false));
        // operate.appendChild(document.createElement("button")) ;
        tr.appendChild(attrLabelIndex);
        tr.appendChild(name);
        tr.appendChild(type);
        tr.appendChild(defaultValue);
        tr.appendChild(selectValues);
        tr.appendChild(setOperator(operate));
        tr.appendChild(isShow);
        console.log(tr);
        $("#templeteTable  tr:last").after(tr);
        $('#datetimepicker').datetimepicker({
            format:'yyyy/mm/dd',
            language:'zh-CN',
            autoclose:true,
            todayBtn: 'linked',
            minView:"month"

        });
    })
    $("#upDown div").bind("click", function () {
        var rowCount = document.getElementsByName("upDownRow");
        if(rowCount.length > 1)
        {
            alert("加减属性最多配置两条，请删除其他属性后配置！")
        }
        else {
            var id = this.id;
            //var title = $(this).attr("name");
            console.log("id11:" + id + "sprint:");

            var trr = document.createElement("tr");
            trr.setAttribute("sortIndex","upDownRow");

            var tr = document.createElement("tr");
            tr.setAttribute("name","upDownRow");
            var nameTd = document.createElement("td");
            var name = document.createElement("select");
            name.setAttribute("style", "width: 150px")
            var upTypeTd = document.createElement("td");
            var upType = document.createElement("select");
            upType.setAttribute("style", "width: 100px")
            var stepValue = document.createElement("td");
            stepValue.innerHTML = 1;
            var operate = document.createElement("td");
            var selectList = [];
            selectList.push(name);
            initAttrSelect(selectList);
            initOpSelect(upType);
            nameTd.appendChild(name);
            upTypeTd.appendChild(upType);
            tr.appendChild(nameTd);
            tr.appendChild(upTypeTd);
            tr.appendChild(stepValue);
            //tr.appendChild(selectValues);
            tr.appendChild(setUpdownOperator(operate));
            console.log(tr);
            $("#templeteTable2  tr:last").after(tr);
            $('#datetimepicker').datetimepicker({
                format: 'yyyy/mm/dd',
                language: 'zh-CN',
                autoclose: true,
                todayBtn: 'linked',
                minView: "month"
            });
        }
    })

    $("#opDown div").bind("click", function () {
        var rowCount = document.getElementsByName("opDownRow");
        if(rowCount.length > 0 )
        {
            alert("加减属性操作最多配置一条，请删除其他属性后配置！")
        }
        else {
            var id = this.id;
            //var title = $(this).attr("name");
            console.log("id11:" + id + "sprint:");



            var tr = document.createElement("tr");
            tr.setAttribute("name","opDownRow");
            var nameTd = document.createElement("td");
            var name = document.createElement("select");
            name.setAttribute("style", "width: 150px")
            var name1 = document.createElement("select");
            name1.setAttribute("style", "width: 150px")
            var opTypeTd = document.createElement("td");
            var opTypeTd1 = document.createElement("td");
            var opType = document.createElement("select");
            opType.setAttribute("style", "width: 100px")

            var operate = document.createElement("td");
            var selectList = [];
            selectList.push(name);
            initAttrSelect(selectList);
            var operate1 = document.createElement("td");
            var selectList1 = [];
            selectList1.push(name1);
            initAttrSelect(selectList1);
            //initAttrSelect(selectList);
            initOpSelect(opType);
            nameTd.appendChild(name);
            opTypeTd.appendChild(opType);
            opTypeTd1.appendChild(name1);
            tr.appendChild(nameTd);
            tr.appendChild(opTypeTd1);
            tr.appendChild(opTypeTd);
            //  tr.appendChild(name1);
            //  tr.appendChild(selectValues);
            tr.appendChild(setOpdownOperator(operate));
            console.log(tr);
            $("#templeteTable3  tr:last").after(tr);
            $('#datetimepicker').datetimepicker({
                format: 'yyyy/mm/dd',
                language: 'zh-CN',
                autoclose: true,
                todayBtn: 'linked',
                minView: "month"
            });
        }
    })

    function initOpSelect(select)
    {
        var Option1 = document.createElement("option");
        Option1.value = "0";
        Option1.text =  "加";
        select.appendChild(Option1);
        var Option2 = document.createElement("option");
        Option2.value = "1";
        Option2.text =  "减";
        select.appendChild(Option2);
        var Option3 = document.createElement("option");
        Option3.value = "0,1";
        Option3.text =  "加减";
        select.appendChild(Option3);
    }
    function initAttrSelect(selectList)
    {
        for(var i = 0;i<parentAttrList.length;i++)
        {
            var value =  parentAttrList[i].value;
            var parentAttrOptionList = [];
            for(var j = 0;j<selectList.length ; j++)//创建多个控件添加到select
            {
                var parentAttrOption = document.createElement("option");
                parentAttrOption.value = value;
                parentAttrOption.text =  value;
                parentAttrOptionList.push(parentAttrOption);
                //attrSelects[j].appendChild(parentAttrOption);
            }
            for(var j = 0;j<selectList.length ; j++)
            {
                selectList[j].appendChild(parentAttrOptionList[j]);
            }
        }
        for(var i = 0;i<attrList.length;i++)
        {
            var value =  attrList[i].value;
            var attrOptionList = [];
            for(var j = 0;j<selectList.length ; j++)//创建多个控件添加到select
            {
                var attrOption = document.createElement("option");
                attrOption.value = value;
                attrOption.text =  value;
                attrOptionList.push(attrOption);
                //attrSelects[j].appendChild(parentAttrOption);
            }
            for(var j = 0;j<selectList.length ; j++)
            {
                selectList[j].appendChild(attrOptionList[j]);
            }
        }
    }
    function getInput( name, classValue, hidden, id){
        var input= document.createElement("input");
        if(id == "date"){
            input.setAttribute("id","datetimepicker");
            input.setAttribute("type","text");

        }
        input.setAttribute("class",classValue);
        input.setAttribute("name",name);
        if(hidden === true){
            input.setAttribute("type","hidden");
        }
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

    function setUpdownOperator(td){
        // td.innerHTML="[<a href=\"#editModel\" data-toggle=\"modal\">编辑</a>][<a href=\"#delete\">删除</a>]";
        var saveButton=document.createElement("input")
        saveButton.setAttribute("class","btn");
        saveButton.setAttribute("value","保存");
        saveButton.setAttribute("type","button");
        $(saveButton).bind("click",function(){
            saveOpTem(this) ;
        })

        var deleteButton=document.createElement("input");
        deleteButton.setAttribute("class","btn");
        deleteButton.setAttribute("value","删除");
        deleteButton.setAttribute("type","button");
        $(deleteButton).bind("click",function(){
            deleteOpTem(this)  ;
        })

        td.appendChild(saveButton);
        td.appendChild(deleteButton);
        return td;
    }

    function setOpdownOperator(td){
        // td.innerHTML="[<a href=\"#editModel\" data-toggle=\"modal\">编辑</a>][<a href=\"#delete\">删除</a>]";
        var saveButton=document.createElement("input")
        saveButton.setAttribute("class","btn");
        saveButton.setAttribute("value","保存");
        saveButton.setAttribute("type","button");
        $(saveButton).bind("click",function(){
            saveOpTem1(this);
            saveButton.setAttribute("disabled", "true");
        })

        var deleteButton=document.createElement("input");
        deleteButton.setAttribute("class","btn");
        deleteButton.setAttribute("value","删除");
        deleteButton.setAttribute("type","button");
        $(deleteButton).bind("click",function(){
            deleteOpTem1(this) ;

        })

        td.appendChild(saveButton);
        td.appendChild(deleteButton);
        return td;
    }



})

function saveTem(btn){
    var tr=$(btn).parent("td").parent("tr");
    var label=tr.children("td:eq(1)").children("input").val();
    //console.log(label);
    var id=tr.children("td:eq(1)").children("input:eq(1)").val();
    var type=tr.children("td:eq(2)").text();
    var defaultValue=tr.children("td:eq(3)").children("input").val();
    var inputOption=tr.children("td:eq(4)").children("input").val();
    var templateId=$("#templateId").val();
    if(id==null||id==""){//新建的属性ID为空
        //add
        var url="/template/attr/add";
        $.ajax({
                url:url,
                type:"POST",
                data:{templateId:templateId,attrLabel:label,inputOption:inputOption,defaultValue:defaultValue,inputType:type},
                dataType:"json",
                success:function(data){
                    //这里是执行程序之后的操作
                    var id=data.data.id;
                    tr.children("td:eq(0)").children("input:eq(1)").val(id);
                    alert("添加成功.id为："+id);
                }
            }
        );

    }else{
        //update
        var url="/template/attr/update";
        $.ajax({
                url:url,
                type:"POST",
                data:{templateId:templateId,attrLabel:label,inputOption:inputOption,defaultValue:defaultValue,inputType:type,id:id},
                dataType:"json",
                success:function(data){
                    //这里是执行程序之后的操作
                    if(data.code==1){
                        alert("修改成功");
                    }else{
                        alert("修改失败") ;
                    }
                }
            }
        );
    }
    location.reload();
}
function saveOpTem(btn){//更新操作数据库
    var thisTr=$(btn).parent("td").parent("tr");
    var label=thisTr.children("td:eq(0)").children("select").find("option:selected").val();
    var templateTabel = document.getElementById("templeteTable")//返回控件 templeteTable
    var trList = templateTabel.getElementsByTagName("tr");
    var selectlist = document.getElementsByName("attrType");
    for(var i = 0;i < (selectlist.length);i++)
    {
        //alert(selectlist.length);
        //alert(selectlist[i].selectedIndex);
        //alert(selectlist[i].childNodes[(selectlist[i].selectedIndex+1)].innerHTML);
        if(selectlist[i].childNodes[(selectlist[i].selectedIndex+1)].innerHTML == label)
        {
            alert("相同属性不能重复添加!");
            return;
        }
    }

    var line = 0;
    for (; line < trList.length; line++)
    {
        console.log(trList[line].cells[1].innerHTML);
        var text;
        if(trList[line].cells[1].childNodes.length > 1)
            text =  trList[line].cells[1].firstChild.value;
        else
            text = trList[line].cells[1].innerHTML
        console.log(text);
        //  var tr = $("#templeteTable tbody:eq(0) tr:eq(8)");
        //  var id = tr.children("td:eq(0)").children("input:eq(0)").val();
        if(label == text) {
            console.log(trList[line].cells[1].innerHTML);
            var tr = $("#templeteTable tr:eq("+line+")");
            var id = tr.children("td:eq(1)").children("input:eq(1)").val();
            //var id =  $("#templeteTable tbody:eq(0) tr:eq(line) td:eq(0) input:eq(1)").val();//.children("input:eq(1)").val();
            if (id == undefined)
                alert("父模板属性无法修改");
            else {
                var type = tr.children("td:eq(2)").text();
                var defaultValue = tr.children("td:eq(3)").children("input").val();
                //var inputOption = tr.children("td:eq(3)").children("input").val();
                var inputOptionValue = thisTr.children("td:eq(1)").children("select").find("option:selected").val();

                var templateId = $("#templateId").val();
                var url="/template/attr/update";//这里的操作类型只有update
                $.ajax({
                        url:url,
                        type:"POST",
                        data:{templateId:templateId,attrLabel:label,inputOption:inputOptionValue,defaultValue:defaultValue,inputType:type,id:id},
                        dataType:"json",
                        success:function(data){
                            //这里是执行程序之后的操作
                            if(data.code==1){
                                alert("修改成功");
                                window.location.reload();
                            }else{
                                alert("修改失败") ;
                            }
                        }
                    }
                );

            }
            break;
        }

        //console.log()
    }



}

function saveOpTem1(btn){        //panting

    var tr=$(btn).parent("td").parent("tr");
    var label1=tr.children("td:eq(0)").children("select").find("option:selected").val();
    var label2=tr.children("td:eq(1)").children("select").find("option:selected").val();

    var templateTabel = document.getElementById("templeteTable")//返回控件 templeteTable
    var trList = templateTabel.getElementsByTagName("tr");
    var line = 0;
    for (; line < trList.length; line++) {
        console.log(trList[line].cells[0].innerHTML);
        var text;
        if (trList[line].cells[0].childNodes.length > 1)
            text = trList[line].cells[0].firstChild.value;
        else
            text = trList[line].cells[0].innerHTML;
        console.log(text);
        //  var tr = $("#templeteTable tbody:eq(0) tr:eq(8)");
        //  var id = tr.children("td:eq(0)").children("input:eq(0)").val();
        if (label1 == text) {
            console.log(trList[line].cells[0].innerHTML);
            var tr = $("#templeteTable tr:eq(" + line + ")");
            var label1_id = tr.find('input[name="id"]').val();//此处获得ID
        }
    }
    var templateTabel = document.getElementById("templeteTable")//返回控件 templeteTable
    var trList = templateTabel.getElementsByTagName("tr");
    for (line = 0; line < trList.length; line++) {
        console.log(trList[line].cells[0].innerHTML);
        var text;
        if (trList[line].cells[0].childNodes.length > 1)
            text = trList[line].cells[0].firstChild.value;
        else
            text = trList[line].cells[0].innerHTML;
        console.log(text);
        //  var tr = $("#templeteTable tbody:eq(0) tr:eq(8)");
        //  var id = tr.children("td:eq(0)").children("input:eq(0)").val();
        if (label2 == text) {
            console.log(trList[line].cells[0].innerHTML);
            var tr = $("#templeteTable tr:eq(" + line + ")");
            var label2_id = tr.find('input[name="id"]').val();//此处获得ID
            //var label1_id  =  $("#templeteTable tbody:eq(0) tr:eq(line) td:eq(0) input:eq(1)").val();//.children("input:eq(1)").val();
        }
    }
    tr=$(btn).parent("td").parent("tr");
    var inputOption=tr.children("td:eq(2)").children("select").find("option:selected").text();
    var inputOptionValue=tr.children("td:eq(2)").children("select").find("option:selected").val() + ":" + label1_id + ":"+ label2_id;
    //var inputOptionValue = "" ;
    var label=  label1 + ":" + inputOption + ":" + label2;
    //var inputOptionValueJson = label1_id + inputOptionValue + label2_id;
    var id = null;
    var type = "execute";
    var defaultValue = "";


    var templateId=$("#templateId").val()

    if(id==null||id==""){//新建的属性ID为空
        //add
        var url="/template/attr/add";
        $.ajax({
                url:url,
                type:"POST",
                data:{templateId:templateId,attrLabel:label,inputOption:inputOptionValue,defaultValue:defaultValue,inputType:type},
                dataType:"json",
                success:function(data){
                    //这里是执行程序之后的操作
                    var id=data.data.id;
                    tr.children("td:eq(0)").children("input:eq(1)").val(id);
                    alert("添加成功.id为："+id);
                }
            }
        );

    }
    location.reload();
    //else{
    //    //update
    //    var url="/template/attr/update";
    //    $.ajax({
    //            url:url,
    //            type:"POST",
    //            data:{templateId:templateId,attrLabel:label,inputOption:inputOption,defaultValue:defaultValue,inputType:type,id:id},
    //            dataType:"json",
    //            success:function(data){
    //                //这里是执行程序之后的操作
    //                if(data.code==1){
    //                    alert("修改成功");
    //                }else{
    //                    alert("修改失败") ;
    //                }
    //            }
    //        }
    //    );
    //}
}
function deleteTem(btn){
    var tr=$(btn).parent("td").parent("tr");
    var id=tr.children("td:eq(0)").children("input:eq(1)").val();
    var templateId=$("#templateId").val();
    alert(id);
    var url="/template/attr/del"    ;
    $.ajax({
            url:url,
            type:"POST",
            data:{id:id, templateId:templateId},
            dataType:"json",
            async : false,
            success:function(data){
                //这里是执行程序之后的操作
                if(data.code==1){
                    alert("删除成功");
                }else{
                    alert("删除失败") ;
                }
            }
        }
    );
    tr.remove();
    location.reload();
}



function deleteOpTem(btn){
    var thisTr=$(btn).parent("td").parent("tr");
    var label=thisTr.children("td:eq(0)").children("select").find("option:selected").val();
    var templateTabel = document.getElementById("templeteTable")//返回控件 templeteTable
    var trList = templateTabel.getElementsByTagName("tr");
    var line = 0;
    for (; line < trList.length; line++)
    {
        console.log(trList[line].cells[1].innerHTML);
        var text;
        if(trList[line].cells[1].childNodes.length > 1)
            text =  trList[line].cells[1].firstChild.value;
        else
            text = trList[line].cells[1].innerHTML
        console.log(text);
        //  var tr = $("#templeteTable tbody:eq(0) tr:eq(8)");
        //  var id = tr.children("td:eq(0)").children("input:eq(0)").val();
        if(label == text) {
            console.log(trList[line].cells[1].innerHTML);
            var tr = $("#templeteTable tr:eq("+line+")");
            var id = tr.children("td:eq(1)").children("input:eq(1)").val();
            //var id =  $("#templeteTable tbody:eq(0) tr:eq(line) td:eq(0) input:eq(1)").val();//.children("input:eq(1)").val();
            if (id == undefined)
                alert("父模板属性无法修改");
            else {
                var type = tr.children("td:eq(2)").text();
                var defaultValue = tr.children("td:eq(3)").children("input").val();
                //var inputOption = tr.children("td:eq(3)").children("input").val();
                //var inputOptionValue = thisTr.children("td:eq(1)").children("select").find("option:selected").val();
                var inputOption = "";//更新为空
                var templateId = $("#templateId").val();
                var url="/template/attr/update";//这里的操作类型只有update
                $.ajax({
                        url:url,
                        type:"POST",
                        data:{templateId:templateId,attrLabel:label,inputOption:inputOption,defaultValue:defaultValue,inputType:type,id:id},
                        dataType:"json",
                        success:function(data){
                            //这里是执行程序之后的操作
                            if(data.code==1){
                                alert("修改成功");
                                thisTr.remove();
                            }else{
                                alert("修改失败") ;
                            }
                        }
                    }
                );

            }
            break;
        }
        //console.log()
    }
}

function deleteOpTem1(btn) {
    var thisTr = $(btn).parent("td").parent("tr");
    var label = thisTr.children("td:eq(0)").children("select").find("option:selected").val();
    var templateTabel = document.getElementById("templeteTable")//返回控件 templeteTable
    var trList = templateTabel.getElementsByTagName("tr");
    var line = 0;
    for (; line < trList.length; line++) {
        console.log(trList[line].cells[0].innerHTML);
        var text;
        if (trList[line].cells[0].childNodes.length > 1)
            text = trList[line].cells[0].firstChild.value;
        else
            text = trList[line].cells[0].innerHTML
        console.log(text);
        //  var tr = $("#templeteTable tbody:eq(0) tr:eq(8)");
        //  var id = tr.children("td:eq(0)").children("input:eq(0)").val();
        if (label == text) {
            console.log(trList[line].cells[0].innerHTML);
            var tr = $("#templeteTable tr:eq(" + line + ")");
            var id = tr.children("td:eq(0)").children("input:eq(1)").val();
            if (id == undefined)
                alert("父模板属性无法修改");
            else {
                var url = "/template/attr/del";
                $.ajax({
                        url: url,
                        type: "POST",
                        data: {id: id},
                        dataType: "json",
                        async: false,
                        success: function (data) {
                            //这里是执行程序之后的操作

                            if (data.code == 1) {
                                alert("删除成功")
                            } else {
                                alert("删除失败");
                            }
                        }
                    }
                );
                thisTr.remove();
                tr.remove();
            }
        }
    }
    location.reload();
}
