<template>
    <div id="zhuzhuang" :style="{width: '1400px', height: '400px'}"></div>
</template>

<script>
    export default {
        name: "ChartZhuzhuang",
        components: {
        },
        watch: {
            //观察option的变化
            echarts1_option: {
                handler: function(newVal, oldVal) {
                    if (this.myChart) {
                        if (newVal) {
                            this.myChart.setOption(newVal);
                        } else {
                            this.myChart.setOption(oldVal);
                        }
                    }
                },
                deep: true //对象内部属性的监听，关键。
            },dataList:{
                handler: function(newVal) {
                    if (this.myChart) {
                        if (newVal) {
                            this.data = [];
                            this.title = [];
                            for(var i =0;i<this.dataList.length; i++){
                                this.data.push(this.dataList[i].count)
                                this.title.push(this.dataList[i].title)
                            }
                            this.echarts1_option.series[0].data=this.data;
                            this.echarts1_option.series[0].title=this.title;
                            this.echarts1_option.xAxis.data=this.title;
                        }
                    }
                },
            }
        },
        data(){
            return{
                legend: {
                    data: ['机票价格']
                },
                myChart:{},
                data:[],
                title:[],
                echarts1_option:{
                    title: {
                        text: '当日机票价格详情',
                        x:'center',
                        textAlign:'left',
                        top:'3%'
                    },
                    tooltip: {
                        trigger: 'axis',
                        axisPointer: {
                            type: 'shadow'
                        }
                    },
                    grid: {
                        left: '1%',
                        right: '1%',
                        top: '15%',
                        bottom:'10%',
                        containLabel: true
                    },
                    backgroundColor:'#fff',
                    xAxis: {
                        type: 'category',
                        data: [],
                        axisLine:{
                            lineStyle:{
                                color:'#9a9a9a'
                            }
                        }
                    },
                    yAxis: {
                        name:'机票价格',
                        type: 'value',
                        axisLine:{
                            lineStyle:{
                                color:'#9a9a9a'
                            }
                        }
                    },
                    series: [{
                        data: [],
                        type: 'bar',
                        itemStyle:{
                            normal:{
                                color:'#720f2c'
                            }
                        }
                    }]
                },
            }
        },
        props: {
            dataList: {
                type: [Array],
            },
        },
        mounted(){
            this.myChart= this.$echarts.init(document.getElementById('zhuzhuang'))
            this.myChart.setOption(this.echarts1_option,true)
            this.myChart.on('click', this.onClick);
            if (this.dataList.length !== 0){
                this.data = [];
                this.title = [];
                for(var i =0;i<this.dataList.length; i++){
                    this.data.push(this.dataList[i].count)
                    this.title.push(this.dataList[i].title)
                }
                this.echarts1_option.series[0].data=this.data;
                this.echarts1_option.series[0].title=this.title;
                this.echarts1_option.xAxis.data=this.title;
            }


        },
        created(){

        },
        methods: {
            onClick: function (param) {
                let index = param.dataIndex;
                let data = this.dataList[index]
                let d = data.d
                alert(JSON.stringify(d));
            }
        }
    }
</script>

<style scoped>
</style>