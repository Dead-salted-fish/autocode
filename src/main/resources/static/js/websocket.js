var websocket = null;
var tempNotification = null
//判断当前浏览器是否支持WebSocket
function createWebSocket(notification){
    var host = document.location.host;
    if ('WebSocket' in window) {
        tempNotification = notification
        let uid = window.localStorage.getItem("uid")
        let token = window.localStorage.getItem("token")
        console.log("浏览器支持Websocket")
        console.log(uid)
        websocket = new WebSocket('ws://'+host+'/webSocket/'+uid,token);

    } else {
        console.log('当前浏览器 Not support websocket')
    }

//连接发生错误的回调方法
    websocket.onerror = function() {
        console.log("WebSocket连接发生错误")
    };

//连接成功建立的回调方法
    websocket.onopen = function() {
        console.log("WebSocket连接成功")
    }

//接收到消息的回调方法
    websocket.onmessage = function(event) {
        console.log("接收到消息的回调方法")
        console.log("这是后台推送的消息："+event.data);
        tempNotification.open({
            message: '后端推送消息',
            description:event.data,
            onClick: () => {
                console.log('Notification Clicked!');
            },
        });
    }

//监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口，server端会抛异常。
    window.onbeforeunload = function() {
        closeWebSocket();
    }

//关闭WebSocket连接
    function closeWebSocket() {
        websocket.close();
        console.log("webSocket已关闭！")
    }
}

