import { useEffect } from 'react';
import Image from 'next/image';

interface PostProps {
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

export default function PostComponent({ postId, userId, username, displayName, text, hasImage, liked, numLikes, numComments, timestamp }: PostProps) {
    // load comments here
    useEffect(() => {
        
    }, []);
    
    return (
        <div className="flex justify-center mb-1">
            <div className="p-4 bg-blue-50 w-96 md:w-3/5">
                <div className="p-1 flex items-center whitespace-nowrap">
                    <Image 
                        src="/img/no_pfp.png"
                        width="35"
                        height="35"
                        alt="Profile photo"
                        className="p-1 border-2 border-blue-500 rounded-full"
                    />
                    <span className="p-2">{displayName}</span>
                </div>
                <div className="py-2 border-t-2 border-t-blue-500">
                    {hasImage && (
                        <div className="">
                            <Image
                                src="/img/example.jpg"
                                width="100"
                                height="100"
                                alt="User image"
                                className="w-full mb-2"
                            />
                        </div>
                    )}
                    <div className="my-2">
                        <div className="mb-2 border-b-2 border-gray-200">
                            <span className="text-xs text-gray-400">{timestamp}</span>
                        </div>
                        <div>
                            <span className="text-sm font-bold mr-2">{username}</span>
                            <span className="text-sm">{text}</span>
                        </div>
                    </div>
                    <div className="flex items-center">
                        <div className="flex items-center p-2 border-2 border-blue-200 rounded-full">
                            <Image
                                src="/img/like.svg"
                                width="23"
                                height="23"
                                alt="Like"
                                className=""
                            />
                            <span className="text-xs ml-2">{numLikes}</span>
                        </div>
                        <div className="flex items-center ml-2 p-2 border-2 border-blue-200 rounded-full">
                            <Image
                                src="/img/comment.svg"
                                width="23"
                                height="23"
                                alt="Comment"
                                className=""
                            />
                            <span className="text-xs ml-1">{numComments}</span>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    )
}