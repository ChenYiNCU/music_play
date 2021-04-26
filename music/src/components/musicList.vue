<template>
    <div>
        <el-table
                :data="tableData"
                style="width: 100%"
        >
            <el-table-column type="expand">
                <template slot-scope="props">
                    <el-form label-position="left" inline class="demo-table-expand">
                        <el-form-item label="发布时间">
                            <span>{{ props.row.time }}</span>
                        </el-form-item>

                        <el-form-item label="上传者">
                            <span class="span">{{ props.row.uploader.u_id }}</span>
                            <span>{{ props.row.uploader.uname }}</span>
                        </el-form-item>

                        <el-form-item label="播放数">
                            <span>{{ props.row.playnum}}</span>
                        </el-form-item>
                        <el-form-item label="喜爱数">
                            <span>{{ props.row.likenum }}</span>
                        </el-form-item>
                        <el-form-item label="收藏数">
                            <span>{{ props.row.storenum }}</span>
                        </el-form-item>
                    </el-form>
                </template>
            </el-table-column>
            <el-table-column
                    label="歌曲编号"
                    prop="mid">
            </el-table-column>
            <el-table-column
                    label="封面"
                    prop="mphoto">
                <!-- 图片的显示 -->
                <template   slot-scope="scope">
                    <img :src="scope.row.mphoto"  min-width="70" height="70" />
                </template>
            </el-table-column>
            <el-table-column
                    label="歌曲名"
                    prop="mname">
            </el-table-column>
            <el-table-column
                    label="歌手"
            >
                <template   slot-scope="scope">
                    <span class="span">{{scope.row.singer.u_id}}</span>
                    <span>{{scope.row.singer.uname}}</span>
                </template>
            </el-table-column>

            <el-table-column
                    label="歌曲类型"
            >
                <template   slot-scope="scope">
                    <span class="span">{{scope.row.mtype.t_id}}</span>
                    <span>{{scope.row.mtype.tname}}</span>
                </template>
            </el-table-column>
            <el-table-column
                    align="right">
                <template slot="header" slot-scope="scope"><!-- 这里slot-scope要cha东西 -->
                    <el-input
                            v-model="search"
                            size="mini"
                            placeholder="输入关键字搜索"
                            @keyup.enter.native="searchMusic(scope)"
                    />
                </template>
                <template slot-scope="scope">
                    <el-button
                            size="mini"
                            @click="handleEdit(scope.row.mid)">修改</el-button>
                    <el-button
                            size="mini"
                            type="danger"
                            @click="handleDelete(scope.row.mid)">删除</el-button>

                </template>
            </el-table-column>
        </el-table>
        <el-dialog title="修改歌曲信息" :visible.sync="dialogFormVisible">
            <el-form :model="music">
                <el-form-item label="歌曲编号" :label-width="formLabelWidth">
                    <el-input v-model="music.mid" autocomplete="off" readonly></el-input>
                </el-form-item>

                <el-form-item label="封面" :label-width="formLabelWidth">
                    <el-upload
                            class="avatar-uploader"
                            action="http://localhost:8082/muser/upload"
                            :show-file-list="false"
                            multiple
                            :on-success="handleAvatarSuccess"
                            :before-upload="beforeAvatarUpload"
                    >
                        <img v-if="mURL!=null" :src="mURL" style="width: 100px">
                        <i v-else class="el-icon-plus avatar-uploader-icon"></i>
                    </el-upload>

                </el-form-item>
                <el-form-item label="歌曲名" :label-width="formLabelWidth">
                    <el-input v-model="music.mname" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="歌手" :label-width="formLabelWidth">
                    <el-input v-model="sname" autocomplete="off" @change.native="haveSinger"></el-input>

                </el-form-item>
                <el-form-item label="歌曲类型" :label-width="formLabelWidth">
                    <el-input v-model="tname" autocomplete="off" @change.native="haveType"></el-input>

                </el-form-item>

            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="dialogFormVisible = false">取 消</el-button>
                <el-button type="primary" @click="update" >确 定</el-button>
            </div>
        </el-dialog>


        <el-pagination
                v-if="isSearch==false"
                style="width: 100%;text-align: center"
                background
                layout="prev, pager, next"
                :current-page="index"
                :total="total"
                :page-size="4"
                @current-change="handle">
        </el-pagination>
        <el-pagination
                v-if="isSearch==true"
                style="width: 100%;text-align: center;"
                background
                layout="prev, pager, next"
                :current-page="indexSearch"
                :total="total2"
                :page-size="4"
                @current-change="handleSearch">
        </el-pagination>
    </div>

</template>

<script>
    export default {
        inject:['reload'],
        name: "musicList",
        data(){
            return{
                dialogFormVisible: false,
                formLabelWidth: '120px',
                uploadUrl:'http://localhost:8082/muser/upload2',
                isSearch:false,
                search: '',
                total:8,   //总记录数，分页时使用
                index:1,   //当前页
                indexSearch:1,
                total2:0,
                music:{},
                sname:'',
                tname:'',
                typeList:[],      //类型对象数组
                singerList:[],    //歌手对象数组
                mURL:'',
                isChangephoto:false

            }
        },
        created(){
            this.init();
            //返回所有的歌手名字和类型名字，用来判断是否存在
            this.$http.get("http://localhost:8082/mtype/findAll").then(req=>{
                this.typeList=req.data;
            });
            this.$http.get("http://localhost:8082/muser/findAllSinger").then(req=>{
                this.singerList=req.data;
            });

        },
        methods:{
            haveType() {
                this.havetype = this.typeList.some(function (t) {
                    console.log(t.tname);
                    if (t.tname==this.tname) {
                        this.music.mtype.t_id = t.t_id;
                        return true;
                    }
                    //alert(t.tname);
                }.bind(this));
                //alert(this.havetype);
                if (this.havetype==false){
                    alert("该类型不存在");
                }
            },
            haveSinger(){
                console.log(this.singerList);
                this.havesinger= this.singerList.some(function(t){
                    //alert(t.u_id);
                    if(t.uname==this.sname){
                        this.music.singer.u_id=t.u_id;
                        return true;
                    }
                }.bind(this));
                //alert(this.havesinger);
                if(this.havesinger==false){
                    alert("该歌手不存在");
                }
            },
            update(){
                this.music.singer.uname=this.sname;
                this.music.mtype.tname= this.tname;         //准备修改数据库
                if(this.isChangephoto==true){
                    this.music.mphoto="music/"+this.music.mname+"/"+this.music.mphoto;
                }
               // this.music.mphoto="music/"+this.music.mname+"/"+this.music.mphoto;
                //alert(this.music.mphoto);
                this.haveSinger();
                this.haveType();
                if (this.havetype==true && this.havesinger==true){
                    //修改数据库并上传数据
                    this.$http.get("http://localhost:8082/mplink/updateMusic",{
                        params:{
                            mid:this.music.mid,
                            mphoto:this.music.mphoto,    //封面
                            mname:this.music.mname,     //歌的名字
                            t_id:this.music.mtype.t_id,          //类型名字
                            u_id:this.music.singer.u_id,          //歌手名字
                        }
                    }).then(req=>{
                        //alert(req.data);
                        if (req.data != null){
                            this.$message({
                                showClose: true,
                                message: '修改成功！',
                                type: 'success'
                            });
                            if(this.isChangephoto==true){
                                this.upload();
                            }

                            //this.$forceUpdate();
                            this.reload();
                            this.dialogFormVisible=false;
                        }
                    })
                }else{
                    //不存在就不做
                }
            },
           handleAvatarSuccess(res, file) {    //上传后
             this.mURL = URL.createObjectURL(file.raw);
           },
            beforeAvatarUpload(file) {     //上传前
                this.isChangephoto=true;
                this.file=file;
                this.music.mphoto=file.name;        //准备修改数据库
                //alert(this.music.mphoto);
                return true;
            },
            upload(){
                console.log("开始上传，上传的文件为："+this.file)
                let formData = new FormData();
                formData.append("multipartFiles", this.file);
                formData.append("multipartFiles", this.file1);
                formData.append("multipartFiles", this.file2);
                formData.append("path", this.music.mname+"/");
                this.$http({
                    method: 'post',
                    url:'http://localhost:8082/muser/upload2',
                    data: formData,
                    headers: {'Content-Type': 'multipart/form-data' }
                }).then((res) => {
                    console.log("文件上传返回："+res)
                }).catch(error => {
                    console.log("文件上传异常:"+error)
                });
            },

            searchMusic(){
                //alert(this.search);
                this.search=this.search.trim();
                if (this.search!=''){
                    //搜索
                    this.isSearch=false;

                    this.search2=this.search;    //存起来，分页时使用
                    this.isSearch=true;
                    this.$http.get("http://localhost:8082/mplink/searchMusic?search="+this.search).then(req=>{
                        this.tableData=req.data;   //返回四条歌
                        this.indexSearch=1;
                        this.$forceUpdate();
                        //alert(this.isSearch);
                    });
                 //更换total
                    this.$http.get("http://localhost:8082/mplink/searchAll?search="+this.search).then(req=>{
                       this.total2=req.data;
                        this.$forceUpdate();
                    });

                    this.search='';
                }else{
                    //搜索框为空   展现出全部
                    this.isSearch=false;
                    this.init();
                    //跟换total

                }
            },
            init(){
                this.$http.get("http://localhost:8082/mplink/findAllMusic").then(req=>{
                    this.tableData=req.data;
                    this.index=1;
                    this.$forceUpdate();
                    console.log(this.tableData);
                });
                //读出音乐总数存入total
                this.$http.get("http://localhost:8082/mplink/sum").then(req=>{
                    this.total=req.data;
                });
            },
            page:function (index) {
                this.$http.get("http://localhost:8082/mplink/findAllMusic?index="+index).then(req=>{
                    this.tableData=req.data;   //返回四条歌
                    this.index=index;
                    this.$forceUpdate();
                });
            },
            handle:function (val) {
                //this.index=val;     //当前页
                this.page(val);
            },
            handleSearch(val){

                this.$http.get("http://localhost:8082/mplink/searchMusic?search="+this.search2+"&index="+val).then(req=>{
                    this.tableData=req.data;   //返回四条歌
                    this.indexSearch=val;
                    this.$forceUpdate();
                });

            },
            handleEdit(mid){
                //alert(mid);
                this.dialogFormVisible=true;
                //查歌曲的所有信息
                this.$http.get("http://localhost:8082/mplink/findMusicById?mid="+mid).then(req=>{
                    this.music= req.data;   //返回一个音乐对象
                    this.sname=this.music.singer.uname;
                    this.tname=this.music.mtype.tname;
                    this.mURL=this.music.mphoto;
                    console.log(this.music);
                    console.log(this.typeList);
                });
 /////////////
            },
            handleDelete(mid){
                //alert('删除'+mid);
                this.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    //删除歌曲
                    this.$http.get("http://localhost:8082/mplink/deleteMusic?mid="+mid).then(req=>{
                        if (req.data!=''){
                            this.$message({
                                type: 'success',
                                message: '删除成功!'
                            });
                            //this.$forceUpdate();
                           //this.init();
                            this.reload();
                        }
                    });
                }).catch(() => {
                    this.$message({
                        type: 'info',
                        message: '已取消删除'
                    });
                });
            }
        }
    }
</script>

<style scoped>
    .demo-table-expand {
        font-size: 0;
    }
    .demo-table-expand label {
        width: 90px;
        color: #99a9bf;
    }
    .demo-table-expand .el-form-item {
        margin-right: 0;
        margin-bottom: 0;
        width: 50%;
    }
    .span{
        color: red;
        margin-right: 10px;
    }
</style>