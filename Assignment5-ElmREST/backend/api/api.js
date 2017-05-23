const express = require('express');
const router = express.Router();

let counter = 0;

router.get('/', function(req, res){
    counter++;
    res.json({'counter': counter});
});

router.get('/:value', function(req, res){  
    //counter = req.body.counter;
    counter = req.params.value;
    res.json({'counter': counter});
});

module.exports = router;