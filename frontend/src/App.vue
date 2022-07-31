<script setup lang="ts">
import { RouterView } from "vue-router";
import Navbar from "@/components/Navbar.vue";

</script>

<template>
  <div>
    <RouterView />
  </div>
</template>

<style>
.glass-container {
  border-radius: 5px;
  /* From https://css.glass */
  background: rgba(255, 255, 255, 0.463);
  border-radius: 16px;
  box-shadow: 0 4px 30px rgba(0, 0, 0, 0.1);
  backdrop-filter: blur(5px);
  -webkit-backdrop-filter: blur(5px);
  border: 1px solid rgba(255, 255, 255, 0.3);
}
</style>
