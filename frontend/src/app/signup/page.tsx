'use client';

import styles from './styles.module.css';

import { useState } from 'react';
import { useRouter } from 'next/navigation';
import Link from 'next/link';
import Image from 'next/image';

import { signup, TokenResponse, uploadFile } from '@/deps/api_requests';

export default function SignupPage() {
    const [username, setUsername] = useState<string>('');
    const [displayName, setDisplayName] = useState<string>('');
    const [bio, setBio] = useState<string>('');
    const [password, setPassword] = useState<string>('');

    const [errorMessage, setErrorMessage] = useState<string>('');

    const [profilePicFile, setProfilePicFile] = useState();
    const [profilePicFilePickerValue, setProfilePicFilePickerValue] = useState<string>('');

    const router = useRouter();

    const onProfilePicFileChange = (e: any) => {
        setProfilePicFilePickerValue(e.target.value);
        setProfilePicFile(e.target.files[0]);
    }

    const removeProfilePicFile = () => {
        setProfilePicFilePickerValue('');
        setProfilePicFile(undefined);
    }

    const handleSignup = async (e: any) => {
        e.preventDefault();

        if (username.length < 3 || username.length > 32) {
            setErrorMessage('Username must be between 3 and 32 characters!');
            return;
        } else if (displayName.length > 32) {
            setErrorMessage("Display name can't be greater than 32 characters!");
            return;
        } else if (!username.match('^[a-zA-Z0-9._\-]{3,}$')) {
            setErrorMessage('Username can only contain alphanumeric characters or ". _ -"');
            return;
        }
        
        signup(username, password, displayName, bio)
        .then(async (res: TokenResponse) => {
            setErrorMessage('');
            document.cookie = 'token=' + res.token + '; path=/';

            if (profilePicFile) {
                await uploadFile(profilePicFile, 'profile_pic', res.token);
            }

            router.push('/home/timeline');
        })
        .catch((err) => {
            if (err.code === -22) {
                setErrorMessage('Username is taken!');
            } else {
                setErrorMessage('Signup failed! Please try again.');
            }
        });
    }

    return (
        <div className={styles.content}>
            <div className="md:flex h-screen items-center justify-center">
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
                    <form onSubmit={handleSignup}>
                        <center>
                            <label htmlFor="image-picker">
                                <div className="p-2">
                                    {profilePicFile ? (
                                        <Image 
                                            src={URL.createObjectURL(profilePicFile)}
                                            width="100"
                                            height="100"
                                            alt="Profile photo"
                                            className="p-1 border-2 border-blue-500 rounded-full"
                                        />
                                    ) : (
                                        <Image 
                                            src="/img/no_pfp.png"
                                            width="100"
                                            height="100"
                                            alt="Profile photo"
                                            className="p-1 border-2 border-blue-500 rounded-full"
                                        />
                                    )}
    
                                    <p className="mt-3">Click to add your profile picture!</p>
                                </div>
                            </label>
                            <input
                                type="file"
                                value={profilePicFilePickerValue}
                                id="image-picker"
                                onChange={onProfilePicFileChange}
                                accept="image/png, image/jpeg, image/gif"
                                className="hidden"
                            />
                        </center>
                        <div className="md:flex max-w-2xl min-w-min">
                            <div className="p-2">
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
                            </div>
                            <div className="m-4 flex-none md:block sm:hidden">
                                <hr className="h-full bg-gray-200 w-0.5" />
                            </div>
                            <div className="p-2">
                                <input 
                                    type="text"
                                    value={displayName}
                                    onChange={(e) => setDisplayName(e.target.value)}
                                    placeholder="Display name..."
                                    required
                                    className="p-3 my-2 w-72 border-2 border-blue-200 rounded shadow-inner focus:outline-none focus-scale"
                                />
                                <br />
                                <input
                                    type="text" 
                                    value={bio}
                                    onChange={(e) => setBio(e.target.value)}
                                    placeholder="Bio..."
                                    required
                                    className="p-3 my-2 w-72 border-2 border-blue-200 rounded shadow-inner focus:outline-none focus-scale"
                                />
                            </div>
                        </div>
                        <button
                            type="submit"
                            className="px-4 py-2 mt-4 rounded bg-blue-500 hover:bg-blue-600 text-white"
                        >
                            Sign up
                        </button>
                    </form>
                    <div className="mt-5">
                        <span className="text-sm">Already have an account? </span>
                        <Link 
                            href="/login"
                            className="text-blue-500 underline text-sm"
                        >
                            Log in!
                        </Link>
                    </div>
                </div>
            </div>
        </div>
    )
}