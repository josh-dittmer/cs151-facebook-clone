import { useState } from 'react';
import Image from 'next/image';
import { useRouter } from 'next/navigation';

import { createPost, CreatePostResponse } from '@/deps/api_requests';

import Cookie from 'js-cookie';

export default function MakePostComponent() {
    const [postText, setPostText] = useState<string>('');

    const router = useRouter();

    const handleMakePost = async (e: any) => {
        e.preventDefault();

        const token: string | undefined = Cookie.get('token');
        if (!token) {
            return;
        }

        createPost(postText, false, token)
        .then((res: CreatePostResponse) => {
            setPostText('');
            location.reload();
        })
        .catch((err) => {
            console.log('Failed to create post: ' + err);
        })
    };

    return (
        <div className="flex justify-center mb-3">
            <div className="p-4 shadow w-96 md:w-3/5">
                <div className="border-b-2 border-gray-200 p-2 mb-3">
                    <p className="text-lg">New Post</p>
                </div>
                <form onSubmit={handleMakePost}>
                    <textarea
                        value={postText}
                        onChange={(e) => setPostText(e.target.value)}
                        placeholder="What's on your mind?"
                        required
                        className="w-full bg-blue-50 rounded p-3 resize-none"
                    />
                    <div className="mt-3 flex items-center">
                        <button 
                            type="submit"
                            className="p-2 bg-blue-500 text-white rounded"
                        >
                            Share
                        </button>
                        <div className="ml-4 bg-gray-200 rounded-full p-2">
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