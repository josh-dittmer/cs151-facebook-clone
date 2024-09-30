import { useEffect } from 'react';
import { useState } from 'react';
import Link from 'next/link';

import { getUserPosts, UserPostsResponse, UserPost } from '@/deps/api_requests';
import PostComponent from './post';
import MakePostComponent from './make_post';

import Cookie from 'js-cookie';

interface FeedProps {
    userIds: Array<string>
};

export default function FeedComponent({ userIds }: FeedProps) {
    //const [posts, setPosts] = useState<React.ReactElement[]>([]);
    const [posts, setPosts] = useState<UserPost[]>([]);
    const [noPosts, setNoPosts] = useState<boolean>(false);
    
    const loadPosts = async () => {
        const token: string | undefined = Cookie.get('token');
        if (!token) {
            return;
        }

        //const postArr: React.ReactElement[] = [];

        let res: UserPostsResponse = await getUserPosts(userIds, 0, token);
        if (res.posts.length === 0) {
            setNoPosts(true);
            return;
        }

        /*res.posts.forEach((post: UserPost) => {
            postArr.push(<PostComponent 
                key={post.postId}
                post={post}
            />);
        });*/

        setPosts(res.posts);
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
                        <span className="text-gray-500">No posts here yet! <Link href="/home/search" className="underline">Search</Link> for some friends to follow!</span>
                    </div>
                </center>
            )}
            {posts.map((post) => (
                <PostComponent key={post.postId} post={post} postsState={posts} setPostsState={setPosts}/>
            ))}
        </div>
    )
}