'use client';

import { useEffect } from 'react';

import FeedComponent from "../components/feed";
import ProfileComponent from '../components/profile';

export default function MyProfilePage() {
    // load users to be in feed (following)
    useEffect(() => {

    }, []);

    return (
        <div>
            <ProfileComponent userId={''}></ProfileComponent>
            <FeedComponent userIds={['abc123']}></FeedComponent>
        </div>
    );
}