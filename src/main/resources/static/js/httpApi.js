async function httpGet(url, _param) {
    let data = null;
    let token = window.localStorage.getItem("token")
    let param = {}

    if(_param){
        Object.keys(_param).forEach(item=>{
            if(_param[item]){
                param[item] = _param[item]
            }
        })
    }


    await Vue.http.get(url, {
        headers: {token: token},
        params: param
    }).then((success) => {
        data = success.body
    }, (error) => {
        console.log('error', error);
    })
    return data
}

async function httpPost(url, _param) {
    let data = null;
    let token = window.localStorage.getItem("token")
    let param = {}

    if(_param){
        Object.keys(_param).forEach(item=>{
            if(_param[item]){
                param[item] = _param[item]
            }
        })
    }

    await Vue.http.post(url, param, {
        headers: {token: token},
    }).then((success) => {
        console.log('success', success)
        data = success.body
    }, (error) => {
        console.log('error', error);
    })
    return data
}