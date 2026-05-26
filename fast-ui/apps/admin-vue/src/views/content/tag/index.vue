<template>
  <CrudEntityPage
    title="标签"
    base-url="/content/tag"
    permission-prefix="admin:content:tag"
    :dict-fields="{
      status: 'status',
      displayType: 'content_tag_display_type',
    }"
  />
</template>

<script setup lang="ts">
import CrudEntityPage from '../components/CrudEntityPage.vue'
</script>

