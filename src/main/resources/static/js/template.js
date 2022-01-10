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
    redirect: '/autoCode',
    component: httpVueLoader(urlPrefix+'/template/main.vue'),
    children: [
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
        }
    ]
}