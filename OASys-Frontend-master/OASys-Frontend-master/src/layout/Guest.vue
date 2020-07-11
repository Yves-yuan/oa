<template>
    <el-container class="text">
        <el-header>
            <TopMenu/>
        </el-header>
        <el-main style="padding: 0;background-color:#F4F6F6">
            <router-view/>
        </el-main>
<!--        <el-footer style="background-color: #B3C0D1">-->
<!--        </el-footer>-->
    </el-container>
</template>

<script>
    // import Chat from "../components/Chat";
    import TopMenu from "../components/TopMenu";
    // import MyFooter from "../components/MyFooter";

    export default {
        name: "Guest",
        components: {
            TopMenu,
            // MyFooter,
            // Chat
        }
    }
</script>

<style scoped>
    .el-header {
        border-bottom: 1px solid #eee;
        line-height: 50px;
        width: 100%;
        box-shadow: 0 1px 3px rgba(26, 26, 26, .1);
    }

    .el-container {
        height: 100%;
    }

    .text {
        color: rgba(123,103,88,0.53);
    }
</style>