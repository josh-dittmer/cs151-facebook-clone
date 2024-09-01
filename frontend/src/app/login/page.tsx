'use client';

import styles from './styles.module.css';

import { useState } from 'react';
import { useRouter } from 'next/navigation';
import Link from 'next/link';

import { login, TokenResponse } from '@/deps/api_requests'

export default function LoginPage() {
    const [username, setUsername] = useState<string>('');
    const [password, setPassword] = useState<string>('');

    const [errorMessage, setErrorMessage] = useState<string>('');

    const router = useRouter();

    const handleLogin = async (e: any) => {
        e.preventDefault();
        
        login(username, password)
        .then((res: TokenResponse) => {
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
                <div className="bg-white py-10 px-10 text-center shadow-xl shadow-inner rounded">
                    <center>
                        <h1 className="p-2 text-4xl text-blue-500">Facebook</h1>
                    </center>
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
                            className="p-3 my-2 w-72 border-2 border-blue-200 rounded shadow-inner focus:outline-none focus-scale"
                        />
                        <br />
                        <input 
                            type="password"
                            value={password}
                            onChange={(e) => setPassword(e.target.value)}
                            placeholder="Password..."
                            required
                            className="p-3 my-2 w-72 border-2 border-blue-200 rounded shadow-inner focus:outline-none focus-scale"
                        />
                        <br />
                        <button
                            type="submit"
                            className="px-4 py-2 mt-4 rounded bg-blue-500 hover:bg-blue-600 text-white"
                        >
                            Login
                        </button>
                    </form>
                    <div className="mt-5">
                        <span className="text-sm">Don&apos;t have an account? </span>
                        <Link 
                            href="/signup"
                            className="text-blue-500 underline text-sm"
                        >
                            Sign up!
                        </Link>
                    </div>
                </div>
            </div>
        </div>
    )
}