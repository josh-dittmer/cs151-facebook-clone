'use client';

import { useEffect } from 'react';

import FeedComponent from "../components/feed";

export default function TimelinePage() {
    // load users to be in feed (following)
    useEffect(() => {

    }, []);

    return (
        <div>
            <div className="border-b-2 border-b-blue-50 mb-5 p-5">
                <h1 className="text-3xl font-bold">My Timeline</h1>
            </div>
            <FeedComponent userIds={['abc123']}></FeedComponent>
        </div>
    );
}