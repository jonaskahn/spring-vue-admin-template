<script setup></script>

<template>
  <router-view />
  <Toast
    position="top-right"
    error-icon="pi pi-times-circle"
    info-icon="pi pi-info-circle"
    success-icon="pi pi-check-circle"
    warn-icon="pi pi-exclamation-circle"
  />
</template>

<style scoped></style>
