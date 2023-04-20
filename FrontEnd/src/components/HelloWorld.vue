<template>

  <div id="wrap">
    <section>
      <p>커피 원두 추천 웹페이지</p>
      <div style="display: flex; justify-content: center; align-items: center; ">
        <a href="/similar" class="banner_img">
          <img :width="400" src="../assets/logo1.png">
        </a>
        <a href="/item" class="banner_img">
          <img :width="400" src="../assets/logo2.png">
        </a>
        <a href="/market" class="banner_img">
          <img :width="400" src="../assets/logo3.png">
        </a>
      </div>

    </section>
    <footer>
      <ul class="menu">
        <li>2017312665 박결</li>
        <li>2023년도 1학기 졸업작품</li>
      </ul>
    </footer>
</div>

  
</template>

<script>

  
export default {
  name: 'HelloWorld',
  props: {
    msg: String
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
p {
  font-family: Georgia;
  padding-top:10px;
  font-size: 70px;
  font-weight: bold;
  color: #472d2d;
}
ul {
  list-style-type: none;
  padding: 0;
}
li {
  display: inline-block;
  margin: 0 10px;
}
a {
  color: #42b983;
}
.banner_img {
  float: left;
  margin-top: 40px;
  margin-left: 50px;
  margin-right: 50px;
  display: flex;
  justify-content: center; 
  align-items: center;
  vertical-align:middle;
}

html, body {
  margin: 0;
  padding: 0;
}

#wrap {
  min-height: 100vh;
  position: relative;
  width: 100%;
}

section {
  height: 800px;
  padding-bottom: 25px;
}

footer {
  width: 100%;
  height: 100px;
  position: absolute;
  background-color: #272727;
  border-top: 1px solid #333;
}
footer .inner {
  padding: 40px 0 60px 0
}
footer .menu {
  display: flex;
  justify-content: center;
  color: #42b983;
}
footer .menu li {
  position: relative;
}
</style>