import React, { useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";

export default function Login() {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const nav = useNavigate();

  const submit = async (e) => {
    e.preventDefault();
    try {
      const res = await axios.post("/auth/login", { email, password });
      const token = res.data.token;
      localStorage.setItem("token", token);
      // redirect to users page (admin)
      nav("/admin/users");
    } catch (err) {
      alert(err.response?.data?.message || "Login failed");
    }
  };

  return (
    <div className="min-h-screen flex items-center justify-center bg-gradient-to-r from-sky-100 to-indigo-100">
      <form
        onSubmit={submit}
        className="bg-white p-8 rounded-2xl shadow-lg w-96"
      >
        <h2 className="text-2xl font-bold mb-6 text-center">Welcome back</h2>
        <label className="block text-sm">Email</label>
        <input
          value={email}
          onChange={(e) => setEmail(e.target.value)}
          className="w-full p-2 border rounded mt-1 mb-4"
        />
        <label className="block text-sm">Password</label>
        <input
          value={password}
          onChange={(e) => setPassword(e.target.value)}
          type="password"
          className="w-full p-2 border rounded mt-1 mb-6"
        />
        <button className="w-full py-2 rounded bg-indigo-600 text-white font-semibold">
          Sign in
        </button>
        <p className="mt-4 text-center text-sm">
          Don't have account?{" "}
          <a href="/register" className="text-indigo-600">
            Register
          </a>
        </p>
      </form>
    </div>
  );
}
