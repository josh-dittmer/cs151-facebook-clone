import { useEffect } from 'react';
import { useState } from 'react';
import Image from 'next/image';

interface ProfileProps {
    userId: string
};

export default function ProfileComponent({ userId }: ProfileProps) {
    const [username, setUsername] = useState<string>('');
    const [displayName, setDisplayName] = useState<string>('');
    const [bio, setBio] = useState<string>('');
    const [numFollowers, setNumFollowers] = useState<number>(0);
    const [numFollowing, setNumFollowing] = useState<number>(0);
    const [myProfile, setMyProfile] = useState<boolean>(false);
    const [following, setFollowing] = useState<boolean>(false);

    // load profile
    useEffect(() => {
        setUsername('test.user1');
        setDisplayName('Test User');
        setBio('I am a very interesting little guy');
        setNumFollowers(1456);
        setNumFollowing(531);
        setMyProfile(false);
        setFollowing(false);
    }, []);

    return (
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
                        <button className="mt-3 ml-2 p-2 bg-gray-200 rounded">Follow</button>
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
    )
};