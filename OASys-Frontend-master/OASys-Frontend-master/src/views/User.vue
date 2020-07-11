<template>
    <div class="user">
        <el-card class="box-card">
            <div slot="header" class="clearfix">
                <span>操作面板</span>
                <el-button style="float: right; padding: 3px 0" type="text" @click="handleAdd(true)">新增用户</el-button>
            </div>
            <el-form  label-width="100px"
                     size="small ">
                <el-row :gutter="24">
                    <el-col :span="4">
                        <el-form-item label="用户名:" >
                            <el-input
                                    placeholder="请输入用户名"
                                    v-model="searchParams.username">
                            </el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="4">
                        <el-form-item label="邮箱:">
                            <el-input label="邮箱："
                                      placeholder="请输入邮箱"
                                      v-model="searchParams.email">
                            </el-input>
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row :gutter="24" style="float: right">
                    <el-col :span="1.5">
                        <el-button type="primary" @click="getUsers()">查询</el-button>
                    </el-col>
                    <el-col :span="1.5">
                        <el-button type="primary" @click="handleAdd(true)">新增</el-button>
                    </el-col>
                </el-row>
            </el-form>
        </el-card>
        <el-table border :data="users" ref="multipleTable"
                  style="width: 100%">
            <el-table-column
                    type="selection"
                    width="50"
                    align="center">
            </el-table-column>
            <el-table-column
                    prop="id"
                    label="ID"
                    align="center"
                    width="100">
            </el-table-column>
            <el-table-column
                    prop="picture"
                    label="头像"
                    align="center"
                    width="100">
                <template slot-scope="scope">
                    <el-avatar :src="scope.row.picture"
                               size="small"/>
                </template>
            </el-table-column>
            <el-table-column
                    prop="username"
                    label="用户名"
                    align="center"
                    width="150">
            </el-table-column>
            <el-table-column
                    prop="role.name"
                    label="角色"
                    align="center"
                    width="100">
            </el-table-column>
            <el-table-column
                    prop="email"
                    label="邮箱"
                    align="center"
                    width="200">
            </el-table-column>
            <el-table-column
                    prop="synopsis"
                    label="简介">
            </el-table-column>
            <el-table-column
                    label="操作"
                    align="center"
                    width="200">
                <template slot-scope="scope">
                    <el-button type="primary" size="mini"
                               @click="handleEdit(scope.row)">编辑
                    </el-button>
                    <el-button type="danger" size="mini"
                               @click="deleteSingleUser(scope.row)">删除
                    </el-button>
                </template>
            </el-table-column>
        </el-table>
        <div class="menu">
            <el-pagination background layout="prev, pager, next" :pager-count="5" :total="total"
                           :hide-on-single-page="false" @current-change="handleCurrentChange"
                           :page-size="8">
            </el-pagination>
            <el-button type="danger" size="small" @click="deleteUser">删除</el-button>
        </div>
        <el-dialog :visible.sync="editDialogVisible">
            <el-form label-width="80px" v-model="user">
                <el-input type="hidden" v-model="user.id"/>
                <el-form-item label="用户名" prop="username">
                    <el-input v-model="user.username"/>
                </el-form-item>
                <el-form-item label="密码" prop="password">
                    <el-input type="password" v-model="user.password"
                              placeholder="不需要修改密码则留空"/>
                </el-form-item>
                <el-form-item label="角色" prop="role_id">
                    <el-select v-model="user.role.id">
                        <el-option :key="item.key" :label="item.name"
                                   :value="item.id" v-for="item in roles"/>
                    </el-select>
                </el-form-item>
            </el-form>
            <div class="dialog-footer" slot="footer">
                <el-button @click="editDialogVisible = false">
                    取消
                </el-button>
                <el-button @click="updateUser" type="primary">
                    确认
                </el-button>
            </div>
        </el-dialog>
        <add-user :addDialogVisible="addDialogVisible" @close="handleAdd(false)"></add-user>
    </div>
</template>

<script>
    import {getUsers, getRoles, updateUser, deleteUser} from "../utils/api";
    import AddUser from './AddUser.vue';

    export default {
        name: "User",
        components: {AddUser},
        data() {
            return {
                users: [],
                roles: [],
                total: 0,
                user: {
                    role: {}
                },
                editDialogVisible: false,
                addDialogVisible: false,
                searchParams: {},
            }
        },
        created() {
            this.getUsers()
            this.getRoles()
        },
        methods: {
            getUsers() {
                getUsers(this.searchParams).then(response => {
                    if (response && response.status === "success") {
                        this.total = response.total
                        this.users = response.object
                    }
                })
            },
            getRoles() {
                getRoles({}).then(response => {
                    if (response && response.status === "success") {
                        this.roles = response.object
                    }
                })
            },
            handleEdit(row) {
                this.user = row
                this.user.password = null
                this.editDialogVisible = true
            },
            handleAdd(value) {
                this.addDialogVisible = value;
                this.getUsers();
            },
            updateUser() {
                updateUser(this.user).then(response => {
                    if (response && response.status === "success") {
                        this.$message.success(response.message)
                        this.editDialogVisible = false
                        this.getUsers()
                    }
                })
            },
            deleteSingleUser(row) {
                this.$confirm("永久删除这些用户, 是否继续?")
                    .then(() => {
                        let ids = [row.id];
                        deleteUser(ids).then(response => {
                            if (response && response.status === "success") {
                                this.$message.success(response.message)
                                this.getUsers()
                            }
                        })
                    })
            },
            deleteUser() {
                this.$confirm("永久删除这些用户, 是否继续?")
                    .then(() => {
                        if (this.$refs.multipleTable.selection < 1) {
                            this.$message.error("至少选择一个用户！")
                        } else {
                            var ids = []
                            this.$refs.multipleTable.selection.forEach(item => {
                                ids.push(item.id)
                            })
                            deleteUser(ids).then(response => {
                                if (response && response.status === "success") {
                                    this.$message.success(response.message)
                                    this.getUsers()
                                }
                            })
                        }
                    })
                    .catch(() => {
                        this.$message.info("已取消删除")
                    })
            },
            handleCurrentChange(pageNumber) {
                this.searchParams.pageNumber = pageNumber;
                this.getUsers();
            }
        }
    }
</script>

<style scoped>
    .el-pagination {
        margin-right: auto;
    }

    .menu {
        margin-top: 20px;
        display: flex;
    }

    .el-row {
        margin-bottom: 20px;

    &
    :last-child {
        margin-bottom: 0;
    }

    }
    .el-col {
        border-radius: 4px;
    }

    .bg-purple-dark {
        background: #DCDFE6;
    }

    .bg-purple {
        background: #d3dce6;
    }

    .bg-purple-light {
        background: #e5e9f2;
    }

    .grid-content {
        border-radius: 4px;
        min-height: 36px;
    }

    .row-bg {
        padding: 10px 0;
        background-color: #f9fafc;
    }
</style>