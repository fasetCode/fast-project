<template>
  <CrudEntityPage
    title="内容"
    base-url="/content/info"
    permission-prefix="admin:content:info"
    :dict-fields="{
      status: 'status',
      publishStatus: 'content_publish_status',
      auditStatus: 'content_audit_status',
      type: 'content_type',
    }"
    :textarea-fields="['summary', 'tags', 'sourceUrl']"
  />
</template>

<script setup lang="ts">
import CrudEntityPage from '../components/CrudEntityPage.vue'
</script>

