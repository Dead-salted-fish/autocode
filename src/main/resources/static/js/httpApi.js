async function  httpGet(url,parm){
    let data = null;
    let token = window.localStorage.getItem("token")

        await Vue.http.get(url, {
            headers:{token:token},
            params:parm
        }).then((success) => {
            console.log('success',success)
            data = success.body
        }, (error) => {
            console.log('error',error);
        })
        return data
}

async function  httpPost(url,parm){
    let data = null;
    let token = window.localStorage.getItem("token")

        await Vue.http.post(url,parm,{
            headers:{token:token},
        }).then((success) => {
            console.log('success',success)
            data = success.body
        }, (error) => {
            console.log('error',error);
        })
        return data
}