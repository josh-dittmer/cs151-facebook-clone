'use client';

import { useEffect } from 'react';

import FeedComponent from '../components/feed';
import ProfileComponent from '../components/profile';
import MakePostComponent from '../components/make_post';

export default function MyProfilePage() {
    useEffect(() => {

    }, []);

    return (
        <div>
            <ProfileComponent userId={'me'}></ProfileComponent>
            <MakePostComponent></MakePostComponent>
            <FeedComponent userIds={['me']}></FeedComponent>
        </div>
    );
}