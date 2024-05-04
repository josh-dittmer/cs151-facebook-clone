import { useEffect } from 'react';
import { useState } from 'react';
import { useRouter } from 'next/navigation';
import Image from 'next/image';

import { getUserProfile, UserProfileResponse, followUser, unfollowUser, SuccessResponse, apiUrl } from '@/deps/api_requests';

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
    const [pfpUrl, setPfpUrl] = useState<string>('/img/no_pfp.png');

    const router = useRouter();

    const follow = () => {
        const token: string | undefined = Cookie.get('token');
        if (!token) {
            return;
        }

        followUser(userId, token)
        .then((res: SuccessResponse) => {
            setFollowing(true);
            setNumFollowers(numFollowers + 1);
        })
        .catch((err) => {
            if (err.code === -3) {
                // session expired
                router.push('/login');
            } else {
                console.log('Failed to follow user!');
                console.log(err);
            }
        })
    };

    const unfollow = () => {
        const token: string | undefined = Cookie.get('token');
        if (!token) {
            return;
        }

        unfollowUser(userId, token)
        .then((res: SuccessResponse) => {
            setFollowing(false);
            setNumFollowers(numFollowers - 1);
        })
        .catch((err) => {
            if (err.code === -3) {
                // session expired
                router.push('/login');
            } else {
                console.log('Failed to unfollow user!');
                console.log(err);
            }
        })
    };

    // load profile
    useEffect(() => {
        const token: string | undefined = Cookie.get('token');
        if (!token) {
            return;
        }

        setPfpUrl(apiUrl + '/resource/' + userId + '?s=' + Cookie.get('token'));

        getUserProfile(userId, token)
        .then((res: UserProfileResponse) => {
            setUsername(res.user.username);
            setDisplayName(res.user.displayName);
            setBio(res.user.bio);
            setNumFollowers(res.user.numFollowers);
            setNumFollowing(res.user.numFollowing);
            setIsMyProfile(res.user.isMyProfile);

            if (isMyProfile) {
                setFollowing(true);
            } else {
                // will check if following in future
                setFollowing(res.user.isFollowing);
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
                        <img 
                            src={pfpUrl}
                            width="100"
                            height="100"
                            alt="Profile photo"
                            className="border-2 border-blue-500 rounded-full"
                        />
                        <div className="ml-5">
                            <span className="text-xl p-2">{username}</span>
                            <br />
                            {isMyProfile === true ? (
                                <button className="mt-3 ml-2 p-2 bg-gray-200 hover:bg-gray-300 rounded">Edit Profile</button>
                            ) : (
                                <div>
                                    {following === true ? (
                                        <button onClick={unfollow} className="mt-3 ml-2 p-2 bg-gray-200 hover:bg-gray-300 rounded">Unfollow</button>
                                    ) : (
                                        <button onClick={follow} className="mt-3 ml-2 p-2 bg-blue-500 hover:bg-blue-600 text-white rounded">Follow</button>
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