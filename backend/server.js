var express = require('express');
bodyParser = require('body-parser');
var app = express();
app.use(bodyParser.json());

const { Pool, Client } = require('pg')

const pool = new Pool({
  user: 'postgres',
  host: 'localhost',
  database: 'postgres',
  password: 'passwort',
  port: 5432,
})

app.get('/user', function (req, res) {
    pool.query('SELECT * FROM public.user', (err, queryRes) => {
        console.log(res) 
        pool.end();
        res.send(queryRes);
    });
});


app.post('/import_street_data', function (req, res) {
    const polygon = req.body.polygon;
    res.send('Hello POST'+polygon.x );
});

var server = app.listen(8081, function () {
   var host = server.address().address
   var port = server.address().port
   
   console.log("MingaBytes app listening at http://%s:%s", host, port)
});