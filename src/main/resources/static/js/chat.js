
//发送图片消息
function chooseFile() {
    let fileList = document.getElementById("file").files;
    let type = fileList[0].type;
    // let selectUser = null;
    // 获取左侧聊天对象
    // $(".select_user").each(function() {
    //     if ($(this).attr("data") === "selected") {
    //         selectUser = $(this).html();
    //     }
    // });
    // let toUser = selectUser;
    let fileMsg;
    if(fileList.length > 0) {
        let fileReader = new FileReader();
        fileReader.readAsDataURL(fileList[0]);
        fileReader.onload = function (e) {
            debugger;
            fileMsg = e.target.result;
            // let username = $('#user_name').text();
            // let msg_content = {
            //     msg: e.target.result,
            //     acceptUser: toUser,
            //     sendUser: username,
            //     sendType: "1",
            //     msgType: "1"
            // };
            // ws.send(JSON.stringify(msg_content));
            return fileMsg;
        }
    } else {
        return null;
    }
}



function addNotice(notice) {
    // $('.chatting').append('<div style="width: 100%;  text-align: center;">'+notice+'</div>');
    simpleToast(notice,'success');
}
// function addMyMessage(message){
//
// }
function addMessage(data){
    $('.chatting').append(data);
}

