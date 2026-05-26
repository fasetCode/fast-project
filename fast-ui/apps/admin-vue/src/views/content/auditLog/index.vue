<template>
  <CrudEntityPage
    title="审核记录"
    base-url="/content/auditLog"
    permission-prefix="admin:content:auditLog"
    :dict-fields="{ auditStatus: 'content_audit_status' }"
    :textarea-fields="['reason']"
  />
</template>

<script setup lang="ts">
import CrudEntityPage from '../components/CrudEntityPage.vue'
</script>

