var DragDrop = function(node){
    var dragging = null,
        dragged = null,
        clientWidth = document.documentElement.clientWidth,
        clientHeight = document.documentElement.clientHeight,
        offsetWidth = node.offsetWidth,
        offsetHeight = node.offsetHeight,
        cardPosLeft = node.offsetLeft,
        cardPosTop = node.offsetTop,
        spanWidth = document.getElementById("span1").offsetWidth,
        titleHeight = document.getElementById("title").offsetHeight,
        mouseOffsetX = 0,
        mouseOffsetY = 0;

    node.onmousedown = function(event){
        beforeDrag(node);

        event = EventUtil.getEvent(event);
        mouseOffsetX = event.pageX - this.offsetLeft;
        mouseOffsetY = event.pageY - this.offsetTop;
        dragging = true;
        console.log("down:"+mouseOffsetX+";"+mouseOffsetY);
        dragged = false;

    };
    node.onmousemove = function(event){
        event = EventUtil.getEvent(event);
        if (dragging === true) {
            var moveY = event.pageY - mouseOffsetY;
            var moveX = event.pageX - mouseOffsetX;
            var maxX = clientWidth - offsetWidth,
                maxY = clientHeight - offsetHeight;
            moveX = moveRange(0,maxX,moveX);
            moveY = moveRange(titleHeight,maxY,moveY);
            node.style.top = moveY + "px";
            node.style.left = moveX + "px";
            console.log(node.style.left);
            dragged = true;
        }
    };
    node.onmouseup = function(){
        var thisSpan = document.getElementById(pos(node));
        var thisSpanX = thisSpan.offsetLeft;
        console.log("mouseUp 当前span偏移:"+thisSpanX);
        if(dragged === true){
            node.style.left = thisSpanX + "px";
            var thisSpanCards = thisSpan.getElementsByClassName("card");
            console.log(thisSpanCards.length);
            var projectStatus = thisSpan.id.substring(4);
            var cardStatus = node.id.substring(4);
            if(projectStatus != cardStatus.split("-")[0]){
                //在卡片 与 现在移动后状态不一致时 才提交数据 并且判断 移动后 的状态栏之前有几个卡片
                if(thisSpanCards.length > 0){
                    node.style.top = thisSpanCards[thisSpanCards.length-1].offsetTop + offsetHeight + "px";
                }
                if(thisSpanCards.length == 0){
                    node.style.top = "90px";
                }
                postData(projectStatus,cardStatus);
            }else{
                //在原来的状态下移动时 返回之前的定位
                node.style.left = cardPosLeft - spanWidth*0.07 + "px";
                node.style.top = cardPosTop - spanWidth*0.07 + "px";
            }
        }/*else{
            node.style.position = "relative";
        }*/
        dragging = false;

    };

    var moveRange = function(min,max,move){
        return Math.min(max,Math.max(min,move));
    };

    var pos = function(node){
        var posId = Math.floor(node.offsetLeft/spanWidth)+1;
        return "span"+posId;
    };

    var beforeDrag = function(node){
        var thisLaneCards = document.getElementById(pos(node)).getElementsByClassName("card");
        var i;
        for(i=thisLaneCards.length-1;i>=0;i--){
            if(thisLaneCards[i].id!=node.id){
                console.log(thisLaneCards[i].id);
                thisLaneCards[i].style.position = "fixed";
            }
        }
        node.style.position="absolute";
    };

    var postData = function(newSpanId,cardId){
        //提交数据：newSpanId 拖拽后所处的项目状态Id 例如span3
        // cardId 项目ID 例如card1-2 表示状态是需求池中的第2个项目
        //表示 需求池中的第2个项目 状态变更为 3：开发中
        console.log("拖拽后的项目状态："+newSpanId+";项目id:"+cardId);
        var xmlhttp;
        if (window.XMLHttpRequest){
            xmlhttp=new XMLHttpRequest();
        }else{
            xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
        }
        xmlhttp.onreadystatechange=function(){
            if (xmlhttp.readyState==4 && xmlhttp.status==200){
                var resData = xmlhttp.responseText;
                console.log(resData);
                location.replace(location.href);//重新加载页面
            }
        }
        var url = "";
        xmlhttp.open("POST",url,true);
        xmlhttp.send();
    }
}

window.onload = function(){
    var cards = document.getElementsByClassName("card");
    var i = 0;
    for(;i<cards.length;i++){

        DragDrop(cards[i]);
    }
}

var EventUtil = {
    getEvent:function(e){
        return e ? e : window.event;
    },
    getTarget:function(e){
        return e.target || e.srcElement;
    },
    preventDefault : function(e){
        if(e.preventDefault){
            e.preventDefault(); //非IE8
        }else{
            e.returnValue = false;  //IE8
        }
    },
    addHandler:function(ele,type,handler){
        //ele 操作元素 type 事件处理名称  handler 处理程序
        if(ele.addEventListener){
            ele.addEventListener(type,handler,false);
        }else if(ele.attachEvent){
            ele.attachEvent("on"+type,handler);
        }else{
            ele["on"+type]=handler;
        }
    },
    removeHandler:function(ele,type,handler){
        if(ele.removeEventListener){
            ele.removeEventListener(type,handler,false);
        }else if(ele.detachEvent){
            ele.detachEvent("on"+type,handler);
        }else{
            ele["on"+type] = null;
        }
    }
}