<template>
  <CrudEntityPage
    title="评论点赞记录"
    base-url="/content/commentLike"
    permission-prefix="admin:content:commentLike"
    :dict-fields="{ action: 'content_comment_like_action' }"
  />
</template>

<script setup lang="ts">
import CrudEntityPage from '../components/CrudEntityPage.vue'
</script>

