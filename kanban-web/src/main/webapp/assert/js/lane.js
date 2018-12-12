var DragDrop = function (node) {
    var dragging = null,
        dragged = null,
        clientWidth = document.documentElement.clientWidth,
        clientHeight = document.documentElement.clientHeight,
        offsetWidth = node.offsetWidth,
        offsetHeight = node.offsetHeight,
        spanWidth = document.getElementById("span1").offsetWidth,
        titleHeight = document.getElementById("title").offsetHeight,
        mouseOffsetX = 0,
        mouseOffsetY = 0;
    var mouseDownAndUpTimer;
    node.onmousedown = function (event) {
        event = EventUtil.getEvent(event);
        node.style.zIndex = 99;
        mouseOffsetX = event.clientX - this.offsetLeft;
        mouseOffsetY = event.clientY - this.offsetTop;
        dragging = true;
    };
    node.onmousemove = function (event) {
        event = EventUtil.getEvent(event);
        if (dragging === true) {
            var moveY = event.clientY - mouseOffsetY;
            var moveX = event.clientX - mouseOffsetX;
            var maxX = clientWidth - offsetWidth,
                maxY = 10000;
            moveX = moveRange(0, maxX, moveX);
            moveY = moveRange(titleHeight, maxY, moveY);
            node.style.top = moveY + "px";
            node.style.left = moveX + "px";
            console.log(node.style.left);
            dragged = true;
        }
    };
    node.onmouseup = function () {
        var thisSpan = document.getElementById(pos(node));
        var thisSpanX = thisSpan.offsetLeft;
        console.log("mouseUp 当前span偏移:" + thisSpanX);
        if (dragged === true) {
            var thisSpanCards = thisSpan.getElementsByClassName("card");
            console.log(thisSpanCards.length);
            if (thisSpanCards.length > 0) {
                node.style.top = thisSpanCards[thisSpanCards.length - 1].offsetTop + offsetHeight + "px";
            } else {
                node.style.top = "90px";
            }
            postData(thisSpan.id, node.id);
        }
        dragging = false;
    };

    var moveRange = function (min, max, move) {
        return Math.min(max, Math.max(min, move));
    };

    var pos = function (node) {
        var posId = Math.floor(node.offsetLeft / spanWidth);
        console.log("span" + posId);
        return "span" + posId;
    };

    var postData = function (newSpanId, cardId) {
        var url = localhostPaht + '/card/modifyCardLifeStatus.htm';
        var formData = "cardId=" + cardId + "&lifeStatus=" + getLifeStatus(newSpanId);

        $.ajax({
            url: url,
            data: formData,
            type: 'POST'
        }).done(function (data) {
            filteredRequest();
        });
    }
}

function getLifeStatus(laneId) {
    var lifeStatus = "";

    if (laneId == "span0") {
        lifeStatus = "需求池";
    } else if (laneId == "span1") {
        lifeStatus = "待开发";
    } else if (laneId == "span2") {
        lifeStatus = "开发中";
    } else if (laneId == "span3") {
        lifeStatus = "开发联调";
    } else if (laneId == "span4") {
        lifeStatus = "待测试";
    } else if (laneId == "span5") {
        lifeStatus = "测试中";
    } else if (laneId == "span6") {
        lifeStatus = "测试通过";
    } else if (laneId == "span7") {
        lifeStatus = "已上线";
    }

    return lifeStatus;
}

var EventUtil = {
    getEvent: function (e) {
        return e ? e : window.event;
    },
    getTarget: function (e) {
        return e.target || e.srcElement;
    },
    preventDefault: function (e) {
        if (e.preventDefault) {
            e.preventDefault(); //非IE8
        } else {
            e.returnValue = false;  //IE8
        }
    },
    addHandler: function (ele, type, handler) {
        //ele 操作元素 type 事件处理名称  handler 处理程序
        if (ele.addEventListener) {
            ele.addEventListener(type, handler, false);
        } else if (ele.attachEvent) {
            ele.attachEvent("on" + type, handler);
        } else {
            ele["on" + type] = handler;
        }
    },
    removeHandler: function (ele, type, handler) {
        if (ele.removeEventListener) {
            ele.removeEventListener(type, handler, false);
        } else if (ele.detachEvent) {
            ele.detachEvent("on" + type, handler);
        } else {
            ele["on" + type] = null;
        }
    }
};