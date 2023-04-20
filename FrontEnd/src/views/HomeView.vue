<template>
  <div class="home">
    <HelloWorld msg="커피 원두 추천 웹페이지"/>
  </div>
</template>

<script>
// @ is an alias to /src
import HelloWorld from '@/components/HelloWorld.vue'

export default {
  name: 'HomeView',
  components: {
    HelloWorld
  }
}
</script>

<style>
.home {
  width: 100%;
  height: 100%;
  text-align: center;
  position: relative;
  z-index: 1;

}
.home::after{
  width: 100%;
  height: 100vh;
  overflow: hidden;
  content: "";
  background: url("../assets/background.jpg");
  background-size: cover;
  background-repeat: no-repeat;
  background-position: center;
  position: absolute;
  top: 0;
  left: 0;
  z-index: -1;
  opacity: 0.4;
}

</style>