import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import api from "../api/apiClient";
import Navbar from "../components/Navbar";
import { useAuth } from "../auth/useAuth";

export default function EditArticle() {
  const { id } = useParams();
  const navigate = useNavigate();
  const { user } = useAuth();

  const [title, setTitle] = useState("");
  const [content, setContent] = useState("");

  useEffect(() => {
    api.get(`/api/articles/${id}`).then(res => {
      setTitle(res.data.title);
      setContent(res.data.content);
    });
  }, [id]);

  const submit = async () => {
    await api.put(`/api/articles/${id}`, {
      title,
      content,
      authorId: user.userId,
    });

    navigate(`/articles/${id}`);
  };

  return (
    <div>
      <Navbar />
      <h2>Edit Article</h2>

      <input
        value={title}
        onChange={e => setTitle(e.target.value)}
        placeholder="Title"
      />

      <textarea
        value={content}
        onChange={e => setContent(e.target.value)}
        placeholder="Content"
      />

      <button onClick={submit}>Update</button>
    </div>
  );
}
