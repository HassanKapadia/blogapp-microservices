import { BrowserRouter, Routes, Route } from "react-router-dom";
import AuthProvider from "./auth/AuthProvider";
import ProtectedRoute from "./auth/ProtectedRoute";

import Login from "./pages/Login";
import Signup from "./pages/Signup";
import Articles from "./pages/Articles";
import ArticleDetails from "./pages/ArticleDetails";
import CreateArticle from "./pages/CreateArticle";
import EditArticle from "./pages/EditArticle";
import EditComment from "./pages/EditComment";
import Account from "./pages/Account";

export default function App() {
  return (
    <AuthProvider>
      <BrowserRouter>
        <Routes>
          {/* Public routes */}
          <Route path="/login" element={<Login />} />
          <Route path="/signup" element={<Signup />} />

          {/* Protected routes */}
          <Route
            path="/"
            element={
              <ProtectedRoute>
                <Articles />
              </ProtectedRoute>
            }
          />

          <Route
            path="/articles/:id"
            element={
              <ProtectedRoute>
                <ArticleDetails />
              </ProtectedRoute>
            }
          />

          <Route
            path="/article/new"
            element={
              <ProtectedRoute>
                <CreateArticle />
              </ProtectedRoute>
            }
          />

          <Route
            path="/edit-article/:id"
            element={
              <ProtectedRoute>
                <EditArticle />
              </ProtectedRoute>
            }
          />

          <Route
            path="/edit-comment/:id"
            element={
              <ProtectedRoute>
                <EditComment />
              </ProtectedRoute>
            }
          />

          <Route
            path="/account"
            element={
              <ProtectedRoute>
                <Account />
              </ProtectedRoute>
            }
          />
        </Routes>
      </BrowserRouter>
    </AuthProvider>
  );
}
