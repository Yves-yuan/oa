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
<!--            <div slot="header" class="clearfix">-->
<!--                <span>option</span>-->
<!--                <el-button style="float: right; padding: 3px 0" type="text" @click="handleAddGoods(true)">add-->
<!--                </el-button>-->
<!--            </div>-->
            <el-form label-width="auto"
                     size="small ">
                <el-row :gutter="24">
                    <el-col :span="10">
                        <el-form-item label="search time range:">
                            <el-date-picker
                                    v-model="searchTimeRange"
                                    value-format="yyyy-MM-dd HH:mm:ss"
                                    format="yyyy-MM-dd HH:mm:ss"
                                    type="datetimerange"
                                    start-placeholder="start"
                                    end-placeholder="end"
                                    :default-time="['12:00:00']">
                            </el-date-picker>
                        </el-form-item>
                    </el-col>
                    <el-col :span="8">
                        <el-form-item label="Custom PO#:">
                            <el-input label="Custom PO#:"
                                      placeholder="input Custom PO#:"
                                      v-model="searchParams.customerPo">
                            </el-input>
                        </el-form-item>
                    </el-col>

                </el-row>

                <el-row :gutter="24">
                    <el-col :span="8">
                        <el-form-item label="Mftr. Part No :">
                            <el-input label="Mftr. Part No :"
                                      placeholder="input Mftr. Part No "
                                      v-model="searchParams.goodsPartNumber">
                            </el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="8">
                        <el-form-item label="Project#:">
                            <el-input label="Project#："
                                      placeholder="input Project#"
                                      v-model="searchParams.project">
                            </el-input>
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row :gutter="24" style="float: right;padding-bottom: 20px">
                    <el-col :span="1.5">
                        <el-button type="primary" @click="handleGetGoods()">search</el-button>
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
        <el-table border :data="goodsList" ref="multipleTable"
                  style="width: 100%">
            <el-table-column
                    prop="id"
                    label="id"
                    align="center"
                    width="100">
            </el-table-column>
            <el-table-column
                    prop="itemNo"
                    label="Item No "
                    align="center"
                    width="100">
            </el-table-column>
            <el-table-column
                    prop="goodsPartNumber"
                    label="Mftr. Part No "
                    align="center"
                    width="100">
            </el-table-column>
            <el-table-column
                    :show-overflow-tooltip="true"
                    prop="customerPo"
                    label="Customer PO#"
                    align="center">
            </el-table-column>
            <el-table-column
                    prop="project"
                    label="Project#"
                    align="center">
            </el-table-column>
            <el-table-column
                    prop="shipmentDate"
                    label="Shipment date"
                    align="center">
            </el-table-column>
            <el-table-column
                    prop="qtyShipped"
                    label="Qty shipped"
                    align="center">
            </el-table-column>
            <el-table-column
                    prop="desc"
                    label="Description"
                    align="center">
            </el-table-column>
            <el-table-column
                    prop="unitPrice"
                    label="Unit Price "
                    align="center">
            </el-table-column>
            <el-table-column
                    prop="extendedPrice"
                    label="Extended Price"
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
            <el-form label-width="150px" v-model="goodsToEdit">
                <el-input type="hidden" v-model="goodsToEdit.id"/>
                <el-form-item label="goodsPartNumber" prop="goodsPartNumber">
                    <el-input disabled="true" v-model="goodsToEdit.goodsPartNumber"/>
                </el-form-item>
                <el-form-item label="qtyShipped" prop="qtyShipped">
                    <el-input disabled="true" v-model="goodsToEdit.qtyShipped"/>
                </el-form-item>
                <el-form-item label="unitPrice" prop="unitPrice">
                    <el-input disabled="true" v-model="goodsToEdit.unitPrice"/>
                </el-form-item>
                <el-form-item label="extendedPrice" prop="extendedPrice">
                    <el-input disabled="true" type="textarea" v-model="goodsToEdit.extendedPrice"/>
                </el-form-item>
                <el-form-item label="customerPo" prop="customerPo">
                    <el-input v-model="goodsToEdit.customerPo"/>
                </el-form-item>
                <el-form-item label="project" prop="project">
                    <el-input v-model="goodsToEdit.project"/>
                </el-form-item>
                <el-form-item label="shipmentDate" prop="shipmentDate">
                    <el-date-picker
                            v-model="goodsToEdit.shipmentDate"
                            type="datetime"
                            format="yyyy-MM-dd HH:mm:ss"
                            value-format="yyyy-MM-dd HH:mm:ss"
                            placeholder="选择日期时间">
                    </el-date-picker>
                </el-form-item>
            </el-form>
            <div class="dialog-footer" slot="footer">
                <el-button @click="editDialogVisible = false">
                    cancel
                </el-button>
                <el-button @click="updateOrdering" type="primary">
                    ok
                </el-button>
            </div>
        </el-dialog>
        <el-dialog :visible.sync="exportDialogVisible" :rules="rules" :ref="goodsToExport">
            <el-form label-width="150px" :model="goodsToExport">
                <el-form-item label="Shipping Details:" prop="shippingDetails">
                    <el-input type="textarea" v-model="goodsToExport.shippingDetails"/>
                </el-form-item>
                <el-form-item label="Company:" prop="company">
                    <el-input type="textarea" v-model="goodsToExport.company"/>
                </el-form-item>
                <el-form-item label="Shipping to:" prop="shippingTo">
                    <el-input type="textarea" v-model="goodsToExport.shippingTo"/>
                </el-form-item>
            </el-form>
            <div class="dialog-footer" slot="footer">
                <el-button @click="exportDialogVisible = false">
                    cancel
                </el-button>
                <el-button @click="exportOrder" type="primary">
                    ok
                </el-button>
            </div>
        </el-dialog>
<!--        <el-dialog :visible.sync="uploadFileDialog" width="26%">-->
<!--            <el-upload ref="upload"-->
<!--                       :file-list="flist"-->
<!--                       drag action="/goodsOrdering/import" multiple :data={}-->
<!--                       :on-success="uploadSuccess">-->
<!--                <i class="el-icon-upload"></i>-->
<!--                <div class="el-upload__text">pull file here,or<em>click upload</em></div>-->
<!--            </el-upload>-->
<!--        </el-dialog>-->
        <el-dialog :visible.sync="uploadFileDialog" width="26%">
            <el-upload ref="upload"
                       :file-list="flist"
                       drag action="/goodsOrdering/importSimple" multiple :data={}
                       :on-success="uploadSuccess">
                <i class="el-icon-upload"></i>
                <div class="el-upload__text">pull file here,or<em>click upload</em></div>
            </el-upload>
        </el-dialog>
    </div>
</template>

<script>
    import {
        getGoodsOrder,
        updateGoodsOrder,
        deleteGoodsOrder,
        deleteAllGoodsOrder,
        exportGoodsOrder, getWarningGoods,
    } from "../utils/api";
    import {mapState} from "vuex";

    export default {
        name: "GoodsPacking",
        data() {
            return {
                flist: [],
                goodsList: [],
                goodsToEdit: {},
                goodsToExport: {},
                tmpShipmentDate: {},
                goodsToAdd: {},
                goodsToOrdering: {},
                total: 0,
                addFolderDialog: false,
                uploadFileDialog: false,
                renameFileDialog: false,
                editDialogVisible: false,
                exportDialogVisible: false,
                orderingDialogVisible: false,
                addDialogVisible: false,
                currentPage4: 1,
                searchParams: {},
                searchTimeRange: [],
                rules: {
                    tagid: [
                        {
                            type: "number",
                            required: true,
                            message: "input number tagid",
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
            handleAddGoods() {
                this.addDialogVisible = true;
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
                        deleteGoodsOrder(ids).then(response => {
                            if (response && response.status === "success") {
                                this.$message.success(response.message)
                                this.handleGetGoods()
                            }
                        })
                    })
            },
            handleGetGoods() {
                if (this.searchTimeRange != null && this.searchTimeRange.length === 2) {
                    this.searchParams.begin = this.searchTimeRange[0]
                    this.searchParams.end = this.searchTimeRange[1]
                }else{
                    this.searchParams.begin = null
                    this.searchParams.end = null
                }
                getGoodsOrder(this.searchParams).then(response => {
                    if (response && response.status === "success") {
                        this.total = response.total
                        this.goodsList = response.object
                    }
                })
            },
            handleExportGoods() {
                this.exportDialogVisible = true;

            },
            handleGetWarningGoods() {
                getWarningGoods(this.searchParams).then(response => {
                    if (response && response.status === "success") {
                        this.total = response.total
                        this.goodsList = response.object
                    }
                })
            },
            handleDeleteAllGoods() {
                deleteAllGoodsOrder().then(response => {
                    if (response && response.status === "success") {
                        this.$message.success(response.message)
                        this.handleGetGoods()
                    } else {
                        this.$message.error(response.message)
                    }
                });
            },
            updateOrdering() {
                updateGoodsOrder(this.goodsToEdit).then(response => {
                    if (response && response.status === "success") {
                        this.$message.success(response.message)
                        this.editDialogVisible = false
                        this.handleGetGoods()
                    }
                })
            },
            exportOrder() {
                this.goodsToExport.customerPo = this.searchParams.customerPo;
                exportGoodsOrder(this.goodsToExport, 'packinglist.xls');
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