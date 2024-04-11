import { useEffect } from 'react';
import { useState } from 'react';

import { getUserPosts, UserPostsResponse, UserPost } from '@/deps/api_requests';
import PostComponent from './post';

import Cookie from 'js-cookie';

interface FeedProps {
    userIds: Array<string>
};

export default function FeedComponent({ userIds }: FeedProps) {
    const [posts, setPosts] = useState<React.ReactElement[]>([]);
    
    const loadPosts = async () => {
        const token: string | undefined = Cookie.get('token');
        if (!token) {
            return;
        }

        const postArr: React.ReactElement[] = [];

        let res: UserPostsResponse = await getUserPosts(userIds, 0, token);
        res.posts.forEach((post: UserPost) => {
            postArr.push(<PostComponent 
                key={post.postId}
                postId={post.postId}
                userId={post.userId}
                username={post.username}
                displayName={post.displayName}
                text={post.text}
                hasImage={post.hasImage}
                liked={post.liked}
                numLikes={post.numLikes}
                numComments={post.numComments}
                timestamp={post.timestamp}
            />);
        });

        setPosts(postArr);
    };

    // load posts here
    useEffect(() => {
        loadPosts();
    }, []);
    
    return (
        <div>
            {posts}
        </div>
    )
}