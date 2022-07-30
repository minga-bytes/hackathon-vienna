<template>
  <div id="map"></div>
  <div class="search">
    <input
      type="text"
      id="geocode-input"
      placeholder="Enter an address or place e.g. Vienna"
      size="50"
      v-model="query"
    />
    <button
      type="button"
      class="btn btn-primary"
      @click="geocodeButton()"
      id="geocode-button"
    >
      Suchen
    </button>
  </div>
</template>

<script setup>
import { ref, onMounted } from "vue";
const query = ref("");
const popup = new Popup();

const apiKey =
  "AAPK9d925958623e4e98a74404210231c96bC2iHvmGkPL5qa7ObmIdQzjdK8LeS3rJWGQC0flK5RdXqGX1-Dmxp9vNJ43PHEwvK";

let map = new ol.Map({ target: "map" });

onMounted(() => {
  map = new ol.Map({ target: "map" });
  map.setView(
    new ol.View({
      center: ol.proj.fromLonLat([151.2093, -33.8688]), // Sydney
      zoom: 13,
    })
  );

  const basemapId = "ArcGIS:Navigation";

  const basemapURL =
    "https://basemaps-api.arcgis.com/arcgis/rest/services/styles/" +
    basemapId +
    "?type=style&token=" +
    apiKey;

  olms(map, basemapURL);

  map.addOverlay(popup);
});

const geocodeButton = () => {
  const authentication = arcgisRest.ApiKeyManager.fromKey(apiKey);
  const center = ol.proj.transform(
    map.getView().getCenter(),
    "EPSG:3857",
    "EPSG:4326"
  );
  console.log(center);
  arcgisRest
    .geocode({
      singleLine: query.value,
      authentication,

      params: {
        outFields: "*",
        location: center.join(","),
        outSR: 3857, // Request coordinates in Web Mercator to simplify displaying
      },
    })
    .then((response) => {
      const result = response.candidates[0];
      if (!result === 0) {
        alert("That query didn't match any geocoding results.");
        return;
      }
      console.log(result);
      const coords = [result.location.x, result.location.y];

      popup.show(coords, result.attributes.LongLabel);
      map.getView().setCenter(coords);
    })

    .catch((error) => {
      alert(
        "There was a problem using the geocoder. See the console for details."
      );
      console.error(error);
    });
};
</script>

<style scoped>
.search {
  position: absolute;
  top: 20px;
  right: 20px;
}
#geocode-input,
#geocode-button {
  font-size: 16px;
  margin: 0 2px 0 0;
  padding: 4px 8px;
  width: 8em;
  height: 2.45em;
  margin-top: -3.5px;
}
#geocode-input {
  width: 19em;
  height: 2.55em;
  border-radius: 10px;
  border: 1px solid white;
}

#map {
  padding: 0;
  margin: 0;
  height: 93.95vh;
  width: 100%;
  font-family: Arial, Helvetica, sans-serif;
  font-size: 14px;
  color: #323232;
}
</style>
