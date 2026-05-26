<template>
  <CrudEntityPage
    title="分类"
    base-url="/content/category"
    permission-prefix="admin:content:category"
    :dict-fields="{ status: 'status' }"
    :textarea-fields="['remark']"
  />
</template>

<script setup lang="ts">
import CrudEntityPage from '../components/CrudEntityPage.vue'
</script>

