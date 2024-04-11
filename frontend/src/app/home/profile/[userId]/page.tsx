'use client';

import { useEffect } from 'react';

import FeedComponent from '../../components/feed';
import ProfileComponent from '../../components/profile';
import MakePostComponent from '../../components/make_post';

interface UserProfilePageParams {
    userId: string;
}

export default function UserProfilePage({ params }: { params: UserProfilePageParams }) {
    return (
        <div>
            <ProfileComponent userId={params.userId}></ProfileComponent>
            <FeedComponent userIds={[params.userId]}></FeedComponent>
        </div>
    );
}