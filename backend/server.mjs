import express from 'express';
import bodyParser from 'body-parser';
var app = express();
app.use(bodyParser.json());

import pkg from 'pg';
const { Pool, Client } = pkg;

const pool = new Pool({
  user: 'postgres',
  host: 'localhost',
  database: 'postgres',
  password: 'passwort',
  port: 5432,
})

const client = await pool.connect();

app.get('/user', function (req, res) {
    client.query('SELECT * FROM public.user', (err, queryRes) => {
        console.log(res) 
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