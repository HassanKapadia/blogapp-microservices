import { useEffect, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import api from "../api/apiClient";
import Navbar from "../components/Navbar";
import { useAuth } from "../auth/useAuth";
import { formatDateTime } from "../utils/dateUtils";

export default function Articles() {
  const [articles, setArticles] = useState([]);
  const { user } = useAuth();
  const navigate = useNavigate();

  // Fetch articles
  useEffect(() => {
    const fetchArticles = async () => {
      try {
        const res = await api.get("/api/articles");
        setArticles(res.data);
      } catch (err) {
        console.error("Failed to fetch articles:", err);
      }
    };

    fetchArticles();
  }, []);

  // Delete article
  const deleteArticle = async (id) => {
    if (!window.confirm("Are you sure you want to delete this article?")) return;

    try {
      await api.delete(`/api/articles/${id}`, {
        headers: { "X-Auth-User-Id": user.id },
      });
      setArticles((prev) => prev.filter((a) => a.id !== id));
    } catch (err) {
      console.error("Failed to delete article:", err);
    }
  };

  // Navigate to edit article
  const editArticle = (id) => {
    navigate(`/edit-article/${id}`);
  };

  return (
    <div>
      <Navbar />
      <h2>Articles</h2>

      {articles.length === 0 && <p>No articles found.</p>}

      {articles.map((a) => (
        <div
          key={a.id}
          style={{
            border: "1px solid #ddd",
            padding: "12px",
            marginBottom: "12px",
            borderRadius: "6px",
            background: "#fff",
          }}
        >
          <h3>
            <Link to={`/articles/${a.id}`}>{a.title}</Link>
          </h3>

          {/* Only Created date shown (Updated hidden on list view) */}
          <p style={{ fontSize: "0.85rem", color: "#666" }}>
            Created: {formatDateTime(a.createdOn)}
          </p>

          {user && user.id === a.authorId && (
            <div style={{ marginTop: "6px" }}>
              <button onClick={() => editArticle(a.id)}>Edit</button>{" "}
              <button onClick={() => deleteArticle(a.id)}>Delete</button>
            </div>
          )}

          <div style={{ marginTop: "6px" }}>
            <Link to={`/articles/${a.id}`}>View Details / Comments</Link>
          </div>
        </div>
      ))}
    </div>
  );
}
