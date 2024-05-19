// src/app/directMessages/DirectMessagesPage.tsx
// import React from 'react';
//
// const DirectMessagesComponent: React.FC = () => {
//     return (
//         <div>
//             <h2>Direct Messages</h2>
//             <p>This is the direct messages page.</p>
//         </div>
//     );
// };
//
// export default DirectMessagesComponent;

import React from 'react';
import { Message } from '@/deps/api_requests';

interface MessageProps {
    message: Message;
}

const MessageComponent: React.FC<MessageProps> = ({ message }) => {
    return (
        <div className="border border-gray-300 rounded-lg p-4 mb-4">
            <p className="text-lg font-bold mb-2">{message.senderName}</p>
            <div className="bg-gray-100 p-3 rounded-lg">
                <p className="text-sm">{message.content}</p>
                <p className="text-xs text-gray-500">{message.timestamp}</p>
            </div>
        </div>
    );
};

export default MessageComponent;
