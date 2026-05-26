<template>
  <CrudEntityPage
    title="附件"
    base-url="/content/attachment"
    permission-prefix="admin:content:attachment"
    :textarea-fields="['url']"
  />
</template>

<script setup lang="ts">
import CrudEntityPage from '../components/CrudEntityPage.vue'
</script>

