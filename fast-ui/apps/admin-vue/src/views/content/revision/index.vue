<template>
  <CrudEntityPage
    title="修订记录"
    base-url="/content/revision"
    permission-prefix="admin:content:revision"
    :textarea-fields="['reason', 'content', 'contentHtml']"
  />
</template>

<script setup lang="ts">
import CrudEntityPage from '../components/CrudEntityPage.vue'
</script>

