import { useEffect } from 'react';
import { useState } from 'react';
import { useRouter } from 'next/navigation';
import Image from 'next/image';

import { getUserProfile, UserProfileResponse } from '@/deps/api_requests';

import Cookie from 'js-cookie';
import MakePostComponent from './make_post';


interface ProfileProps {
    userId: string
};

export default function ProfileComponent({ userId }: ProfileProps) {
    const [username, setUsername] = useState<string>('');
    const [displayName, setDisplayName] = useState<string>('');
    const [bio, setBio] = useState<string>('');
    const [numFollowers, setNumFollowers] = useState<number>(0);
    const [numFollowing, setNumFollowing] = useState<number>(0);
    const [isMyProfile, setIsMyProfile] = useState<boolean>(false);
    const [following, setFollowing] = useState<boolean>(false);

    const router = useRouter();

    // load profile
    useEffect(() => {
        const token: string | undefined = Cookie.get('token');
        if (!token) {
            return;
        }

        getUserProfile(userId, token)
        .then((res: UserProfileResponse) => {
            setUsername(res.user.username);
            setDisplayName(res.user.displayName);
            setBio(res.user.bio);
            setNumFollowers(res.user.numFollowers);
            setNumFollowing(res.user.numFollowing);
            setIsMyProfile(res.user.isMyProfile);

            if (isMyProfile) {
                setFollowing(false);
            } else {
                // will check if following in future
                setFollowing(false);
            }
        })
        .catch((err) => {
            if (err.code === -3) {
                // session expired
                router.push('/login');
            } else {
                console.log('Failed to load user profile!');
                console.log(err);
            }
        });
    }, []);

    return (
        <div>
            <div className="flex justify-center shadow mb-3">
                <div className="p-2 w-full">
                    <div className="p-1 flex items-center">
                        <Image 
                            src="/img/no_pfp.png"
                            width="100"
                            height="100"
                            alt="Profile photo"
                            className="p-1 border-2 border-blue-500 rounded-full"
                        />
                        <div className="ml-5">
                            <span className="text-xl p-2">{username}</span>
                            <br />
                            {isMyProfile === true ? (
                                <button className="mt-3 ml-2 p-2 bg-gray-200 rounded">Edit Profile</button>
                            ) : (
                                <div>
                                    {following === true ? (
                                        <button className="mt-3 ml-2 p-2 bg-gray-200 rounded">Unfollow</button>
                                    ) : (
                                        <button className="mt-3 ml-2 p-2 bg-blue-500 text-white rounded">Follow</button>
                                    )}
                                </div>
                            )}
                        </div>
                    </div>
                    <div className="mt-3">
                        <span className="font-bold">{numFollowers}</span><span className="mr-3"> followers</span>
                        <span className="font-bold">{numFollowing}</span><span className=""> following</span>
                    </div>
                    <div className="mt-3">
                        <span className="font-bold">{displayName}</span>
                        <br />
                        <span className="">{bio}</span>
                    </div>
                </div>
            </div>
            {isMyProfile === true && (
                <MakePostComponent></MakePostComponent>
            )}
        </div>
    )
};