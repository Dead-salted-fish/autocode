const loginTemplate = {
    path: '/login',
    name: 'login',
    component: httpVueLoader(urlPrefix+'/template/login.vue'),
}

const defaultTemplate = {
    path: '/',
    redirect: '/login',
}

const mainTemplate = {
    path: '/main',
    name: 'main',
    component: httpVueLoader(urlPrefix+'/template/main.vue'),
    //redirect: '/autoCode',
    children: [
        //加入兜底的404之后，进入首页的重定向失效，导致每次都匹配到404页面
        //这么写就不会因为/main/ 无匹配路由而匹配到404了
        {
            path: '/',
            redirect: '/autoCode',
        },
        {
            path: '/autoCode',
            component: httpVueLoader(urlPrefix+'/template/autoCodeMain.vue')
        },
        {
            path: '/jx3/hmdPersonal',
            component: httpVueLoader(urlPrefix+'/template/jx3/hmdPersonal.vue')
        },
        {
            path: '/jx3/hmdTeam',
            component: httpVueLoader(urlPrefix+'/template/jx3/hmdTeam.vue')
        },
        {
            path: '/system/menuManage',
            component: httpVueLoader(urlPrefix+'/template/system/menuManage.vue')
        },
        {
            path: '/*',
            component: httpVueLoader(urlPrefix+'/template/404.vue')
        },
    ]
}