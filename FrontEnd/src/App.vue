<template>
  <nav>
    <router-link to="/">Home</router-link> |
    <router-link to="/about">원두 용어 설명</router-link> |
    <router-link to="/similar">유사도 추천</router-link> |
    <router-link to="/item">항목별 추천</router-link> |
    <router-link to="/market">매장별 추천</router-link>
  </nav>
  <router-view/>
</template>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}

nav {
  padding: 30px;
}

nav a {
  font-weight: bold;
  color: #2c3e50;
}

nav a.router-link-exact-active {
  color: #42b983;
}
</style>
