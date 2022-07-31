<template>
  <div style="display: flex; gap: 0.5rem; justify-self: flex-end">
    <button
      class="glass-container"
      @click="isEditModeEnabled = !isEditModeEnabled"
      :style="
        isEditModeEnabled
          ? '--btn-color: 163, 49, 49'
          : '--btn-color: 49, 77, 163'
      "
    >
      <span v-if="isEditModeEnabled" class="icon-remove"></span>
      <span v-else class="icon-plus"></span>
    </button>
    <button
      v-if="isEditModeEnabled"
      :disabled="!hasFeature"
      class="glass-container"
      @click="submit"
      style="--btn-color: 102, 170, 102"
    >
      <span class="icon-save"></span>
    </button>
  </div>
</template>

<script lang="ts" setup>
import type { Feature, Map as OLMap } from "ol";
import VectorLayer from "ol/layer/Vector";
import VectorSource from "ol/source/Vector";
import WKT from "ol/format/WKT";
import { ref, watch, type PropType } from "vue";
import { Draw, Interaction, Modify } from "ol/interaction";
import Style from "ol/style/Style";
import Fill from "ol/style/Fill";
import Stroke from "ol/style/Stroke";

const props = defineProps({
  map: {
    type: Object as PropType<OLMap>,
    required: true,
  },
});

function onFeature(feature: Feature) {
  const geo = feature.getGeometry();

  if (geo === undefined) return;

  const format = new WKT();
  const str = format.writeGeometry(geo);
  // TODO: Make api call to fetch selected roads
  console.log(str);
}

const SOURCE = new VectorSource({ wrapX: false });

const LAYER = new VectorLayer({
  source: SOURCE,
  style: new Style({
    stroke: new Stroke({ color: "rgba(49, 77, 163, 0.75" }),
    fill: new Fill({ color: "rgba(49, 77, 163, 0.2)" }),
  }),
});

const interactions: WeakMap<OLMap, Interaction[]> = new Map();

function addInteraction(map?: OLMap) {
  map = map ?? props.map;

  map.addLayer(LAYER);

  const modify = new Modify({ source: SOURCE });
  map.addInteraction(modify);

  modify.on("modifyend", (e) => {
    const f = e.features.pop();
    if (f === undefined) return;
    onFeature(f as Feature);
  });

  const draw = new Draw({
    source: SOURCE,
    type: "Polygon",
    style: new Style({
      stroke: new Stroke({ color: "rgba(49, 77, 163, 1)" }),
      fill: new Fill({ color: "rgba(49, 77, 163, 0.3)" }),
    }),
  });

  draw.on("drawstart", () => {
    SOURCE.clear();
  });

  draw.on("drawend", (e) => {
    onFeature(e.feature);
  });

  map.addInteraction(draw);

  interactions.set(map, [modify, draw]);
}

function removeInteraction(map?: OLMap) {
  map = map ?? props.map;

  if (!interactions.has(map)) return;

  map.removeLayer(LAYER);

  for (const interaction of interactions.get(map) ?? []) {
    map.removeInteraction(interaction);
  }

  interactions.delete(map);
}

const isEditModeEnabled = ref(false);

watch(
  () => props.map,
  (newMap, oldMap) => {
    removeInteraction(oldMap);
    if (isEditModeEnabled.value) addInteraction(newMap);
  }
);

watch(isEditModeEnabled, (val) => {
  if (val) {
    addInteraction();
  } else {
    removeInteraction();
    SOURCE.clear();
  }
});

const hasFeature = ref(false);

SOURCE.on("change", () => {
  hasFeature.value = !SOURCE.isEmpty();
});

function submit() {
  console.log("shit");
}
</script>

<style scoped>
div {
  margin-inline-end: 1rem;
  margin-block-end: 1rem;
}
button {
  border-radius: 50%;
  aspect-ratio: 1 / 1;
  background: rgba(var(--btn-color, 255, 255, 255), 0.8);
  padding: 1rem;
  font-size: 1.5rem;
  line-height: 1rem;
  color: white;
  block-size: 2ch;
  inline-size: 2ch;
  box-sizing: content-box;
  display: flex;
  justify-content: center;
  align-items: center;
  box-shadow: 0 10px 20px rgba(0, 0, 0, 0.19), 0 6px 6px rgba(0, 0, 0, 0.23);
  border: none;
}

button:disabled {
  opacity: 50%;
  pointer-events: none;
}
</style>
