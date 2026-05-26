<template>
  <CrudEntityPage
    title="举报"
    base-url="/content/report"
    permission-prefix="admin:content:report"
    :dict-fields="{
      targetType: 'content_report_target_type',
      reasonType: 'content_report_reason_type',
      status: 'content_report_status',
    }"
    :textarea-fields="['reason', 'userAgent']"
  />
</template>

<script setup lang="ts">
import CrudEntityPage from '../components/CrudEntityPage.vue'
</script>

