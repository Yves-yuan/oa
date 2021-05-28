<template>
    <div class="login">
        <el-form :model="forms" :rules="rules" :ref="forms" class="login-form">
<!--            <el-image :src="require('../assets/favicon.ico')"></el-image>-->
            <el-form-item class="logo" align-items="center" justify-content="center" >
                <el-image :src="require('../assets/logo.png')" fit="cover" align-items="center" justify-content="center"></el-image>
            </el-form-item>
            <h3 class="title">
                APL Inventory User View
            </h3>
            <el-form-item prop="username">
                <el-input autofocus type="text" v-model="forms.username" placeholder="username"/>
            </el-form-item>
            <el-form-item prop="password">
                <el-input type="password" v-model="forms.password" placeholder="password"
                          @keyup.enter.native="onSubmit(forms)"/>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="onSubmit(forms)" :loading="load" round>login</el-button>
            </el-form-item>
        </el-form>
    </div>
</template>

<script>
    import qs from "qs";
    import {setAuth} from "../utils/auth";
    import {getAuth, login} from "../utils/api";

    export default {
        name: "Login",
        data() {
            return {
                forms: {
                    username: "",
                    password: ""
                },
                rules: {
                    username: [
                        {
                            required: true,
                            message: "User name needed",
                            trigger: "blur"
                        }
                    ],
                    password: [
                        {
                            required: true,
                            message: "Password needed",
                            trigger: "blur"
                        }
                    ]
                },
                load: false
            }
        },
        methods: {
            onSubmit(forms) {
                this.$refs[forms].validate((valid) => {
                    if (valid) {
                        this.load = true
                        login(qs.stringify(forms)).then(response => {
                            this.load = false
                            if (response && response.status === "success") {
                                getAuth().then(response => {
                                    setAuth(response.object)
                                })
                                this.$message.success(response.message)
                                this.$router.push({name: "index"})
                            }
                        })
                    }
                })
            }
        }
    }
</script>

<style scoped>
    .login {
        background-image: url(/img/background.jpg);
        background-size: cover;
        display: flex;
        justify-content: center;
        align-items: center;
        height: 100%;
    }

    .title {
        margin: 0 auto 30px;
        text-align: center;
        color: #707070;
    }
    .logo {
        text-align: center;
    }

    .login-form {
        padding: 25px 25px 5px;
        border-radius: 10px;
        background: #fff;
        width: 300px;
    }
    .el-image{
        text-align: center;
    }
    .el-button {
        width: 100%;
    }
</style>