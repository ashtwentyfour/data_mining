var express = require('express');

var app = express();

var exec = require('child_process').exec

var bodyParser = require('body-parser');

var multer  = require('multer');

app.use(express.static('public'));

app.use(bodyParser.urlencoded({ extended: false }));

app.use(multer({dest:'./apriori_uploads/'}).single('file'));

app.use(multer({dest:'./kmeans_uploads/'}).single('file'));

app.use(multer({dest:'./decisiontree_uploads/'}).single('file'));

app.use(multer({dest:'./fpgrowth_uploads/'}).single('file'));

app.get('/', function (req, res) {
   res.sendFile( __dirname + "/" + "index.html" );
});

app.post('/apriori', function (req, res) {

    var rename_file = exec('mv '+req.file.path+' apriori_uploads/input.txt', function(error, stdout, stderr) {
                            if (error !== null) {
                               console.log('exec error: ' + error);
                            }
                      });
    var run_algo = exec('./run_apriori.sh', function(error, stdout, stderr) {
                            if (error !== null) {
                              console.log('exec error: ' + error);
                            }
                            res.sendFile( __dirname + "/" + "apriori_output.txt");
                   });
    response = {
        message:'Apriori Algorithm execution successful'
    };
    console.log(response);
});

app.post('/kmeans', function (req, res) {

    var rename_file = exec('mv '+req.file.path+' kmeans_uploads/input.txt', function(error, stdout, stderr) {
                            if (error !== null) {
                               console.log('exec error: ' + error);
                            }
                      });
    var run_algo = exec('./run_kmeans.sh', function(error, stdout, stderr) {
                            if (error !== null) {
                              console.log('exec error: ' + error);
                            }
                            res.sendFile( __dirname + "/" + "kmeans_output.txt");
                   });
    response = {
        message:'K-Means Clustering Algorithm execution successful'
    };
    console.log(response);
});

app.post('/id3', function (req, res) {

    var rename_file = exec('mv '+req.file.path+' id3_uploads/input.txt', function(error, stdout, stderr) {
                            if (error !== null) {
                               console.log('exec error: ' + error);
                            }
                      });
    var run_algo = exec('./run_id3.sh', function(error, stdout, stderr) {
                            if (error !== null) {
                              console.log('exec error: ' + error);
                            }
                            res.sendFile( __dirname + "/" + "id3_output.txt");
                   });
    response = {
        message:'ID3 Decision Tree Algorithm execution successful'
    };
    console.log(response);
});

app.post('/fpgrowth', function (req, res) {

    var rename_file = exec('mv '+req.file.path+' fpgrowth_uploads/input.txt', function(error, stdout, stderr) {
                            if (error !== null) {
                               console.log('exec error: ' + error);
                            }
                      });
    var run_algo = exec('./run_fpgrowth.sh', function(error, stdout, stderr) {
                            if (error !== null) {
                              console.log('exec error: ' + error);
                            }
                            res.sendFile( __dirname + "/" + "fpgrowth_output.txt");
                   });
    response = {
        message:'FP-Growth Algorithm execution successful'
    };
    console.log(response);
});
          
var server = app.listen(8081, function () {

  var host = '0.0.0.0';
  var port = server.address().port

  console.log("app listening at http://%s:%s", host, port)

})
