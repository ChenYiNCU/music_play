<template>
    <div>
        <el-table
                :data="tableData"
                style="width: 100%"><!--            data	显示的数据-->
            <el-table-column
                    label="编号"
                    prop="u_id">
            </el-table-column>
            <el-table-column
                    label="头像"
                    prop="uphoto">
                <template slot-scope="scope">
                    <el-image
                            style="width: 100px; height: 100px;"
                            :src="scope.row.uphoto"
                            :fit="fit"></el-image>
                </template>
            </el-table-column>
            <el-table-column
                    label="昵称"
                    prop="uname">
            </el-table-column>
            <el-table-column
                    label="密码"
                    prop="upwd">
            </el-table-column>
            <el-table-column
                    label="年龄"
                    prop="age">
            </el-table-column>
            <el-table-column
                    label="性别"
                    prop="sex">
            </el-table-column>
            <el-table-column
                    label="电话号码"
                    prop="tel">
            </el-table-column>
            <el-table-column
                    label="邮箱"
                    prop="email">
            </el-table-column>
            <!--太长了不想显示，但或许可以编辑-->
            <!--        <el-table-column-->
            <!--                label="个人简介"-->
            <!--                prop="des">-->
            <!--        </el-table-column>-->
            <el-table-column
                    align="right">
                <template slot="header" slot-scope="scope">
                    <el-input
                            type="text"
                            v-model="search"
                            size="mini"
                            placeholder="输入关键字搜索"
                            @keyup.enter.native="searchUser(scope)"
                    />
                    <!--<el-button
                            size="mini"
                            type="primary"
                            @click="searchUser(scope)" >搜索</el-button>-->
                </template>
                <template slot-scope="scope">
                    <el-button
                            size="mini"
                            type="primary"
                            @click="findById(scope.row)" >查看</el-button>
                    <el-button
                            size="mini"
                            type="success"
                            @click="recover(scope.row)" >解封</el-button>
                </template>
            </el-table-column>
        </el-table>
        <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="index"
                       :page-sizes="[5, 10, 20, 40]" :page-size="size" layout="total, sizes, prev, pager, next, jumper" :total="count" v-if="pageVisible1">
        </el-pagination>

        <el-pagination @size-change="handleSizeChange1" @current-change="handleCurrentChange1" :current-page="index1"
                       :page-sizes="[5, 10, 20, 40]" :page-size="size1" layout="total, sizes, prev, pager, next, jumper" :total="count" v-if="pageVisible2">
        </el-pagination>
        <el-dialog title="会员信息" :visible.sync="dialogCheckVisible">
            <el-form :model="checkForm">
                <el-form-item label="网名" :label-width="formLabelWidth">
                    <el-input v-model="checkForm.uname" autocomplete="off" :disabled="true"></el-input>
                </el-form-item>
                <el-form-item label="头像" :label-width="formLabelWidth">
                    <el-image
                            style="width: 100px; height: 100px;"
                            :src="checkForm.uphoto"
                            :fit="fit"></el-image>
                </el-form-item>
                <el-form-item label="手机号" :label-width="formLabelWidth">
                    <el-input v-model="checkForm.tel" autocomplete="off" :disabled="true"></el-input>
                </el-form-item>
                <el-form-item label="密码" :label-width="formLabelWidth">
                    <el-input v-model="checkForm.upwd" autocomplete="off" :disabled="true"></el-input>
                </el-form-item>
                <el-form-item label="邮箱" :label-width="formLabelWidth">
                    <el-input v-model="checkForm.email" autocomplete="off" :disabled="true"></el-input>
                </el-form-item>
                <el-form-item label="年龄" :label-width="formLabelWidth">
                    <el-input v-model="checkForm.age" autocomplete="off" :disabled="true"></el-input>
                </el-form-item>
                <el-form-item label="性别" :label-width="formLabelWidth">
                    <el-select v-model="checkForm.sex" placeholder="" :disabled="true">
                        <el-option label="男" value="男"></el-option>
                        <el-option label="女" value="女"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="个人简介" :label-width="formLabelWidth">
                    <el-input type="textarea" v-model="checkForm.des" autocomplete="off" :disabled="true"></el-input>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button type="primary" @click="dialogCheckVisible=false">确 定</el-button>
            </div>
        </el-dialog>
    </div>
</template>

<script>
    export default {
        inject:['reload'],
        name: "freezeList",
        data() {
            return {
                tableData: [],
                checkForm:{
                    uname:'',
                    uphoto:'',
                    tel:'',
                    upwd:'',
                    email:'',
                    age:'',
                    sex:'',
                    des:''
                },
                flag:3,   //封禁状态flag的值为3
                search: '',
                index:1,
                size:5,
                index1:1,
                size1:5,
                count:'',
                pageVisible1: true,
                pageVisible2: false,
                dialogCheckVisible:false,
                formLabelWidth: '120px'
            }
        },
        created(){
            this.$http.get("http://localhost:8082/muser/findPage?flag="+this.flag+"&index="+this.index+"&size="+this.size).then(req=>{
                this.tableData=req.data.list;
                this.index=req.data.index;
                this.count=req.data.count;
            })
        },
        methods:{
            searchUser:function(scope){
                console.log(scope);
                //alert(this.search);
                if(this.search=='' || this.search==null){
                    this.pageVisible1=true;
                    this.pageVisible2=false;
                }else{
                    this.pageVisible2=true;
                    this.pageVisible1=false;
                }
                this.$http.get("http://localhost:8082/muser/findSearchPage?flag="+this.flag+"&index="+this.index1+"&size="+this.size1+"&search="+this.search).then(req=>{
                    this.tableData=req.data.list;
                    this.index1=req.data.index;
                    this.count=req.data.count;
                })
            },
            findById:function (row) {
                console.log(row);
                this.checkForm.uname=row.uname;
                this.checkForm.uphoto=row.uphoto;
                this.checkForm.tel=row.tel;
                this.checkForm.upwd=row.upwd;
                this.checkForm.email=row.email;
                this.checkForm.age=row.age;
                this.checkForm.sex=row.sex;
                this.checkForm.des=row.des;
                this.dialogCheckVisible = true;
            },
            recover:function(row){
                //将flag修改为1 即会员状态
                this.$http.get("http://localhost:8082/muser/examine?u_id="+row.u_id).then(req=>{
                    if (req.data!=""){
                       /* alert(req.data);*/
                        this.$message({
                            showClose: true,
                            message: '会员'+row.u_id+'账号解禁成功！',
                            type: 'success'
                        });
                        this.reload();
                    }
                })
            },
            // 初始页currentPage、初始每页数据数pagesize和数据data
            handleSizeChange(size){
                this.size = size;        //将每页显示的条数 = 改变的值
                this.$http.get("http://localhost:8082/muser/findPage?flag="+this.flag+"&index="+this.index+"&size="+this.size).then(req=>{
                    this.tableData=req.data.list;
                    this.index=req.data.index;
                    this.count=req.data.count;
                })
                console.log(this.size) //每页下拉显示数据
            },
            handleCurrentChange(index) {
                this.index = index;        //然后将当前页 = 改变的值
                this.$http.get("http://localhost:8082/muser/findPage?flag="+this.flag+"&index="+this.index+"&size="+this.size).then(req=>{
                    this.tableData=req.data.list;
                    this.index=req.data.index;
                    this.count=req.data.count;
                })
                console.log(this.index)        //点击第几页
            },
            // 初始页currentPage、初始每页数据数pagesize和数据data
            handleSizeChange1(size){
                this.size1 = size;        //将每页显示的条数 = 改变的值
                this.$http.get("http://localhost:8082/muser/findSearchPage?flag="+this.flag+"&index="+this.index1+"&size="+this.size1+"&search="+this.search).then(req=>{
                    this.tableData=req.data.list;
                    this.index1=req.data.index;
                    this.count=req.data.count;
                })
                console.log(this.size1) //每页下拉显示数据
            },
            handleCurrentChange1(index) {
                this.index1 = index;        //然后将当前页 = 改变的值
                this.$http.get("http://localhost:8082/muser/findSearchPage?flag="+this.flag+"&index="+this.index1+"&size="+this.size1+"&search="+this.search).then(req=>{
                    this.tableData=req.data.list;
                    this.index1=req.data.index;
                    this.count=req.data.count;
                })
                console.log(this.index1)        //点击第几页
            }
        }
    }
</script>
<style scoped>
    .el-pagination{
        text-align: center;
    }
</style>