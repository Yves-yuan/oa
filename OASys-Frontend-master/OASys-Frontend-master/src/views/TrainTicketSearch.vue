<template>
    <div class="files">
        <el-card class="box-card" body-style="{padding: '20px' }">
            <div slot="header" class="clearfix">
                <span>操作面板</span>
            </div>
            <el-form label-width="auto"
                     size="small ">
                <el-row :gutter="24">
                    <el-col :span="8">
                        <el-form-item label="出发地:">
                            <el-input
                                    placeholder="输入出发城市搜索"
                                    v-model="searchParams.fromName">
                            </el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="8">
                        <el-form-item label="到达地:">
                            <el-input label="cityToName："
                                      placeholder="请输入到达城市"
                                      v-model="searchParams.toName">
                            </el-input>
                        </el-form-item>
                    </el-col>

                </el-row>

                <el-row :gutter="24">
                    <el-col :span="8">
                        <el-form-item label="车次号:">
                            <el-input label="trainNum："
                                      placeholder="请输入车次号"
                                      v-model="searchParams.trainNum">
                            </el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="8">
                        <el-time-select
                                placeholder="起始时间"
                                v-model="searchParams.depTimeFrom"
                                :picker-options="{
                          start: '08:30',
                          step: '00:15',
                          end: '18:30'
                        }">
                        </el-time-select>

                    </el-col>
                    <el-col :span="8">
                        <el-time-select
                                placeholder="结束时间"
                                v-model="searchParams.depTimeTo"
                                :picker-options="{
                                  start: '08:30',
                                  step: '00:15',
                                  end: '18:30',
                                  minTime: searchParams.depTimeFrom
                                }">
                        </el-time-select>
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
                        <el-button type="primary" @click="handleGetTrainTicket()">查询</el-button>
                    </el-col>
                </el-row>
            </el-form>
        </el-card>
        <el-table border :data="trainTicketList" ref="multipleTable"
                  style="width: 100%">
            <el-table-column
                    prop="id"
                    label="id"
                    align="center"
                    width="100">
            </el-table-column>
            <el-table-column
                    prop="fromName"
                    label="出发地点"
                    align="center"
                    width="100">
            </el-table-column>
            <el-table-column
                    prop="toName"
                    label="到达地点"
                    align="center">
            </el-table-column>
            <el-table-column
                    prop="trainNum"
                    label="车次号"
                    align="center">
            </el-table-column>
            <el-table-column
                    prop="depTime"
                    label="出发时间"
                    align="center">
            </el-table-column>
            <el-table-column
                    prop="arrTime"
                    label="预计到达时间"
                    align="center">
            </el-table-column>
            <el-table-column
                    prop="price"
                    label="价格"
                    align="center">
            </el-table-column>
            <el-table-column
                    prop="remarks"
                    label="备注"
                    align="center">
            </el-table-column>
            <el-table-column
                    label="操作"
                    align="center"
                    width="200"
                    fixed="right">
                <template slot-scope="scope">
                    <el-button type="primary" size="mini"
                               @click="handleOrder(scope.row)">订票
                    </el-button>
                </template>
            </el-table-column>
        </el-table>
        <div class="pagination">
            <el-pagination background layout="prev, pager, next" :pager-count="5" :total="total"
                           :hide-on-single-page="false" @current-change="handleCurrentChange">
            </el-pagination>
        </div>
        <el-dialog :visible.sync="editDialogVisible">
            <el-form label-width="80px" v-model="trainTicketToEdit" :ref="trainTicketToEdit" :rules="rules">
                <el-input type="hidden" v-model="trainTicketToEdit.id"/>
                <el-form-item label="trainNum" prop="trainNum">
                    <el-input v-model="trainTicketToEdit.trainNum"/>
                </el-form-item>
                <el-form-item label="fromName" prop="fromName">
                    <el-cascader
                            v-model="trainTicketToEdit.fromName"
                            :options="areaOptions"
                    >
                    </el-cascader>
                </el-form-item>
                <el-form-item label="toName" prop="toName">
                    <el-cascader
                            v-model="trainTicketToEdit.toName"
                            :options="areaOptions"
                    >
                    </el-cascader>
                </el-form-item>
                <el-date-picker
                        value-format="yyyy-MM-dd HH:mm:ss"
                        format="yyyy-MM-dd HH:mm:ss"
                        v-model="depAndArrTime"
                        type="datetimerange"
                        start-placeholder="车次出发时间"
                        end-placeholder="车次预计到达时间"
                        :default-time="['12:00:00']">
                </el-date-picker>
                <el-form-item label="price" prop="price">
                    <el-input  type="number" v-model="trainTicketToEdit.price"/>
                </el-form-item>
                <el-form-item label="备注" prop="remarks">
                    <el-input   v-model="trainTicketToEdit.remarks"/>
                </el-form-item>
            </el-form>

            <div class="dialog-footer" slot="footer">
                <el-button @click="editDialogVisible = false">
                    取消
                </el-button>
                <el-button @click="updateTrainTicket" type="primary">
                    确认
                </el-button>
            </div>
        </el-dialog>

        <el-dialog :visible.sync="addDialogVisible">
            <el-form v-model="trainTicketToAdd" :ref="trainTicketToAdd" :model="trainTicketToAdd">
                <el-form-item label="trainNum" prop="trainNum">
                    <el-input v-model="trainTicketToAdd.trainNum"/>
                </el-form-item>
                <el-form-item label="fromName" prop="fromName">
                    <el-cascader
                            v-model="trainTicketToAdd.fromName"
                            :options="areaOptions"
                    >
                    </el-cascader>
                </el-form-item>
                <el-form-item label="toName" prop="toName">
                    <el-cascader
                            v-model="trainTicketToAdd.toName"
                            :options="areaOptions"
                    >
                    </el-cascader>
                </el-form-item>
                <el-date-picker
                        value-format="yyyy-MM-dd HH:mm:ss"
                        format="yyyy-MM-dd HH:mm:ss"
                        v-model="depAndArrTime"
                        type="datetimerange"
                        start-placeholder="车次出发时间"
                        end-placeholder="车次预计到达时间"
                        :default-time="['12:00:00']">
                </el-date-picker>
                <el-form-item label="price" prop="price">
                    <el-input  type="number" v-model="trainTicketToAdd.price"/>
                </el-form-item>
                <el-form-item label="备注" prop="remarks">
                    <el-input   v-model="trainTicketToAdd.remarks"/>
                </el-form-item>
            </el-form>
            <div class="dialog-footer" slot="footer">
                <el-button @click="addDialogVisible = false">
                    取消
                </el-button>
                <el-button @click="addTrainTicket" type="primary">
                    确认
                </el-button>
            </div>
        </el-dialog>
    </div>
</template>

<script>
    import {addTrainTicketOrder,addTrainTicket, getTrainTicket, updateTrainTicket, deleteTrainTicket} from "../utils/api";
    import area from '../utils/country-level2-data.js'
    import {mapState} from "vuex";

    export default {
        name: "TrainTicketSearch",
        data() {
            return {
                depAndArrTime:[],
                ordering: [{label: '价格升序', value: 0}, {label: '价格降序', value: 1}],
                areaOptions: area.filter(item=>item.value==='广西壮族自治区'),
                trainTicketList: [],
                trainTicketToEdit: {
                    depDate: '',
                },
                trainTicketToOrder: {

                },
                trainTicketToAdd: {
                    depDate: '',
                },
                total: 0,
                addFolderDialog: false,
                uploadFileDialog: false,
                renameFileDialog: false,
                editDialogVisible: false,
                addDialogVisible: false,
                searchParams: {},
                rules: {
                    fromName: [
                        {
                            required: true,
                            max: 50,
                            message: "长度不超过50",
                            trigger: "blur"
                        }
                    ],
                    toName: [
                        {
                            required: true,
                            max: 50,
                            message: "长度不超过50",
                            trigger: "blur"
                        }
                    ],
                    trainNum: [
                        {
                            required: true,
                            max: 10,
                            message: "长度不超过10",
                            trigger: "blur"
                        }
                    ],
                    depTime: [
                        {
                            required: true,
                            message: "请输入出发时间",
                            trigger: "blur"
                        }
                    ],
                    arrTime: [
                        {
                            required: true,
                            message: "请输入到达时间",
                            trigger: "blur"
                        }
                    ],
                    price: [
                        {
                            required: true,
                            message: "请输入价格",
                            trigger: "blur"
                        }
                    ],
                },
            }
        },
        props: [],

        created() {
            this.handleGetTrainTicket()
        },
        computed: {
            ...mapState(["auth"])
        },
        methods: {
            handleAddTrainTicket() {
                this.addDialogVisible = true;
            },
            addTrainTicket() {
                alert(JSON.stringify(this.depAndArrTime))
                if (this.trainTicketToAdd.fromName) {
                    this.trainTicketToAdd.fromName = this.trainTicketToAdd.fromName.join(',')
                }
                if (this.trainTicketToAdd.toName) {
                    this.trainTicketToAdd.toName = this.trainTicketToAdd.toName.join(',')
                }
                this.trainTicketToAdd.depTime=this.depAndArrTime[0];
                this.trainTicketToAdd.arrTime=this.depAndArrTime[1];
                addTrainTicket(this.trainTicketToAdd).then(response => {
                    if (response && response.status === "success") {
                        this.$message.success(response.message)
                        this.addDialogVisible = false
                        this.trainTicketToAdd = {}
                        this.handleGetTrainTicket()
                    }
                })
            },
            handleOrder(row) {
                this.trainTicketToOrder.user={}
                this.trainTicketToOrder.trainTicket={}
                this.trainTicketToOrder.user.id = this.$store.state.auth.id
                this.trainTicketToOrder.trainTicket.id = row.id
                addTrainTicketOrder(this.trainTicketToOrder).then(response=>{
                    if (response && response.status === "success") {
                        this.$message.success(response.message)
                    }else{
                        this.$message.error(response.message)
                    }
                })
            },
            handleEdit(row) {
                this.trainTicketToEdit = row
                if (this.trainTicketToEdit.cityToName) {
                    this.trainTicketToEdit.cityToName = this.trainTicketToEdit.cityToName.split(',');
                }
                if (this.trainTicketToEdit.cityFromName) {
                    this.trainTicketToEdit.cityFromName = this.trainTicketToEdit.cityFromName.split(',');
                }
                this.editDialogVisible = true;
            },
            deleteTrainTicket(row) {
                this.$confirm("永久删除这些数据, 是否继续?")
                    .then(() => {
                        let ids = [row.id];
                        deleteTrainTicket(ids).then(response => {
                            if (response && response.status === "success") {
                                this.$message.success(response.message)
                                this.handleGetTrainTicket()
                            }
                        })
                    })
            },
            handleGetTrainTicket() {
                getTrainTicket(this.searchParams).then(response => {
                    if (response && response.status === "success") {
                        this.total = response.total
                        this.trainTicketList = response.object
                    }
                })
            },
            updateTrainTicket() {
                if (this.trainTicketToEdit.cityFromName) {
                    this.trainTicketToEdit.cityFromName = this.trainTicketToEdit.cityFromName.join(',')
                }
                if (this.trainTicketToEdit.cityToName) {
                    this.trainTicketToEdit.cityToName = this.trainTicketToEdit.cityToName.join(',')
                }
                updateTrainTicket(this.trainTicketToEdit).then(response => {
                    if (response && response.status === "success") {
                        this.$message.success(response.message)
                        this.editDialogVisible = false
                        this.handleGetTrainTicket()
                    }
                })
            },
            uploadFile() {
                this.uploadFileDialog = true
            },
            uploadSuccess(response) {
                this.$message.success(response.message)
                this.handleGetTrainTicket()
                this.uploadFileDialog = false
            },
            handleCurrentChange(pageNumber) {
                this.searchParams.pageNumber = pageNumber;
                this.handleGetTrainTicket();
            },
        }
    }
</script>

<style scoped>

</style>