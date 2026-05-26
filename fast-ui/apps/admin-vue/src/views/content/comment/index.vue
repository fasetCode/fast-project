<template>
  <CrudEntityPage
    title="评论"
    base-url="/content/comment"
    permission-prefix="admin:content:comment"
    :dict-fields="{ status: 'content_comment_status' }"
    :textarea-fields="['content']"
  />
</template>

<script setup lang="ts">
import CrudEntityPage from '../components/CrudEntityPage.vue'
</script>

