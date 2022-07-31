<template>
  <form @submit="submit">
    <input
      id="geocode-input"
      placeholder="Enter an address or place e.g. Vienna"
      size="50"
      v-model="query"
      type="search"
    />
    <button type="submit" class="input-group-text" id="geocode-button">
      Suchen
    </button>
  </form>
</template>

<script lang="ts" setup>
import { useVModel } from "@vueuse/core";
import { ref, type PropType } from "vue";
import type { Coordinate } from "ol/coordinate";
import { geocode, type IGeocodeResponse } from "@esri/arcgis-rest-geocoding";
import { ApiKeyManager } from "@esri/arcgis-rest-request";

const props = defineProps({
  center: {
    type: Array as PropType<Coordinate>,
    required: true,
  },
});

const emit = defineEmits(["update:center"]);

const mapCenter = useVModel(props, "center", emit);

const query = ref("");

const API_KEY =
  "AAPK9d925958623e4e98a74404210231c96bC2iHvmGkPL5qa7ObmIdQzjdK8LeS3rJWGQC0flK5RdXqGX1-Dmxp9vNJ43PHEwvK";

const KEY_MANAGER = ApiKeyManager.fromKey(API_KEY);

async function submit(e: Event) {
  e.preventDefault();

  let response: IGeocodeResponse;

  try {
    response = await geocode({
      singleLine: query.value,
      authentication: KEY_MANAGER,
      params: {
        outFields: "",
        location: mapCenter.value.join(","),
        outSR: 3857, // Request coordinates in Web Mercator to simplify displaying
      },
    });
  } catch (err) {
    alert(
      "There was a problem using the geocoder. See the console for details."
    );
    console.log(err);
    return;
  }

  // TODO: Handle "error"
  if (response.candidates.length === 0) {
    alert("That query didn't match any geocoding results.");
    return;
  }

  const { location } = response.candidates[0];

  console.log('shit');

  mapCenter.value = [location.x, location.y];
}
</script>

<style scoped>
form {
  position: absolute;
  top: 20px;
  right: 20px;
  color: #555;
  display: flex;
  padding: 2px;
  border-radius: 5px;
  /* From https://css.glass */
  background: rgba(255, 255, 255, 0.463);
  border-radius: 16px;
  box-shadow: 0 4px 30px rgba(0, 0, 0, 0.1);
  backdrop-filter: blur(5px);
  -webkit-backdrop-filter: blur(5px);
  border: 1px solid rgba(255, 255, 255, 0.3);
}

input[type="search"] {
  border: none;
  background-color: transparent;
  margin: 0;
  padding: 7px 8px;
  font-size: 1.25em;
  /* color: inherit; */
}

input[type="search"]::placeholder {
  color: rgba(0, 0, 0, 0.766);
}

button[type="submit"] {
  text-indent: -999px;
  overflow: hidden;
  width: 2.5em;
  height: 2.5em;
  padding: 0;
  margin: 0;
  border: 1px solid transparent;
  border-radius: inherit;
  background: transparent
    url("data:image/svg+xml,%3Csvg xmlns='http://www.w3.org/2000/svg' width='16' height='16' class='bi bi-search' viewBox='0 0 16 16'%3E%3Cpath d='M11.742 10.344a6.5 6.5 0 1 0-1.397 1.398h-.001c.03.04.062.078.098.115l3.85 3.85a1 1 0 0 0 1.415-1.414l-3.85-3.85a1.007 1.007 0 0 0-.115-.1zM12 6.5a5.5 5.5 0 1 1-11 0 5.5 5.5 0 0 1 11 0z'%3E%3C/path%3E%3C/svg%3E")
    no-repeat center;
  cursor: pointer;
  opacity: 0.7;
}

button[type="submit"]:hover {
  opacity: 1;
}

button[type="submit"]:focus,
input[type="search"]:focus {
  box-shadow: 0 0 3px 0 transparent;
  border-color: transparent;
  outline: none;
}

[type="search"]::-webkit-search-cancel-button {
  -webkit-appearance: none;
  appearance: none;
  height: 10px;
  width: 10px;
  background-image: url(data:image/svg+xml;base64,PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0iaXNvLTg4NTktMSI/Pg0KPCEtLSBHZW5lcmF0b3I6IEFkb2JlIElsbHVzdHJhdG9yIDE2LjAuMCwgU1ZHIEV4cG9ydCBQbHVnLUluIC4gU1ZHIFZlcnNpb246IDYuMDAgQnVpbGQgMCkgIC0tPg0KPCFET0NUWVBFIHN2ZyBQVUJMSUMgIi0vL1czQy8vRFREIFNWRyAxLjEvL0VOIiAiaHR0cDovL3d3dy53My5vcmcvR3JhcGhpY3MvU1ZHLzEuMS9EVEQvc3ZnMTEuZHRkIj4NCjxzdmcgdmVyc2lvbj0iMS4xIiBpZD0iQ2FwYV8xIiB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHhtbG5zOnhsaW5rPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5L3hsaW5rIiB4PSIwcHgiIHk9IjBweCINCgkgd2lkdGg9IjEyMy4wNXB4IiBoZWlnaHQ9IjEyMy4wNXB4IiB2aWV3Qm94PSIwIDAgMTIzLjA1IDEyMy4wNSIgc3R5bGU9ImVuYWJsZS1iYWNrZ3JvdW5kOm5ldyAwIDAgMTIzLjA1IDEyMy4wNTsiDQoJIHhtbDpzcGFjZT0icHJlc2VydmUiPg0KPGc+DQoJPHBhdGggZD0iTTEyMS4zMjUsMTAuOTI1bC04LjUtOC4zOTljLTIuMy0yLjMtNi4xLTIuMy04LjUsMGwtNDIuNCw0Mi4zOTlMMTguNzI2LDEuNzI2Yy0yLjMwMS0yLjMwMS02LjEwMS0yLjMwMS04LjUsMGwtOC41LDguNQ0KCQljLTIuMzAxLDIuMy0yLjMwMSw2LjEsMCw4LjVsNDMuMSw0My4xbC00Mi4zLDQyLjVjLTIuMywyLjMtMi4zLDYuMSwwLDguNWw4LjUsOC41YzIuMywyLjMsNi4xLDIuMyw4LjUsMGw0Mi4zOTktNDIuNGw0Mi40LDQyLjQNCgkJYzIuMywyLjMsNi4xLDIuMyw4LjUsMGw4LjUtOC41YzIuMy0yLjMsMi4zLTYuMSwwLTguNWwtNDIuNS00Mi40bDQyLjQtNDIuMzk5QzEyMy42MjUsMTcuMTI1LDEyMy42MjUsMTMuMzI1LDEyMS4zMjUsMTAuOTI1eiIvPg0KPC9nPg0KPGc+DQo8L2c+DQo8Zz4NCjwvZz4NCjxnPg0KPC9nPg0KPGc+DQo8L2c+DQo8Zz4NCjwvZz4NCjxnPg0KPC9nPg0KPGc+DQo8L2c+DQo8Zz4NCjwvZz4NCjxnPg0KPC9nPg0KPGc+DQo8L2c+DQo8Zz4NCjwvZz4NCjxnPg0KPC9nPg0KPGc+DQo8L2c+DQo8Zz4NCjwvZz4NCjxnPg0KPC9nPg0KPC9zdmc+DQo=);
  background-size: 10px 10px;
}
</style>
