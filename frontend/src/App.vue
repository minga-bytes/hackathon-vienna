<script setup lang="ts">
import { RouterView } from "vue-router";
import Navbar from "@/components/Navbar.vue";
import Search from "@/components/Search.vue";

</script>

<template>
  <div>
    <Navbar />
    <br>
    <Search />
    <br>
    <RouterView />
  </div>
</template>

