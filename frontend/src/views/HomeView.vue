<template>
    <div class="cell cell-map">
      <MapContainer
        :geojson="geojson"
        v-on:select="selected = $event"
      ></MapContainer>
    </div>
</template>

<script>
import MapContainer from "@/components/MapContainer.vue";
export default {
  name: "App",
  components: {
    MapContainer,
  },
  data: () => ({
    selected: undefined,
    geojson: {
      type: "Feature",
      properties: {
        name: "default object",
        quality: "top",
      },
      geometry: {
        type: "Polygon",
        coordinates: [
          [
            [-27.0703125, 43.58039085560784],
            [-28.125, 23.563987128451217],
            [-10.8984375, 32.84267363195431],
            [-27.0703125, 43.58039085560784],
          ],
        ],
      },
    },
  }),
};
</script>

<style>
html,
body {
  height: 100%;
  margin: 0;
}
.cell {
  border-radius: 4px;
  background-color: lightgrey;
}
.cell-map {
  height: 100vh;
}
</style>
