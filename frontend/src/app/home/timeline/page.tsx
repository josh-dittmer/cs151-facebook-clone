'use client';

import { useEffect, useState } from 'react';
import Link from 'next/link';

import { getUserFollowing, UserListResponse, User } from '@/deps/api_requests';
import FeedComponent from "../components/feed";

import Cookie from 'js-cookie';


export default function TimelinePage() {
    const [feedElement, setFeedElement] = useState<React.ReactElement>();

    // load users to be in feed (following)
    useEffect(() => {
        const token: string | undefined = Cookie.get('token');
        if (!token) {
            return;
        }

        getUserFollowing('me', token)
        .then((res: UserListResponse) => {
            const userIds: string[] = [];

            res.users.forEach((user: User) => {
                userIds.push(user.userId);
            });

            userIds.push('me');

            setFeedElement(<FeedComponent userIds={userIds}></FeedComponent>);
        })
        .catch((err) => {
            console.log('Search failed!')
            console.log(err);
        })
    }, []);

    return (
        <div>
            <div className="border-b-2 border-b-blue-50 mb-5 p-5">
                <h1 className="text-3xl font-bold">My Timeline</h1>
            </div>
            {feedElement}
        </div>
    );
}