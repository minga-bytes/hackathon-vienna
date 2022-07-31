<template>
  <div class="map">
    <div id="map" ref="mapEl"></div>
    <SearchBar v-model:center="mapCenter" style="grid-area: search"></SearchBar>
    <Login style="grid-area: login"></Login>
    <Edit :map="MAP" style="grid-area: edit" />
    <!-- <LoginRegister /> -->
  </div>
</template>

<script setup lang="ts">
import { ref, watch, type ComputedRef, type Ref } from "vue";
import SearchBar from "./SearchBar.vue";
import Login from "./Login.vue";
import Edit from "./Edit.vue";
import { computed } from "vue";
import { transform } from "ol/proj";
import type { Coordinate } from "ol/coordinate";
import { Collection, Map as OLMap, View } from "ol";
import XYZ from "ol/source/XYZ";
import { fromLonLat } from "ol/proj";
import TileLayer from "ol/layer/Tile";
import VectorSource from "ol/source/Vector";
import VectorLayer from "ol/layer/Vector";
import { useAreasStore, type Area } from "@/stores/areas";
import Stroke from "ol/style/Stroke";
import Style from "ol/style/Style";
import type LineString from "ol/geom/LineString";

const MAP = new OLMap({
  view: new View({
    center: fromLonLat([12.9816657, 47.4106084]), // Sydney
    zoom: 15,
  }),
  controls: [],
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

function useAreas(areas: ComputedRef<Area[]>, style: Style): VectorLayer<any> {
  const source = new VectorSource({ wrapX: false });
  const layer = new VectorLayer({ source });

  function setSource() {
    source.clear();

    for (const a of areas.value) {
      source.addFeature(a);
      // TODO: Add Features
    }
    console.log(layer.getSource()?.getFeatures())
  }

  setSource();

  watch(areas, setSource);

  return layer;
}

const areasStore = useAreasStore();

const myLayer = useAreas(
  computed(() => areasStore.myAreas),
  new Style({ stroke: new Stroke({ color: "#FF0000" }) })
);

const othersLayer = useAreas(
  computed(() => areasStore.otherAreas),
  new Style({ stroke: new Stroke({ color: "#00FF00" }) })
);

MAP.addLayer(myLayer);
MAP.addLayer(othersLayer);
</script>

<style scoped>
#location {
  position: absolute;
  bottom: 10px;
  right: 80px;
}

#map {
  font-family: Arial, Helvetica, sans-serif;
  font-size: 14px;
  color: #323232;
  position: absolute;
  inset: 0;
}

.map {
  padding: 0.5rem;
  margin: 0;
  position: fixed;
  inset: 0;
  display: grid;
  grid-template:
    "a search login" auto
    "b c d" 1fr
    "b edit edit" auto / 1fr auto auto;
  gap: 0.5rem;
}
</style>
