var express = require('express');
var router = express.Router();
var assert  = require('assert');
var fs = require('fs');
var parsers = require('../lib/parsers');
const packageJson = require('package-json');


/* GET home page. */
router.get('/', function(req, res, next) {
  res.render('index', { title: 'Express' });
});

router.get('/index',function(req,res,next){
    res.writeHead(200, {'Content-Type':'text/html;charset=utf-8'});
    res.end(JSON.stringify({ "index" : "欢迎来到首页" }));
})

router.get('/health',function(req,res,next){
    res.writeHead(200, {'Content-Type':'application/json;charset=utf-8'});
    res.end(JSON.stringify({"status":"UP"}));
})
//  POST 请求
router.get('/testparse', function (req, res,next) {
    console.log("解析包管理文件 POST 请求");
    //var platform = req.body.platform;
    //var filestr = req.body.filestr;
    var filestr = fs.readFileSync(__dirname +'/pom.xml').toString();
    console.log(filestr);
    var platform = 'maven';
    parsers.parse(platform, filestr)
        .then(function(packages) {
          res.send({'status':'200','packages':packages});
        }).catch(function (err) {
        console.log(err);
        res.send({'status':'400'})
    }).done();

});


//  POST 请求
router.post('/parse', function (req, res) {
    console.log("解析包管理文件 POST 请求");
    console.log(req.body);
    var platform = req.body.platform;
    var filestr = req.body.filestr;
    parsers.parse(platform, filestr)
        .then(function(packages) {
            res.send({'status':'200','packages':packages});
        }).catch(function (err) {
            console.log(err);
            res.send({'status':'400','msg':err.toString()})
    }).done();

});

//  POST 请求
router.post('/packageversion', function (req, res) {

    console.log("解析包管理文件 POST 请求");
    console.log(req.body);
    var packageName = req.body.packageName;
    var packageversion = req.body.packageversion;
    (async () => {
        var content = await packageJson(packageName,{version:packageversion}).catch((error)=>{
            //console.log(error);
            res.send({'status':'400','msg':'failure'});
        });

        if(content!=null)
        {
            var name = content.name;
            var version = content.version;
            var dependencies = content.dependencies;
            var devDependencies = content.devDependencies;
            var ret = {"name":name,"version":version,"dependencies":dependencies,"devDependencies":devDependencies};
            res.send({'status':'200','packages':ret,'msg':'success'});
        }
        else
        {
            res.send({'status':'400','msg':'failure'});
        }
    })();

});

module.exports = router;
