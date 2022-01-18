const urlPrefix = ''

const jx3UrlSetting = {
    hmdPersonalAdd: urlPrefix+'/jx3/hmdPersonal/add',
    hmdPersonalDelete: urlPrefix+'/jx3/hmdPersonal/delete',
    hmdPersonalList: urlPrefix+'/jx3/hmdPersonal/list',
    hmdPersonalUpdate: urlPrefix+'/jx3/hmdPersonal/update',
    getHmdPersonalById: urlPrefix+'/jx3/hmdPersonal/getById',

    hmdTeamList: urlPrefix+'/jx3/hmdTeam/list',
    hmdTeamAdd: urlPrefix+'/jx3/hmdTeam/add',
    hmdTeamUpdate: urlPrefix+'/jx3/hmdTeam/update',
    hmdTeamDelete: urlPrefix+'/jx3/hmdTeam/delete',
    getHmdTeamById: urlPrefix+'/jx3/hmdTeam/getById',
}

const appUrlSetting = {
    getMenus: urlPrefix+'/system/menu/getMenus',
}

const loginUrlSetting = {
    userlogin: urlPrefix + '/userlogin',
    captcha: urlPrefix + '/captcha'
}

const systemUrlSetting = {
    getMenusList: urlPrefix + '/system/menu/getMenusList',
    getParentMenusTree:urlPrefix + '/system/menu/getParentMenusTree',
    addMenu:urlPrefix + '/system/menu/addMenu',
    updateMenu:urlPrefix + '/system/menu/updateMenu',
    deleteMenu:urlPrefix + '/system/menu/deleteMenu',
}