<template>
<el-dialog title="新增用户" :visible.sync="addDialogVisible" :showClose=false>
    <el-form :model="forms" :rules="rules" :ref="forms" >
        <el-form-item label="邮箱" prop="email">
            <el-input type="email" v-model="forms.email" placeholder="邮箱"/>
        </el-form-item>
        <el-form-item label="用户名" prop="username">
            <el-input type="text" v-model="forms.username" placeholder="用户名"/>
        </el-form-item>
        <el-form-item label="密码" prop="password">
            <el-input type="password" v-model="forms.password" placeholder="密码"/>
        </el-form-item>
    </el-form>
    <div class="dialog-footer" slot="footer">
        <el-button @click="cancel()">
            取消
        </el-button>
        <el-button @click="onSubmit(forms)"  type="primary">
            确认
        </el-button>
    </div>
</el-dialog>
</template>

<script>
    import {addUser} from "../utils/api";

    export default {
        props:{
            addDialogVisible: {
                type: Boolean
            }
        },
        name: "AddUser",
        data() {
            return {
                forms: {
                    email: "",
                    username: "",
                    password: ""
                },
                rules: {
                    email: [
                        {
                            type: "email",
                            required: true,
                            message: "请输入正确的邮箱",
                            trigger: "blur"
                        }
                    ],
                    username: [
                        {
                            required: true,
                            message: "请输入用户名",
                            trigger: "blur"
                        },
                        {
                            min: 2,
                            max: 10,
                            message: "长度在2到10个字符",
                            trigger: "blur"
                        }
                    ],
                    password: [
                        {
                            required: true,
                            message: "请输入密码",
                            trigger: "blur"
                        },
                        {
                            min: 6,
                            max: 20,
                            message: "长度在6到20个字符",
                            trigger: "blur"
                        }
                    ]
                },
            }
        },
        methods: {
            onSubmit(forms) {
                this.$refs[forms].validate((valid) => {
                    if (valid) {
                        addUser(forms).then(response => {
                            if (response && response.status === "success") {
                                this.$message.success(response.message)
                                this.$emit("close")
                            }
                        })
                    }
                })
            },
            cancel(){
                this.$emit("close");
            }
        }
    }
</script>

<style scoped>
    .add-user {
        text-align: center;
    }

    .el-form {
        max-width: 400px;
        margin: 50px auto auto;
    }
</style>