import Vue from 'vue'
import Router from 'vue-router'
import elementUi from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
//用户
import index from "@/components/index";
import choose from "@/components/choose";
import aLogin from "@/components/aLogin";
import uLogin from "@/components/uLogin";
import regist from "@/components/regist";
import person from "@/components/person";
import play from "@/components/play";
import search from "@/components/search";
import upload from "@/components/upload";
import examineList from "@/components/examineList";
import freezeList from "@/components/freezeList";
import others from "@/components/others";
//管理员
import admin from "@/components/admin";
import userList from "@/components/userList";
import singerList from "@/components/singerList";
import musicList from "@/components/musicList";
import typeList from "@/components/typeList";
import cycleList from "@/components/cycleList";

Vue.use(Router)
Vue.use(elementUi)

export default new Router({
    routes:[{
        path:'/',
        name:'index',
        component:index
    },{
        path:'/choose',
        name:'choose',
        component:choose
    },{
        path:'/aLogin',
        name:'aLogin',
        component:aLogin
    },{
        path:'/uLogin',
        name:'uLogin',
        component:uLogin
    },{
        path:'/regist',
        name:'regist',
        component:regist
    }, {
        path:'/person',
        name:'person',
        component:person
    },{
        path:'/play',
        name:'play',
        component:play
    },{
        path:'/search',
        name:'search',
        component:search
    },{
        path:'/upload',
        name:'upload',
        component:upload
    },{
        path:'/others',
        name:'others',
        component:others
    },

        //管理员
        {
            path:'/admin',
            name:'admin',
            component:admin,
            children:[{
                path:'/userList',
                name:'userList',
                component:userList,
            },{
                path:'/singerList',
                name:'singerList',
                component:singerList,
            },{
                path:'/musicList',
                name:'musicList',
                component:musicList,
            },{
                path:'/typeList',
                name:'typeList',
                component:typeList,
            },{
                path:'/cycleList',
                name:'cycleList',
                component:cycleList,
            },{
                path:'/examineList',
                name:'examineList',
                component:examineList
            },{
                path:'/freezeList',
                name:'freezeList',
                component:freezeList
            }

            ]
        },


    ]
})