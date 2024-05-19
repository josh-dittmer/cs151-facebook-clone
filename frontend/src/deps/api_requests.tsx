const apiHost: string = 'localhost';
const apiPort: string = '9000';

export const apiUrl: string = 'http://' + apiHost + ':' + apiPort;

export interface SuccessResponse {
    success: boolean
}

export interface Notification {
    notificationId: string;
    userId: string;
    username: string;
    action: string;
    targetId: string;
    targetType: string;
    timestamp: string;
}

// used to generate the notification message for notifications
export function generateNotificationMessage(action: string, targetType: string, targetId: string): string {
    switch (action) {
        case 'like':
            return `You received a like on your ${targetType} (${targetId}).`;
        case 'follow':
            return `You have a new follower (${targetId}).`;
        default:
            return `New notification: ${action} ${targetType} (${targetId}).`;
    }
}

export interface NotificationListResponse {
    success: boolean;
    notifications: Notification[];
}

export async function getNotifications(token: string): Promise<NotificationListResponse> {
    return new Promise<NotificationListResponse>((resolve, reject) => {
        // Implementation to fetch notifications goes here
        // This function is responsible for fetching notifications from the server
        // You can replace the dummy data with actual API calls
        // For now, we're just returning some dummy data for testing
        setTimeout(() => {
            resolve({
                success: true,
                notifications: [
                    {
                        notificationId: '1',
                        userId: 'user123',
                        username: 'user123',
                        action: 'liked',
                        targetId: 'post123',
                        targetType: 'post',
                        timestamp: '2024-04-30T12:00:00Z',
                    },
                    {
                        notificationId: '2',
                        userId: 'user456',
                        username: 'user456',
                        action: 'followed',
                        targetId: 'user123',
                        targetType: 'user',
                        timestamp: '2024-04-29T12:00:00Z',
                    },
                ],
            });
        }, 1000); // Simulating a delay
    });
}

export interface Message {
    messageId: string;
    senderId: string;
    senderName: string;
    receiverId: string;
    receiverName: string;
    content: string;
    timestamp: string;
}

export interface MessageListResponse {
    success: boolean;
    messages: Message[];
}

export async function getMessages(token: string): Promise<MessageListResponse> {
    return new Promise<MessageListResponse>((resolve, reject) => {
        setTimeout(() => {
            resolve({
                success: true,
                messages: [
                    {
                        messageId: '1',
                        senderId: 'Admin',
                        senderName: 'Admin',
                        receiverId: 'user456',
                        receiverName: 'user456',
                        content: 'Hello! Welcome to Facebook!',
                        timestamp: '2024-05-19T3:31:00Z',
                    },
                    {
                        messageId: '2',
                        senderId: 'Mark Zuckerborg (Bot)',
                        senderName: 'Mark ZuckerBorg',
                        receiverId: 'user123',
                        receiverName: 'user123',
                        content: 'Hi there! I am a human like you. Would you like to be friends?',
                        timestamp: '2024-05-02T12:00:00Z',
                    },
                ],
            });
        }, 1000); // Simulating a delay
    });
}



export async function makeRequest(url: string, method: string, data: Object): Promise<any> {
    return new Promise((resolve, reject) => {
        let body: string = JSON.stringify(data);
        body = body.replaceAll('%', ''); // NOT on my watch

        fetch(url, {
            method: method,
            headers: {
                'Content-Type': 'application/json'
            },
            body: body
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

export interface PostComment {
    commentId: string,
    postId: string,
    userId: string,
    username: string,
    displayName: string,
    text: string,
    isMyComment: boolean,
    timestamp: string
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
    comments: Array<PostComment>
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

export interface CreateCommentResponse {
    success: boolean,
    commentId: string,
    userId: string,
    username: string,
    displayName: string
}

export async function createComment(postId: string, text: string, token: string): Promise<CreateCommentResponse> {
    return new Promise<CreateCommentResponse>(async (resolve, reject) => {
        makeRequest(apiUrl + '/create_comment', 'post', {
            postId: postId,
            text: text,
            token: token
        })
        .then((res) => {
            if (!res.success) {
                reject(res);
            }

            let createCommentResponse: CreateCommentResponse = res as CreateCommentResponse;
            resolve(createCommentResponse);
        })
        .catch((err) => {
            reject(err);
        });
    });
}

export async function deleteComment(postId: string, commentId: string, token: string): Promise<SuccessResponse> {
    return new Promise<SuccessResponse>(async (resolve, reject) => {
        makeRequest(apiUrl + '/delete_comment', 'post', {
            postId: postId,
            commentId: commentId,
            token: token
        })
        .then((res) => {
            if (!res.success) {
                reject(res);
            }

            let deleteCommentResponse: SuccessResponse = res as SuccessResponse;
            resolve(deleteCommentResponse);
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

