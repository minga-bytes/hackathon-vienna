<template>
  <div
    v-if="showModal"
    id="exampleModal"
    class="position-absolute top-50 start-50 bg-light"
    aria-labelledby="exampleModalLabel"
    aria-hidden="true"
  >
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title">Modal title</h5>
        <button
          type="button"
          class="btn-close"
          data-bs-dismiss="modal"
          aria-label="Close"
        ></button>
      </div>
      <div class="modal-body">
        <p>Modal body text goes here.</p>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">
          Close
        </button>
        <button type="button" class="btn btn-primary">Save changes</button>
      </div>
    </div>
  </div>
</template>

<script lang="ts" setup>
import { ref } from 'vue';
const showModal = ref(false);

import { useVModel } from '@vueuse/core';

const props = defineProps({
    showModal: Boolean
})

const emit = defineEmits(['update:showModal'])

const shown = useVModel(props, 'showModel', emit);
</script>
