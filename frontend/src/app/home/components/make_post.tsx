import { useState } from 'react';
import Image from 'next/image';
import { useRouter } from 'next/navigation';

import { createPost, CreatePostResponse, uploadFile } from '@/deps/api_requests';

import Cookie from 'js-cookie';

export default function MakePostComponent() {
    const [postText, setPostText] = useState<string>('');
    const [postFile, setPostFile] = useState();

    const router = useRouter();

    const onPostFileChange = (e: any) => {
        //console.log(e.target.files[0]);
        setPostFile(e.target.files[0]);
    }

    const removePostFile = () => {
        setPostFile(undefined);
    }

    const handleMakePost = async (e: any) => {
        e.preventDefault();

        const token: string | undefined = Cookie.get('token');
        if (!token) {
            return;
        }

        const hasImage: boolean = !!postFile;

        createPost(postText, hasImage, token)
        .then(async (res: CreatePostResponse) => {
            if (hasImage) {
                await clientUploadFile(res.postId, token);
            }

            setPostText('');
            location.reload();
        })
        .catch((err) => {
            if (err.code === -3) {
                // session expired
                router.push('/login');
            } else {
                console.log('Failed to create post!');
                console.log(err);
            }
        })
    };

    const clientUploadFile = async (postId: string, token: string) => {
        if (!postFile) {
            return;
        }

        await uploadFile(postFile, postId, token);
    };

    return (
        <div className="flex justify-center mb-3">
            <div className="p-4 shadow w-96 md:w-3/5">
                <div className="border-b-2 border-gray-200 p-2 mb-3">
                    <p className="text-lg">New Post</p>
                </div>
                {postFile && (
                    <div className="relative p-4">
                        <div className="">
                            <img
                                src={URL.createObjectURL(postFile)}
                                alt="Image preview"
                                className="max-width-200 max-height-200 min-width-100 min-height-100"
                            />
                        </div>
                        <div className="absolute top-6 right-6">
                            <button
                                className="rounded bg-red-400 hover:bg-red-500 p-2"
                                onClick={removePostFile}
                            >
                                <Image
                                    src="/img/x_symbol.svg"
                                    width="16"
                                    height="16"
                                    alt="Confirm delete post"
                                    className=""
                                />
                            </button>
                        </div>
                    </div>
                )}
                <form onSubmit={handleMakePost}>
                    <textarea
                        value={postText}
                        onChange={(e) => setPostText(e.target.value)}
                        placeholder="What's on your mind?"
                        className="w-full bg-blue-50 rounded p-3 resize-none"
                    />
                    <div className="mt-3 flex items-center">
                        <button 
                            type="submit"
                            className="p-2 bg-blue-500 hover:bg-blue-600 text-white rounded"
                        >
                            Share
                        </button>
                        <div className="ml-4 bg-gray-200 hover:bg-gray-300 rounded-full p-2">
                            <label 
                                htmlFor="image-picker"
                                className=""
                            >
                                <div className="flex items-center">
                                    <Image
                                        src="/img/image.svg"
                                        width="16"
                                        height="16"
                                        alt="Add image"
                                        className="mr-2"
                                    />
                                    <span className="text-xs">Add Image</span>
                                </div>
                            </label>
                            <input
                                type="file"
                                id="image-picker"
                                onChange={onPostFileChange}
                                accept="image/png, image/jpeg, image/gif"
                                className="hidden"
                            />
                        </div>
                    </div>
                </form>
            </div>
        </div>
    );
}