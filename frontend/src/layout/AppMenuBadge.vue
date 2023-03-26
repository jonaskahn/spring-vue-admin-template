<script setup>
import { computed } from 'vue'

const props = defineProps(['badge'])
let shorterVal = computed(() => {
  return props.badge.value.length > 12 ? props.badge.value.slice(0, 10) + ' ...' : props.badge.value
})
</script>

<template>
  <div v-if="badge" :title="badge.value" class="layout-menu-badge">
    <Badge :severity="badge.severity" :title="badge.value" :value="shorterVal"></Badge>
  </div>
</template>

<style scoped>
.layout-menu-badge {
  font-size: 75%;
  margin-left: 1rem;
}
</style>
