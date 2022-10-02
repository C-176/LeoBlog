
import(Swal)


function ajax(ajaxReq,method,url,formData){
    setTimeout(function(){
        ajaxReq(method,url,formData);
    },25000);
}






function ajaxReq (method,url,formData){

    var xhr = new XMLHttpRequest();
    xhr.open(method, url);
    xhr.send(formData);
    xhr.onreadystatechange = function () {
        var result = xhr.responseText;
        result = JSON.parse(result);
        var code = result.code;
        var msg = result.msg;
        if (code !== 200) errorAlert(msg);
    }
}


function returnPara(para){
    return para;
}

var errorAlert = function (msg) {
    simpleAlert(msg,'error')
}
var questionAlert = function (msg){
    var a=0;
    Swal.fire({
        title: msg,
        icon: 'question',
        showCancelButton: true,
        confirmButtonText: '确认',
        cancelButtonText: '取消'
    }).then((result)=>{
        if(result.value){
            
        } else{
            
        }
    })
    return a;
    

}
var successAlert = function (msg) {
    simpleAlert(msg,'success')
}

var simpleAlert = function (msg,type) {
    Swal.fire({
        title: msg,
        icon: type,
        showCancelButton: false,
        confirmButtonText: 'OK'
    })
}

var simpleToast = function (msg,type){
    // 弹窗提示可以修改了
    const Toast = Swal.mixin({
        toast: true,
        position: 'top',
        showConfirmButton: false,
        timer: 1500
    })

    Toast.fire({
        icon: type,
        title: msg
    })

}
