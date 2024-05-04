import { useState, useEffect } from 'react';
import Image from 'next/image';
import Link from 'next/link';

import { User, apiUrl } from '@/deps/api_requests';

import Cookie from 'js-cookie';

interface ProfileCardProps {
    user: User
}

export default function ProfileCardComponent({ user }: ProfileCardProps) {
    const [pfpUrl, setPfpUrl] = useState<string>('/img/no_pfp.png');

    useEffect(() => {
        setPfpUrl(apiUrl + '/resource/' + user.userId + '?s=' + Cookie.get('token'));
    }, []);

    return (
        <div>
            <div className="flex p-3 shadow my-2 items-center">
                <div className="mr-2">
                    <img 
                        src={pfpUrl}
                        width="30"
                        height="30"
                        alt="Profile photo"
                        className="border-2 border-blue-500 rounded-full"
                    />
                </div>
                <div className="">
                    <Link href={'/home/profile/' + user.userId}>
                        <span className="mr-3">{user.displayName}</span>
                        <span className="text-sm text-gray-500">{user.username}</span>
                    </Link>
                </div>
            </div>
        </div>
    )
}