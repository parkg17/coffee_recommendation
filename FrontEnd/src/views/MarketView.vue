<template>
    <div class="back_color">
        <div class="market">
        <h1>관심있는 카페(매장)을 선택하세요.</h1>
        </div>

    
        <div>
            <p class="selectText">기준 1</p>
            <select class="selectBox1" v-model="market">
                <option
                v-for="(item, index) in selectListMarket"
                :key="index"
                :value="item.value"
                >{{ item.name }}</option
                >
            </select>
        </div>

        <br>
        <button v-on:click="getMarketCoffee">추천받기</button>
        <br>
        <br>
        <br>
        <br>
        <a-pagination v-model:current="current" simple :total="100" />
        <br>
        <div style="margin: auto; text-align=center" v-if="!result_true">
            <a-layout style="width:50%; display: flex; justify-content: center; margin: auto;">
                <a-layout-sider
                    breakpoint="lg"
                    collapsed-width="0">
                <div class="logo" />

                <a-menu theme="dark" mode="inline">
                    <a-menu-item key="sub12"> </a-menu-item>
                    <a-menu-item key="sub13"> </a-menu-item>
                    <a-sub-menu key="sub1">
                        <template #title>
                        <span>
                            커피 지역
                        </span>
                        </template>
                    </a-sub-menu>
                    <a-menu-item style="text-align: center" key="1">
                    <span class="nav-text">{{result[current-1].growing_region}}</span>
                    </a-menu-item>
                    <a-sub-menu key="sub2">
                        <template #title>
                        <span>
                            커피 나무
                        </span>
                        </template>
                    </a-sub-menu>
                    <a-menu-item style="text-align: center" key="2">
                    <span class="nav-text">{{result[current-1].tree_variety}}</span>
                    </a-menu-item>
                    <a-sub-menu key="sub3">
                        <template #title>
                        <span>
                            로스팅 레벨
                        </span>
                        </template>
                    </a-sub-menu>
                    <a-menu-item style="text-align: center" key="3">
                    <span class="nav-text">{{result[current-1].roast_level}}</span>
                    </a-menu-item>
                </a-menu>
                </a-layout-sider>
                <a-layout>
                <a-layout-header :style="{ background: '#fff', padding: 0 }" >
                    {{result[current-1].growing_region.replace("region_", "")}}에서 자란 {{result[current-1].tree_variety.replace("tree_variety_", "")}} 커피 나무 원두
                </a-layout-header>
                <a-layout-content :style="{ margin: '24px 16px 0' }">
                    <div :style="{ padding: '24px', background: '#fff', minHeight: '360px' }">
                        <Radar 
                        :data= chartData
                        :options="chartOptions"/>
                    </div>
                    <div :style="{ padding: '24px', background: '#fff', minHeight: '360px'}" >
                        <Bar
                        :data= barData
                        :options="barOptions" />
                    </div>
                    
                </a-layout-content>
                <a-layout-footer style="text-align: center">
                    {{current}}번째 커피 원두 추천
                </a-layout-footer>
                </a-layout>
            </a-layout>
        </div>
    </div>
  
</template>


<script>
import { Radar, Bar } from 'vue-chartjs'
import { Chart, registerables } from 'chart.js'
Chart.register(...registerables)

export default {
    components: { Radar, Bar },
    data() {
        return {
            barData: {
                labels: [ 'before_agtron', 'after_agtron'],
                datasets: [{
                    label: 'agtron',
                    data: [44, 65],
                    backgroundColor: [
                        'rgba(255, 205, 86, 0.2)',
                        'rgba(75, 192, 192, 0.2)',
                        'rgba(54, 162, 235, 0.2)',
                        'rgba(153, 102, 255, 0.2)',
                        'rgba(201, 203, 207, 0.2)'
                        ],
                    borderColor: [
                        'rgb(255, 205, 86)',
                        'rgb(75, 192, 192)',
                        'rgb(54, 162, 235)',
                        'rgb(153, 102, 255)',
                        'rgb(201, 203, 207)'
                        ],
                    borderWidth: 1

                }]
            },
            barOptions: {
                responsive: true,
                plugins: {
                    title: {
                        display: true,
                        text: 'Agtron 커피 로스팅 전/후'
                    }
                },
                scales: {
                    y: {
                        display: true,
                        ticks: {
                            beginAtZero: 0,
                            steps: 10,
                            max: 100,
                        }
                    }
                },

            },

            chartData: {
            labels: [ 'aroma', 'acidity', 'body', 'flavor', 'aftertaste' ],
            datasets: [{ 
                label: 'average',
                data: [7, 7, 7, 7, 7],
                fill: true,
                backgroundColor: 'rgba(255, 99, 132, 0.2)',
                borderColor: 'rgb(255, 99, 132)',
                pointBackgroundColor: 'rgb(255, 99, 132)',
                pointBorderColor: '#fff',
                pointHoverBackgroundColor: '#fff',
                pointHoverBorderColor: 'rgb(255, 99, 132)'
                }, {
                label: 'bean',
                data: [6, 6, 6, 6, 6],
                fill: true,
                backgroundColor: 'rgba(54, 162, 235, 0.2)',
                borderColor: 'rgb(54, 162, 235)',
                pointBackgroundColor: 'rgb(54, 162, 235)',
                pointBorderColor: '#fff',
                pointHoverBackgroundColor: '#fff',
                pointHoverBorderColor: 'rgb(54, 162, 235)'
                }]
            },
            chartOptions: {
                responsive: true,
                plugins: {
                    title: {
                        display: true,
                        text: '커피 특징 그래프'
                    }
                },
                scales: {
                    r: {
                        angleLines: {
                            display: false
                        },
                        suggestedMin: 0,
                        suggestedMax: 10
                    }
                },
                chart: {
                    height: 350,
                    type: 'radar',
                    dropShadow: {
                        enabled: true,
                        blur: 1,
                        left: 1,
                        top: 1
                    }
                },
                elements: {
                    line: {
                        borderWidth: 3
                    }
                },
                stroke: {
                    width: 2
                },
                fill: {
                    opacity: 0.1
                },
                markers: {
                    size: 0
                }
            },
            market: "",
            selectListMarket: [
                { name: "선택해주세요.", value: "" },
                { name: "앤젤리너스", value: "angelinus" },
                { name: "이디야커피", value: "ediya" },
                { name: "할리스커피", value: "hollys" },
                { name: "스타벅스", value: "starbucks" },
                { name: "투섬플레이스", value: "twosome" },
                { name: "탐앤탐스", value: "tomntoms" },
            ],
            result: [],
            result_true: true,
            new_arr: [],
            new_arr2: [],
            current: 1
        };
    },
    methods: { 
        changeCharDataset(i) {
            this.new_arr = []
            this.new_arr.push(this.result[i].aroma);
            this.new_arr.push(this.result[i].acidity);
            this.new_arr.push(this.result[i].body);
            this.new_arr.push(this.result[i].flavor);
            this.new_arr.push(this.result[i].aftertaste);
            this.chartData.datasets[1].data = this.new_arr;
            
            this.new_arr2 = []
            this.new_arr2.push(Number(this.result[i].agtron.split('/')[0]));
            this.new_arr2.push(Number(this.result[i].agtron.split('/')[1]));
            this.barData.datasets[0].data = this.new_arr2;
        },
        async getMarketCoffee() {
            this.$axios.get('http://127.0.0.1:8080/market', {
                params: {
                    rc: 200,
                    market: this.market,
                },
            }).then(response => {
                console.log('### response: ' + JSON.stringify(response))
                this.result = response.data.result
                this.result_true = false;
                this.changeCharDataset(0);
            }).catch(error => {
                console.log(error)
            })
        }
    },
    watch: {
        current(newvalue, oldvalue) {
            this.changeCharDataset(oldvalue - 1);
            console.log(oldvalue, newvalue, this.result_true);
            console.log( this.chartData.datasets[1].data);
        }
    },
};
</script>

<style>
#components-layout-demo-basic .code-box-demo {
  text-align: center;
}
#components-layout-demo-basic .ant-layout-header,
#components-layout-demo-basic .ant-layout-footer {
  color: #fff;
  background: #7dbcea;
}
[data-theme='dark'] #components-layout-demo-basic .ant-layout-header {
  background: #6aa0c7;
}
[data-theme='dark'] #components-layout-demo-basic .ant-layout-footer {
  background: #6aa0c7;
}
#components-layout-demo-basic .ant-layout-footer {
  line-height: 1.5;
}
#components-layout-demo-basic .ant-layout-sider {
  color: #fff;
  line-height: 120px;
  background: #3ba0e9;
}
[data-theme='dark'] #components-layout-demo-basic .ant-layout-sider {
  background: #3499ec;
}
#components-layout-demo-basic .ant-layout-content {
  min-height: 120px;
  color: #fff;
  line-height: 120px;
  background: rgba(16, 142, 233, 1);
}
[data-theme='dark'] #components-layout-demo-basic .ant-layout-content {
  background: #107bcb;
}
#components-layout-demo-basic > .code-box-demo > .ant-layout + .ant-layout {
  margin-top: 48px;
}

.selectBox1 {
    position: relative;
    width: 200px;
    height: 40px;
    border-radius: 4px;
    border: 2px solid lightcoral;
    background-size: 20px;
    cursor: pointer;
}

.selectText {
    margin-top:20px;
    font-weight: bold;
}

.back_color {
    background-size: cover;
    background-color:#ebddcc;
    min-height: 100vh;
}

</style>