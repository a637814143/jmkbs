import axios from 'axios';

const client = axios.create({
  baseURL: '/api',
  timeout: 10000
});

client.interceptors.response.use(
  (response) => response,
  (error) => {
    if (error.response?.data?.message) {
      return Promise.reject(new Error(error.response.data.message));
    }
    if (error.response?.data?.error) {
      return Promise.reject(new Error(error.response.data.error));
    }
    return Promise.reject(error);
  }
);

export function registerUser(payload) {
  return client.post('/auth/register', payload).then((res) => res.data);
}

export function loginUser(payload) {
  return client.post('/auth/login', payload).then((res) => res.data);
}

export function fetchMovies(params) {
  return client.get('/movies', { params }).then((res) => res.data);
}

export function fetchMovieDetail(movieId) {
  return client.get(`/movies/${movieId}`).then((res) => res.data);
}

export function fetchMovieReviews(movieId, status) {
  return client
    .get(`/movies/${movieId}/reviews`, { params: status ? { status } : undefined })
    .then((res) => res.data);
}

export function createReview(movieId, payload) {
  return client.post(`/movies/${movieId}/reviews`, payload).then((res) => res.data);
}

export function likeReview(reviewId, userId) {
  return client.post(`/reviews/${reviewId}/like`, { userId }).then((res) => res.data);
}

export function unlikeReview(reviewId, userId) {
  return client
    .delete(`/reviews/${reviewId}/like`, { data: { userId } })
    .then((res) => res.data);
}

export function reportTarget(payload) {
  return client.post('/reports', payload).then((res) => res.data);
}

export function fetchUserProfile(userId) {
  return client.get(`/users/${userId}`).then((res) => res.data);
}

export function fetchUserReviews(userId) {
  return client.get(`/users/${userId}/reviews`).then((res) => res.data);
}

export function exportUserData(userId) {
  return client.get(`/users/${userId}/export`).then((res) => res.data);
}

export function deleteUser(userId) {
  return client.delete(`/users/${userId}`).then((res) => res.data);
}

export function applyAdAccount(payload) {
  return client.post('/ads/accounts', payload).then((res) => res.data);
}

export function fetchAdAccountByUser(userId) {
  return client.get(`/ads/accounts/by-user/${userId}`).then((res) => res.data);
}

export function rechargeAdAccount(accountId, payload) {
  return client.post(`/ads/accounts/${accountId}/recharge`, payload).then((res) => res.data);
}

export function createCampaign(payload) {
  return client.post('/ads/campaigns', payload).then((res) => res.data);
}

export function listCampaigns(accountId) {
  return client.get(`/ads/accounts/${accountId}/campaigns`).then((res) => res.data);
}

export function createCreative(payload) {
  return client.post('/ads/creatives', payload).then((res) => res.data);
}

export function listCreatives(accountId) {
  return client.get(`/ads/accounts/${accountId}/creatives`).then((res) => res.data);
}

export function submitCreative(creativeId) {
  return client.post(`/ads/creatives/${creativeId}/submit`).then((res) => res.data);
}

export function activateCreative(creativeId) {
  return client.post(`/ads/creatives/${creativeId}/activate`).then((res) => res.data);
}

export function fetchAdDashboard(accountId) {
  return client.get(`/ads/accounts/${accountId}/dashboard`).then((res) => res.data);
}

export function settleAdAccount(accountId, payload) {
  return client.post(`/ads/accounts/${accountId}/settle`, payload).then((res) => res.data);
}

export function adminDashboard() {
  return client.get('/admin/dashboard').then((res) => res.data);
}

export function adminCreateMovie(payload) {
  return client.post('/admin/movies', payload).then((res) => res.data);
}

export function adminUpdateMovie(movieId, payload) {
  return client.post(`/admin/movies/${movieId}`, payload).then((res) => res.data);
}

export function adminDeleteMovie(movieId) {
  return client.post(`/admin/movies/${movieId}/delete`).then((res) => res.data);
}

export function adminPendingReviews() {
  return client.get('/admin/reviews/pending').then((res) => res.data);
}

export function adminModerateReview(reviewId, action, payload) {
  return client.post(`/admin/reviews/${reviewId}/${action}`, payload).then((res) => res.data);
}

export function adminPendingReports() {
  return client.get('/admin/reports/pending').then((res) => res.data);
}

export function adminResolveReport(reportId, payload) {
  return client.post(`/admin/reports/${reportId}/resolve`, payload).then((res) => res.data);
}

export function adminPendingCreatives() {
  return client.get('/admin/creatives/pending').then((res) => res.data);
}

export function adminReviewCreative(creativeId, payload) {
  return client.post(`/admin/creatives/${creativeId}/decision`, payload).then((res) => res.data);
}

export function adminAdAccounts() {
  return client.get('/admin/accounts').then((res) => res.data);
}

export function adminUpdateAccountStatus(accountId, payload) {
  return client.post(`/admin/accounts/${accountId}/status`, payload).then((res) => res.data);
}
