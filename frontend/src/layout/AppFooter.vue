<script setup>
import { useLayout } from "@/layout/composables/layout";
import { computed } from "vue";

const { layoutConfig, contextPath } = useLayout();

const logoUrl = computed(() => {
  return `${contextPath}layout/images/${
    layoutConfig.darkTheme.value ? "logo-white" : "logo-dark"
  }.svg`;
});
</script>

<template>
  <div class="layout-footer">
    <img :src="logoUrl" alt="Logo" class="mr-2" height="20" />
    by
    <span class="font-medium ml-2">PrimeVue</span>
  </div>
</template>
<style lang="scss" scoped></style>
