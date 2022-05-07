//引入express
const express = require('express');
//创建应用对象
const app = express();
// //引入路由模块
// const router = require('./router');
//创建路由规则
//
app.get('/', (request, response) => {
    //设置可以跨域
    response.setHeader('Access-Control-Allow-Origin', '*');

    response.send('hello express');
});

app.post('/', (request, response) => {
    //设置可以跨域
    response.setHeader('Access-Control-Allow-Origin', '*');

    response.send('hello express post');
});
//监听端口启动服务
app.listen(8000, () => {
    console.log('服务器启动成功');
});
