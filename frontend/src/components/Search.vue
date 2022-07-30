<template>
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

