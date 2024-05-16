import React from 'react';
import { Notification } from '@/deps/api_requests';
import { generateNotificationMessage } from '@/deps/api_requests';

interface NotificationProps {
    notification: Notification;
}


// const NotificationComponent: React.FC<NotificationProps> = ({ notification }) => {
//     return (
//         <div>
//             {/* Render notification content here */}
//             <p>{notification.action}</p>
//             <div className="bg-gray-200 p-4 rounded-lg">
//                 <p>This is a big container under the notification content.</p>
//                 <p>It can contain additional information or actions related to the notification.</p>
//             </div>
//         </div>
//     );
// };

// const NotificationComponent: React.FC<NotificationProps> = ({ notification }) => {
//     return (
//         <div className="border border-gray-300 rounded-lg p-4 mb-4">
//             <p className="text-lg font-bold mb-2">{notification.action}</p>
//             <div className="bg-gray-100 p-3 rounded-lg">
//                 <p className="text-sm">{notification.notificationId}</p>
//                 <button className="mt-2 px-3 py-1 bg-blue-500 text-white rounded-md hover:bg-blue-600">
//                     View Details
//                 </button>
//             </div>
//         </div>
//     );
// };

const NotificationComponent: React.FC<NotificationProps> = ({ notification }) => {
    const notificationMessage = generateNotificationMessage(notification.action, notification.targetType, notification.targetId);

    return (
        <div className="border border-gray-300 rounded-lg p-4 mb-4">
            <p className="text-lg font-bold mb-2">{notification.action}</p>
            <div className="bg-gray-100 p-3 rounded-lg">
                <p className="text-sm">{notificationMessage}</p>
            </div>
        </div>
    );
};

export default NotificationComponent;
