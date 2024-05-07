import { Notification } from '@/deps/api_requests';

interface NotificationProps {
    notification: Notification;
}

const NotificationComponent: React.FC<NotificationProps> = ({ notification }) => {
    return (
        <div>
            {/* Render notification content here */}
            <p>{notification.action}</p>
            <div className="bg-gray-200 p-4 rounded-lg">
                <p>This is a big container under the notification content.</p>
                <p>It can contain additional information or actions related to the notification.</p>
            </div>
        </div>
    );
};

export default NotificationComponent;