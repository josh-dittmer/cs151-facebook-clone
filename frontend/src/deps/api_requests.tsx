const apiHost: string = 'localhost';
const apiPort: string = '9000';

export const apiUrl: string = 'http://' + apiHost + ':' + apiPort;

export async function makeRequest(url: string, method: string, data: Object): Promise<any> {
    return new Promise((resolve, reject) => {
        fetch(url, {
            method: method,
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(data)
        })
        .then((res) => {
            resolve(res.json());
        })
        .catch(() => {
            reject('request to ' + url + ' failed');
        })
    });
}

export interface LoginResponse {
    success: boolean,
    token: string
}

export async function login(username: string, password: string): Promise<LoginResponse> {
    return new Promise<LoginResponse>(async (resolve, reject) => {
        makeRequest(apiUrl + '/login', 'post', {
            username: username,
            password: password
        })
        .then((res) => {
            if (!res.success) {
                reject('login failed');
            }

            let loginResponse: LoginResponse = res as LoginResponse;
            resolve(loginResponse);
        })
        .catch((err) => {
            reject(err);
        });
    });
}