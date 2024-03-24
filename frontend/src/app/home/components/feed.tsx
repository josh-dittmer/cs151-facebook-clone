import { useEffect } from 'react';
import { useState } from 'react';
import PostComponent from './post';

interface FeedProps {
    userIds: Array<string>
};

export default function FeedComponent({ userIds }: FeedProps) {
    const [posts, setPosts] = useState<Array<React.ReactElement>>([]);
    
    // load posts here
    useEffect(() => {
        const postArr: Array<React.ReactElement> = [];

        postArr.push(<PostComponent 
            postId={'abc123'}
            userId={'abc123'}
            username={'test.user1'}
            displayName={'Test User'}
            text={'An example caption'}
            hasImage={true}
            liked={false}
            numLikes={123}
            numComments={456}
            timestamp={'1/1/1980 at 12:00am'}
        />);

        postArr.push(<PostComponent 
            postId={'abc123'}
            userId={'abc123'}
            username={'test.user2'}
            displayName={'Another Test'}
            text={'A cooler caption, with more writing in order to make the text wrap. Interesting how that looks.'}
            hasImage={false}
            liked={true}
            numLikes={696}
            numComments={420}
            timestamp={'1/2/2000 at 4:56am'}
        />);

        setPosts(postArr);
    }, []);
    
    return (
        <div>
            {posts}
        </div>
    )
}