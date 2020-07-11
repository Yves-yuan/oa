<template>
    <div class="files">
        <el-card class="box-card" body-style="{padding: '20px' }">
            <div slot="header" class="clearfix">
                <span>操作面板</span>
<!--                <el-button style="float: right; padding: 3px 0" type="text" @click="handleAddFlight(true)">新增数据-->
<!--                </el-button>-->
            </div>
            <el-form label-width="auto"
                     size="small ">
                <el-row :gutter="24">
                    <el-col :span="8">
                        <el-form-item label="出发城市:">
                            <el-input
                                    placeholder="输入出发城市搜索"
                                    v-model="searchParams.cityFromName">
                            </el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="8">
                        <el-form-item label="到达城市:">
                            <el-input label="cityToName："
                                      placeholder="请输入到达城市"
                                      v-model="searchParams.cityToName">
                            </el-input>
                        </el-form-item>
                    </el-col>

                </el-row>

                <el-row :gutter="24">
                    <el-col :span="8">
                        <el-form-item label="航班名:">
                            <el-input label="flightCompanyName："
                                      placeholder="请输入航班名"
                                      v-model="searchParams.flightCompanyName">
                            </el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="8">
                        <el-form-item label="出发日期:">
                            <el-date-picker value-format="yyyy-MM-dd HH:mm:ss"
                                            v-model="searchParams.depDate"
                                            type="date"
                                            placeholder="选择日期">
                            </el-date-picker>
                            <!--                            <el-input label="depDate："-->
                            <!--                                      placeholder="请输入出发日期"-->
                            <!--                                      v-model="searchParams.depDate">-->
                            <!--                            </el-input>-->
                        </el-form-item>
                    </el-col>


                </el-row>
                <el-row :gutter="24">
                    <el-col :span="8">
                        <el-form-item label="价格排序:">
                            <el-select v-model="searchParams.ordering" placeholder="请选择">
                                <el-option
                                        v-for="item in ordering"
                                        :key="item.value"
                                        :label="item.label"
                                        :value="item.value">
                                </el-option>
                            </el-select>
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row :gutter="24" style="float: right;padding-bottom: 20px">
                    <el-col :span="1.5">
                        <el-button type="primary" @click="handleGetFlight()">查询</el-button>
                    </el-col>
                </el-row>
            </el-form>
        </el-card>
        <el-table border :data="flightList" ref="multipleTable"
                  style="width: 100%">
            <el-table-column label="航班信息">
                <i class="el-icon-s-promotion"></i>
            </el-table-column>
            <el-table-column
                    prop="flightCompanyName"
                    label="航空公司名字"
                    align="center"
                    width="100">
            </el-table-column>
            <el-table-column
                    prop="flightNo"
                    label="航班号"
                    align="center">
            </el-table-column>
            <el-table-column
                    prop="cityFromName"
                    label="出发城市"
                    align="center">
            </el-table-column>
            <el-table-column
                    prop="depInfo"
                    label="出发航站楼信息"
                    align="center">
            </el-table-column>
            <el-table-column
                    prop="depDate"
                    label="出发日期"
                    align="center">
            </el-table-column>
            <el-table-column
                    :show-overflow-tooltip="true"
                    prop="depTime"
                    label="出发具体时间"
                    align="center">
            </el-table-column>
            <el-table-column
                    prop="cityToName"
                    label="到达城市"
                    align="center">
            </el-table-column>
            <el-table-column
                    prop="flightStatus"
                    label="经停信息"
                    align="center">
            </el-table-column>
            <el-table-column
                    prop="arrInfo"
                    label="到达航站楼信息"
                    align="center">
            </el-table-column>
            <el-table-column
                    prop="arrTime"
                    label="到达时间"
                    align="center">
            </el-table-column>
            <el-table-column
                    prop="price"
                    label="价格"
                    align="center">
            </el-table-column>
            <el-table-column
                    prop="punctualRate"
                    label="准点率"
                    align="center">
            </el-table-column>
<!--            <el-table-column-->
<!--                    label="操作"-->
<!--                    align="center"-->
<!--                    width="200"-->
<!--                    fixed="right">-->
<!--                <template slot-scope="scope">-->
<!--                    <el-button type="primary" size="mini"-->
<!--                               @click="handleEdit(scope.row)">编辑-->
<!--                    </el-button>-->
<!--                    <el-button type="danger" size="mini"-->
<!--                               @click="deleteFlight(scope.row)">删除-->
<!--                    </el-button>-->
<!--                </template>-->
<!--            </el-table-column>-->
        </el-table>
        <div class="pagination">
            <el-pagination background layout="prev, pager, next" :pager-count="5" :total="total"
                           :hide-on-single-page="false" @current-change="handleCurrentChange">
            </el-pagination>
        </div>
        <el-dialog :visible.sync="editDialogVisible"  >
            <el-form label-width="80px" v-model="flightToEdit" :ref="flightToEdit" :rules="rules">
                <el-input type="hidden" v-model="flightToEdit.id"/>
                <el-form-item label="flightCompanyName" prop="flightCompanyName">
                    <el-input v-model="flightToEdit.flightCompanyName"/>
                </el-form-item>
                <el-form-item label="flightNo" prop="flightNo">
                    <el-input v-model="flightToEdit.flightNo"/>
                </el-form-item>
                <el-form-item label="cityFromName" prop="cityFromName">
                    <el-cascader
                            v-model="flightToEdit.cityFromName"
                            :options="areaOptions"
                    >
                    </el-cascader>
                </el-form-item>
                <el-form-item label="depInfo" prop="depInfo">
                    <el-input v-model="flightToEdit.depInfo"/>
                </el-form-item>
                <el-form-item label="depDate" prop="depDate">
                    <el-date-picker value-format="yyyy-MM-dd HH:mm:ss"
                                    v-model="flightToEdit.depDate"
                                    type="date"
                                    placeholder="选择日期">
                    </el-date-picker>
                </el-form-item>
                <el-form-item label="depTime" prop="depTime">
                    <el-date-picker value-format="yyyy-MM-dd HH:mm:ss"
                                    v-model="flightToEdit.depTime"
                                    type="datetime"
                                    placeholder="选择日期时间">
                    </el-date-picker>
                </el-form-item>
                <el-form-item label="cityToName" prop="cityToName">
                    <el-cascader
                            v-model="flightToEdit.cityToName"
                            :options="areaOptions"
                    >
                    </el-cascader>
                </el-form-item>
                <el-form-item label="flightStatus" prop="flightStatus">
                    <el-input v-model="flightToEdit.flightStatus"/>
                </el-form-item>
                <el-form-item label="arrInfo" prop="arrInfo">
                    <el-input v-model="flightToEdit.arrInfo"/>
                </el-form-item>
                <el-form-item label="arrTime" prop="arrTime">
                    <el-date-picker value-format="yyyy-MM-dd HH:mm:ss"
                                    v-model="flightToEdit.arrTime"
                                    type="datetime"
                                    placeholder="选择日期时间">
                    </el-date-picker>
                </el-form-item>
                <el-form-item label="price" prop="price">
                    <el-input type="number" v-model="flightToEdit.price"/>
                </el-form-item>
                <el-form-item label="punctualRate" prop="punctualRate">
                    <el-input type="number" v-model="flightToEdit.punctualRate"/>
                </el-form-item>
            </el-form>
            <div class="dialog-footer" slot="footer">
                <el-button @click="editDialogVisible = false">
                    取消
                </el-button>
                <el-button @click="updateFlight" type="primary">
                    确认
                </el-button>
            </div>
        </el-dialog>

        <el-dialog :visible.sync="addDialogVisible">
            <el-form v-model="flightToAdd" :ref="flightToAdd"  :model="flightToAdd">
                <el-form-item label="flightCompanyName" prop="flightCompanyName">
                    <el-input v-model="flightToAdd.flightCompanyName"/>
                </el-form-item>
                <el-form-item label="flightNo" prop="flightNo">
                    <el-input v-model="flightToAdd.flightNo"/>
                </el-form-item>
                <el-form-item label="cityFromName" prop="cityFromName">
                    <el-cascader
                            v-model="flightToAdd.cityFromName"
                            :options="areaOptions"
                    >
                    </el-cascader>
                </el-form-item>
                <el-form-item label="depInfo" prop="depInfo">
                    <el-input v-model="flightToAdd.depInfo"/>
                </el-form-item>
                <el-form-item label="depDate" prop="depDate">
                    <el-date-picker value-format="yyyy-MM-dd HH:mm:ss"
                                    v-model="flightToAdd.depDate"
                                    type="date"
                                    placeholder="选择日期">
                    </el-date-picker>
                </el-form-item>
                <el-form-item label="depTime" prop="depTime">
                    <el-date-picker value-format="yyyy-MM-dd HH:mm:ss"
                                    v-model="flightToAdd.depTime"
                                    type="datetime"
                                    placeholder="选择日期时间">
                    </el-date-picker>
                </el-form-item>
                <el-form-item label="cityToName" prop="cityToName">
                    <el-cascader
                            v-model="flightToAdd.cityToName"
                            :options="areaOptions"
                    >
                    </el-cascader>
                </el-form-item>
                <el-form-item label="flightStatus" prop="flightStatus">
                    <el-input v-model="flightToAdd.flightStatus"/>
                </el-form-item>
                <el-form-item label="arrInfo" prop="arrInfo">
                    <el-input v-model="flightToAdd.arrInfo"/>
                </el-form-item>
                <el-form-item label="arrTime" prop="arrTime">
                    <el-date-picker value-format="yyyy-MM-dd HH:mm:ss"
                                    v-model="flightToAdd.arrTime"
                                    type="datetime"
                                    placeholder="选择日期时间">
                    </el-date-picker>
                </el-form-item>
                <el-form-item label="price" prop="price">
                    <el-input type="number" v-model="flightToAdd.price"/>
                </el-form-item>
                <el-form-item label="punctualRate" prop="punctualRate">
                    <el-input type="number" v-model="flightToAdd.punctualRate"/>
                </el-form-item>
            </el-form>
            <div class="dialog-footer" slot="footer">
                <el-button @click="editDialogVisible = false">
                    取消
                </el-button>
                <el-button @click="addFlight" type="primary">
                    确认
                </el-button>
            </div>
        </el-dialog>
    </div>
</template>
<script>
    import {addFlight, getFlight, updateFlight, deleteFlight} from "../utils/api";
    import area from '../utils/country-level2-data.js'
    import {mapState} from "vuex";

    export default {
        name: "Flight",
        data() {
            return {
                areaOptions:area,
                flightList: [],
                flightToEdit: {
                    depDate:'',
                },
                flightToAdd: {
                    depDate:'',
                },
                total: 0,
                addFolderDialog: false,
                uploadFileDialog: false,
                renameFileDialog: false,
                editDialogVisible: false,
                addDialogVisible: false,
                searchParams: {},
                rules: {
                    flightCompanyName: [
                        {
                            max: 10,
                            message: "长度不超过10",
                            trigger: "blur"
                        }
                    ],
                    flightNo: [
                        {
                            max: 10,
                            message: "长度不超过10",
                            trigger: "blur"
                        }
                    ],
                    cityFromName: [
                        {
                            max: 10,
                            message: "长度不超过10",
                            trigger: "blur"
                        }
                    ],
                    depInfo: [
                        {
                            max: 10,
                            message: "长度不超过10",
                            trigger: "blur"
                        }
                    ],
                    depDate: [
                        {
                            required:true,
                            message: "请输入出发日期",
                            trigger: "blur"
                        }
                    ],
                    depTime: [
                        {
                            required:true,
                            message: "请输入出发具体时间",
                            trigger: "blur"
                        }
                    ],
                    cityToName: [
                        {
                            max: 10,
                            message: "长度不超过10",
                            trigger: "blur"
                        }
                    ],
                    flightStatus: [
                        {
                            max: 10,
                            message: "长度不超过10",
                            trigger: "blur"
                        }
                    ],
                    arrInfo: [
                        {
                            max: 10,
                            message: "长度不超过10",
                            trigger: "blur"
                        }
                    ],
                    arrTime: [
                        {
                            required: true,
                            message: "请填写时间",
                            trigger: "blur"
                        }
                    ],
                    price: [
                        {
                            type: 'number',
                            message: "请输入数值",
                            trigger: "blur"
                        }
                    ],
                    punctualRate: [
                        {
                            type: 'number',
                            message: "请输入数值",
                            trigger: "blur"
                        }
                    ],
                },
            }
        },
        props: [],

        created() {
            this.handleGetFlight()
        },
        computed: {
            ...mapState(["auth"])
        },
        methods: {
            handleAddFlight() {
                this.addDialogVisible = true;
            },
            addFlight() {
                alert(JSON.stringify(this.flightToAdd));
                if(this.flightToAdd.cityFromName){
                    this.flightToAdd.cityFromName=this.flightToAdd.cityFromName.join(',')
                }
                if(this.flightToAdd.cityToName){
                    this.flightToAdd.cityToName=this.flightToAdd.cityToName.join(',')
                }
                addFlight(this.flightToAdd).then(response => {
                    if (response && response.status === "success") {
                        this.$message.success(response.message)
                        this.addDialogVisible = false
                        this.flightToAdd={}
                        this.handleGetFlight()
                    }
                })
            },
            handleEdit(row) {
                alert(JSON.stringify(row))
                this.flightToEdit = row
                if (this.flightToEdit.cityToName){
                    this.flightToEdit.cityToName = this.flightToEdit.cityToName.split(',');
                }
                if (this.flightToEdit.cityFromName){
                    this.flightToEdit.cityFromName = this.flightToEdit.cityFromName.split(',');
                }
                this.editDialogVisible = true;
            },
            deleteFlight(row) {
                this.$confirm("永久删除这些数据, 是否继续?")
                    .then(() => {
                        let ids = [row.id];
                        deleteFlight(ids).then(response => {
                            if (response && response.status === "success") {
                                this.$message.success(response.message)
                                this.handleGetFlight()
                            }
                        })
                    })
            },
            handleGetFlight() {
                getFlight(this.searchParams).then(response => {
                    if (response && response.status === "success") {
                        this.total = response.total
                        this.flightList = response.object
                    }
                })
            },
            updateFlight() {
                if(this.flightToEdit.cityFromName){
                    this.flightToEdit.cityFromName=this.flightToEdit.cityFromName.join(',')
                }
                if(this.flightToEdit.cityToName){
                    this.flightToEdit.cityToName=this.flightToEdit.cityToName.join(',')
                }
                updateFlight(this.flightToEdit).then(response => {
                    if (response && response.status === "success") {
                        this.$message.success(response.message)
                        this.editDialogVisible = false
                        this.handleGetFlight()
                    }
                })
            },
            uploadFile() {
                this.uploadFileDialog = true
            },
            uploadSuccess(response) {
                this.$message.success(response.message)
                this.handleGetFlight()
                this.uploadFileDialog = false
            },
            handleCurrentChange(pageNumber) {
                this.searchParams.pageNumber = pageNumber;
                this.handleGetFlight();
            },
        }
    }
</script>

<style scoped>
    .path {
        float: right
    }

    .search {
        float: right
    }
</style>