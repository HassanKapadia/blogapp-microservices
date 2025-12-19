import { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import api from "../api/apiClient";
import Navbar from "../components/Navbar";
import { useAuth } from "../auth/useAuth";
import { formatDateTime } from "../utils/dateUtils";

export default function ArticleDetails() {
  const { id } = useParams();
  const { user } = useAuth();
  const [article, setArticle] = useState(null);
  const [authorName, setAuthorName] = useState("");
  const [comments, setComments] = useState([]);
  const [commentText, setCommentText] = useState("");
  const [loading, setLoading] = useState(true);
  const navigate = useNavigate();

  useEffect(() => {
    const fetchData = async () => {
      try {
        const articleRes = await api.get(`/api/articles/${id}`);
        const articleData = articleRes.data;
        setArticle(articleData);

        try {
          const authorRes = await api.get(`/api/users/${articleData.authorId}`);
          setAuthorName(authorRes.data.name);
        } catch {
          setAuthorName("Unknown");
        }

        const commentsRes = await api.get(`/api/comments/by-article/${id}`);
        const commentsData = commentsRes.data;

        const commentsWithUser = await Promise.all(
          commentsData.map(async (c) => {
            try {
              const userRes = await api.get(`/api/users/${c.commentorId}`);
              return { ...c, commentorName: userRes.data.name };
            } catch {
              return { ...c, commentorName: "User" };
            }
          })
        );

        setComments(commentsWithUser);
      } catch (err) {
        console.error("Failed to fetch article or comments:", err);
      } finally {
        setLoading(false);
      }
    };

    fetchData();
  }, [id]);

  const addComment = async () => {
    if (!commentText.trim()) return;

    try {
      await api.post("/api/comments", {
        articleId: Number(id),
        commentorId: Number(user.id),
        content: commentText,
      });

      const commentsRes = await api.get(`/api/comments/by-article/${id}`);
      const commentsData = commentsRes.data;

      const commentsWithUser = await Promise.all(
        commentsData.map(async (c) => {
          try {
            const userRes = await api.get(`/api/users/${c.commentorId}`);
            return { ...c, commentorName: userRes.data.name };
          } catch {
            return { ...c, commentorName: "User" };
          }
        })
      );

      setComments(commentsWithUser);
      setCommentText("");
    } catch (err) {
      console.error("Failed to add comment:", err);
    }
  };

  const deleteArticle = async () => {
    if (!window.confirm("Are you sure you want to delete this article?")) return;

    try {
      await api.delete(`/api/articles/${id}`, {
        headers: { "X-Auth-User-Id": Number(user.id) },
      });
      navigate("/");
    } catch (err) {
      console.error("Failed to delete article:", err);
    }
  };

  const editArticle = () => navigate(`/edit-article/${id}`);
  const editComment = (commentId) => navigate(`/edit-comment/${commentId}`);

  const deleteComment = async (commentId) => {
    if (!window.confirm("Delete this comment?")) return;

    try {
      await api.delete(`/api/comments/${commentId}`, {
        headers: { "X-Auth-User-Id": Number(user.id) },
      });

      setComments((prev) => prev.filter((c) => c.id !== commentId));
    } catch (err) {
      console.error("Failed to delete comment:", err);
    }
  };

  if (loading) return <p>Loading article...</p>;
  if (!article) return <p>Article not found.</p>;

  const createdAt = formatDateTime(article.createdOn);
  const updatedAt = article.updatedOn
    ? formatDateTime(article.updatedOn)
    : null;

  return (
    <div>
      <Navbar />

      <h2>{article.title}</h2>

      <p style={{ fontSize: "0.9rem", color: "#666" }}>
        By <strong>{authorName}</strong>
      </p>

      <p style={{ fontSize: "0.85rem", color: "#666" }}>
        Created: {createdAt}
        {updatedAt && updatedAt !== createdAt && (
          <> • Updated: {updatedAt}</>
        )}
      </p>

      <p>{article.content}</p>

      {user && Number(user.id) === Number(article.authorId) && (
        <div style={{ marginBottom: "15px" }}>
          <button onClick={editArticle}>Edit Article</button>{" "}
          <button onClick={deleteArticle}>Delete Article</button>
        </div>
      )}

      <h3>Comments</h3>

      {comments.length === 0 && <p>No comments yet.</p>}

      {comments.map((c) => {
        const commentCreated = formatDateTime(c.createdOn);

        return (
          <div
            key={c.id}
            style={{ borderTop: "1px solid #ddd", padding: "10px 0" }}
          >
            <p style={{ marginBottom: "4px" }}>
              <strong>{c.commentorName}</strong>{" "}
              <span style={{ fontSize: "0.8rem", color: "#666" }}>
                • {commentCreated}
              </span>
            </p>

            <p>{c.content}</p>

            {c.updatedOn && c.updatedOn !== c.createdOn && (
              <span
                title={`Edited on ${formatDateTime(c.updatedOn)}`}
                style={{
                  display: "inline-block",
                  marginTop: "4px",
                  padding: "2px 6px",
                  fontSize: "0.7rem",
                  fontWeight: "500",
                  color: "#1f2937",
                  backgroundColor: "#e2e8f0",
                  borderRadius: "10px",
                }}
              >
                Edited
              </span>
            )}

            {user && Number(user.id) === Number(c.commentorId) && (
              <div>
                <button onClick={() => editComment(c.id)}>Edit</button>{" "}
                <button onClick={() => deleteComment(c.id)}>Delete</button>
              </div>
            )}
          </div>
        );
      })}

      {user && (
        <div style={{ marginTop: "12px" }}>
          <input
            placeholder="Add comment"
            value={commentText}
            onChange={(e) => setCommentText(e.target.value)}
            style={{ width: "70%", padding: "6px" }}
          />
          <button onClick={addComment} style={{ marginLeft: "6px" }}>
            Comment
          </button>
        </div>
      )}
    </div>
  );
}
