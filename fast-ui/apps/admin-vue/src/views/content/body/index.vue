<template>
  <CrudEntityPage
    title="内容正文"
    base-url="/content/body"
    permission-prefix="admin:content:body"
    :textarea-fields="['content', 'contentHtml']"
  />
</template>

<script setup lang="ts">
import CrudEntityPage from '../components/CrudEntityPage.vue'
</script>

