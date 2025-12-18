import { useEffect, useState } from "react";
import api from "../api/apiClient";
import { useAuth } from "../auth/useAuth";
import Navbar from "../components/Navbar";

export default function Account() {
  const { user, token } = useAuth();
  const [form, setForm] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    if (!user?.id || !token) return;

    let cancelled = false;

    api.get(`/api/users/${user.id}`)
      .then(res => {
        if (!cancelled) {
          setForm({
            name: res.data.name || "",
            username: res.data.username || "",
            email: res.data.email || "",
            bio: res.data.bio || "",
          });
          setLoading(false);
        }
      })
      .catch(() => setLoading(false));

    return () => {
      cancelled = true;
    };
  }, [user?.id, token]);

  const updateAccount = async () => {
    try {
      await api.put(
        `/api/users/${user.id}`,
        { name: form.name, bio: form.bio },
        { headers: { "X-Auth-User-Id": user.id } }
      );

      alert("Account updated successfully!");
    } catch (err) {
      console.error("Failed to update account:", err);
      alert("Update failed");
    }
  };

  if (loading || !form) {
    return (
      <div>
        <Navbar />
        <p>Loading account...</p>
      </div>
    );
  }

  return (
    <div>
      <Navbar />
      <h2>My Account</h2>

      <input value={form.username} disabled />
      <input value={form.email} disabled />

      <input
        placeholder="Name"
        value={form.name}
        onChange={e => setForm({ ...form, name: e.target.value })}
      />

      <textarea
        placeholder="Bio"
        value={form.bio}
        onChange={e => setForm({ ...form, bio: e.target.value })}
      />

      <button onClick={updateAccount}>Update Account</button>
    </div>
  );
}
