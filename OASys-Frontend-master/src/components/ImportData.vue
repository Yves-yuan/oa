<template>
    <div class="files">
        <el-form :inline="true">
            <el-form-item>
                <el-button-group>
                    <el-button type="primary" icon="el-icon-upload" @click="uploadFile" plain>upload</el-button>
                </el-button-group>
            </el-form-item>
        </el-form>
        <el-card class="box-card" body-style="{padding: '20px' }">
            <div slot="header" class="clearfix">
                <span>option</span>
                <el-button style="float: right; padding: 3px 0" type="text" @click="handleAddGoods(true)">add
                </el-button>
            </div>
            <el-form label-width="auto"
                     size="small ">
                <el-row :gutter="24">
                    <el-col :span="8">
                        <el-form-item label="tagid:">
                            <el-input
                                    placeholder="input tagid"
                                    v-model="searchParams.tagid">
                            </el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="8">
                        <el-form-item label=" Manufacturer part number:">
                            <el-input label="Manufacturer part number："
                                      placeholder="input Manufacturer part number"
                                      v-model="searchParams.manufacturerPartNumber">
                            </el-input>
                        </el-form-item>
                    </el-col>

                </el-row>

                <el-row :gutter="24">
                    <el-col :span="8">
                        <el-form-item label="Description:">
                            <el-input label="Description："
                                      placeholder="Description"
                                      v-model="searchParams.description">
                            </el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="8">
                        <el-form-item label="Component Type:">
                            <el-input label="Component Type："
                                      placeholder="Component Type"
                                      v-model="searchParams.componentType">
                            </el-input>
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row :gutter="24" style="float: right;padding-bottom: 20px">
                    <el-col :span="1.5">
                        <el-button type="primary" @click="handleGetGoods()">search</el-button>
                    </el-col>
                    <el-col :span="1.5">
                        <el-button type="primary" @click="handleGetWarningGoods()">{{warningContent}}</el-button>
                    </el-col>
                    <el-col :span="1.5">
                        <el-button type="primary" @click="handleExportGoods()">export</el-button>
                    </el-col>
                    <el-col :span="1.5">
                        <el-button type="warning" @click="handleDeleteAllGoods()">delete all</el-button>
                    </el-col>
                </el-row>
            </el-form>
        </el-card>
        <el-table border :data="goodsList" ref="multipleTable" :row-style="changeColor"
                  style="width: 100%">
            <el-table-column
                    prop="rank"
                    label="rank"
                    align="center"
                    width="100">
            </el-table-column>
            <el-table-column
                    prop="tagid"
                    label="tagid"
                    align="center"
                    width="100">
            </el-table-column>
            <el-table-column
                    prop="componentType"
                    label="Component type"
                    align="center">
            </el-table-column>
            <el-table-column
                    prop="subType"
                    label="Sub-Type"
                    align="center">
            </el-table-column>
            <el-table-column
                    prop="manufacturer"
                    label="Manufacturer"
                    align="center">
            </el-table-column>
            <el-table-column
                    min-width="180%"
                    prop="manufacturerPartNumber"
                    label="Manufacturer part number"
                    align="center">
            </el-table-column>
            <el-table-column
                    min-width="200%"
                    prop="description"
                    label="Description"
                    align="center">
            </el-table-column>
            <el-table-column
                    prop="date"
                    label="date"
                    align="center">
            </el-table-column>
            <el-table-column
                    prop="stockQty"
                    label="stock qty"
                    align="center">
            </el-table-column>
            <el-table-column
                    prop="price"
                    label="price"
                    align="center">
            </el-table-column>
            <el-table-column
                    prop="autoReplenishRate"
                    label="Safety Stock Line"
                    align="center">
            </el-table-column>
            <el-table-column
                    prop="leadTime"
                    label="lead time"
                    align="center">
            </el-table-column>
            <el-table-column
                    prop="notes"
                    label="Notes"
                    align="center">
            </el-table-column>
            <el-table-column
                    label="操作"
                    align="center"
                    width="300"
                    fixed="right">
                <template slot-scope="scope">
                    <el-button type="primary" size="mini"
                               @click="handleEdit(scope.row)">edit
                    </el-button>
                    <el-button type="danger" size="mini"
                               @click="deleteGoods(scope.row)">del
                    </el-button>
                    <el-button type="info" size="mini"
                               @click="ordering(scope.row)">ordering
                    </el-button>
                </template>
            </el-table-column>
        </el-table>
        <div class="pagination">
            <el-pagination
                    @size-change="handlePageSizeChange"
                    @current-change="handleCurrentChange"
                    :current-page="currentPage4"
                    :page-sizes="[5, 10, 20, 40]"
                    :page-size="100"
                    layout="total, sizes, prev, pager, next, jumper"
                    :total="total"
                    :hide-on-single-page="false"
            >
            </el-pagination>
        </div>
        <el-dialog :visible.sync="editDialogVisible" :rules="rules" :ref="goodsToEdit">
            <el-form label-width="80px" :model="goodsToEdit">
                <el-input type="hidden" v-model="goodsToEdit.id"/>
                <el-form-item label="tagid" prop="tagid">
                    <el-input v-model="goodsToEdit.tagid"/>
                </el-form-item>
                <el-form-item label="Component type" prop="componentType">
                    <el-input v-model="goodsToEdit.componentType"/>
                </el-form-item>
                <el-form-item label="Sub-Type" prop="subType">
                    <el-input v-model="goodsToEdit.subType"/>
                </el-form-item>
                <el-form-item label="Manufacturer" prop="manufacturer">
                    <el-input v-model="goodsToEdit.manufacturer"/>
                </el-form-item>
                <el-form-item label="manufacturerPartNumber" prop="manufacturerPartNumber">
                    <el-input v-model="goodsToEdit.manufacturerPartNumber"/>
                </el-form-item>
                <el-form-item label="description" prop="description">
                    <el-input type="textarea" v-model="goodsToEdit.description"/>
                </el-form-item>
                <el-form-item label="date" prop="date">
                    <el-date-picker
                            v-model="goodsToEdit.date"
                            type="datetime"
                            format="yyyy-MM-dd HH:mm:ss"
                            value-format="yyyy-MM-dd HH:mm:ss"
                            placeholder="choose date">
                    </el-date-picker>
                </el-form-item>
                <el-form-item label="stockQty" prop="stockQty">
                    <el-input type="number" v-model="goodsToEdit.stockQty"/>
                </el-form-item>
                <el-form-item label="price" prop="price">
                    <el-input type="number" v-model="goodsToEdit.price"/>
                </el-form-item>
                <el-form-item label="autoReplenishRate" prop="autoReplenishRate">
                    <el-input type="number" v-model="goodsToEdit.autoReplenishRate"/>
                </el-form-item>
                <el-form-item label="leadTime" prop="leadTime">
                    <el-input v-model="goodsToEdit.leadTime"/>
                </el-form-item>
                <el-form-item label="Notes" prop="notes">
                    <el-input v-model="goodsToEdit.notes"/>
                </el-form-item>
            </el-form>
            <div class="dialog-footer" slot="footer">
                <el-button @click="editDialogVisible = false">
                    cancel
                </el-button>
                <el-button @click="updateGoods" type="primary">
                    ok
                </el-button>
            </div>
        </el-dialog>

        <el-dialog :visible.sync="addDialogVisible">
            <el-form :model="goodsToAdd" :rules="rules" :ref="goodsToAdd">
                <el-form-item label="tagid" prop="tagid">
                    <el-input v-model.number="goodsToAdd.tagid"/>
                </el-form-item>
                <el-form-item label="Component type" prop="componentType">
                    <el-input v-model="goodsToAdd.componentType"/>
                </el-form-item>
                <el-form-item label="Sub-Type" prop="subType">
                    <el-input v-model="goodsToAdd.subType"/>
                </el-form-item>
                <el-form-item label="Manufacturer" prop="manufacturer">
                    <el-input v-model="goodsToAdd.manufacturer"/>
                </el-form-item>
                <el-form-item label="manufacturerPartNumber" prop="manufacturerPartNumber">
                    <el-input v-model="goodsToAdd.manufacturerPartNumber"/>
                </el-form-item>
                <el-form-item label="description" prop="description">
                    <el-input type="textarea" v-model="goodsToAdd.description"/>
                </el-form-item>
                <el-form-item label="date" prop="date">
                    <el-date-picker
                            v-model="goodsToAdd.date"
                            type="datetime"
                            format="yyyy-MM-dd HH:mm:ss"
                            value-format="yyyy-MM-dd HH:mm:ss"
                            placeholder="choose date">
                    </el-date-picker>
                </el-form-item>
                <el-form-item label="stockQty" prop="stockQty">
                    <el-input v-model="goodsToAdd.stockQty"/>
                </el-form-item>
                <el-form-item label="price" prop="price">
                    <el-input v-model="goodsToAdd.price"/>
                </el-form-item>
                <el-form-item label="autoReplenishRate" prop="autoReplenishRate">
                    <el-input type="number" v-model="goodsToAdd.autoReplenishRate"/>
                </el-form-item>
                <el-form-item label="leadTime" prop="leadTime">
                    <el-input v-model="goodsToAdd.leadTime"/>
                </el-form-item>
                <el-form-item label="Notes" prop="notes">
                    <el-input v-model="goodsToAdd.notes"/>
                </el-form-item>
            </el-form>
            <div class="dialog-footer" slot="footer">
                <el-button @click="editDialogVisible = false">
                    cancel
                </el-button>
                <el-button @click="addGoods" type="primary">
                    ok
                </el-button>
            </div>
        </el-dialog>
        <el-dialog :visible.sync="orderingDialogVisible">
            <h1>ordering</h1>
            <el-form label-width="160px" :model="order" :rules="rules" :ref="order">
                <el-form-item label="Item No" prop="itemNo">
                    <el-input type="number" v-model="order.itemNo"/>
                </el-form-item>
                <el-form-item label="Qty shipped" prop="qtyShipped">
                    <el-input type="number" v-model.number="order.qtyShipped"/>
                </el-form-item>
                <el-form-item label="Customer PO#">
                    <el-input v-model="order.customerPo"/>
                </el-form-item>
                <el-form-item label="Project#">
                    <el-input v-model="order.project"/>
                </el-form-item>
                <el-form-item label="Shipment date">
                    <el-date-picker
                            v-model="order.shipmentDate"
                            type="datetime"
                            value-format="yyyy-MM-dd HH:mm:ss"
                            placeholder="选择日期时间">
                    </el-date-picker>
                </el-form-item>
            </el-form>
            <div class="dialog-footer" slot="footer">
                <el-button @click="orderingDialogVisible = false">
                    cancel
                </el-button>
                <el-button @click="updateOrdering" type="primary">
                    ok
                </el-button>
            </div>
        </el-dialog>
        <el-dialog :visible.sync="uploadFileDialog" width="26%">
            <el-upload ref="upload"
                       :file-list="flist"
                       drag action="/api/importData" multiple :data={}
                       :on-success="uploadSuccess">
                <i class="el-icon-upload"></i>
                <div class="el-upload__text">pull file here,or<em>click upload</em></div>
            </el-upload>
        </el-dialog>
    </div>
</template>

<script>
    import {
        addGoods,
        getGoods,
        updateGoods,
        deleteGoods,
        exportGoods,
        deleteAllGoods,
        getWarningGoods,
        addOrdering
    } from "../utils/api";
    import {mapState} from "vuex";

    export default {
        name: "ImportData",
        data() {
            return {
                warningContent:'warning',
                warning:false,
                flist: [],
                goodsList: [],
                goodsToEdit: {},
                goodsToAdd: {},
                goodsToOrdering: {},
                order: {},
                total: 0,
                addFolderDialog: false,
                uploadFileDialog: false,
                renameFileDialog: false,
                editDialogVisible: false,
                orderingDialogVisible: false,
                addDialogVisible: false,
                currentPage4: 1,
                searchParams: {},
                rules: {
                    tagid: [
                        {required: true, message: 'required', trigger: "blur"},
                        {
                            type: 'number',
                            message: "number required",
                            trigger: "blur"
                        }
                    ],
                    componentType: [
                        {
                            max: 100,
                            message: "max length 100",
                            trigger: "blur"
                        }
                    ],
                    subType: [
                        {
                            max: 100,
                            message: "max length 100",
                            trigger: "blur"
                        }
                    ],
                    manufacturer: [
                        {
                            max: 255,
                            message: "max length 255",
                            trigger: "blur"
                        }
                    ],
                    manufacturerPartNumber: [
                        {
                            max: 255,
                            message: "max length 255",
                            trigger: "blur"
                        }
                    ],
                    description: [
                        {
                            max: 512,
                            message: "max length 255",
                            trigger: "blur"
                        }
                    ],
                    stock_qty: [
                        {
                            max: 255,
                            message: "max length 255",
                            trigger: "blur"
                        }
                    ],
                    qtyShipped: [
                        {
                            required: true, message: 'required', trigger: "blur"
                        },
                        {
                            type: "number",
                            message: "required",
                            trigger: "blur"
                        }
                    ],
                },
            }
        },
        props: [],

        created() {
            this.handleGetGoods()
        },
        computed: {
            ...mapState(["auth"])
        },
        methods: {
            changeColor(row){
                let colors = ['#CCFF33','#CCFF99','#66CC33','#99FFFF','#CCFFFF','#FFCCCC','#FFFFFF','white']
                var r = row.row.rank
                window.console.log(JSON.stringify(row))
                let i = r % 7
                let color = colors[i]

                return { "background-color": color }
            },
            handleAddGoods() {
                this.addDialogVisible = true;
            },
            addGoods() {
                addGoods(this.goodsToAdd).then(response => {
                    if (response && response.status === "success") {
                        this.$message.success(response.message)
                        this.addDialogVisible = false
                        this.goodsToAdd = {}
                        this.handleGetGoods()
                    }
                })
            },
            handleEdit(row) {
                this.goodsToEdit = row
                this.editDialogVisible = true;
            },
            ordering(row) {
                this.goodsToOrdering = row
                this.orderingDialogVisible = true;
            },
            deleteGoods(row) {
                this.$confirm("delete , continue?")
                    .then(() => {
                        let ids = [row.id];
                        deleteGoods(ids).then(response => {
                            if (response && response.status === "success") {
                                this.$message.success(response.message)
                                this.handleGetGoods()
                            }
                        })
                    })
            },
            handleGetGoods() {
                if (this.warning === true){
                    getWarningGoods(this.searchParams).then(response => {
                        if (response && response.status === "success") {
                            this.total = response.total
                            this.goodsList = response.object
                        }
                    })
                }else{
                    getGoods(this.searchParams).then(response => {
                        if (response && response.status === "success") {
                            this.total = response.total
                            this.goodsList = response.object
                        }
                    })
                }
            },
            handleGetWarningGoods() {
                this.warning = !this.warning;
                if (this.warning){
                    this.warningContent = 'cancel warning'
                }else{
                    this.warningContent = 'warning'
                }
                this.handleGetGoods();
            },
            handleExportGoods() {
                exportGoods(this.searchParams, 'data.xls');
            },
            handleDeleteAllGoods() {
                this.$confirm("delete , continue?")
                    .then(() => {
                        deleteAllGoods().then(response => {
                            if (response && response.status === "success") {
                                this.$message.success(response.message)
                                this.handleGetGoods()
                            } else {
                                this.$message.error(response.message)
                            }
                        });
                    });
            },
            updateGoods() {
                updateGoods(this.goodsToEdit).then(response => {
                    if (response && response.status === "success") {
                        this.$message.success(response.message)
                        this.editDialogVisible = false
                        this.handleGetGoods()
                    }
                })
            },
            updateOrdering() {
                this.order.goodsPartNumber = this.goodsToOrdering.manufacturerPartNumber
                this.order.goodsId = this.goodsToOrdering.id
                addOrdering(this.order).then(response => {
                    if (response && response.status === "success") {
                        this.$message.success(response.message)
                        this.orderingDialogVisible = false
                        this.order.qtyShipped = 0
                        this.handleGetGoods()
                    }
                })
            },
            uploadFile() {
                this.uploadFileDialog = true
            },
            uploadSuccess(response) {
                this.$message.success(response.message)
                this.handleGetGoods()
                this.uploadFileDialog = false
                this.$refs.upload.clearFiles()
            },
            handlePageSizeChange(pageSize) {
                this.searchParams.pageSize = pageSize;
                this.handleGetGoods();
            },
            handleCurrentChange(pageNumber) {
                this.searchParams.pageNumber = pageNumber;
                this.handleGetGoods();
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