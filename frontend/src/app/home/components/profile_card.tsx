import { useState, useEffect } from 'react';
import Image from 'next/image';
import Link from 'next/link';

import { User } from '@/deps/api_requests';

import Cookie from 'js-cookie';

interface ProfileCardProps {
    user: User
}

export default function ProfileCardComponent({ user }: ProfileCardProps) {
    return (
        <div>
            <div className="flex p-3 shadow my-2 items-center">
                <div className="mr-2">
                    <Image 
                        src="/img/no_pfp.png"
                        width="30"
                        height="30"
                        alt="Profile photo"
                        className="p-1 border-2 border-blue-500 rounded-full"
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