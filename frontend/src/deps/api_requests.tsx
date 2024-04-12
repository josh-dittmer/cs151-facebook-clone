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
                reject(res);
            }

            let loginResponse: LoginResponse = res as LoginResponse;
            resolve(loginResponse);
        })
        .catch((err) => {
            reject(err);
        });
    });
}

export async function signup(username: string, password: string, displayName: string, bio: string): Promise<LoginResponse> {
    return new Promise<LoginResponse>(async (resolve, reject) => {
        makeRequest(apiUrl + '/signup', 'post', {
            username: username,
            password: password,
            displayName: displayName,
            bio: bio
        })
        .then((res) => {
            if (!res.success) {
                reject(res);
            }

            let loginResponse: LoginResponse = res as LoginResponse;
            resolve(loginResponse);
        })
        .catch((err) => {
            reject(err);
        });
    });
}

export interface User {
    userId: string,
    username: string,
    displayName: string,
    bio: string,
    numFollowers: number,
    numFollowing: number,
    isMyProfile: boolean
}

export interface UserProfileResponse {
    success: boolean,
    user: User
    /*username: string,
    displayName: string,
    bio: string,
    numFollowers: number,
    numFollowing: number,
    isMyProfile: boolean*/
}

export async function getUserProfile(userId: string, token: string): Promise<UserProfileResponse> {
    return new Promise<UserProfileResponse>(async (resolve, reject) => {
        makeRequest(apiUrl + '/user_profile', 'post', {
            userId: userId,
            token: token
        })
        .then((res) => {
            if (!res.success) {
                reject(res);
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
                reject(res);
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
                reject(res);
            }

            let createPostResponse: CreatePostResponse = res as CreatePostResponse;
            resolve(createPostResponse);
        })
        .catch((err) => {
            reject(err);
        });
    });
}

export interface LikePostResponse {
    success: boolean
};

export async function likePost(postId: string, token: string): Promise<LikePostResponse> {
    return new Promise<LikePostResponse>(async (resolve, reject) => {
        makeRequest(apiUrl + '/like_post', 'post', {
            postId: postId,
            token: token
        })
        .then((res) => {
            if (!res.success) {
                reject(res);
            }

            let likePostResponse: LikePostResponse = res as LikePostResponse;
            resolve(likePostResponse);
        })
        .catch((err) => {
            reject(err);
        });
    });
}

export async function unlikePost(postId: string, token: string): Promise<LikePostResponse> {
    return new Promise<LikePostResponse>(async (resolve, reject) => {
        makeRequest(apiUrl + '/unlike_post', 'post', {
            postId: postId,
            token: token
        })
        .then((res) => {
            if (!res.success) {
                reject(res);
            }

            let likePostResponse: LikePostResponse = res as LikePostResponse;
            resolve(likePostResponse);
        })
        .catch((err) => {
            reject(err);
        });
    });
}

export interface UserListResponse {
    success: boolean,
    users: Array<User>
}

export async function search(query: string, token: string): Promise<UserListResponse> {
    return new Promise<UserListResponse>(async (resolve, reject) => {
        makeRequest(apiUrl + '/search', 'post', {
            query: query,
            token: token
        })
        .then((res) => {
            if (!res.success) {
                reject(res);
            }

            let userListResponse: UserListResponse = res as UserListResponse;
            resolve(userListResponse);
        })
        .catch((err) => {
            reject(err);
        });
    });
}