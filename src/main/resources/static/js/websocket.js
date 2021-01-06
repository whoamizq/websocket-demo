/*
/!*
web socket绑定
 *!/
var ws =null;

function webSocketBind() {
    /!*
    主流浏览器现在都支持H5的websocket通信，但建议还是要判断
     *!/
    if ("webSocket" in window){
        /!**
         * 创建web socket实例
         * 如果连接失败，浏览器前台报错，连接失败
         * 前缀 ws:// 必须正确，yyServer是应用名称，websocket/yy.action是后台访问路径
         * localhost：服务器地址
         * @type {WebSocket}
         *!/
        ws = new WebSocket("ws://localhost:8881/yyServer/websocket/yy.action");

        //onopen：服务器连接成功后，自动触发
        ws.onopen = function () {
            //WebSocket已连接上，使用send方法发送数据
            console.log("服务器连接成功，并发送数据到后台...")
        };

        /!*
        服务器发送数据后，自动触发此方法，客户端进行获取数据，使用evt.data 获取数据
         *!/
        ws.onmessage = function (evt) {
            var received_msg = evt.data;
            console.log("接收到服务器数据：" + received_msg);
            showClientMessage(received_msg);
        };

        //客户端与服务器数据传输错误时触发
        ws.onerror = function (evt) {
            console.log("客户端与服务器数据传输错误...");
        };

        //websocket连接关闭时触发
        ws.onclose = function () {
            console.log("web socket连接关闭...")
        }
    }else {
        alert("您的浏览器不支持websocket！");
    }
}

/!**
 * 显示服务器发送的消息
 * @param message
 *!/
let showServerMessage = function (message) {
    if (message != undefined && message.trim() != ""){
        //往服务器发送消息
        ws.send(message.trim());

        /!*
        scrollHeight：div区域内文档的高度，只能DOM操作，JQuery没有提供相应的方法
         *!/
        let messageShow = "<div class='messageLine server'><div class='messageContent serverCon'>"
            + message + "</div><span>:我</span>";
        $(".centerTop").append(messageShow + "<br>");
        $(".messageArea").val("");

        let scrollHeight = $(".centerTop")[0].scrollHeight;
        $(".centerTop").scrollTop(scrollHeight - $(".centerTop").height());
    }
}

/!**
 * 显示客户端的消息
 * @param message
 *!/
let showClientMessage = function (message) {
    if (message != undefined && message.trim() != ""){
        let messageShow = "<div class='messageLine client'><span>服务器：</span><div class='messageContent clientCon'>"
            + message + "</div>";
        $(".centerTop").append(messageShow + "<br>");
        $(".messageArea").val("");

        let scrollHeight = $(".centerTop")[0].scrollHeight;
        $(".centerTop").scrollTop(scrollHeight - $(".centerTop").height());
    }
}

$(function () {
    //初始化后清空消息发送区域
    $(".messageArea").val("");

    //为消息发送按钮绑定事件
    $(".sendButton").click(function () {
        let message = $(".messageArea").val();
        showServerMessage(message);
    });

    //绑定键盘点击事件 ---- 用于按回车键发送消息
    $(window).keydown(function (event) {
        if (event.keyCode === 13){
            let message = $(".messageArea").val();
            showServerMessage(message);
        }
    });

    //绑定websocket，连接服务器
    webSocketBind();
})*/
