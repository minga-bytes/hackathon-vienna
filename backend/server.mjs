import express from 'express';
import bodyParser from 'body-parser';
var app = express();
app.use(bodyParser.json({limit: '70mb'}));
app.use(bodyParser.urlencoded({limit: '70mb', extended: true}));

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


app.post('/import_street_data', async function (req, res) {
    const features = req.body.features;

    const promises = features.map(async (feature)=>{
        if (feature.geometry) {
            await client.query(`INSERT INTO public.street_data VALUES (ST_GeomFromGeoJSON('${JSON.stringify(feature.geometry)}'));`);
        }
    });

    await Promise.all(promises)

    res.send('Hello POST' );
});

var server = app.listen(8081, function () {
   var host = server.address().address
   var port = server.address().port
   
   console.log("MingaBytes app listening at http://%s:%s", host, port)
});