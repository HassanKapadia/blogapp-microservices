import { useState } from "react";
import api from "../api/apiClient";
import { useNavigate } from "react-router-dom";
import Navbar from "../components/Navbar";
import { useAuth } from "../auth/useAuth";

export default function CreateArticle() {
  const [title, setTitle] = useState("");
  const [content, setContent] = useState("");
  const { user } = useAuth();
  const navigate = useNavigate();

  const submit = async () => {
	if (!user) return;
	
	await api.post("/api/articles", {
      title,
      content,
      authorId: user.id
    });

    navigate("/");
  };

  return (
    <div>
      <Navbar />
      <h2>Create Article</h2>

      <input placeholder="Title" onChange={e => setTitle(e.target.value)} />
      <textarea placeholder="Content" onChange={e => setContent(e.target.value)} />
      <button onClick={submit} disabled={!user}>Create</button>
    </div>
  );
}
