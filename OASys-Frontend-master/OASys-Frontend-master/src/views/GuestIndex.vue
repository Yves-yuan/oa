<template>
        <div class="guest">
                <ul  v-infinite-scroll="load" class="guest-list">
                    <div style="height: 50px"></div>
                    <li v-for="i in count" :key="i" class="infinite-list-item">
                        <el-card class="box-card" >
                            <div slot="header" class="clearfix title">
                                <span >文章标题</span>
                            </div>
                            <div v-for="o in 1" :key="o" class="text item">
                                {{'文章内容内容 ' }}
                            </div>
                        </el-card>
                    </li>

                </ul>

        </div>

</template>

<script>
    export default {
        data(){
            return {
                count: 50
            }

        },
        methods:{
            load(){
                this.count = this.count+5;
            }
        }
    }
</script>
<style scoped>
    .guest {
        display: flex;
        width: 100%;
        height: 100%;
    }
    .guest-list {
        height: 100%;
        overflow-y: auto;
        width: 100%;
    }
    .infinite-list-item{
        display: flex;
        justify-content: center;
    }

    .text {
        font-size: 14px;
        color: rgba(123,103,88,0.53);
    }

    .item {
        margin-bottom: 18px;

    }

    .clearfix:before,
    .clearfix:after {
        display: table;
        content: "";
    }
    .clearfix:after {
        clear: both
    }

    .box-card {
        width: 480px;

    }
    .title{
        color: rgba(123,103,88,0.53);
    }

</style>