const apiHost: string = 'localhost';
const apiPort: string = '9000';

export const apiUrl: string = 'http://' + apiHost + ':' + apiPort;

export interface SuccessResponse {
    success: boolean
}

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

export interface TokenResponse {
    success: boolean,
    token: string
}

export async function login(username: string, password: string): Promise<TokenResponse> {
    return new Promise<TokenResponse>(async (resolve, reject) => {
        makeRequest(apiUrl + '/login', 'post', {
            username: username,
            password: password
        })
        .then((res) => {
            if (!res.success) {
                reject(res);
            }

            let loginResponse: TokenResponse = res as TokenResponse;
            resolve(loginResponse);
        })
        .catch((err) => {
            reject(err);
        });
    });
}

export async function logout(token: string): Promise<SuccessResponse> {
    return new Promise<SuccessResponse>(async (resolve, reject) => {
        makeRequest(apiUrl + '/logout', 'post', {
            token: token,
        })
        .then((res) => {
            if (!res.success) {
                reject(res);
            }

            let logoutResponse: SuccessResponse = res as SuccessResponse;
            resolve(logoutResponse);
        })
        .catch((err) => {
            reject(err);
        });
    });
}

export async function signup(username: string, password: string, displayName: string, bio: string): Promise<TokenResponse> {
    return new Promise<TokenResponse>(async (resolve, reject) => {
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

            let loginResponse: TokenResponse = res as TokenResponse;
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
    isMyProfile: boolean,
    isFollowing: boolean
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
    timestamp: string,
    isMyPost: boolean
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

export async function deletePost(postId: string, token: string): Promise<SuccessResponse> {
    return new Promise<SuccessResponse>(async (resolve, reject) => {
        makeRequest(apiUrl + '/delete_post', 'post', {
            itemId: postId,
            token: token
        })
        .then((res) => {
            if (!res.success) {
                reject(res);
            }

            let deletePostResponse: SuccessResponse = res as SuccessResponse;
            resolve(deletePostResponse);
        })
        .catch((err) => {
            reject(err);
        });
    });
}

export async function likePost(postId: string, token: string): Promise<SuccessResponse> {
    return new Promise<SuccessResponse>(async (resolve, reject) => {
        makeRequest(apiUrl + '/like_post', 'post', {
            itemId: postId,
            token: token
        })
        .then((res) => {
            if (!res.success) {
                reject(res);
            }

            let likePostResponse: SuccessResponse = res as SuccessResponse;
            resolve(likePostResponse);
        })
        .catch((err) => {
            reject(err);
        });
    });
}

export async function unlikePost(postId: string, token: string): Promise<SuccessResponse> {
    return new Promise<SuccessResponse>(async (resolve, reject) => {
        makeRequest(apiUrl + '/unlike_post', 'post', {
            itemId: postId,
            token: token
        })
        .then((res) => {
            if (!res.success) {
                reject(res);
            }

            let likePostResponse: SuccessResponse = res as SuccessResponse;
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

export async function followUser(userId: string, token: string): Promise<SuccessResponse> {
    return new Promise<SuccessResponse>(async (resolve, reject) => {
        makeRequest(apiUrl + '/follow_user', 'post', {
            itemId: userId,
            token: token
        })
        .then((res) => {
            if (!res.success) {
                reject(res);
            }

            let likePostResponse: SuccessResponse = res as SuccessResponse;
            resolve(likePostResponse);
        })
        .catch((err) => {
            reject(err);
        });
    });
}

export async function unfollowUser(userId: string, token: string): Promise<SuccessResponse> {
    return new Promise<SuccessResponse>(async (resolve, reject) => {
        makeRequest(apiUrl + '/unfollow_user', 'post', {
            itemId: userId,
            token: token
        })
        .then((res) => {
            if (!res.success) {
                reject(res);
            }

            let likePostResponse: SuccessResponse = res as SuccessResponse;
            resolve(likePostResponse);
        })
        .catch((err) => {
            reject(err);
        });
    });
}

export async function getUserFollowing(userId: string, token: string): Promise<UserListResponse> {
    return new Promise<UserListResponse>(async (resolve, reject) => {
        makeRequest(apiUrl + '/user_following', 'post', {
            itemId: userId,
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

export async function uploadFile(file: File, associatedId: string, token: string): Promise<SuccessResponse> {
    return new Promise<SuccessResponse>(async (resolve, reject) => {
        let data: FormData = new FormData();

        data.append('file', file);
        data.append('associatedId', associatedId);
        data.append('token', token);

        fetch(apiUrl + '/upload', {
            method: 'POST',
            body: data
        })
        .then((res) => {
            res.json()
            .then((jsonRes) => {
                if (!jsonRes.success) {
                    reject(jsonRes);
                }

                resolve(jsonRes);
            })
        })
        .catch(() => {
            reject('upload failed');
        })
    });
}