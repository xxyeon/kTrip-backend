import React, { useEffect, useState } from 'react';
import api from './api';

const Mypage = () => {
    const [userInfo, setUserInfo] = useState({});
    const [reviews, setReviews] = useState([]);
    const [favorites, setFavorites] = useState([]);

    useEffect(() => {
        const fetchData = async () => {
            try {
                const userResponse = await api.get('/mypage');
                setUserInfo(userResponse.data);

                const reviewsResponse = await api.get('/mypage/review');
                setReviews(reviewsResponse.data);

                const favoritesResponse = await api.get('/mypage/favorite');
                setFavorites(favoritesResponse.data);
            } catch (error) {
                console.error('Failed to fetch data', error);
                // Handle fetch error (e.g., redirect to login page if unauthorized)
            }
        };
        fetchData();
    }, []);

    return (
        <div>
            <h1>My Page</h1>
            <h2>User Info</h2>
            <p>Name: {userInfo.name}</p>
            <p>Nickname: {userInfo.nickname}</p>
            <p>Email: {userInfo.email}</p>

            <h2>Reviews</h2>
            <ul>
                {reviews.map(review => (
                    <li key={review.id}>{review.content}</li>
                ))}
            </ul>

            <h2>Favorites</h2>
            <ul>
                {favorites.map(favorite => (
                    <li key={favorite.id}>{favorite.name}</li>
                ))}
            </ul>
        </div>
    );
};

export default Mypage;