# 커피 원두 추천 웹페이지


## 개요

사람들의 커피에 대한 관심도는 많아지고 있고, 커피 시장의 크기는 계속해서 커지고 있지만 커피 원두에 대해 알기 쉽지 않음.
커피 원두를 추천해주는 시스템이나 서비스가 국내에 크게 없음.
<br/> <br/> 

## (아키텍처) 구성

![아키텍처 구성도](https://github.com/parkg17/coffee_recommendation/assets/38908136/5ca74b3c-666f-4024-b71d-df1fb2a22280)

<br/> 

## 구현
<p align="center", width="100%">
  <img src="https://github.com/parkg17/coffee_recommendation/assets/38908136/916899d2-f05b-499a-9132-8462fa2ca21b.JPG" width="40%">
  <img src="https://github.com/parkg17/coffee_recommendation/assets/38908136/a437f7cd-444f-4a68-b48b-7b255d19b123.JPG" width="50%">
</p>


- Coffeereview.com 사이트에서 여러 커피 원두 리뷰 데이터 이용
- Python BeautifulSoup 라이브러리를 기반으로 웹 크롤링 진행 
<br/> <br/> <br/> <br/>

<p align="center"><img src="https://github.com/parkg17/coffee_recommendation/assets/38908136/4ca3487b-0d87-4418-a3ca-b49b798cd6e7.JPG" width="50%"></p>

- 데이터 이상치 제거 및 처리
- 커피 원두 판단 기준을 하나의 Column으로 그룹화 및 평균
- 유사도 함수를 이용하여 커피 원두 정보가 들어오면 유사한 원두를 출력할 수 있도록 추천 시스템 구현
<br/> <br/> <br/> <br/>

<p align="center", width="100%">
  <img src="https://github.com/parkg17/coffee_recommendation/assets/38908136/0406c4c4-48bc-4e1a-8269-d91cbee12744.JPG" width="30%">
  <img src="https://github.com/parkg17/coffee_recommendation/assets/38908136/a8e800f4-06b2-4612-b9e6-adf13b5fc05b.JPG" width="50%">
</p>

- Spring으로 프론트엔드와의 통신을 위한 API 서버 개발
- 유사도 추천의 경우 Flask 서버를 이용하여 유사도 함수를 통해 결과값을 추출할 수 있도록 구현
- Vue.js를 이용하여 프론트엔드 개발

<br/> <br/> 

## 결과
![웹페이지 화면](https://user-images.githubusercontent.com/38908136/233314398-3038661c-3be2-41ea-a4b5-c9b811879194.png)

<div align="center"> 처음 웹페이지 화면 </div>
<br/> <br/> 

![추천 받기전](https://user-images.githubusercontent.com/38908136/233314499-0ad08db7-a03e-4096-baed-f11b63cbc794.png)

<div align="center"> 추천 받기 전 화면 </div>
<br/> <br/> 

![추천 받은후](https://user-images.githubusercontent.com/38908136/233314571-532497fc-9c83-4de0-8607-def14f698e81.png)

<div align="center"> 추천 받은 이후 화면 </div>

