import { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import api from "../api/apiClient";
import Navbar from "../components/Navbar";
import { useAuth } from "../auth/useAuth";

export default function EditComment() {
  const { id } = useParams(); // comment id
  const { user } = useAuth();
  const navigate = useNavigate();

  const [comment, setComment] = useState(null);
  const [content, setContent] = useState("");
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchComment = async () => {
      try {
        const res = await api.get(`/api/comments/${id}`);
        setComment(res.data);
        setContent(res.data.content);
      } catch (err) {
        console.error("Failed to fetch comment:", err);
      } finally {
        setLoading(false);
      }
    };

    fetchComment();
  }, [id]);

  const updateComment = async () => {
    try {
      await api.put(
        `/api/comments/${id}`,
        {
          articleId: comment.articleId,       // important!
          commentorId: comment.commentorId,   // important!
          content: content,
        },
        {
          headers: { "X-Auth-User-Id": Number(user.id) },
        }
      );
      navigate(`/articles/${comment.articleId}`);
    } catch (err) {
      console.error("Failed to update comment:", err);
    }
  };

  if (loading) return <p>Loading comment...</p>;
  if (!comment) return <p>Comment not found.</p>;

  return (
    <div>
      <Navbar />
      <h2>Edit Comment</h2>
      <textarea
        value={content}
        onChange={(e) => setContent(e.target.value)}
      />
      <button onClick={updateComment}>Save</button>
    </div>
  );
}
