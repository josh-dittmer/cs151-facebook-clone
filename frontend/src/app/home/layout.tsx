'use client';

import  { useState, useEffect } from 'react';
import { useRouter } from 'next/navigation';
import Link from 'next/link';
import Image from 'next/image';

import { getUserProfile, UserProfileResponse, logout, SuccessResponse, apiUrl } from '@/deps/api_requests';

import Cookie from 'js-cookie';

export default function HomeLayout({ children }: Readonly<{children: React.ReactNode;}>) {
    const [username, setUsername] = useState<string>();
    const [userId, setUserId] = useState<string>();

    const [sidebarShowing, setSidebarShowing] = useState<boolean>(true);
    const [profileDropdownShowing, setProfileDropdownShowing] = useState<boolean>(false);
    
    const toggleSidebar = () => {
        setSidebarShowing(!sidebarShowing);
    }

    const toggleProfileDropdown = () => {
        setProfileDropdownShowing(!profileDropdownShowing);
    }

    const [pfpUrl, setPfpUrl] = useState<string>('/img/no_pfp.png');

    const router = useRouter();

    useEffect(() => {
        const token: string | undefined = Cookie.get('token');
        if (!token) {
            return;
        }

        getUserProfile('me', token)
        .then((res: UserProfileResponse) => {
            setUsername(res.user.username);
            setUserId(res.user.userId);

            setPfpUrl(apiUrl + '/resource/' + res.user.userId + '?s=' + Cookie.get('token'));
        })
        .catch((err) => {
            if (err.code === -3) {
                // session expired
                router.push('/login');
            } else {
                console.log('Failed to load user profile!');
                console.log(err);
            }
        });
    }, []);

    const clientLogout = () => {
        const token: string | undefined = Cookie.get('token');
        if (!token) {
            return;
        }

        logout(token)
        .then((res: SuccessResponse) => {
            document.cookie = '';
            router.push('/login');
        })
        .catch((err) => {
            console.log('Failed to logout!');
            console.log(err);
        })
    }

    return (
        <div>
            <nav className="fixed top-0 z-50 w-full bg-white border-b border-gray-200">
            <div className="px-3 py-3 lg:px-5 lg:pl-3">
                <div className="flex items-center justify-between">
                    <div className="flex items-center justify-start">
                        <button onClick={toggleSidebar} type="button" className="inline-flex items-center p-2 mx-3 text-sm text-gray-500 rounded-lg sm:hidden hover:bg-gray-100 focus:outline-none focus:ring-2 focus:ring-gray-200 text-gray-400">
                            <span className="sr-only">Open sidebar</span>
                            <img src="/img/sidebar.png" className="w-6 h-6" />
                        </button>
                        
                        <h1 className="p-2 text-blue-500 text-3xl overflow-hidden text-nowrap">Facebook</h1>
                    </div>
                    <div className="flex items-center justify-end">
                        <div className="flex items-center">
                            <img 
                                src={pfpUrl}
                                onClick={toggleProfileDropdown}
                                width="30"
                                height="30"
                                alt="Profile photo"
                                className="border-2 border-blue-500 rounded-full mr-1"
                            />
                            <button onClick={toggleProfileDropdown} className="text-gray-500 hidden md:block">{username}⌄</button>
                        </div>
                    </div>
                </div>
            </div>
            <div className={(profileDropdownShowing ? '' : 'hidden')}>
                <div className="fixed top-100 right-0 z-50 p-5 border border-gray-200 bg-white">
                    <h1 className="text-lg">Options</h1>
                    <ul>
                        <li className="p-2">
                            <Link href="/home/profile/me" onClick={toggleProfileDropdown} className="underline"> Profile</Link>
                        </li>
                        <li className="p-2">
                            <Link href="#"  onClick={toggleProfileDropdown} className="underline">Settings</Link>
                        </li>
                        <li className="p-2">
                            <button onClick={clientLogout} className="text-red-500 underline">Log out</button>
                        </li>
                    </ul>
                </div>
            </div>
            </nav>
            <aside className={'fixed top-0 left-0 z-40 w-64 h-screen pt-20 transition-transform ' + (sidebarShowing ? '-translate-x-full' : '') + ' bg-white border-r border-gray-200 sm:translate-x-0'}>
            <div className="h-full px-3 pb-4 overflow-y-none bg-whit">
                <ul className="space-y-2 font-medium">
                    <li>
                        <Link href="/home/timeline" onClick={toggleSidebar}
                              className="flex items-center p-2 text-gray-900 rounded-lg hover:bg-gray-100 group">
                            <img src="/img/home.png"
                                 className="flex-shrink-0 w-5 h-5 text-gray-500 transition duration-75 group-hover:text-gray-900"/>
                            <span className="flex-1 ms-3 whitespace-nowrap">Home</span>
                        </Link>
                    </li>
                    <li>
                        <Link href="/home/search" onClick={toggleSidebar}
                              className="flex items-center p-2 text-gray-900 rounded-lg hover:bg-gray-100 group">
                            <img src="/img/search.png"
                                 className="flex-shrink-0 w-5 h-5 text-gray-500 transition duration-75 group-hover:text-gray-900"/>
                            <span className="flex-1 ms-3 whitespace-nowrap">Search</span>
                        </Link>
                    </li>
                    <li>
                        <Link href="/home/notifications" onClick={toggleSidebar}
                              className="flex items-center p-2 text-gray-900 rounded-lg hover:bg-gray-100 group">
                            <img src="/img/notification.png"
                                 className="flex-shrink-0 w-5 h-5 text-gray-500 transition duration-75 group-hover:text-gray-900"/>
                            <span className="flex-1 ms-3 whitespace-nowrap">Notifications</span>
                            <span
                                className="inline-flex items-center justify-center w-3 h-3 p-3 ms-3 text-sm font-medium text-blue-500 bg-blue-100 rounded-full">69</span>
                        </Link>
                    </li>
                    <li>
                        <Link href="/home/profile/me" onClick={toggleSidebar}
                              className="flex items-center p-2 text-gray-900 rounded-lg hover:bg-gray-100 group">
                            <img src="/img/profile.png"
                                 className="flex-shrink-0 w-5 h-5 text-gray-500 transition duration-75 group-hover:text-gray-900"/>
                            <span className="flex-1 ms-3 whitespace-nowrap">My Profile</span>
                        </Link>
                    </li>
                    <li>
                        <Link href="/home/messages" onClick={toggleSidebar}
                              className="flex items-center p-2 text-gray-900 rounded-lg hover:bg-gray-100 group">
                            <img src="/img/messages.png"
                                 className="flex-shrink-0 w-5 h-5 text-gray-500 transition duration-75 group-hover:text-gray-900"/>
                            <span className="flex-1 ms-3 whitespace-nowrap">Messages</span>
                        </Link>
                    </li>
                </ul>
            </div>
            </aside>

            <div className="p-4 sm:ml-64">
                <div className="p-4 mt-14">
                    <div className="block">
                        {children}
                    </div>
                </div>
            </div>
        </div>
    );
}