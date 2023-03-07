<script setup></script>

<template>
  <router-view v-slot="{ Component }">
    <transition name="slide-fade" :duration="500">
      <component :is="Component" />
    </transition>
  </router-view>

  <Toast
    group="default"
    error-icon="pi pi-times-circle"
    info-icon="pi pi-info-circle"
    position="top-right"
    success-icon="pi pi-check-circle"
    warn-icon="pi pi-exclamation-circle"
  />
  <Toast
    group="mobile"
    error-icon="pi pi-times-circle"
    info-icon="pi pi-info-circle"
    position="top-center"
    success-icon="pi pi-check-circle"
    warn-icon="pi pi-exclamation-circle"
  />
</template>

<style scoped></style>
