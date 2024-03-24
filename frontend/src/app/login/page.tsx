'use client';

import styles from './styles.module.css';

import { useState } from 'react';
import { useRouter } from 'next/navigation';

import { login, LoginResponse } from '@/deps/api_requests'

export default function LoginPage() {
    const [username, setUsername] = useState<string>('');
    const [password, setPassword] = useState<string>('');

    const [errorMessage, setErrorMessage] = useState<string>('');

    const router = useRouter();

    const handleLogin = async (e: any) => {
        e.preventDefault();
        
        login(username, password)
        .then((res) => {
            setErrorMessage('');
            document.cookie = 'token=' + res.token + '; path=/';
            router.push('/home/timeline');
        })
        .catch((err) => {
            setErrorMessage('Login failed! Please try again.');
        });
    }

    return (
        <div className={styles.content}>
            <div className="flex h-screen items-center justify-center">
                <div className="bg-white py-10 px-10 text-center shadow-xl rounded">
                    <h1 className="p-2 rounded bg-blue-500 text-white text-2xl text-bold">Facebook😂😂</h1>
                    <br />
                    {errorMessage && (
                    <div className="px-1 py-1">
                        <span className="text-sm text-red-800">{errorMessage}</span>
                        <br />
                    </div>
                    )}
                    <form onSubmit={handleLogin}>
                        <input 
                            type="text"
                            value={username}
                            onChange={(e) => setUsername(e.target.value)}
                            placeholder="Username..."
                            required
                            className="p-2 my-2 border-b-2 border-blue-500 placeholder:text-black"
                        />
                        <br />
                        <input 
                            type="text"
                            value={password}
                            onChange={(e) => setPassword(e.target.value)}
                            placeholder="Password..."
                            required
                            className="p-2 my-2 border-b-2 border-blue-500 placeholder:text-black"
                        />
                        <br />
                        <br />
                        <button
                            type="submit"
                            className="px-4 py-2 rounded bg-blue-500 hover:bg-blue-600 text-white font-bold"
                        >
                            Login
                        </button>
                    </form>
                </div>
            </div>
        </div>
    )
}