import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import { Provider } from 'react-redux';
import { store } from './redux/store';
import Navigation from './components/Navigation';
import LandingPage from './components/LandingPage';
import SignUp from './components/SignUp';
import LogIn from './components/LogIn';
import Dashboard from './components/Dashboard';
import SearchExplore from './components/SearchExplore';
import CreateRequest from './components/CreateRequest';
import RequestDetail from './components/RequestDetail';
import Messaging from './components/Messaging';
import ProfileManagement from './components/ProfileManagement';
import TransactionHistory from './components/TransactionHistory';
import NotificationCenter from './components/NotificationCenter';
import FeedbackSupport from './components/FeedbackSupport';

function App() {
  return (
    <Provider store={store}>
      <Router>
        <div className="min-h-screen bg-gray-900 text-white">
          <Navigation />
          <Routes>
            <Route path="/" element={<LandingPage />} />
            <Route path="/signup" element={<SignUp />} />
            <Route path="/login" element={<LogIn />} />
            <Route path="/dashboard" element={<Dashboard />} />
            <Route path="/search" element={<SearchExplore />} />
            <Route path="/create-request" element={<CreateRequest />} />
            <Route path="/request/:id" element={<RequestDetail />} />
            <Route path="/messages" element={<Messaging />} />
            <Route path="/profile" element={<ProfileManagement />} />
            <Route path="/transactions" element={<TransactionHistory />} />
            <Route path="/notifications" element={<NotificationCenter />} />
            <Route path="/feedback" element={<FeedbackSupport />} />
          </Routes>
        </div>
      </Router>
    </Provider>
  );
}

export default App;