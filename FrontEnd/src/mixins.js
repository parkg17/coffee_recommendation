import axios from 'axios';


axios.defaults.baseURL = 'http://127.0.0.1:8080';
axios.defaults.headers.post['Content-Type'] = 'application/json;charset=utf-8';
axios.defaults.headers.post['Access-Control-Allow-Origin'] = '*';

const options = {
    headers: {
        'content-type' : 'application/json'
    }
}

export default {
    methods: {
        async $api(url, method, data) {
            return (await axios({
                method: method,
                url,
                data,
                options
            }).catch( e=> {
                console.log(e)
            })).data;
        }
    }
}