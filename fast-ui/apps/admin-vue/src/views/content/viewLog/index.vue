<template>
  <CrudEntityPage
    title="浏览记录"
    base-url="/content/viewLog"
    permission-prefix="admin:content:viewLog"
    :textarea-fields="['referer', 'userAgent']"
  />
</template>

<script setup lang="ts">
import CrudEntityPage from '../components/CrudEntityPage.vue'
</script>

