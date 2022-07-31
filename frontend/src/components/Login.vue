<template>
  <div>
    <button class="glass-container" @click="modalShown = !modalShown">
      <span><i class="icon-user icon-3x"></i></span>
    </button>
    <Modal v-model:showModal="modalShown" />
  </div>
</template>

<script setup lang="ts">
import Modal from '@/components/Modal.vue'
import { ref } from 'vue';

const modalShown = ref(false)

</script>

