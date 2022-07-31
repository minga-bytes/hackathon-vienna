import express from 'express';
import bodyParser from 'body-parser';
var app = express();
app.use(bodyParser.json({limit: '70mb'}));
app.use(bodyParser.urlencoded({limit: '70mb', extended: true}));

app.use(bodyParser.text());

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
        res.send(queryRes);
    });
});

app.get('/area', function (req, res) {
    pool.query('SELECT * FROM public.area', (err, queryRes) => {
        console.log(res) 
        res.send(queryRes);
    });
});

app.post('/user', function (req, res) {
    const user = req.body;
    client.query(`Insert into INSERT INTO public.user (username) VALUES ('Hans', ${new Date()});`, (err, queryRes) => {
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

    res.send('Hello POST');
});

app.post('/area_to_streets', async function (req, res) {
    const areaGeo = req.body;
    console.log('areaGeo', areaGeo)

    const result = await client.query(`SELECT ST_AsGeoJSON(st_intersection(public.street_data.geo, polygon.polygon)) AS geo
        FROM public.street_data,
        st_geomfromtext('${areaGeo}'::text, 4326) polygon(polygon)
        WHERE st_intersects(public.street_data.geo, polygon.polygon);`);

        const fc = { type: "FeatureCollection", features: result.rows.map((json) => ({ geometry: JSON.parse(json.geo), type: 'Feature'})) };

    res.send(fc);
});

var server = app.listen(8081, function () {
   var host = server.address().address
   var port = server.address().port
   
   console.log("MingaBytes app listening at http://%s:%s", host, port)
});