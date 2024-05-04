import { useState, Dispatch, SetStateAction, useEffect } from 'react';
import { useRouter } from 'next/navigation';
import Image from 'next/image';
import Link from 'next/link';

import { PostComment, deleteComment, SuccessResponse, apiUrl } from '@/deps/api_requests';

import Cookie from 'js-cookie';

interface CommentProps {
    comment: PostComment,
    commentsState: PostComment[],
    setCommentsState: Dispatch<SetStateAction<PostComment[]>>,
    numCommentsState: number,
    setNumCommentsState: Dispatch<SetStateAction<number>>
}

export default function CommentComponent({ comment, commentsState, setCommentsState, numCommentsState, setNumCommentsState }: CommentProps) {
    const [showDeleteConfirmation, setShowDeleteConfirmation] = useState<boolean>(false);

    const toggleDeleteConfirmation = () => {
        setShowDeleteConfirmation(!showDeleteConfirmation);
    }

    const [pfpUrl, setPfpUrl] = useState<string>('/img/no_pfp.png');

    const router = useRouter();

    useEffect(() => {
        setPfpUrl(apiUrl + '/resource/' + comment.userId + '?s=' + Cookie.get('token'));
    }, []);

    const clientDeleteComment = () => {
        const token: string | undefined = Cookie.get('token');
        if (!token) {
            return;
        }

        deleteComment(comment.postId, comment.commentId, token)
        .then((res: SuccessResponse) => {
            let updatedComments: PostComment[] = commentsState.filter((e: PostComment) => {
                return e.commentId !== comment.commentId;
            });

            setCommentsState(updatedComments);
            setNumCommentsState(numCommentsState - 1);
        })
        .catch((err) => {
            if (err.code === -3) {
                // session expired
                router.push('/login');
                //alert('test');
            } else {
                console.log('Failed to delete post!');
                console.log(err);
            }
        })
    }
    
    return (
        <div className="pb-2">
            <div className="flex items-center justify-between">
                <div className="flex justify-start">
                    <Link href={'/home/profile/' + comment.userId}>
                        <div className="flex my-2 items-center">
                            <div className="mr-2">
                                <img 
                                    src={pfpUrl}
                                    width="25"
                                    height="25"
                                    alt="Profile photo"
                                    className="border-2 border-blue-500 rounded-full"
                                />
                            </div>
                            <div className="">
                                <span className="text-sm mr-3">{comment.displayName}</span>
                            </div>
                        </div>
                    </Link>
                </div>
                <div className={'flex justify-end ' + (comment.isMyComment ? '' : 'hidden')}>
                    {showDeleteConfirmation ? (
                        <div className="flex items-center">
                            <span className="text-xs text-gray-500 mr-1">Delete comment?</span>
                            <button
                                className="rounded hover:bg-red-500 p-2"
                                onClick={clientDeleteComment}
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
                                    alt="Cancel delete comment"
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
                                    alt="Delete comment"
                                    className=""
                                />
                            </button>
                        </div>
                    )}
                </div>
            </div>
            <div className="">
                <span className="text-sm">{comment.text}</span>
            </div>
            <div className="">
                <span className="text-xs text-gray-400">{comment.timestamp}</span>
            </div>
        </div>
    )
}