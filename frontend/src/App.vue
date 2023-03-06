<script setup></script>

<template>
  <router-view />
  <Toast
    error-icon="pi pi-times-circle"
    info-icon="pi pi-info-circle"
    position="top-right"
    success-icon="pi pi-check-circle"
    warn-icon="pi pi-exclamation-circle"
  />
</template>

<style scoped></style>
