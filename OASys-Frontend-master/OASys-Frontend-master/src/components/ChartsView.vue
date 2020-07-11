<template>
    <div id="echartss" :style="{width: '1400px', height: '400px'}"></div>
</template>

<script>
    export default {
        name: "ChartsView",
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
                myChart:{},
                data:[],
                title:[],
                echarts1_option:{
                    legend: {
                        data: ['每日机票平均价格']
                    },
                    xAxis: {
                        type: 'category',   // 还有其他的type，可以去官网喵两眼哦
                        data: ['2020-01-01', '2020-01-02', '2020-01-03','2020-01-04'],   // x轴数据
                        name: '日期',   // x轴名称
                        // x轴名称样式
                        nameTextStyle: {
                            fontWeight: 600,
                            fontSize: 18
                        }
                    },
                    yAxis: {
                        type: 'value',
                        name: '机票价格',   // y轴名称
                        // y轴名称样式
                        nameTextStyle: {
                            fontWeight: 600,
                            fontSize: 18
                        }
                    },
                    label: {},
                    tooltip: {
                        trigger: 'axis'   // axis   item   none三个值
                    },
                    series: [{
                        name:'机票价格',
                        data:[100,100,100],
                        type:'line'
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
            this.myChart= this.$echarts.init(document.getElementById('echartss'))
            this.myChart.setOption(this.echarts1_option,true)
            this.myChart.on('click', this.onClick);
            this.data = [];
            this.title = [];
            for(var i =0;i<this.dataList.length; i++){
                this.data.push(this.dataList[i].count)
                this.title.push(this.dataList[i].title)
            }
            this.echarts1_option.series[0].data=this.data;
            this.echarts1_option.series[0].title=this.title;
            this.echarts1_option.xAxis.data=this.title;

        },
        created(){

        },
        methods: {
            onClick: function (param) {
                let dt = param.name;
                this.$emit('charts-click',dt);
            }
        }
    }
</script>



<style scoped>
    #myChart{
        padding-top: 80px;
        color: white;
    }
</style>