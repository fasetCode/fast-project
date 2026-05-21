<script setup lang="ts">

import Carousel from './public/Carousel.vue'
import TopInformationBar from './mall/TopInformationBar.vue'

withDefaults(defineProps<{ config: any,type:string }>(), {
  config: () => ({}),
  type: ()=>'',
})
</script>

<template>
  <template v-if="type === 'carousel'">
    <Carousel :config="config.props"></Carousel>
  </template>
  <template v-if="type === 'mall_top_information_bar'">
    <TopInformationBar :config="config.props"></TopInformationBar>
  </template>
</template>

<style scoped>

</style>