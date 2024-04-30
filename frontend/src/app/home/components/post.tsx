import { useState, useEffect } from 'react';
import { useRouter } from 'next/navigation';
import Image from 'next/image';
import Link from 'next/link';

import { SuccessResponse, likePost, unlikePost, deletePost, UserPost, apiUrl } from '@/deps/api_requests';

import Cookie from 'js-cookie';

interface PostProps {
    post: UserPost
    /*postId: string,
    userId: string,
    username: string,
    displayName: string,
    text: string,
    hasImage: boolean,
    liked: boolean,
    numLikes: number,
    numComments: number,
    timestamp: string*/
}

export default function PostComponent({ post }: PostProps) {
    const [likedState, setLikedState] = useState<boolean>(post.liked);
    const [numLikesState, setNumLikesState] = useState<number>(post.numLikes);
    
    const [showDeleteConfirmation, setShowDeleteConfirmation] = useState<boolean>(false);

    const toggleDeleteConfirmation = () => {
        setShowDeleteConfirmation(!showDeleteConfirmation);
    }

    const router = useRouter();

    const token: string | undefined = Cookie.get('token');
    if (!token) {
        return;
    }

    const clientLikePost = () => {
        likePost(post.postId, token)
        .then((res: SuccessResponse) => {
            setLikedState(true);
            setNumLikesState((+numLikesState + 1));
        })
        .catch((err) => {
            if (err.code === -3) {
                // session expired
                router.push('/login');
            } else {
                console.log('Failed to like post!');
                console.log(err);
            }
        })
    };

    const clientUnlikePost = () => {
        unlikePost(post.postId, token)
        .then((res: SuccessResponse) => {
            setLikedState(false);
            setNumLikesState((+numLikesState - 1));
        })
        .catch((err) => {
            if (err.code === -3) {
                // session expired
                router.push('/login');
            } else {
                console.log('Failed to unlike post!');
                console.log(err);
            }
        })
    };

    const clientDeletePost = () => {
        deletePost(post.postId, token)
        .then((res: SuccessResponse) => {
            location.reload();
        })
        .catch((err) => {
            if (err.code === -3) {
                // session expired
                router.push('/login');
            } else {
                console.log('Failed to delete post!');
                console.log(err);
            }
        })
    }

    // load comments here
    useEffect(() => {
        
    }, []);
    
    return (
        <div className="flex justify-center mb-1">
            <div className="p-4 shadow w-96 md:w-3/5">
                <div className="flex justify-between items-center">
                    <Link href={'/home/profile/' + post.userId}>
                        <div className="p-1 flex justify-start whitespace-nowrap">
                            <Image 
                                src="/img/no_pfp.png"
                                width="40"
                                height="40"
                                alt="Profile photo"
                                className="p-1 border-2 border-blue-500 rounded-full"
                            />
                            <span className="p-2">{post.displayName}</span>
                        </div>
                    </Link>
                    <div className={'flex justify-end ' + (post.isMyPost ? '' : 'hidden')}>
                        {showDeleteConfirmation ? (
                            <div className="flex items-center">
                                <span className="text-xs text-gray-500 mr-1">Delete post?</span>
                                <button
                                    className="rounded hover:bg-red-500 p-2"
                                    onClick={clientDeletePost}
                                >
                                    <Image
                                        src="/img/x_symbol.svg"
                                        width="16"
                                        height="16"
                                        alt="Confirm delete post"
                                        className=""
                                    />
                                </button>
                                <button
                                    className="rounded hover:bg-gray-200 p-2 mr-1"
                                    onClick={toggleDeleteConfirmation}
                                >
                                    <Image
                                        src="/img/back.svg"
                                        width="16"
                                        height="16"
                                        alt="Cancel delete post"
                                        className=""
                                    />
                                </button>
                            </div>
                        ) : (
                            <div>
                                <button
                                    className="rounded hover:bg-gray-200 p-2"
                                    onClick={toggleDeleteConfirmation}
                                >
                                    <Image
                                        src="/img/x_symbol.svg"
                                        width="16"
                                        height="16"
                                        alt="Delete post"
                                        className=""
                                    />
                                </button>
                            </div>
                        )}
                    </div>
                </div>
                <div className="py-2">
                    {post.hasImage === true && (
                        <div className="flex justify-center">
                            <img
                                src={apiUrl + '/resource/' + post.postId + '?s=' + Cookie.get('token')}
                                alt="User image"
                                className="max-w-96 max-h-96 min-w-60 min-h-60 mb-2"
                            />
                        </div>
                    )}
                    <div className="my-2 break-words">
                        <div className="mb-2 border-b-2s border-gray-200">
                            <span className="text-xs text-gray-400">{post.timestamp}</span>
                        </div>
                        <div className="">
                            <Link href={'/home/profile/' + post.userId}>
                                <span className="text-sm font-bold mr-2">{post.username}</span>
                            </Link>
                            <span className="text-sm">{post.text}</span>
                        </div>
                    </div>
                    <div className="flex items-center">
                        <div className="flex items-center p-2 border-2 border-gray-200 hover:border-gray-300 rounded-full">
                            {likedState === true ? (
                                <a onClick={clientUnlikePost}>
                                    <Image
                                        src="/img/liked.svg"
                                        width="23"
                                        height="23"
                                        alt="Like"
                                        className="pulse-animation"
                                    />
                                </a>
                            ) : (
                                <a onClick={clientLikePost}>
                                    <Image
                                        src="/img/unliked.svg"
                                        width="23"
                                        height="23"
                                        alt="Like"
                                        className=""
                                    />
                                </a> 
                            )}
                            <span className="text-xs ml-2">{numLikesState}</span>
                        </div>
                        <div className="flex items-center ml-2 p-2 border-2 border-gray-200 hover:border-gray-300w rounded-full">
                            <Image
                                src="/img/comment.png"
                                width="23"
                                height="23"
                                alt="Comment"
                                className=""
                            />
                            <span className="text-xs ml-1">{post.numComments}</span>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    )
}