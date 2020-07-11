<template>
    <div class="files">
        <el-form :inline="true">
            <el-form-item>
                <el-button-group>
                    <el-button type="primary" icon="el-icon-upload" @click="uploadFile" plain>上传文件</el-button>
                </el-button-group>
            </el-form-item>
        </el-form>
        <el-card class="box-card" body-style="{padding: '20px' }">
            <div slot="header" class="clearfix">
                <span>操作面板</span>
                <el-button style="float: right; padding: 3px 0" type="text" @click="handleAddGoods(true)">新增数据
                </el-button>
            </div>
            <el-form label-width="auto"
                     size="small ">
                <el-row :gutter="24">
                    <el-col :span="8">
                        <el-form-item label="tagid:">
                            <el-input
                                    placeholder="输入tagid搜索"
                                    v-model="searchParams.tagid">
                            </el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="8">
                        <el-form-item label=" Manufacturer part number:">
                            <el-input label="Manufacturer part number："
                                      placeholder="请输入Manufacturer part number"
                                      v-model="searchParams.manufacturerPartNumber">
                            </el-input>
                        </el-form-item>
                    </el-col>

                </el-row>

                <el-row :gutter="24">
                    <el-col :span="8">
                        <el-form-item label="Description:">
                            <el-input label="Description："
                                      placeholder="请输入Description"
                                      v-model="searchParams.description">
                            </el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="8">
                        <el-form-item label="Component Type:">
                            <el-input label="Component Type："
                                      placeholder="请输入Component Type"
                                      v-model="searchParams.componentType">
                            </el-input>
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row :gutter="24" style="float: right;padding-bottom: 20px">
                    <el-col :span="1.5">
                        <el-button type="primary" @click="handleGetGoods()">查询</el-button>
                    </el-col>
                    <el-col :span="1.5">
                        <el-button type="primary" @click="handleExportGoods()">导出</el-button>
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
                    prop="manufacturerPartNumber"
                    label="Manufacturer part number"
                    align="center">
            </el-table-column>
            <el-table-column
                    :show-overflow-tooltip="true"
                    prop="description"
                    label="Description"
                    align="center">
            </el-table-column>
            <el-table-column
                    prop="stockQty"
                    label="stock qty"
                    align="center">
            </el-table-column>
            <el-table-column
                    prop="annualStock"
                    label="annual stock"
                    align="center">
            </el-table-column>
            <el-table-column
                    prop="autoReplenishRate"
                    label="auto replenish rate"
                    align="center">
            </el-table-column>
            <el-table-column
                    prop="leadTime"
                    label="lead time"
                    align="center">
            </el-table-column>
            <el-table-column
                    label="操作"
                    align="center"
                    width="200"
                    fixed="right">
                <template slot-scope="scope">
                    <el-button type="primary" size="mini"
                               @click="handleEdit(scope.row)">编辑
                    </el-button>
                    <el-button type="danger" size="mini"
                               @click="deleteGoods(scope.row)">删除
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
            <el-form label-width="80px" v-model="goodsToEdit">
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
                <el-form-item label="stockQty" prop="stockQty">
                    <el-input v-model="goodsToEdit.stockQty"/>
                </el-form-item>
                <el-form-item label="annualStock" prop="annualStock">
                    <el-input v-model="goodsToEdit.annualStock"/>
                </el-form-item>
                <el-form-item label="autoReplenishRate" prop="autoReplenishRate">
                    <el-input v-model="goodsToEdit.autoReplenishRate"/>
                </el-form-item>
                <el-form-item label="leadTime" prop="leadTime">
                    <el-input v-model="goodsToEdit.leadTime"/>
                </el-form-item>
            </el-form>
            <div class="dialog-footer" slot="footer">
                <el-button @click="editDialogVisible = false">
                    取消
                </el-button>
                <el-button @click="updateGoods" type="primary">
                    确认
                </el-button>
            </div>
        </el-dialog>

        <el-dialog :visible.sync="addDialogVisible">
            <el-form v-model="goodsToAdd" :rules="rules" :ref="goodsToAdd">
                <el-form-item label="tagid" prop="tagid">
                    <el-input v-model="goodsToAdd.tagid"/>
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
                <el-form-item label="stockQty" prop="stockQty">
                    <el-input v-model="goodsToAdd.stockQty"/>
                </el-form-item>
                <el-form-item label="annualStock" prop="annualStock">
                    <el-input v-model="goodsToEdit.annualStock"/>
                </el-form-item>
                <el-form-item label="autoReplenishRate" prop="autoReplenishRate">
                    <el-input v-model="goodsToEdit.autoReplenishRate"/>
                </el-form-item>
                <el-form-item label="leadTime" prop="leadTime">
                    <el-input v-model="goodsToEdit.leadTime"/>
                </el-form-item>
            </el-form>
            <div class="dialog-footer" slot="footer">
                <el-button @click="editDialogVisible = false">
                    取消
                </el-button>
                <el-button @click="addGoods" type="primary">
                    确认
                </el-button>
            </div>
        </el-dialog>
        <el-dialog :visible.sync="uploadFileDialog" width="26%">
            <el-upload ref="upload"
                       :file-list="flist"
                       drag action="/api/importGoods" multiple :data={}
                       :on-success="uploadSuccess">
                <i class="el-icon-upload"></i>
                <div class="el-upload__text">pull file here,or<em>click upload</em></div>
            </el-upload>
        </el-dialog>
    </div>
</template>

<script>
    import {addGoods, getGoods, updateGoods, deleteGoods, exportGoods, deleteAllGoods} from "../utils/api";
    import {mapState} from "vuex";

    export default {
        name: "ImportData",
        data() {
            return {
                flist: [],
                goodsList: [],
                goodsToEdit: {},
                goodsToAdd: {},
                total: 0,
                addFolderDialog: false,
                uploadFileDialog: false,
                renameFileDialog: false,
                editDialogVisible: false,
                addDialogVisible: false,
                searchParams: {},
                rules: {
                    tagid: [
                        {
                            type: "number",
                            required: true,
                            message: "请输入数字tagid",
                            trigger: "blur"
                        }
                    ],
                    componentType: [
                        {
                            max: 100,
                            message: "长度不超过100",
                            trigger: "blur"
                        }
                    ],
                    subType: [
                        {
                            max: 100,
                            message: "长度不超过100",
                            trigger: "blur"
                        }
                    ],
                    manufacturer: [
                        {
                            max: 255,
                            message: "长度不超过255",
                            trigger: "blur"
                        }
                    ],
                    manufacturerPartNumber: [
                        {
                            max: 255,
                            message: "长度不超过255",
                            trigger: "blur"
                        }
                    ],
                    description: [
                        {
                            max: 512,
                            message: "长度不超过512",
                            trigger: "blur"
                        }
                    ],
                    stock_qty: [
                        {
                            max: 255,
                            message: "长度不超过255",
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
            deleteGoods(row) {
                this.$confirm("永久删除这些数据, 是否继续?")
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
                getGoods(this.searchParams).then(response => {
                    if (response && response.status === "success") {
                        this.total = response.total
                        this.goodsList = response.object
                    }
                })
            },
            handleExportGoods() {
                exportGoods(this.searchParams, 'data.xls');
            },
            handleDeleteAllGoods() {
                deleteAllGoods().then(response => {
                    if (response && response.status === "success") {
                        this.$message.success(response.message)
                        this.handleGetGoods()
                    } else {
                        this.$message.error(response.message)
                    }
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