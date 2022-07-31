<template>
  <div id="map" ref="mapEl"></div>
  <SearchBar v-model:center="mapCenter"></SearchBar>
</template>

<script setup lang="ts">
import { ref, watch } from "vue";
import SearchBar from "./SearchBar.vue";
import { computed } from "vue";
import { transform } from "ol/proj";
import type { Coordinate } from "ol/coordinate";
import { Map as OLMap, View } from "ol";
import XYZ from "ol/source/XYZ";
import { fromLonLat } from "ol/proj";
import TileLayer from "ol/layer/Tile";

const MAP = new OLMap({
  view: new View({
    center: fromLonLat([151.2093, -33.8688]), // Sydney
    zoom: 13,
  }),
  layers: [
    new TileLayer({
      source: new XYZ({
        url: "https://{a-c}.tile.openstreetmap.org/{z}/{x}/{y}.png",
      }),
    }),
  ],
});

const mapEl = ref<HTMLDivElement>();
watch(mapEl, (val) => MAP.setTarget(val));

const mapCenter = computed<Coordinate>({
  get() {
    // eslint-disable-next-line @typescript-eslint/no-non-null-assertion
    return transform(MAP.getView().getCenter()!, "EPSG:3857", "EPSG:4326");
  },
  set(val) {
    MAP.getView().setCenter(val);
  },
});
</script>

<style scoped>
#location {
  position: absolute;
  bottom: 10px;
  right: 80px;
}

#map {
  padding: 0;
  margin: 0;
  height: 100vh;
  width: 100%;
  font-family: Arial, Helvetica, sans-serif;
  font-size: 14px;
  color: #323232;
}
</style>
