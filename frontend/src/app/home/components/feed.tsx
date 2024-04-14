import { useEffect } from 'react';
import { useState } from 'react';
import Link from 'next/link';

import { getUserPosts, UserPostsResponse, UserPost } from '@/deps/api_requests';
import PostComponent from './post';

import Cookie from 'js-cookie';

interface FeedProps {
    userIds: Array<string>
};

export default function FeedComponent({ userIds }: FeedProps) {
    const [posts, setPosts] = useState<React.ReactElement[]>([]);
    const [noPosts, setNoPosts] = useState<boolean>(false);
    
    const loadPosts = async () => {
        const token: string | undefined = Cookie.get('token');
        if (!token) {
            return;
        }

        const postArr: React.ReactElement[] = [];

        let res: UserPostsResponse = await getUserPosts(userIds, 0, token);
        if (res.posts.length === 0) {
            setNoPosts(true);
            return;
        }

        res.posts.forEach((post: UserPost) => {
            postArr.push(<PostComponent 
                key={post.postId}
                post={post}
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
            {noPosts && (
                <center>
                    <div className="mt-10">
                        <span className="text-gray-500">No posts here yet!</span>
                    </div>
                </center>
            )}
            {posts}
        </div>
    )
}