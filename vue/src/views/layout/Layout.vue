<template>
  <div class="app-wrapper" :class="{hideSidebar:!sidebar.opened}">
    <titlebar></titlebar>
    <sidebar class="sidebar-container"></sidebar>
    <div class="main-container">
      <navbar v-if="$route.meta.showNavBar"></navbar>
      <app-main></app-main>
    </div>
  </div>
</template>

<script>
import { Titlebar,Navbar, Sidebar, AppMain } from '@/views/layout/components'

export default {
  name: 'layout',
  components: {
    Titlebar,
    Navbar,
    Sidebar,
    AppMain
  },
  computed: {
    sidebar() {
      return this.$store.state.app.sidebar
    }
  }
}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
@import "src/styles/mixin.scss";
.app-wrapper {
  @include clearfix;
  position: relative;
  height: 100%;
  width: 100%;
}
</style>
