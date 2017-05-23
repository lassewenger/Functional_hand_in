const express = require('express');
const bodyParser = require('body-parser');

const app = express();


app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: false }));

app.use(function(req, res, next){
res.header("Access-Control-Allow-Origin", "*");
res.header("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
res.header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
return next();
});

app.use('/api', require('./api/api'));

const port = 3000;
app.listen(process.env.port || port, function(){
    console.log('listening on port: ' + port);
});