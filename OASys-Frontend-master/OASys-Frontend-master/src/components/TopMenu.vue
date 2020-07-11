<template>
    <div class="head">
        <el-menu :default-active="activeIndex"  text-color=rgba(123,103,88,0.53) mode="horizontal" @select="handleSelect"
                 router active-text-color="#C0C0C0">
            <el-menu-item index="/guest" >新闻中心</el-menu-item>
<!--            <el-submenu index="/guest">-->
<!--                <template slot="title">我的工作台</template>-->
<!--                <el-menu-item index="/guest">选项1</el-menu-item>-->
<!--                <el-menu-item index="/guest">选项2</el-menu-item>-->
<!--                <el-menu-item index="/guest">选项3</el-menu-item>-->
<!--                <el-submenu index="/guest">-->
<!--                    <template slot="title">选项4</template>-->
<!--                    <el-menu-item index="/guest">选项1</el-menu-item>-->
<!--                    <el-menu-item index="/guest">选项2</el-menu-item>-->
<!--                    <el-menu-item index="/guest">选项3</el-menu-item>-->
<!--                </el-submenu>-->
<!--            </el-submenu>-->
<!--            <el-menu-item index="/guest" >消息中心</el-menu-item>-->
<!--            <el-menu-item index="/guest" ><a  target="_blank">订单管理</a></el-menu-item>-->
            <el-menu-item index="/login">后台管理</el-menu-item>
        </el-menu>
    </div>


</template>

<script>
    export default {
        name: "TopMenu" ,
        data() {
            return {
                activeIndex: '/guest',
                activeIndex2: '/guest'
            };
        },
        methods: {
            handleSelect(key, keyPath) {
                console.log(key, keyPath);
            }
        }
    }
</script>

<style scoped>
    .head {
        display: flex;
        justify-content: center; /* 水平居中 */
        align-items: center;     /* 垂直居中 */
    }
</style>