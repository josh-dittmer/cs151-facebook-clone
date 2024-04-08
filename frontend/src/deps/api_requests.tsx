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

export interface UserProfileResponse {
    success: boolean,
    username: string,
    displayName: string,
    bio: string,
    numFollowers: number,
    numFollowing: number
}

export async function getUserProfile(userId: string, token: string): Promise<UserProfileResponse> {
    return new Promise<UserProfileResponse>(async (resolve, reject) => {
        makeRequest(apiUrl + '/user_profile', 'post', {
            userId: userId,
            token: token
        })
        .then((res) => {
            if (!res.success) {
                reject('get user profile failed');
            }

            let userProfileResponse: UserProfileResponse = res as UserProfileResponse;
            resolve(userProfileResponse);
        })
        .catch((err) => {
            reject(err);
        });
    });
}

export interface UserPost {
    postId: string,
    userId: string,
    username: string,
    displayName: string,
    text: string,
    hasImage: boolean,
    liked: boolean,
    numLikes: number,
    numComments: number,
    timestamp: string
}

export interface UserPostsResponse {
    success: boolean,
    posts: Array<UserPost>
}

export async function getUserPosts(userIds: string[], page: number, token: string): Promise<UserPostsResponse> {
    return new Promise<UserPostsResponse>(async (resolve, reject) => {
        makeRequest(apiUrl + '/user_posts', 'post', {
            userIds: userIds,
            page: page,
            token: token
        })
        .then((res) => {
            if (!res.success) {
                reject('get user posts failed');
            }

            let userPostsResponse: UserPostsResponse = res as UserPostsResponse;
            resolve(userPostsResponse);
        })
        .catch((err) => {
            reject(err);
        });
    });
}

export interface CreatePostResponse {
    success: boolean,
    postId: string
}

export async function createPost(text: string, hasImage: boolean, token: string): Promise<CreatePostResponse> {
    return new Promise<CreatePostResponse>(async (resolve, reject) => {
        makeRequest(apiUrl + '/create_post', 'post', {
            text: text,
            hasImage: hasImage,
            token: token
        })
        .then((res) => {
            if (!res.success) {
                reject('create post failed');
            }

            let createPostResponse: CreatePostResponse = res as CreatePostResponse;
            resolve(createPostResponse);
        })
        .catch((err) => {
            reject(err);
        });
    });
}