<template>
    <div class="files">
<!--        <el-card class="box-card" body-style="{padding: '20px' }">-->
<!--            <div slot="header" class="clearfix">-->
<!--                <span>操作面板</span>-->
<!--            </div>-->
<!--            <el-form label-width="auto"-->
<!--                     size="small ">-->
<!--                <el-row :gutter="24">-->
<!--                    <el-col :span="8">-->
<!--                        <el-form-item label="出发地:">-->
<!--                            <el-input-->
<!--                                    placeholder="输入出发城市搜索"-->
<!--                                    v-model="searchParams.fromName">-->
<!--                            </el-input>-->
<!--                        </el-form-item>-->
<!--                    </el-col>-->
<!--                    <el-col :span="8">-->
<!--                        <el-form-item label="到达地:">-->
<!--                            <el-input label="cityToName："-->
<!--                                      placeholder="请输入到达城市"-->
<!--                                      v-model="searchParams.toName">-->
<!--                            </el-input>-->
<!--                        </el-form-item>-->
<!--                    </el-col>-->

<!--                </el-row>-->

<!--                <el-row :gutter="24">-->
<!--                    <el-col :span="8">-->
<!--                        <el-form-item label="车次号:">-->
<!--                            <el-input label="trainNum："-->
<!--                                      placeholder="请输入车次号"-->
<!--                                      v-model="searchParams.trainNum">-->
<!--                            </el-input>-->
<!--                        </el-form-item>-->
<!--                    </el-col>-->
<!--                    <el-col :span="8">-->
<!--                        <el-time-select-->
<!--                                placeholder="起始时间"-->
<!--                                v-model="searchParams.depTimeFrom"-->
<!--                                :picker-options="{-->
<!--                          start: '08:30',-->
<!--                          step: '00:15',-->
<!--                          end: '18:30'-->
<!--                        }">-->
<!--                        </el-time-select>-->

<!--                    </el-col>-->
<!--                    <el-col :span="8">-->
<!--                        <el-time-select-->
<!--                                placeholder="结束时间"-->
<!--                                v-model="searchParams.depTimeTo"-->
<!--                                :picker-options="{-->
<!--                                  start: '08:30',-->
<!--                                  step: '00:15',-->
<!--                                  end: '18:30',-->
<!--                                  minTime: searchParams.depTimeFrom-->
<!--                                }">-->
<!--                        </el-time-select>-->
<!--                    </el-col>-->


<!--                </el-row>-->
<!--                <el-row :gutter="24">-->
<!--                    <el-col :span="8">-->
<!--                        <el-form-item label="价格排序:">-->
<!--                            <el-select v-model="searchParams.ordering" placeholder="请选择">-->
<!--                                <el-option-->
<!--                                        v-for="item in ordering"-->
<!--                                        :key="item.value"-->
<!--                                        :label="item.label"-->
<!--                                        :value="item.value">-->
<!--                                </el-option>-->
<!--                            </el-select>-->
<!--                        </el-form-item>-->
<!--                    </el-col>-->
<!--                </el-row>-->
<!--                <el-row :gutter="24" style="float: right;padding-bottom: 20px">-->
<!--                    <el-col :span="1.5">-->
<!--                        <el-button type="primary" @click="handleGetTrainTicketOrder()">查询</el-button>-->
<!--                    </el-col>-->
<!--                </el-row>-->
<!--            </el-form>-->
<!--        </el-card>-->
        <el-table border :data="trainTicketOrderList" ref="multipleTable"
                  style="width: 100%">
            <el-table-column
                    prop="id"
                    label="订单编号"
                    align="center"
                    width="100">
            </el-table-column>
            <el-table-column
                    prop="user.username"
                    label="用户名"
                    align="center"
                    width="100">
            </el-table-column>
            <el-table-column
                    prop="trainTicket.fromName"
                    label="出发地点"
                    align="center"
                    width="100">
            </el-table-column>
            <el-table-column
                    prop="trainTicket.toName"
                    label="到达地点"
                    align="center">
            </el-table-column>
            <el-table-column
                    prop="trainTicket.trainNum"
                    label="车次号"
                    align="center">
            </el-table-column>
            <el-table-column
                    prop="trainTicket.depTime"
                    label="出发时间"
                    align="center">
            </el-table-column>
            <el-table-column
                    prop="trainTicket.arrTime"
                    label="预计到达时间"
                    align="center">
            </el-table-column>
            <el-table-column
                    prop="trainTicket.price"
                    label="价格"
                    align="center">
            </el-table-column>
            <el-table-column
                    prop="trainTicket.remarks"
                    label="备注"
                    align="center">
            </el-table-column>
<!--            <el-table-column-->
<!--                    label="操作"-->
<!--                    align="center"-->
<!--                    width="200"-->
<!--                    fixed="right">-->
<!--                <template slot-scope="scope">-->
<!--                    <el-button type="primary" size="mini"-->
<!--                               @click="handleOrder(scope.row)">订票-->
<!--                    </el-button>-->
<!--                </template>-->
<!--            </el-table-column>-->
        </el-table>
        <div class="pagination">
            <el-pagination background layout="prev, pager, next" :pager-count="5" :total="total"
                           :hide-on-single-page="false" @current-change="handleCurrentChange">
            </el-pagination>
        </div>
        <el-dialog :visible.sync="editDialogVisible">
            <el-form label-width="80px" v-model="trainTicketOrderToEdit" :ref="trainTicketOrderToEdit" :rules="rules">
                <el-input type="hidden" v-model="trainTicketOrderToEdit.id"/>
                <el-form-item label="trainNum" prop="trainNum">
                    <el-input v-model="trainTicketOrderToEdit.trainNum"/>
                </el-form-item>
                <el-form-item label="fromName" prop="fromName">
                    <el-cascader
                            v-model="trainTicketOrderToEdit.fromName"
                            :options="areaOptions"
                    >
                    </el-cascader>
                </el-form-item>
                <el-form-item label="toName" prop="toName">
                    <el-cascader
                            v-model="trainTicketOrderToEdit.toName"
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
                    <el-input  type="number" v-model="trainTicketOrderToEdit.price"/>
                </el-form-item>
                <el-form-item label="备注" prop="remarks">
                    <el-input   v-model="trainTicketOrderToEdit.remarks"/>
                </el-form-item>
            </el-form>

            <div class="dialog-footer" slot="footer">
                <el-button @click="editDialogVisible = false">
                    取消
                </el-button>
                <el-button @click="updateTrainTicketOrder" type="primary">
                    确认
                </el-button>
            </div>
        </el-dialog>

        <el-dialog :visible.sync="addDialogVisible">
            <el-form v-model="trainTicketOrderToAdd" :ref="trainTicketOrderToAdd" :model="trainTicketOrderToAdd">
                <el-form-item label="trainNum" prop="trainNum">
                    <el-input v-model="trainTicketOrderToAdd.trainNum"/>
                </el-form-item>
                <el-form-item label="fromName" prop="fromName">
                    <el-cascader
                            v-model="trainTicketOrderToAdd.fromName"
                            :options="areaOptions"
                    >
                    </el-cascader>
                </el-form-item>
                <el-form-item label="toName" prop="toName">
                    <el-cascader
                            v-model="trainTicketOrderToAdd.toName"
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
                    <el-input  type="number" v-model="trainTicketOrderToAdd.price"/>
                </el-form-item>
                <el-form-item label="备注" prop="remarks">
                    <el-input   v-model="trainTicketOrderToAdd.remarks"/>
                </el-form-item>
            </el-form>
            <div class="dialog-footer" slot="footer">
                <el-button @click="addDialogVisible = false">
                    取消
                </el-button>
                <el-button @click="addTrainTicketOrder" type="primary">
                    确认
                </el-button>
            </div>
        </el-dialog>
    </div>
</template>

<script>
    import {addTrainTicketOrder, getTrainTicketOrder, updateTrainTicketOrder, deleteTrainTicketOrder} from "../utils/api";
    import area from '../utils/country-level2-data.js'
    import {mapState} from "vuex";

    export default {
        name: "TrainTicketOrderSearch",
        data() {
            return {
                depAndArrTime:[],
                ordering: [{label: '价格升序', value: 0}, {label: '价格降序', value: 1}],
                areaOptions: area.filter(item=>item.value==='广西壮族自治区'),
                trainTicketOrderList: [],
                trainTicketOrderToEdit: {
                    depDate: '',
                },
                trainTicketOrderToOrder: {

                },
                trainTicketOrderToAdd: {
                    depDate: '',
                },
                total: 0,
                addFolderDialog: false,
                uploadFileDialog: false,
                renameFileDialog: false,
                editDialogVisible: false,
                addDialogVisible: false,
                searchParams: {userId:this.$store.state.auth.id},
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
            this.handleGetTrainTicketOrder()
        },
        computed: {
            ...mapState(["auth"])
        },
        methods: {
            handleAddTrainTicketOrder() {
                this.addDialogVisible = true;
            },
            addTrainTicketOrder() {
                if (this.trainTicketOrderToAdd.fromName) {
                    this.trainTicketOrderToAdd.fromName = this.trainTicketOrderToAdd.fromName.join(',')
                }
                if (this.trainTicketOrderToAdd.toName) {
                    this.trainTicketOrderToAdd.toName = this.trainTicketOrderToAdd.toName.join(',')
                }
                this.trainTicketOrderToAdd.depTime=this.depAndArrTime[0];
                this.trainTicketOrderToAdd.arrTime=this.depAndArrTime[1];
                addTrainTicketOrder(this.trainTicketOrderToAdd).then(response => {
                    if (response && response.status === "success") {
                        this.$message.success(response.message)
                        this.addDialogVisible = false
                        this.trainTicketOrderToAdd = {}
                        this.handleGetTrainTicketOrder()
                    }
                })
            },
            handleOrder(row) {
                this.trainTicketOrderToOrder.user={}
                this.trainTicketOrderToOrder.trainTicketOrder={}
                this.trainTicketOrderToOrder.user.id = this.$store.state.auth.id
                this.trainTicketOrderToOrder.trainTicketOrder.id = row.id
                addTrainTicketOrder(this.trainTicketOrderToOrder).then(response=>{
                    if (response && response.status === "success") {
                        this.$message.success(response.message)
                    }else{
                        this.$message.error(response.message)
                    }
                })
            },
            handleEdit(row) {
                this.trainTicketOrderToEdit = row
                if (this.trainTicketOrderToEdit.cityToName) {
                    this.trainTicketOrderToEdit.cityToName = this.trainTicketOrderToEdit.cityToName.split(',');
                }
                if (this.trainTicketOrderToEdit.cityFromName) {
                    this.trainTicketOrderToEdit.cityFromName = this.trainTicketOrderToEdit.cityFromName.split(',');
                }
                this.editDialogVisible = true;
            },
            deleteTrainTicketOrder(row) {
                this.$confirm("永久删除这些数据, 是否继续?")
                    .then(() => {
                        let ids = [row.id];
                        deleteTrainTicketOrder(ids).then(response => {
                            if (response && response.status === "success") {
                                this.$message.success(response.message)
                                this.handleGetTrainTicketOrder()
                            }
                        })
                    })
            },
            handleGetTrainTicketOrder() {
                getTrainTicketOrder(this.searchParams).then(response => {
                    if (response && response.status === "success") {
                        this.total = response.total
                        this.trainTicketOrderList = response.object
                    }
                })
            },
            updateTrainTicketOrder() {
                if (this.trainTicketOrderToEdit.cityFromName) {
                    this.trainTicketOrderToEdit.cityFromName = this.trainTicketOrderToEdit.cityFromName.join(',')
                }
                if (this.trainTicketOrderToEdit.cityToName) {
                    this.trainTicketOrderToEdit.cityToName = this.trainTicketOrderToEdit.cityToName.join(',')
                }
                updateTrainTicketOrder(this.trainTicketOrderToEdit).then(response => {
                    if (response && response.status === "success") {
                        this.$message.success(response.message)
                        this.editDialogVisible = false
                        this.handleGetTrainTicketOrder()
                    }
                })
            },
            uploadFile() {
                this.uploadFileDialog = true
            },
            uploadSuccess(response) {
                this.$message.success(response.message)
                this.handleGetTrainTicketOrder()
                this.uploadFileDialog = false
            },
            handleCurrentChange(pageNumber) {
                this.searchParams.pageNumber = pageNumber;
                this.handleGetTrainTicketOrder();
            },
        }
    }
</script>

<style scoped>

</style>