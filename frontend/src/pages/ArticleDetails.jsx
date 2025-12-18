import { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import api from "../api/apiClient";
import Navbar from "../components/Navbar";
import { useAuth } from "../auth/useAuth";

export default function ArticleDetails() {
  const { id } = useParams();
  const { user } = useAuth();
  const [article, setArticle] = useState(null);
  const [comments, setComments] = useState([]);
  const [commentText, setCommentText] = useState("");
  const [loading, setLoading] = useState(true);
  const navigate = useNavigate();

  // Fetch article and comments
  useEffect(() => {
    const fetchData = async () => {
      try {
        const articleRes = await api.get(`/api/articles/${id}`);
        setArticle(articleRes.data);

        const commentsRes = await api.get(`/api/comments/by-article/${id}`);
        const commentsData = commentsRes.data;

        // Fetch username for each comment
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

  // Add comment
  const addComment = async () => {
    if (!commentText.trim()) return;

    try {
      await api.post("/api/comments", {
        articleId: Number(id),
        commentorId: Number(user.id),
        content: commentText,
      });

      // Refresh comments after adding
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

  // Delete article
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

  // Edit article
  const editArticle = () => navigate(`/edit-article/${id}`);

  // Delete comment
  const deleteComment = async (commentId) => {
    if (!window.confirm("Delete this comment?")) return;

    try {
      await api.delete(`/api/comments/${commentId}`, {
        headers: { "X-Auth-User-Id": Number(user.id) },
      });

      // Refresh comments
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
      console.error("Failed to delete comment:", err);
    }
  };

  // Edit comment
  const editComment = (commentId) => navigate(`/edit-comment/${commentId}`);

  if (loading) return <p>Loading article...</p>;
  if (!article) return <p>Article not found.</p>;

  return (
    <div>
      <Navbar />
      <h2>{article.title}</h2>
      <p>{article.content}</p>

      {user && Number(user.id) === Number(article.authorId) && (
        <div>
          <button onClick={editArticle}>Edit Article</button>
          <button onClick={deleteArticle}>Delete Article</button>
        </div>
      )}

      <h3>Comments</h3>
      {comments.length === 0 && <p>No comments yet.</p>}

      {comments.map((c) => (
        <div
          key={c.id}
          style={{ borderTop: "1px solid #ccc", padding: "5px 0" }}
        >
          <p>
            <strong>{c.commentorName}</strong>: {c.content}
          </p>

          {user && Number(user.id) === Number(c.commentorId) && (
            <div>
              <button onClick={() => editComment(c.id)}>Edit</button>
              <button onClick={() => deleteComment(c.id)}>Delete</button>
            </div>
          )}
        </div>
      ))}

      <div style={{ marginTop: "10px" }}>
        <input
          placeholder="Add comment"
          value={commentText}
          onChange={(e) => setCommentText(e.target.value)}
        />
        <button onClick={addComment}>Comment</button>
      </div>
    </div>
  );
}
